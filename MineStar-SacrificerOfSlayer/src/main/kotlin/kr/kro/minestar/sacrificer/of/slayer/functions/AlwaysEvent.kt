package kr.kro.minestar.sacrificer.of.slayer.functions

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.active.ImpactGrenade
import kr.kro.minestar.utility.event.enable
import kr.kro.minestar.utility.location.Axis
import kr.kro.minestar.utility.location.addAxis
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Entity
import org.bukkit.entity.ShulkerBullet
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent

object AlwaysEvent: Listener {
    init {
        enable(pl)
    }

    @EventHandler
    fun projectileHit(e: ProjectileHitEvent) {
        val entity = e.entity
        val location = entity.location

        if (entity !is ShulkerBullet) return
        if (entity.customName != ImpactGrenade.codeName()) return

        val damage = ImpactGrenade.damage
        val radius = ImpactGrenade.radius

        val players = entity.location.getNearbyPlayers(radius)

        SoundClass.explode(location, 0.7F)
        entity.world.spawnParticle(Particle.EXPLOSION_LARGE, location, 15, 1.0, 1.0, 1.0, 0.0)

        for (player in players) {
            if (player == e.hitEntity) {
                player.damage(damage, entity.shooter as Entity?)
                continue
            }
            val distance = location.distance(player.location.clone().addAxis(Axis.Y, 0.9))
            val finalDamage = damage - (damage * (distance / radius))
            player.damage(finalDamage, entity.shooter as Entity?)
        }
    }
}