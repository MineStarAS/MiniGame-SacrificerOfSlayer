package kr.kro.minestar.sacrificer.of.slayer.data.worlds

import kr.kro.minestar.sacrificer.of.slayer.Main
import kr.kro.minestar.sacrificer.of.slayer.data.bossbar.SlayerHealthBar
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerData
import kr.kro.minestar.sacrificer.of.slayer.functions.WorldClass
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitTask
import java.io.File

@Suppress("DEPRECATION")
abstract class WorldData(final override val world: World) : WorldEvent {

    companion object {
        private val map = hashMapOf<World, WorldData>()

        fun get(world: World) = map[world]
    }

    init {
        map[world] = this
    }

    protected var folder = File("${WorldClass.worldFolder}/${world.name}")

    private fun startLocation() = world.spawnLocation.clone()

    fun allPlayers(): MutableCollection<out Player> = Bukkit.getOnlinePlayers()
    fun worldPlayers(): MutableList<Player> = world.players

    var slayerHealthBar: SlayerHealthBar? = null

    /**
     * Creature function
     */
    override val playerDataMap = hashMapOf<Player, PlayerData>()

    /**
     * Task function
     */
    private var tickTask: BukkitTask? = null
    protected fun tickTaskRun() {
        tickTaskCancel()
        tickTask = Bukkit.getScheduler().runTaskTimer(Main.pl, Runnable {
            for (creature in playerDataMap.values) {
                if (creature.player.gameMode == GameMode.SPECTATOR) continue
                creature.removeActiveCoolTime()
                creature.passiveActivation(null)
            }
            if (this is GameWorld) if (!finish) checkFinish()
        }, 0, 1)
    }

    protected fun tickTaskCancel() {
        tickTask?.cancel()
        tickTask = null
    }

    /**
     * Other function
     */
    fun teleportToStartLocation(player: Player) {
        player.gameMode = GameMode.ADVENTURE
        player.teleport(startLocation())
        player.health = player.maxHealth
    }

    protected fun inventoryClear(player: Player) {
        val inventory = player.inventory
        inventory.clear()
    }
}