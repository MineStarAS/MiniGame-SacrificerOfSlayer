package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.tool

import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.SkillType
import kr.kro.minestar.utility.sound.PlaySound
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.block.data.type.Door
import org.bukkit.event.player.PlayerInteractEvent

object MasterKey : Tool {

    override var material: Material = Material.NETHERITE_SCRAP
    override var displayName: String = "마스터 키"

    override val description: List<String> = listOf(
        "철문을 좌클릭으로",
        "열고 닫을 수 있습니다")

    override var amount: Int = 5
    override val skillType: SkillType = SkillType.DISTURBING

    override fun used(e: PlayerInteractEvent): Boolean {
        val clickBlock = e.clickedBlock ?: return false
        val door = clickBlock.blockData
        if (door !is Door) return false
        if (door.material != Material.IRON_DOOR) return false
        door.isOpen = door.isOpen
        clickBlock.blockData = door
        if (door.isOpen) doorOpenSound.play(clickBlock.location)
        else doorCloseSound.play(clickBlock.location)
        return true
    }

    private val doorOpenSound = PlaySound().apply {
        soundCategory = SoundCategory.BLOCKS
        sound = Sound.BLOCK_IRON_DOOR_OPEN
    }

    private val doorCloseSound = PlaySound().apply {
        soundCategory = SoundCategory.BLOCKS
        sound = Sound.BLOCK_IRON_DOOR_CLOSE
    }
}