package kr.kro.minestar.sacrificer.of.slayer.data.worlds

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import kr.kro.minestar.sacrificer.of.slayer.data.bossbar.SlayerHealthBar
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces.RangedWeapon
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces.Weapon
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import org.bukkit.Bukkit
import org.bukkit.EntityEffect
import org.bukkit.GameMode
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent

interface WorldEvent : Listener {
    val world: World

    val creatureMap: HashMap<Player, PlayerCreature>
    fun addCreature(creature: PlayerCreature): Boolean {
        if (this is GameWorld) if (creatureMap.contains(creature.player)) return false
        creatureMap[creature.player] = creature
        return true
    }

    fun getCreature(player: Player) = creatureMap[player]
    fun getCreatures() = creatureMap.values
    fun sacrificerAmount() = creatureMap.size - 1

    /**
     * Event function
     */
    @EventHandler
    fun attack(e: EntityDamageByEntityEvent) {
        if (e.entity.world != world) return
        e.isCancelled = true

        val target = e.entity
        val attacker = e.damager

        if (target !is Player) return

        when (attacker) {
            is Player -> {
                val playerCreature = getCreature(attacker) ?: return
                val creature = playerCreature.creature
                creature.weapon?.hit(e, this)
            }
            is Projectile -> {
                val shooter = attacker.shooter ?: return
                if (shooter !is Player) return
                val playerCreature = getCreature(shooter) ?: return
                val creature = playerCreature.creature
                creature.weapon?.hit(e, this)
            }
        }
    }

    @EventHandler
    fun shoot(e: EntityShootBowEvent) {
        if (e.entity.world != world) return
        if (e.entity !is Player) return

        val player = e.entity as Player
        val playerCreature = getCreature(player) ?: return
        val weapon = playerCreature.creature.weapon ?: return
        if (weapon !is RangedWeapon) return
        weapon.shootEffect(e)
    }

    @EventHandler
    fun active(e: PlayerSwapHandItemsEvent) {
        if (e.player.world != world) return
        e.isCancelled = true

        val player = e.player
        val playerCreature = getCreature(player) ?: return

        playerCreature.useActiveSkill()
    }

    @EventHandler
    fun useTool(e: PlayerInteractEvent) {
        if (e.player.world != world) return
        if (!e.action.isRightClick) return
        val player = e.player
        val playerCreature = getCreature(player) ?: return
        playerCreature.useTool(e)
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
    fun damaged(e: EntityDamageEvent) {
        if (e.entity.world != world) return
        if (e.entity !is Player) return
        if (e.isCancelled) return

        val player = e.entity as Player
        val playerCreature = getCreature(player) ?: return
        playerCreature.passiveActivation(e)

        Bukkit.getScheduler().runTask(pl, Runnable {
            if (playerCreature.creature is Slayer) {
                if (e.cause == EntityDamageEvent.DamageCause.FALL) {
                    e.isCancelled = true
                    return@Runnable
                }
                playerCreature.removeHealth(e.damage)
                SlayerHealthBar(playerCreature)
                e.isCancelled = true
                player.playEffect(EntityEffect.HURT)
            }
        })
    }
}