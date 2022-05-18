package kr.kro.minestar.sacrificer.of.slayer.data.worlds

import kr.kro.minestar.sacrificer.of.slayer.Main
import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import kr.kro.minestar.sacrificer.of.slayer.functions.WorldClass
import kr.kro.minestar.utility.event.enable
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import org.bukkit.scheduler.BukkitTask
import java.io.File

@Suppress("DEPRECATION")
abstract class WorldData(final override val world: World) : WorldEvent {

    protected var folder = File("${WorldClass.worldFolder}/${world.name}")

    private fun startLocation() = world.spawnLocation.clone()


    /**
     * Creature function
     */
    override val creatureMap = hashMapOf<Player, PlayerCreature>()

    /**
     * Event function
     */
    /*
    @EventHandler
    protected open fun attack(e: EntityDamageByEntityEvent) {
        if (e.entity.world != world) return
        e.isCancelled = true

        val target = e.entity
        val attacker = e.damager

        if (target !is Player || attacker !is Player) return

        val playerCreature = getCreature(attacker) ?: return
        val creature = playerCreature.creature
        creature.weapon?.hit(e)
    }

    @EventHandler
    protected open fun active(e: PlayerSwapHandItemsEvent) {
        if (e.player.world != world) return
        if (e.player.gameMode != GameMode.ADVENTURE) return
        e.isCancelled = true

        val player = e.player
        val playerCreature = getCreature(player) ?: return

        playerCreature.useActiveSkill()
    }

    @EventHandler
    protected open fun useTool(e: PlayerInteractEvent) {
        if (e.player.world != world) return
        if (e.player.gameMode != GameMode.ADVENTURE) return
        if (!e.action.isRightClick) return
        val player = e.player
        val playerCreature = getCreature(player) ?: return
        playerCreature.useTool(e)
    }

    @EventHandler
    protected open fun slayerDamaged(e: EntityDamageEvent) {
        if (e.entity.world != world) return
        if (e.entity !is Player) return

        val player = e.entity as Player
        val playerCreature = getCreature(player) ?: return
        if (playerCreature.creature !is Slayer) return

        if (e.cause != EntityDamageEvent.DamageCause.FALL) return
        e.damage = 1.0
    }

    @EventHandler
    protected open fun death(e: PlayerDeathEvent) {
        if (e.player.world != world) return
        val player = e.player
        e.isCancelled = true
        player.gameMode = GameMode.SPECTATOR
        player.health = player.maxHealth
        if (player.location.y < -64) player.teleport(world.spawnLocation)
    }

    /**
     * Passive trigger event
     */
    @EventHandler
    protected open fun damagedPassive(e: EntityDamageEvent) {
        if (e.entity.world != world) return
        if (e.entity !is Player) return

        val player = e.entity as Player
        val playerCreature = getCreature(player) ?: return
        playerCreature.passiveActivation(e)
    }*/

    /**
     * Task function
     */
    private var tickTask: BukkitTask? = null
    protected fun tickTaskRun() {
        tickTaskCancel()
        tickTask = Bukkit.getScheduler().runTaskTimer(Main.pl, Runnable {
            for (creature in creatureMap.values) {
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