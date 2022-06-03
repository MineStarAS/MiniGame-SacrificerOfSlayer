package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.active

import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.Grenade
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.active.ActiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.SkillType
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerData
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import kr.kro.minestar.sacrificer.of.slayer.functions.SoundClass
import kr.kro.minestar.utility.location.offset
import org.bukkit.Particle
import org.bukkit.entity.ShulkerBullet
import org.bukkit.event.entity.ProjectileHitEvent

object ImpactGrenade : ActiveSkill(), Grenade {

    override val name: String = "충격 수류탄"
    override val coolTime = 20 * 30
    override val startCoolTime = coolTime
    override val skillType = SkillType.ATTACK

    private const val power = 3F

    override val description = mutableListOf(
        "폭발을 이르키는 투척물을 던집니다",
    )

    override fun activeEffect(playerData: PlayerData, worldData: WorldData) {
        val player = playerData.player
        val grenade = player.world.spawn(player.eyeLocation.offset(1), ShulkerBullet::class.java)
        grenade.shooter = player
        grenade.customName = className()
        val vector = player.eyeLocation.direction.multiply(1.0)
        grenade.velocity = vector
    }

    override fun hitEffect(e: ProjectileHitEvent) {
        val projectile = e.entity
        val location = projectile.location
        location.world.createExplosion(location, power, false)
        location.world.spawnParticle(Particle.EXPLOSION_HUGE, location, 10, 1.0, 1.0, 1.0)
        SoundClass.explode(location, 1.0F)
    }
}