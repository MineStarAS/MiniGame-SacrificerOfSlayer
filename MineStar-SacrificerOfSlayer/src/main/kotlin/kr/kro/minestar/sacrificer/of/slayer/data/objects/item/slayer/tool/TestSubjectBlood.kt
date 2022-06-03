package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.tool

import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.item.tool.Tool
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.SkillType
import kr.kro.minestar.sacrificer.of.slayer.functions.SoundClass
import kr.kro.minestar.sacrificer.of.slayer.functions.UtilityClass.effect
import kr.kro.minestar.sacrificer.of.slayer.functions.UtilityClass.give
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffectType

object TestSubjectBlood : Tool() {

    override var material: Material = Material.NETHERITE_SCRAP
    override var displayName: String = "실험체의 피"
    override val description: List<String> = listOf(
        "사용 시 10초간 '힘 V'를 받습니다"
    )
    override var amount: Int = 0

    override val skillType: SkillType = SkillType.MOVEMENT

    override fun used(e: PlayerInteractEvent): Boolean {
        val player = e.player
        SoundClass.drink.play(player)
        PotionEffectType.INCREASE_DAMAGE.effect(10, 4).give(player)
        return true
    }
}