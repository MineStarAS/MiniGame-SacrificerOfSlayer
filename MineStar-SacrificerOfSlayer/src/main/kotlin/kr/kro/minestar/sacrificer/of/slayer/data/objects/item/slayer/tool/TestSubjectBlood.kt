package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.tool

import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.item.tool.Tool
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.SkillType
import kr.kro.minestar.sacrificer.of.slayer.functions.UtilityClass.effect
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffectType

object TestSubjectBlood : Tool() {

    override var material: Material = Material.NETHERITE_SCRAP
    override var displayName: String = "실험체의 피"
    override val description: List<String> = listOf(
        "사용 시 15초간 '신속III', '점프강화 II' 를 받습니다"
    )
    override var amount: Int = 0

    override val skillType: SkillType = SkillType.MOVEMENT

    override fun used(e: PlayerInteractEvent): Boolean {
        val player = e.player
        player.addPotionEffect(PotionEffectType.SPEED.effect(15, 2))
        player.addPotionEffect(PotionEffectType.JUMP.effect(15, 2))
        return true
    }
}