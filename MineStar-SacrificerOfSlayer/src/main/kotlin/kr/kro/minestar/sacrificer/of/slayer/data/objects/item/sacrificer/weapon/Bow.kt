package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.sacrificer.weapon

import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces.RangedWeapon
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces.Weapon
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.tool.JumpBomb
import kr.kro.minestar.sacrificer.of.slayer.functions.SoundClass
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.util.Vector

object Bow : RangedWeapon() {

    override val material: Material = Material.BOW
    override val displayName: String = "기본 활"
    override val damage: Double = 7.0
    override val hitEffect: List<String> = listOf()
    override val killEffect: List<String> = listOf()

    override fun shootEffect(e: EntityShootBowEvent) {

    }

    override fun hitEffect(e: EntityDamageByEntityEvent) {
    }

    override fun killEffect(e: EntityDamageByEntityEvent) {
    }
}