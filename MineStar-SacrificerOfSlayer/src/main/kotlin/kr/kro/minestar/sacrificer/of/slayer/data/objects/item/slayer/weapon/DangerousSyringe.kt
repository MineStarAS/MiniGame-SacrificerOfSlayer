package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.weapon

import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.item.weapon.MeleeWeapon
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.tool.TestSubjectBlood
import kr.kro.minestar.sacrificer.of.slayer.functions.UtilityClass.effect
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffectType

object DangerousSyringe : MeleeWeapon() {

    override val material: Material = Material.IRON_SWORD
    override val displayName: String = "위험한 주사기"
    override val damage: Double = 3.0
    override val hitEffect: List<String> = listOf(
        "풀 차징 공격 시",
        "대상이 1초간 '구속I','실명'을 받습니다"
    )
    override val killEffect: List<String> = listOf("'실험체의 피'를 얻습니다")


    override fun hitEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        val attacker = e.damager as Player
        target.addPotionEffect(PotionEffectType.SLOW.effect(-10, 1))
        target.addPotionEffect(PotionEffectType.BLINDNESS.effect(-10, 1))
    }

    override fun killEffect(e: EntityDamageByEntityEvent) {
        val attacker = e.damager as Player
        attacker.inventory.addItem(TestSubjectBlood.getItem())
    }
}