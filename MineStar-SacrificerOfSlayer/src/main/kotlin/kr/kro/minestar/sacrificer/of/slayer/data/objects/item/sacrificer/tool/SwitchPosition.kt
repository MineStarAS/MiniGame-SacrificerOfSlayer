package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.sacrificer.tool

import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces.Tool
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces.SkillType
import kr.kro.minestar.utility.location.offset
import kr.kro.minestar.utility.sound.PlaySound
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent

object SwitchPosition : Tool, Listener {

    override var material: Material = Material.NETHERITE_SCRAP
    override var displayName: String = "위치 교환 장치"
    override val description: List<String> = listOf(
        "30블럭 이내에 있는 플레이어를 바라보고",
        "사용하면 서로의 위치로 이동합니다"
    )
    override var amount: Int = 2
    override val skillType: SkillType = SkillType.MOVEMENT


    override fun used(e: PlayerInteractEvent): Boolean {
        if (e.action != Action.RIGHT_CLICK_AIR) return true
        val player = e.player
        var distance = 0.0
        val location = player.eyeLocation
        while (true) {
            val offsetLocation = location.clone().offset(distance)
            if (distance > 30) break
            distance += 0.1
            if (offsetLocation.block.type != Material.AIR) break
            val players = offsetLocation.getNearbyPlayers(0.0)
            if (players.toTypedArray().isNotEmpty())
                for (target in players) if (target != player)
                    if (target.gameMode != GameMode.SPECTATOR) {
                        val loc1 = player.location
                        val loc2 = target.location
                        target.teleport(loc1)
                        player.teleport(loc2)
                        teleportSound.play(target.location)
                        teleportSound.play(player.location)
                        return true
                    }
        }
        return false
    }

    private val teleportSound = PlaySound().apply {
        soundCategory = SoundCategory.RECORDS
        sound = Sound.ENTITY_ENDERMAN_TELEPORT
    }
}