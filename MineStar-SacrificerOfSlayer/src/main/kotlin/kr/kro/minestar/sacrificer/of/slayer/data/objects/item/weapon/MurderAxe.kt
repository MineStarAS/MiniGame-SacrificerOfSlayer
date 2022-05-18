package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.weapon

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object MurderAxe : Weapon() {

    override val material: Material = Material.IRON_AXE
    override val displayName: String = "살인 도끼"
    override val damage: Double = 12.0
    override val hitEffect: List<String> = listOf("대상에게 '구속 I'을 3초 부여 합니다")
    override val killEffect: List<String> = listOf("'신속 III'을 5초간 받습니다")


    override fun hitEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        val attacker = e.damager as Player
        target.addPotionEffect(PotionEffect(PotionEffectType.SLOW, 20 * 3, 0))
    }

    override fun killEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        val attacker = e.damager as Player
        attacker.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 20 * 5, 2))
    }
}