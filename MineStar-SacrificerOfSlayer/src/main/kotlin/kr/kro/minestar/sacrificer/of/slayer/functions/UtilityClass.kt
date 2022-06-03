package kr.kro.minestar.sacrificer.of.slayer.functions

import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

internal object UtilityClass {

    /**
     * Potion function
     */
    fun PotionEffectType.effect(secOrTick: Int, level: Int): PotionEffect {
        return if (secOrTick >= 0) PotionEffect(this, 20 * secOrTick, level, false, false, true)
        else PotionEffect(this, -secOrTick, level, false, false, true)
    }

}

