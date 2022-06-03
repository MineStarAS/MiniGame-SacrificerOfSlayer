package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.sacrificer.weapon

import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.item.weapon.RangedWeapon
import org.bukkit.Material
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityShootBowEvent

object Bow : RangedWeapon() {

    override val material: Material = Material.BOW
    override val displayName: String = "기본 활"
    override val damage: Double = 3.0
    override val shootEffect: List<String> = listOf()
    override val hitEffect: List<String> = listOf()
    override val killEffect: List<String> = listOf()

    override fun shootEffect(e: EntityShootBowEvent) {
    }

    override fun hitEffect(e: EntityDamageByEntityEvent) {
    }

    override fun killEffect(e: EntityDamageByEntityEvent) {
    }
}