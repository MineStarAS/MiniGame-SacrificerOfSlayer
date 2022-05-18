package kr.kro.minestar.sacrificer.of.slayer.data.worlds

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Sacrificer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
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
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.player.PlayerGameModeChangeEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class GameWorld(world: World, private val worldName: String) : WorldData(world) {
    init {
        enable(pl)
        WorldClass.startGame(this)
        gameReady()
    }


    private fun allPlayers() = Bukkit.getOnlinePlayers()
    private val worldPlayers = world.players

    /**
    Game function
     */
    private fun gameReady() {

        for (player in allPlayers()) {
            teleportToStartLocation(player)
            inventoryClear(player)
        }

        val scheduler = Scheduler(pl)
        scheduler.addRun(RunNow { SoundClass.gameWorldEnter.play(worldPlayers) })
        scheduler.addRun(RunTitle(worldPlayers, " ", "§c${WorldClass.readUnicode(worldName).removeUnderBar()}", 5, 60, 5, 20))
        scheduler.addRun(RunTitle(worldPlayers, " ", "§c30초 후 각자의 역할이 지정됩니다", 5, 40, 5, 20))
        var countDown = 30
        scheduler.addRun(RunTitle(worldPlayers, " ", "§c$countDown", 5, 15, 0, 0))
        while (countDown > 0) {
            --countDown
            scheduler.addRun(RunTitle(worldPlayers, " ", "§c$countDown", 0, 20, 0, 0))
        }
        scheduler.addRun(RunNow { gameStart() })

        scheduler.play()
    }

    private fun gameStart() {
        val scheduler = Scheduler(pl)
        setCreature()
        tickTaskRun()
    }

    private fun setCreature() {
        val livePlayer = mutableListOf<Player>()
        for (player in worldPlayers) if (player.gameMode == GameMode.ADVENTURE) livePlayer.add(player)
        livePlayer.shuffle()
        for (player in livePlayer) {
            val creature = if (player == livePlayer.first()) PlayerCreature.randomSlayer(player, this)
            else PlayerCreature.randomSacrificer(player, this)
            addCreature(creature)
        }
    }

    /**
     * Game finish function
     */
    fun checkFinish() {
        var slayer = 0
        var sacrificer = 0
        for (creature in creatureMap.values) {
            if (creature.player.gameMode != GameMode.ADVENTURE) continue
            if (creature.creature is Slayer) slayer++
            else if (creature.creature is Sacrificer) sacrificer++
        }
        if (slayer == 0) sacrificerWin()
        else if (sacrificer == 0) slayerWin()
    }

    private fun slayerWin() {

        val scheduler = Scheduler(pl)
        scheduler.addRun(RunNow { SoundClass.slayerWinMusic(worldPlayers) })
        scheduler.addRun(RunTitle(worldPlayers, "§cSlayer", "§eWon", 5, 40, 5, 15))
        scheduler.addRun(RunTitle(worldPlayers, " ", "§a잠시 후 오버월드로 이동합니다", 5, 50, 5, 20 * 3))

        scheduler.play()

        gameFinishing()
    }

    private fun sacrificerWin() {
        SoundClass.slayerWinMusic(worldPlayers)
        gameFinishing()
    }

    private fun gameFinishing() {
        val world = Bukkit.getWorlds().first()
        for (player in worldPlayers) {
            player.gameMode = GameMode.ADVENTURE
            for (potionEffect in player.activePotionEffects) player.removePotionEffect(potionEffect.type)
            player.teleport(world.spawnLocation)
        }
        disable()
        tickTaskCancel()
        WorldClass.finishGame(this)
    }


    /**
     * Event function
     */
    @EventHandler
    override fun attack(e: EntityDamageByEntityEvent) {
        if (e.entity.world != world) return
        super.attack(e)
    }

    @EventHandler
    override fun active(e: PlayerSwapHandItemsEvent) {
        if (e.player.world != world) return
        super.active(e)
    }

    @EventHandler
    override fun useTool(e: PlayerInteractEvent) {
        if (e.player.world != world) return
        super.useTool(e)
    }

    /**
     * Lock event
     */
    @EventHandler
    private fun changeGameModeLock(e: PlayerGameModeChangeEvent) {
        if (e.player.world != world) return
        if (e.newGameMode == GameMode.SURVIVAL) return
        if (e.newGameMode == GameMode.CREATIVE) return
        e.isCancelled = true
    }

    @EventHandler
    private fun foodLevelChangeLock(e: FoodLevelChangeEvent) {
        if (e.entity.world != world) return
        e.isCancelled = true
    }
}