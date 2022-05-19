package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.tool

import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces.Tool
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces.SkillType
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object TestSubjectBlood : Tool {

    override var material: Material = Material.NETHERITE_SCRAP
    override var displayName: String = "실험체의 피"
    override val description: List<String> = listOf(
        "사용 시 30초간 '신속III', '점프강화 II' 를 받습니다"
    )
    override var amount: Int = 0

    override val skillType: SkillType = SkillType.MOVEMENT

    override fun used(e: PlayerInteractEvent): Boolean {
        val player = e.player
        player.addPotionEffect(
            PotionEffect(PotionEffectType.SPEED, 30, 2, false, false, true)
        )
        player.addPotionEffect(
            PotionEffect(PotionEffectType.JUMP, 30, 2, false, false, true)
        )
        return true
    }
}