package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.weapon

import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.item.weapon.MeleeWeapon
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.tool.TestSubjectBlood
import kr.kro.minestar.sacrificer.of.slayer.functions.UtilityClass.effect
import kr.kro.minestar.sacrificer.of.slayer.functions.UtilityClass.give
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffectType

object DangerousSyringe : MeleeWeapon() {

    override val material: Material = Material.IRON_SWORD
    override val displayName: String = "위험한 주사기"
    override val damage: Double = 3.5
    override val hitEffect: List<String> = listOf(
        "대상이 3초간 '신속I','독I'을 받습니다"
    )
    override val killEffect: List<String> = listOf("'실험체의 피'를 얻습니다")


    override fun hitEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        val attacker = e.damager as Player
        PotionEffectType.SLOW.effect(3, 1).give(target)
        PotionEffectType.BLINDNESS.effect(3, 1).give(target)
    }

    override fun killEffect(e: EntityDamageByEntityEvent) {
        val attacker = e.damager as Player
        attacker.inventory.addItem(TestSubjectBlood.getItem())
    }
}