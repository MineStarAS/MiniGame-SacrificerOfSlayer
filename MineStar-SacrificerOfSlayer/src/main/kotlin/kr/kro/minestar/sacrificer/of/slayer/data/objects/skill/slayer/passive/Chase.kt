package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.passive

import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.PassiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.SkillType
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import kr.kro.minestar.utility.location.offset
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object Chase : PassiveSkill() {
    override val name: String = "추격"
    override val description = mutableListOf(
        "30블럭 이내에 있는 플레이어를",
        "바라보고 있으면 '신속 I'을 받습니다"
    )
    override val skillType = SkillType.MOVEMENT
    override val period = 0

    override fun effect(playerCreature: PlayerCreature, worldData: WorldData) {
        val player = playerCreature.player
        var distance = 0.0
        val location = player.eyeLocation
        while (true) {
            if (distance > 30) break
            val offsetLocation = location.clone().offset(distance)
            distance += 0.1
            if (offsetLocation.block.type != Material.AIR) break
            val players = offsetLocation.getNearbyPlayers(0.0)
            if (players.toTypedArray().isNotEmpty()) for (target in players) if (target != player) if (target.gameMode != GameMode.SPECTATOR) {
                player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 1, 0))
                break
            }
        }

    }
}