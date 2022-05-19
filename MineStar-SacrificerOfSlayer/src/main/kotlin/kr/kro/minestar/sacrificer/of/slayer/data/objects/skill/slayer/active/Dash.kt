package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.active

import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces.ActiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces.SkillType
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import org.bukkit.util.Vector

object Dash : ActiveSkill() {

    override val name: String = "대쉬"
    override val description = mutableListOf(
        "전방으로 돌진합니다"
    )
    override val coolTime = 20 * 10
    override val startCoolTime = coolTime
    override val duration = 20 * 0
    override val skillType = SkillType.MOVEMENT

    override fun activeEffect(playerCreature: PlayerCreature, worldData: WorldData) {
        val player = playerCreature.player
        val x = player.location.direction.x
        val z = player.location.direction.z
        val v = Vector(x, 0.0, z).normalize()
        player.velocity = v.add(Vector(0, 0, 0)).multiply(2).setY(0.5)
    }
}