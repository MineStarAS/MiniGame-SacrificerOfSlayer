package kr.kro.minestar.sacrificer.of.slayer.functions

import kr.kro.minestar.utility.location.offset
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

internal object UtilityClass {

    /**
     * Potion function
     */
    fun PotionEffectType.effect(sec: Int, level: Int) = PotionEffect(this, 20 * sec, level, false, false, true)

    fun PotionEffectType.effect(sec: Double, level: Int) = PotionEffect(this, (sec / 0.05).toInt(), level, false, false, true)

    fun PotionEffect.give(entity: LivingEntity) = entity.addPotionEffect(this)

    /**
     * Aim targeting function
     */
    fun aimTargeting(player: Player, range: Double): Player? {
        var distance = 0.0
        while (true) {
            val offsetLocation = player.eyeLocation.offset(distance)
            if (distance > range) break
            distance += 0.1
            if (offsetLocation.block.type != Material.AIR) break
            val players = offsetLocation.getNearbyPlayers(0.0)
            if (players.toTypedArray().isNotEmpty())
                for (target in players) {
                    if (target == player) continue
                    if (target.gameMode != GameMode.SPECTATOR) continue
                    return target
                }
        }
        return null
    }
}

