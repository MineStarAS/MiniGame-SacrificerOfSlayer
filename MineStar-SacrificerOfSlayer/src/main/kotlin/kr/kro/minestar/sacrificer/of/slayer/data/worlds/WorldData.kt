package kr.kro.minestar.sacrificer.of.slayer.data.worlds

import kr.kro.minestar.sacrificer.of.slayer.Main
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import kr.kro.minestar.sacrificer.of.slayer.functions.WorldClass
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.scheduler.BukkitTask
import java.io.File

@Suppress("DEPRECATION")
abstract class WorldData(internal val world: World) : Listener {
    protected var folder = File("${WorldClass.worldFolder}/${world.name}")

    protected fun startLocation() = world.spawnLocation.clone()


    /**
     * Creature function
     */
    protected val creatureMap = hashMapOf<Player, PlayerCreature>()
    protected fun addCreature(creature: PlayerCreature): Boolean {
        if (creatureMap.contains(creature.player)) return false
        creatureMap[creature.player] = creature
        return true
    }

    protected fun getCreature(player: Player) = creatureMap[player]
    fun getCreatures() = creatureMap.values

    /**
     * Event function
     */
    protected open fun attack(e: EntityDamageByEntityEvent) {
        val target = e.entity
        val attacker = e.damager

        if (target !is Player || attacker !is Player) {
            e.isCancelled = true
            return
        }

        val playerCreature = getCreature(attacker) ?: return
        val creature = playerCreature.creature
        creature.weapon?.hit(e)
    }

    protected open fun active(e: PlayerSwapHandItemsEvent) {
        if (e.player.gameMode != GameMode.ADVENTURE) return
        e.isCancelled = true

        val player = e.player
        val playerCreature = getCreature(player) ?: return

        playerCreature.useActiveSkill()
    }

    protected open fun useTool(e: PlayerInteractEvent) {
        if (e.player.gameMode != GameMode.ADVENTURE) return
        if (!e.action.isRightClick) return
        val player = e.player
        val playerCreature = getCreature(player) ?: return
        playerCreature.useTool(e)
    }


    /**
     * Task function
     */
    private var tickTask: BukkitTask? = null
    protected fun tickTaskRun() {
        tickTaskCancel()
        tickTask = Bukkit.getScheduler().runTaskTimer(Main.pl, Runnable {
            for (creature in creatureMap.values) {
                creature.removeActiveCoolTime()
                creature.passiveActivation()
                if(this is GameWorld) checkFinish()
            }
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
        player.teleport(startLocation())
        player.health = player.maxHealth
        player.gameMode = GameMode.ADVENTURE
    }

    protected fun inventoryClear(player: Player) {
        val inventory = player.inventory
        inventory.clear()
    }
}