package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.weapon

import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.item.weapon.MeleeWeapon
import kr.kro.minestar.sacrificer.of.slayer.functions.UtilityClass.effect
import kr.kro.minestar.sacrificer.of.slayer.functions.UtilityClass.give
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffectType

object MurderAxe : MeleeWeapon() {

    override val material: Material = Material.IRON_AXE
    override val displayName: String = "살인 도끼"
    override val damage: Double = 8.0
    override val hitEffect: List<String> = listOf("대상에게 '구속 I'을 3초 부여 합니다")
    override val killEffect: List<String> = listOf("'신속 III'을 5초간 받습니다")


    override fun hitEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        val attacker = e.damager as Player
        PotionEffectType.SLOW.effect(3, 0).give(target)
    }

    override fun killEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        val attacker = e.damager as Player
        PotionEffectType.SPEED.effect(5, 2).give(attacker)
    }
}