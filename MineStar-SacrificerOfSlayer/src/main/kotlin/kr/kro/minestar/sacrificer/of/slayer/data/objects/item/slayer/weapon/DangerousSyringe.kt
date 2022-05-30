package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.weapon

import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces.MeleeWeapon
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces.Weapon
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.tool.TestSubjectBlood
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object DangerousSyringe : MeleeWeapon() {

    override val material: Material = Material.IRON_SWORD
    override val displayName: String = "위험한 주사기"
    override val damage: Double = 3.0
    override val hitEffect: List<String> = listOf("대상이 3초간 '구속I','실명'을 받습니다")
    override val killEffect: List<String> = listOf("'실험체의 피'를 얻습니다")


    override fun hitEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        val attacker = e.damager as Player
        target.addPotionEffect(
            PotionEffect(PotionEffectType.SLOW, 20 * 3, 1, false, false, true)
        )
        target.addPotionEffect(
            PotionEffect(PotionEffectType.BLINDNESS, 20 * 3, 1, false, false, true)
        )
    }

    override fun killEffect(e: EntityDamageByEntityEvent) {
        val attacker = e.damager as Player
        attacker.inventory.addItem(TestSubjectBlood.getItem())
    }
}