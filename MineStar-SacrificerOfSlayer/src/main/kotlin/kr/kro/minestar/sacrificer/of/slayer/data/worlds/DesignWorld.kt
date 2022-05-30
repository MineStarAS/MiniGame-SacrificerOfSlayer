package kr.kro.minestar.sacrificer.of.slayer.data.worlds

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Sacrificer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerData
import kr.kro.minestar.sacrificer.of.slayer.functions.WorldClass
import kr.kro.minestar.utility.event.enable
import kr.kro.minestar.utility.location.toCenter
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import kotlin.math.absoluteValue

class DesignWorld(world: World) : WorldData(world) {

    init {
        enable(pl)
        WorldClass.addDesignWorld(this)
        tickTaskRun()
    }

    fun save() {
        world.save()
        WorldClass.fileCopy(world.worldFolder, folder)
    }

    /**
     * Creature function
     */
    fun setSlayer(player: Player, slayer: Slayer?) {
        val playerData = if (slayer != null) PlayerData(player, this, slayer)
        else PlayerData.randomSlayer(player, this)
        addCreature(playerData)
    }
    fun setSacrificer(player: Player, sacrificer: Sacrificer?) {
        val playerData = if (sacrificer != null) PlayerData(player, this, sacrificer)
        else PlayerData.randomSacrificer(player, this)
        addCreature(playerData)
    }

    /**
     * Mark function
     */
    private fun summonMark(location: Location): ArmorStand {
        val mark = world.spawn(location, ArmorStand::class.java)
        mark.setGravity(false)
        mark.isInvisible = true
        mark.isSmall = true
        mark.isMarker = true
        mark.isCustomNameVisible = false
        mark.setBasePlate(false)
        return mark
    }

    /**
     * Utility function
     */
    private fun String.title(player: Player) = player.sendTitle(" ", this, 5, 10, 5)

    private fun Location.setUp(): Location {
        toCenter()
        val newYaw = when (yaw.toInt().absoluteValue) {
            in 0 until 8 -> 0
            in 8 until 23 -> 15
            in 23 until 38 -> 30
            in 38 until 53 -> 45
            in 53 until 68 -> 60
            in 68 until 83 -> 75
            in 83 until 98 -> 90
            in 98 until 113 -> 105
            in 113 until 128 -> 120
            in 128 until 143 -> 135
            in 143 until 158 -> 150
            in 158 until 173 -> 165
            in 173 until 180 -> 180
            else -> 0
        }
        yaw = if (yaw < 0) -newYaw.toFloat()
        else newYaw.toFloat()
        return this
    }
}