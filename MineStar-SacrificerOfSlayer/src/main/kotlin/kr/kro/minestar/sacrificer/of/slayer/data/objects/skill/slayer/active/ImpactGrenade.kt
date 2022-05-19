package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.active

import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces.ActiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces.SkillType
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import kr.kro.minestar.sacrificer.of.slayer.functions.SoundClass
import org.bukkit.entity.ShulkerBullet

object ImpactGrenade : ActiveSkill() {

    override val name: String = "충격 수류탄"
    override val coolTime = 20 * 30
    override val startCoolTime = coolTime
    override val duration = 20 * 0
    override val skillType = SkillType.ATTACK

    const val damage = 15.0
    const val radius = 3.0

    override val description = mutableListOf(
        "충격 수류탄을 던집니다",
        " ",
        "최대 대미지 : $damage",
    )

    override fun activeEffect(playerCreature: PlayerCreature, worldData: WorldData) {
        val player = playerCreature.player
        SoundClass.explode(player.location, 2.0F)
        val grenade = player.world.spawn(player.eyeLocation, ShulkerBullet::class.java)
        grenade.shooter = player
        grenade.customName = codeName()
        val vector = player.eyeLocation.direction.multiply(1.0)
        grenade.velocity = vector
    }
}