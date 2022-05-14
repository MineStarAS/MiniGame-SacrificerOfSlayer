package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.weapon

import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.tool.JumpBomb
import kr.kro.minestar.sacrificer.of.slayer.functions.SoundClass
import kr.kro.minestar.utility.sound.PlaySound
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.util.Vector

object BlastHammer : Weapon {

    override val material: Material = Material.IRON_AXE
    override val displayName: String = "폭발 망치"
    override val damage: Double = 7.0
    override val hitEffect: List<String> = listOf("대상이 폭발하며 날아갑니다")
    override val killEffect: List<String> = listOf("'폭발 부스터'를 얻습니다")


    override fun hitEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        val attacker = e.damager as Player
        val x = attacker.location.direction.x
        val z = attacker.location.direction.z
        val vector = Vector(x, 0.0, z).normalize()
        target.velocity = vector.multiply(2.5).setY(3.0)
        SoundClass.explode(target.location, 1.0F)
        target.world.spawnParticle(Particle.EXPLOSION_LARGE, target.location, 5, 0.4, 0.0, 0.4, 0.0)
    }

    override fun killEffect(e: EntityDamageByEntityEvent) {
        val attacker = e.damager as Player
        attacker.inventory.addItem(JumpBomb.getItem())
    }
}