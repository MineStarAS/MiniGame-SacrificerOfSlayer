package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.weapon

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object HiddenDagger : Weapon {

    override val material: Material = Material.IRON_SWORD
    override val displayName: String = "히든 대거"
    override val damage: Double = 12.0
    override val hitEffect: List<String> = listOf("대상에게 '독 I'을 5초 부여 합니다")
    override val killEffect: List<String> = listOf("'투명화'를 5초간 받습니다")


    override fun hitEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        val attacker = e.damager as Player
        target.addPotionEffect(PotionEffect(PotionEffectType.POISON, 20 * 5, 0))
        target.location.direction = attacker.location.subtract(target.location).toVector()
    }

    override fun killEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        val attacker = e.damager as Player
        attacker.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 20 * 5, 0))
    }
}