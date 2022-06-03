package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.sacrificer.tool

import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.item.tool.Trap
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.SkillType
import kr.kro.minestar.utility.string.toServer
import org.bukkit.Color
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.scheduler.BukkitTask

object TestTrap : Trap(Color.RED) {

    override var material: Material = Material.NETHERITE_SCRAP
    override var displayName: String = "타스토 트레푸"

    override val description: List<String> = listOf(
        "나는야 테스트 트랩!",
        "그리고 유미가 말했지!",
        "\"빨간 점을 잘 찾아봐, 책아!\"",
    )

    override var amount: Int = 5
    override val skillType: SkillType = SkillType.DISTURBING

    override val enableDelay = 10L
    override val detectRadius = 1.5

    override fun detectedEffect(location: Location, task: BukkitTask) {
        super.detectedEffect(location, task)
        location.world.createExplosion(location, 3.0F)
    }
}