package kr.kro.minestar.sacrificer.of.slayer.data.worlds

import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
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

interface WorldEvent: Listener {
    val world: World

    /**
     * Creature function
     */

    val creatureMap : HashMap<Player, PlayerCreature>
    fun addCreature(creature: PlayerCreature): Boolean {
        if (this is GameWorld) if (creatureMap.contains(creature.player)) return false
        creatureMap[creature.player] = creature
        return true
    }

    fun getCreature(player: Player) = creatureMap[player]
    fun getCreatures() = creatureMap.values

    /**
     * Event function
     */
    @EventHandler
    fun attack(e: EntityDamageByEntityEvent) {
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
    fun active(e: PlayerSwapHandItemsEvent) {
        if (e.player.world != world) return
        if (e.player.gameMode != GameMode.ADVENTURE) return
        e.isCancelled = true

        val player = e.player
        val playerCreature = getCreature(player) ?: return

        playerCreature.useActiveSkill()
    }

    @EventHandler
    fun useTool(e: PlayerInteractEvent) {
        if (e.player.world != world) return
        if (e.player.gameMode != GameMode.ADVENTURE) return
        if (!e.action.isRightClick) return
        val player = e.player
        val playerCreature = getCreature(player) ?: return
        playerCreature.useTool(e)
    }

    @EventHandler
    fun slayerDamaged(e: EntityDamageEvent) {
        if (e.entity.world != world) return
        if (e.entity !is Player) return

        val player = e.entity as Player
        val playerCreature = getCreature(player) ?: return
        if (playerCreature.creature !is Slayer) return

        if (e.cause != EntityDamageEvent.DamageCause.FALL) return
        e.damage = 1.0
    }

    @EventHandler
    fun death(e: PlayerDeathEvent) {
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
    fun damagedPassive(e: EntityDamageEvent) {
        if (e.entity.world != world) return
        if (e.entity !is Player) return

        val player = e.entity as Player
        val playerCreature = getCreature(player) ?: return
        playerCreature.passiveActivation(e)
    }
}