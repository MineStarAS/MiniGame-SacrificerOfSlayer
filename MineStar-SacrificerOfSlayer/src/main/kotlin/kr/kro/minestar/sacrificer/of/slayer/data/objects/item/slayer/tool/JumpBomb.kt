package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.tool

import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.item.tool.Tool
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.SkillType
import kr.kro.minestar.sacrificer.of.slayer.functions.SoundClass
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.util.Vector

object JumpBomb : Tool() {

    override var material: Material = Material.NETHERITE_SCRAP
    override var displayName: String = "점프 폭탄"
    override val description: List<String> = listOf(
        "폭발을 이르켜 앞으로 점프 합니다"
    )
    override var amount: Int = 0

    override val skillType: SkillType = SkillType.MOVEMENT

    override fun used(e: PlayerInteractEvent): Boolean {
        if (e.action != Action.RIGHT_CLICK_AIR) return false
        val player = e.player
        val x = player.location.direction.x
        val z = player.location.direction.z
        val v = Vector(x, 0.0, z).normalize()
        player.velocity = v.add(Vector(0, 0, 0)).multiply(1).setY(1.5)
        SoundClass.explode(player.location, 1.0F)
        player.world.spawnParticle(Particle.EXPLOSION_LARGE, player.location, 5, 0.4, 0.0, 0.4, 0.0)
        return true
    }
}