package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.passive

import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces.SkillType
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces.TickPassiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import org.bukkit.event.Event
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object Hide : TickPassiveSkill() {

    override val name: String = "은신"
    override val description = mutableListOf("구석에 움크리고 있으면 '투명화'가 됩니다")
    override val skillType = SkillType.MOVEMENT
    override val period = 0

    override fun effect(playerCreature: PlayerCreature, worldData: WorldData, e: Event?) {
        val player = playerCreature.player
        if (player.isSneaking) {
            val location = player.location.block.location
            var count = 0
            if (location.clone().add(1.0, 0.0, 0.0).block.type.isSolid)
                if (location.clone().add(1.0, 1.0, 0.0).block.type.isSolid) ++count
            if (location.clone().add(-1.0, 0.0, 0.0).block.type.isSolid)
                if (location.clone().add(-1.0, 1.0, 0.0).block.type.isSolid) ++count
            if (location.clone().add(0.0, 0.0, 1.0).block.type.isSolid)
                if (location.clone().add(0.0, 1.0, 1.0).block.type.isSolid) ++count
            if (location.clone().add(0.0, 0.0, -1.0).block.type.isSolid)
                if (location.clone().add(0.0, 1.0, -1.0).block.type.isSolid) ++count
            if (count >= 2) player.addPotionEffect(
                PotionEffect(PotionEffectType.INVISIBILITY, 2, 0, false, false, true))

        }
    }
}