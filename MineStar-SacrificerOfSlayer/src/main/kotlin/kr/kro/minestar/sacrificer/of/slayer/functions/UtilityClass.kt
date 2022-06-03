package kr.kro.minestar.sacrificer.of.slayer.functions

import org.bukkit.entity.LivingEntity
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

internal object UtilityClass {

    /**
     * Potion function
     */
    fun PotionEffectType.effect(sec: Int, level: Int) = PotionEffect(this, 20 * sec, level, false, false, true)

    fun PotionEffectType.effect(sec: Double, level: Int) = PotionEffect(this, (sec / 0.05).toInt(), level, false, false, true)

    fun PotionEffect.give(entity: LivingEntity) = entity.addPotionEffect(this)
}

