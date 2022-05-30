package kr.kro.minestar.sacrificer.of.slayer.functions

import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

internal object UtilityClass {

    /**
     * Potion function
     */
    fun PotionEffectType.effect(sec: Int, level: Int) = PotionEffect(this, 20 * sec, level, false, false, true)
}

