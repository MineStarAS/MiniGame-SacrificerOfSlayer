package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.active

import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.active.ActiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.SkillType
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerData
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import org.bukkit.util.Vector

object Dash : ActiveSkill() {

    override val name: String = "대쉬"
    override val description = mutableListOf(
        "전방으로 돌진합니다"
    )
    override val coolTime = 20 * 10
    override val startCoolTime = coolTime
    override val skillType = SkillType.MOVEMENT

    override fun activeEffect(playerData: PlayerData, worldData: WorldData) {
        val player = playerData.player
        val x = player.location.direction.x
        val z = player.location.direction.z
        val v = Vector(x, 0.0, z).normalize()
        player.velocity = v.add(Vector(0, 0, 0)).multiply(2).setY(0.5)
    }
}