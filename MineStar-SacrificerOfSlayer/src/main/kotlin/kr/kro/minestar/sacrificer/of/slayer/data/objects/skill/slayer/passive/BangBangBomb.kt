package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.passive

import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.SkillType
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.PassiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.GameWorld
import kr.kro.minestar.sacrificer.of.slayer.functions.SoundClass
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object BangBangBomb : PassiveSkill {
    override val name: String = "콰과광!"
    override val description = mutableListOf(
        "30블럭 이내에서 폭발하는 소리가 들리면",
        "'신속 III'을 5초 받습니다"
    )
    override val skillType = SkillType.MOVEMENT

    override fun effect(playerCreature: PlayerCreature, gameWorld: GameWorld) {
        val player = playerCreature.player
        val soundDistance = SoundClass.minExplodeDistance(player.location) ?: return
        if (soundDistance > 30) return
        player.addPotionEffect(PotionEffect(PotionEffectType.SPEED, 20 * 5, 2))
    }
}