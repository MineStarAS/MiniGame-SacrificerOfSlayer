package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.passive

import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.PassiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.SkillType
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.GameWorld
import org.bukkit.Material
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object Hide : PassiveSkill {

    override val name: String = "은신"
    override val description = mutableListOf(
        "구석에 움크리고 있으면 '투명화'가 됩니다"
    )
    override val skillType = SkillType.MOVEMENT

    override fun effect(playerCreature: PlayerCreature, gameWorld: GameWorld) {
        val player = playerCreature.player
        if (player.isSneaking) {
            val loc = player.location.block.location
            var count = 0
            if (loc.clone().add(1.0, 0.0, 0.0).block.type != Material.AIR)
                if (loc.clone().add(1.0, 1.0, 0.0).block.type != Material.AIR) ++count
            if (loc.clone().add(-1.0, 0.0, 0.0).block.type != Material.AIR)
                if (loc.clone().add(-1.0, 1.0, 0.0).block.type != Material.AIR) ++count
            if (loc.clone().add(0.0, 0.0, 1.0).block.type != Material.AIR)
                if (loc.clone().add(0.0, 1.0, 1.0).block.type != Material.AIR) ++count
            if (loc.clone().add(0.0, 0.0, -1.0).block.type != Material.AIR)
                if (loc.clone().add(0.0, 1.0, -1.0).block.type != Material.AIR) ++count
            if (count >= 2) player.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, 1, 0))
        }
    }
}