package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.sacrificer.passive

import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.PassiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.SkillType
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import kr.kro.minestar.utility.sound.PlaySound
import org.bukkit.Sound
import org.bukkit.SoundCategory

object SlayerStep : PassiveSkill() {
    override val name: String = "학살자의 발소리"
    override val description = mutableListOf("슬레이어으로부터 특별한 소리가 들립니다")
    override val skillType: SkillType = SkillType.SEARCH
    override val period = 20

    override fun effect(playerCreature: PlayerCreature, worldData: WorldData) {
        if (!canEffectActivation(playerCreature)) return
        for (creature in worldData.getCreatures()) if (creature.creature is Slayer) slayerStep.play(playerCreature.player, creature.player.location)

    }

    private val slayerStep = PlaySound().apply {
        soundCategory = SoundCategory.RECORDS
        sound = Sound.ENTITY_PIGLIN_BRUTE_STEP
        volume = 2.0F
        pitch = 0.5F
    }
}