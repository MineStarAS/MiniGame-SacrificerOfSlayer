package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.passive

import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.SkillType
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.passive.TickPassiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerData
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import kr.kro.minestar.sacrificer.of.slayer.functions.SoundClass
import kr.kro.minestar.sacrificer.of.slayer.functions.UtilityClass.effect
import kr.kro.minestar.sacrificer.of.slayer.functions.UtilityClass.give
import org.bukkit.event.Event
import org.bukkit.potion.PotionEffectType

object InterestingSubject : TickPassiveSkill() {
    override val name: String = "흥미로운 대상"
    override val description = mutableListOf(
        "10블럭 이내에 '효과'를 받고 있는 세크리파이서가 있으면",
        "'신속 I'을 받습니다"
    )
    override val skillType = SkillType.MOVEMENT
    override val period = 0

    override fun effect(playerData: PlayerData, worldData: WorldData, e: Event?) {
        val player = playerData.player
        val subjects = player.location.getNearbyPlayers(10.0)
        if (subjects.isEmpty()) return
        for (subject in subjects) {
            if (subject.activePotionEffects.isEmpty()) continue
            PotionEffectType.SPEED.effect(0.1, 0).give(player)
            return
        }
    }
}