package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.active

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.Grenade
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.active.ActiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.SkillType
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerData
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import kr.kro.minestar.sacrificer.of.slayer.functions.UtilityClass.effect
import kr.kro.minestar.sacrificer.of.slayer.functions.UtilityClass.give
import kr.kro.minestar.utility.location.offset
import kr.kro.minestar.utility.particle.ParticleData
import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.GameMode
import org.bukkit.entity.ShulkerBullet
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitTask

object ParalysisGasGrenade : ActiveSkill(), Grenade {

    override val name: String = "마비 가스탄"
    override val coolTime = 20 * 30
    override val startCoolTime = coolTime
    override val skillType = SkillType.ATTACK

    override val description = mutableListOf(
        "가스탄을 던져 15초간 유지되는 가스 구름을 생성합니다",
        "가스 구름 안에 있는 세크리파이서는 '구속 III'를 받습니다",
    )

    override fun activeEffect(playerData: PlayerData, worldData: WorldData) {
        val player = playerData.player
        val grenade = player.world.spawn(player.eyeLocation.offset(1), ShulkerBullet::class.java)
        grenade.shooter = player
        grenade.customName = className()
        val vector = player.eyeLocation.direction.multiply(1.0)
        grenade.velocity = vector
    }

    private val particle = ParticleData().apply {
        count = 10
        transitionColorData(Color.YELLOW, Color.GREEN, 4F)
    }

    override fun hitEffect(e: ProjectileHitEvent) {
        val worldData = WorldData.get(e.entity.world) ?: return
        val projectile = e.entity
        val location = projectile.location

        var radius = 0.0

        fun effect() {
            particle.clone().offset(radius / 2.5).play(location)

            val players = location.getNearbyPlayers(radius)
            for (player in players) {
                if (player.gameMode != GameMode.ADVENTURE) continue
                val data = worldData.getPlayerData(player) ?: continue
                if (data.creature is Slayer) continue
                PotionEffectType.SLOW.effect(1, 2).give(player)
            }
        }

        var task: BukkitTask? = null
        var time = 0
        task = Bukkit.getScheduler().runTaskTimer(pl, Runnable {
            if (radius < 3) radius += 0.2
            else time += 2
            effect()
            if (time >= 20 * 15) task?.cancel()
        }, 0, 2)
    }
}