package kr.kro.minestar.sacrificer.of.slayer.data.worlds

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Sacrificer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerData
import kr.kro.minestar.sacrificer.of.slayer.functions.UtilityClass.effect
import kr.kro.minestar.sacrificer.of.slayer.functions.SoundClass
import kr.kro.minestar.sacrificer.of.slayer.functions.WorldClass
import kr.kro.minestar.utility.event.disable
import kr.kro.minestar.utility.event.enable
import kr.kro.minestar.utility.scheduler.Scheduler
import kr.kro.minestar.utility.scheduler.objectes.later.RunTitle
import kr.kro.minestar.utility.scheduler.objectes.now.RunNow
import kr.kro.minestar.utility.string.removeUnderBar
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.player.PlayerGameModeChangeEvent
import org.bukkit.potion.PotionEffectType
import org.bukkit.scoreboard.Team

class GameWorld(world: World, private val worldName: String) : WorldData(world) {
    init {
        enable(pl)
        WorldClass.startGame(this)
        gameReady()
    }

    val team = Bukkit.getScoreboardManager().mainScoreboard.getTeam("player")
        ?: Bukkit.getScoreboardManager().mainScoreboard.registerNewTeam("player").apply {
            setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.NEVER)
            setCanSeeFriendlyInvisibles(false)
        }

    /**
    Game function
     */
    private fun gameReady() {

        for (player in allPlayers()) {
            teleportToStartLocation(player)
            inventoryClear(player)
        }

        val scheduler = Scheduler(pl)
        scheduler.addRun(RunNow { SoundClass.gameWorldEnter.play(worldPlayers()) })
        scheduler.addRun(RunTitle(worldPlayers(), " ", "§c${WorldClass.readUnicode(worldName).removeUnderBar()}", 5, 60, 5, 20))
        scheduler.addRun(RunTitle(worldPlayers(), " ", "§c30초 후 각자의 역할이 지정됩니다", 5, 40, 5, 20))
        var countDown = 3
        scheduler.addRun(RunNow { for (player in worldPlayers()) player.addPotionEffect(PotionEffectType.SPEED.effect(countDown, 2)) })
        scheduler.addRun(RunTitle(worldPlayers(), " ", "§c$countDown", 5, 16, 0, -1))
        while (countDown > 1) {
            --countDown
            scheduler.addRun(RunTitle(worldPlayers(), " ", "§c$countDown", 0, 21, 0, -1))
        }
        scheduler.addRun(RunNow { gameStart() })

        scheduler.play()
    }

    private fun gameStart() {
        setCreature()
        tickTaskRun()
    }

    private fun setCreature() {
        val livePlayer = mutableListOf<Player>()
        for (player in worldPlayers()) if (player.gameMode == GameMode.ADVENTURE) livePlayer.add(player)
        livePlayer.shuffle()

        for (player in livePlayer) {
            player.inventory.heldItemSlot = 4
            val creature = if (player == livePlayer.first()) PlayerData.randomSlayer(player, this)
            else PlayerData.randomSacrificer(player, this)
            addPlayerData(creature)
        }
    }

    /**
     * Game finish function
     */
    var finish = false

    fun checkFinish() {
        var slayer = 0
        var sacrificer = 0
        for (creature in playerDataMap.values) {
            if (!creature.player.isOnline) continue
            if (creature.player.world != world) continue
            if (creature.player.gameMode != GameMode.ADVENTURE) continue
            if (creature.creature is Slayer) slayer++
            else if (creature.creature is Sacrificer) sacrificer++
        }
        if (slayer == 0) sacrificerWin()
        else if (sacrificer == 0) slayerWin()
    }

    private fun slayerWin() {
        if (finish) return
        finish = true
        val scheduler = Scheduler(pl)
        scheduler.addRun(RunNow { SoundClass.slayerWinMusic(worldPlayers()) })
        scheduler.addRun(RunTitle(worldPlayers(), "§cSlayer", "§eWon", 5, 40, 5, 15))
        scheduler.addRun(RunTitle(worldPlayers(), " ", "§a잠시 후 오버월드로 이동합니다", 5, 50, 5, 20 * 3))
        scheduler.addRun(RunNow { gameFinishing() })

        scheduler.play()
    }

    private fun sacrificerWin() {
        if (finish) return
        finish = true
        val scheduler = Scheduler(pl)
        scheduler.addRun(RunNow { SoundClass.slayerWinMusic(worldPlayers()) })
        scheduler.addRun(RunTitle(worldPlayers(), "§9Sacrificer", "§eWon", 5, 40, 5, 15))
        scheduler.addRun(RunTitle(worldPlayers(), " ", "§a잠시 후 오버월드로 이동합니다", 5, 50, 5, 20 * 3))
        scheduler.addRun(RunNow { gameFinishing() })

        scheduler.play()
    }

    private fun gameFinishing() {
        val world = Bukkit.getWorlds().first()
        for (player in worldPlayers()) {
            player.gameMode = GameMode.ADVENTURE
            for (potionEffect in player.activePotionEffects) player.removePotionEffect(potionEffect.type)
            player.inventory.clear()
            player.teleport(world.spawnLocation)
        }
        disable()
        tickTaskCancel()
        WorldClass.finishGame(this)
    }

    /**
     * Lock event
     */
    @EventHandler
    private fun changeGameModeLock(e: PlayerGameModeChangeEvent) {
        if (e.player.world != world) return
        e.isCancelled = true
        if (e.newGameMode == GameMode.SURVIVAL) return
        if (e.newGameMode == GameMode.CREATIVE) return
        e.isCancelled = false
    }
}