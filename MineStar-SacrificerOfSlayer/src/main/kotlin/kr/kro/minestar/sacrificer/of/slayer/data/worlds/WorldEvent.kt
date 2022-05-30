package kr.kro.minestar.sacrificer.of.slayer.data.worlds

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import kr.kro.minestar.sacrificer.of.slayer.data.bossbar.SlayerHealthBar
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Sacrificer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces.RangedWeapon
import kr.kro.minestar.sacrificer.of.slayer.data.objects.Grenade
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerData
import org.bukkit.*
import org.bukkit.entity.Arrow
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.entity.ShulkerBullet
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.*
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerSwapHandItemsEvent

interface WorldEvent : Listener {
    val world: World

    val playerDataMap: HashMap<Player, PlayerData>
    fun addPlayerData(creature: PlayerData): Boolean {
        if (this is GameWorld) if (playerDataMap.contains(creature.player)) return false
        playerDataMap[creature.player] = creature
        return true
    }

    fun getPlayerData(player: Player) = playerDataMap[player]
    fun getPlayerDataList() = playerDataMap.values
    fun sacrificerAmount(): Int {
        var amount = 0
        for (creature in playerDataMap.values) if (creature.creature is Sacrificer) ++amount
        return amount
    }

    fun liveSacrificerAmount(): Int {
        var amount = 0
        for (creature in playerDataMap.values) {
            if (creature.creature !is Sacrificer) continue
            if (creature.player.gameMode != GameMode.ADVENTURE) continue
            ++amount
        }
        return amount
    }

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
        val targetData = getPlayerData(target) ?: return

        when (attacker) {
            is Player -> {
                val attackerData = getPlayerData(attacker) ?: return
                val creature = attackerData.creature
                creature.weapon?.hit(e, this)
            }
            is Projectile -> when (attacker) {
                is Arrow -> {
                    val shooter = attacker.shooter ?: return
                    if (shooter !is Player) return
                    val attackerData = getPlayerData(shooter) ?: return
                    val creature = attackerData.creature
                    creature.weapon?.hit(e, this)
                    target.arrowsInBody = 0
                }

                is ShulkerBullet -> {}
            }
        }
        if (liveSacrificerAmount() <= 2)
            if (targetData.creature is Slayer) e.damage = e.damage * 2
    }

    @EventHandler
    fun shoot(e: EntityShootBowEvent) {
        if (e.entity.world != world) return
        if (e.entity !is Player) return

        val player = e.entity as Player
        val playerData = getPlayerData(player) ?: return
        val weapon = playerData.creature.weapon ?: return
        if (weapon !is RangedWeapon) return
        e.projectile.customName = e.force.toString()
        weapon.shootEffect(e)
    }

    @EventHandler
    fun projectileHit(e: ProjectileHitEvent) {
        if (e.entity.world != world) return

        when (val projectile = e.entity) {
            is Arrow -> Bukkit.getScheduler().runTaskLater(pl, Runnable { e.entity.remove() }, 30)

            is ShulkerBullet -> {
                val customName = projectile.customName ?: return
                val skill = Grenade.get(customName) ?: return
                skill.hitEffect(e)
            }
        }
    }

    @EventHandler
    fun active(e: PlayerSwapHandItemsEvent) {
        if (e.player.world != world) return
        e.isCancelled = true

        val player = e.player
        val playerData = getPlayerData(player) ?: return

        playerData.useActiveSkill()
    }

    @EventHandler
    fun useTool(e: PlayerInteractEvent) {
        if (e.player.world != world) return
        if (!e.action.isRightClick) return
        val player = e.player
        val playerData = getPlayerData(player) ?: return
        playerData.useTool(e)
    }

    @EventHandler
    fun death(e: PlayerDeathEvent) {
        if (e.player.world != world) return
        e.isCancelled = true
        val player = e.player
        val playerData = getPlayerData(player) ?: return
        if (playerData.creature is Slayer) if (playerData.health() > 0) return
        player.gameMode = GameMode.SPECTATOR
        player.health = player.maxHealth
        if (player.location.y < -64) player.teleport(world.spawnLocation)
    }

    @EventHandler
    fun dropItemCancel(e: PlayerDropItemEvent) {
        if (e.player.world != world) return
        e.isCancelled = true
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
        val playerData = getPlayerData(player) ?: return
        playerData.passiveActivation(e)

        Bukkit.getScheduler().runTask(pl, Runnable {
            if (playerData.creature is Slayer) {
                if (e.cause == EntityDamageEvent.DamageCause.FALL) {
                    e.isCancelled = true
                    return@Runnable
                }
                playerData.removeHealth(e.damage)
                SlayerHealthBar(playerData)
                e.isCancelled = true
                player.playEffect(EntityEffect.HURT)
            }
        })
    }
}