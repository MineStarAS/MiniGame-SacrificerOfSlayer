package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.sacrificer.tool

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.item.tool.Trap
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.SkillType
import kr.kro.minestar.sacrificer.of.slayer.functions.ParticleClass
import kr.kro.minestar.sacrificer.of.slayer.functions.SoundClass
import org.bukkit.*
import org.bukkit.scheduler.BukkitTask

object BombTrap : Trap(Color.BLACK.setRed(130)) {

    override var material: Material = Material.NETHERITE_SCRAP
    override var displayName: String = "폭탄 트랩"

    override val description: List<String> = listOf(
        "슬레이어가 접근 시 폭파 합니다.",
    )

    override var amount: Int = 3
    override val skillType: SkillType = SkillType.ATTACK

    override val enableDelay = 10L
    override val detectRadius = 2.5

    override fun detectedEffect(location: Location, task: BukkitTask) {
        super.detectedEffect(location, task)
        Bukkit.getScheduler().runTaskLater(pl, Runnable {
            location.world.createExplosion(location, 3.0F)
            SoundClass.explode(location, 1.0F)
            ParticleClass.explode(10, 1.0).play(location)
        },10)
    }
}