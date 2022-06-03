package kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.item.tool

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Creature
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Sacrificer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import kr.kro.minestar.utility.item.addLore
import kr.kro.minestar.utility.location.look
import kr.kro.minestar.utility.location.offset
import kr.kro.minestar.utility.number.round
import kr.kro.minestar.utility.particle.ParticleData
import kr.kro.minestar.utility.string.toServer
import org.bukkit.*
import org.bukkit.block.BlockFace
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.scheduler.BukkitTask

abstract class Trap(particleColor: Color) : Tool() {
    companion object {
        private val locationSet = hashSetOf<Location>()
        fun addLocation(location: Location) = locationSet.add(location.toBlockLocation())
        fun removeLocation(location: Location) = locationSet.remove(location.toBlockLocation())
    }

    abstract val enableDelay: Long
    abstract val detectRadius: Double

    private val particleData = ParticleData().colorData(particleColor, 0.2F)

    override fun getItem() = super.getItem().addLore(" ").addLore("§f§7감지 반경 : ${detectRadius.round(2)} 블럭")

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

            fun check(loc: Location): Boolean {
                var distance = 0.0
                while (true) {
                    if (distance > detectRadius) break
                    val offsetLocation = loc.clone().offset(distance)
                    distance += 0.1
                    if (offsetLocation.block.type != Material.AIR) break
                    if (offsetLocation.getNearbyPlayers(0.0).contains(player)) return true
                }
                return false
            }

            if (check(location.clone().look(player.location))) return true
            if (check(location.clone().look(player.eyeLocation))) return true
        }
        return false
    }

    protected open fun detectedEffect(location: Location, task: BukkitTask) {
        removeLocation(location)
        task.cancel()
    }


}