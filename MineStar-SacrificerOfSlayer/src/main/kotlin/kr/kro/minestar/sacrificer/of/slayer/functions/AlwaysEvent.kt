package kr.kro.minestar.sacrificer.of.slayer.functions

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.active.ImpactGrenade
import kr.kro.minestar.utility.event.enable
import kr.kro.minestar.utility.string.toServer
import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockExplodeEvent
import org.bukkit.event.entity.FoodLevelChangeEvent
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerJoinEvent

object AlwaysEvent : Listener {

    init {
        enable(pl)
    }

    @EventHandler
    fun join(e: PlayerJoinEvent) {
        e.player.teleport(Bukkit.getWorlds().first().spawnLocation)
    }

    @EventHandler
    fun projectileHit(e: ProjectileHitEvent) {
        val projectile = e.entity
        val location = projectile.location

        when (projectile.customName) {
            ImpactGrenade.codeName() -> {
                location.world.createExplosion(location, ImpactGrenade.power, false)
                location.world.spawnParticle(Particle.EXPLOSION_HUGE, location, 10, 1.0, 1.0, 1.0)
                SoundClass.explode(location, 1.0F)
            }
        }
    }

    @EventHandler
    fun blockExplodeLock(e: BlockExplodeEvent) {
        e.isCancelled = true
    }

    @EventHandler
    fun foodLevelChangeLock(e: FoodLevelChangeEvent) {
        e.isCancelled = true
    }
}