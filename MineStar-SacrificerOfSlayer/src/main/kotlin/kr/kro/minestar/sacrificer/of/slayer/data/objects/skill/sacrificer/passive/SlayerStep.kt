package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.sacrificer.passive

import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces.SkillType
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces.TickPassiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerData
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import kr.kro.minestar.utility.sound.PlaySound
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.event.Event

object SlayerStep : TickPassiveSkill() {
    override val name: String = "슬레이어의 발소리"
    override val description = mutableListOf("슬레이어의 발소리가 들립니다")
    override val skillType: SkillType = SkillType.SEARCH
    override val period = 20

    override fun effect(playerData: PlayerData, worldData: WorldData, e: Event?) {
        if (!canEffectActivation(playerData)) return
        for (creature in worldData.getPlayerDataList())
            if (creature.creature is Slayer)
                slayerStep.play(playerData.player, creature.player.location)
    }

    private val slayerStep = PlaySound().apply {
        soundCategory = SoundCategory.RECORDS
        sound = Sound.ENTITY_PIGLIN_BRUTE_STEP
        volume = 2.0F
        pitch = 0.5F
    }
}