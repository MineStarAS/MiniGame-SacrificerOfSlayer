package kr.kro.minestar.sacrificer.of.slayer.data.worlds

import org.bukkit.Bukkit
import org.bukkit.World

class GameWorld(world: World, private val worldName: String) : WorldData(world) {
    init {
        init()
    }

    private fun allPlayers() = Bukkit.getOnlinePlayers()
    private val worldPlayers = world.players

    private var started = false
    internal fun isStarted() = started

    /**
    Game function
     */

}