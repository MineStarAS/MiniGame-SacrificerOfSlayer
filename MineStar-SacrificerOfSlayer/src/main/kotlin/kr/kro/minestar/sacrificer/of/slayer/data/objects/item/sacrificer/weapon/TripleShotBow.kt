package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.sacrificer.weapon

import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.item.weapon.RangedWeapon
import kr.kro.minestar.utility.location.Axis
import kr.kro.minestar.utility.location.addAxis
import kr.kro.minestar.utility.location.offset
import org.bukkit.Material
import org.bukkit.entity.Arrow
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityShootBowEvent

object TripleShotBow : RangedWeapon() {

    override val material: Material = Material.BOW
    override val displayName: String = "트리플 샷 보우"
    override val damage: Double = 4.0
    override val shootEffect: List<String> = listOf("한 번에 3개의 화살을 발사합니다")
    override val hitEffect: List<String> = listOf()
    override val killEffect: List<String> = listOf()

    override fun shootEffect(e: EntityShootBowEvent) {
        val shootPower = e.projectile.velocity.clone()
        val location = e.entity.eyeLocation.offset(1)
        e.entity.world.spawn(location.clone().addAxis(Axis.YAW, 90).offset(0.7), Arrow::class.java).velocity = shootPower
        e.entity.world.spawn(location.clone().addAxis(Axis.YAW, -90).offset(0.7), Arrow::class.java).velocity = shootPower
    }

    override fun hitEffect(e: EntityDamageByEntityEvent) {
    }

    override fun killEffect(e: EntityDamageByEntityEvent) {
    }
}