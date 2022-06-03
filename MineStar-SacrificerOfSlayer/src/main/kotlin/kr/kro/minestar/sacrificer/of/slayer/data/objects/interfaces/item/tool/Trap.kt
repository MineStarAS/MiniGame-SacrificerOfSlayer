package kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.item.tool

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Creature
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Sacrificer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import kr.kro.minestar.utility.location.look
import kr.kro.minestar.utility.location.offset
import kr.kro.minestar.utility.particle.ParticleData
import org.bukkit.*
import org.bukkit.block.BlockFace
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitTask

abstract class Trap(particleColor: Color) : Tool() {
    companion object {
        val locationSet = hashSetOf<Location>()
        fun addLocation(location: Location) = locationSet.add(location.toBlockLocation())
        fun removeLocation(location: Location) = locationSet.remove(location.toBlockLocation())
    }

    abstract val enableDelay: Long
    abstract val detectRadius: Double

    private val particleData = ParticleData().colorData(particleColor, 0.5F)

    private fun BlockFace.toLocation(world: World) = Location(world, modX.toDouble(), modY.toDouble(), modZ.toDouble())

    override fun used(e: PlayerInteractEvent): Boolean {
        if (e.action != Action.RIGHT_CLICK_BLOCK) return false

        val player = e.player
        val worldData = WorldData.get(player.world) ?: return false
        val creature = worldData.getPlayerData(player)?.creature ?: return false

        val location = e.clickedBlock!!.location.toCenterLocation()
        val offset = e.blockFace.toLocation(e.clickedBlock!!.world).multiply(0.6)
        location.add(offset)

        if (!addLocation(location)) return false

        var task: BukkitTask? = null
        task = Bukkit.getScheduler().runTaskTimer(pl, Runnable {
            particleData.play(location)
            if (checkDetected(location, worldData, creature)) detectedEffect(location, task!!)
        }, enableDelay, 1)
        return true
    }

    private fun checkDetected(location: Location, worldData: WorldData, setterCreature: Creature): Boolean {
        val players = location.getNearbyPlayers(detectRadius)
        for (player in players) {
            if (player.gameMode == GameMode.SPECTATOR) continue

            val playerData = worldData.getPlayerData(player) ?: continue
            val playerCreature = playerData.creature

            if (setterCreature is Slayer && playerCreature is Slayer) continue
            if (setterCreature is Sacrificer && playerCreature is Sacrificer) continue

            val loc = location.clone().look(player.location)
            var distance = 0.0
            var isNotBlocking = true
            while (true) {
                if (distance > detectRadius) break
                val offsetLocation = loc.clone().offset(distance)
                distance += 0.1
                if (offsetLocation.block.type != Material.AIR) {
                    isNotBlocking = false
                    break
                }
                val players = offsetLocation.getNearbyPlayers(0.0)
                if (players.contains(player)) continue
                else break
            }
            return isNotBlocking
        }
        return false
    }

    protected open fun detectedEffect(location: Location, task: BukkitTask) {
        removeLocation(location)
        task.cancel()
    }


}