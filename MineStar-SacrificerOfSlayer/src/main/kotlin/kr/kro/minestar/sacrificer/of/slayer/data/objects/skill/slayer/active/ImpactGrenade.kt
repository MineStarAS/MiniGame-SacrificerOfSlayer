package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.active

import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.ActiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.SkillType
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.GameWorld
import kr.kro.minestar.sacrificer.of.slayer.functions.SoundClass
import kr.kro.minestar.utility.location.Axis
import kr.kro.minestar.utility.location.addAxis
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Entity
import org.bukkit.entity.ShulkerBullet
import org.bukkit.event.Event
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerInteractEvent

object ImpactGrenade : ActiveSkill {

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

    override fun active(playerCreature: PlayerCreature, gameWorld: GameWorld) {
        val player = playerCreature.player
        SoundClass.explode(player.location, 2.0F)
        val grenade = player.world.spawn(player.eyeLocation, ShulkerBullet::class.java)
        grenade.shooter = player
        grenade.customName = codeName()
        val vector = player.eyeLocation.direction.multiply(1.0)
        grenade.velocity = vector
    }
}