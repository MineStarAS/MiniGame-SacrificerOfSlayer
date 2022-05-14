package kr.kro.minestar.sacrificer.of.slayer.data.worlds

import kr.kro.minestar.sacrificer.of.slayer.functions.WorldClass
import org.bukkit.GameMode
import org.bukkit.World
import org.bukkit.entity.Player
import java.io.File

@Suppress("DEPRECATION")
abstract class WorldData(internal val world: World) {
    protected var folder = File("${WorldClass.worldFolder}/${world.name}")

    protected fun startLocation() = world.spawnLocation.clone()

    protected fun init() {
    }

    /**
     * Other Function
     */
    fun teleportToStartLocation(player: Player) {
        player.teleport(startLocation())
        player.health = player.maxHealth
        player.gameMode = GameMode.ADVENTURE
    }

    protected fun inventoryClear(player: Player) {
        val inventory = player.inventory
        inventory.clear()
    }
}