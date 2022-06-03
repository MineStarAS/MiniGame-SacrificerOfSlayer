package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.weapon

import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.item.weapon.MeleeWeapon
import kr.kro.minestar.sacrificer.of.slayer.functions.UtilityClass.effect
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.potion.PotionEffectType

object PoisonBlade : MeleeWeapon() {

    override val material: Material = Material.IRON_SWORD
    override val displayName: String = "포이즌 블레이드"
    override val damage: Double = 5.0
    override val hitEffect: List<String> = listOf("대상에게 '독 I'을 5초 부여 합니다")
    override val killEffect: List<String> = listOf("'투명화'를 5초간 받습니다")


    override fun hitEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        val attacker = e.damager as Player
        target.addPotionEffect(PotionEffectType.POISON.effect(5, 0))
        target.location.direction = attacker.location.subtract(target.location).toVector()
    }

    override fun killEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        val attacker = e.damager as Player
        attacker.addPotionEffect(PotionEffectType.INVISIBILITY.effect(5, 0))
    }
}