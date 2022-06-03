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

object BangBangBomb : TickPassiveSkill() {
    override val name: String = "콰과광!"
    override val description = mutableListOf(
        "30블럭 이내에서 폭발하는 소리가 들리면",
        "'신속 III'을 5초 받습니다"
    )
    override val skillType = SkillType.MOVEMENT
    override val period = 0

    override fun effect(playerData: PlayerData, worldData: WorldData, e: Event?) {
        val player = playerData.player
        val soundDistance = SoundClass.minExplodeDistance(player.location) ?: return
        if (soundDistance > 30) return
        PotionEffectType.SPEED.effect(5, 2).give(player)
    }
}