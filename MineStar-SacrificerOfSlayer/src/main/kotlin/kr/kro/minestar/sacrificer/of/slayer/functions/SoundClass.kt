package kr.kro.minestar.sacrificer.of.slayer.functions

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import kr.kro.minestar.utility.location.Axis
import kr.kro.minestar.utility.location.addAxis
import kr.kro.minestar.utility.sound.PlaySound
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player
import java.lang.Double.min

object SoundClass {
    val playerDeath = PlaySound().apply {
        soundCategory = SoundCategory.RECORDS
        sound = Sound.ENTITY_PLAYER_DEATH
    }

    val criticalHit = PlaySound().apply {
        soundCategory = SoundCategory.RECORDS
        sound = Sound.ENTITY_PLAYER_ATTACK_CRIT
    }

    private val explodeSet = hashSetOf<Location>()
    fun minExplodeDistance(location: Location): Double? {
        var minDistance: Double? = null
        for (loc in explodeSet) {
            if (minDistance == null) {
                minDistance = loc.distance(location)
                continue
            }
            minDistance = min(minDistance, loc.distance(location))
        }
        return minDistance
    }

    fun explode(location: Location, pitch: Float) {
        val sound = PlaySound().apply {
            soundCategory = SoundCategory.RECORDS
            sound = Sound.ENTITY_GENERIC_EXPLODE
            this.pitch = pitch
        }
        sound.play(location)
        explodeSet.add(location)
        Bukkit.getScheduler().runTaskLater(pl, Runnable { explodeSet.remove(location) }, 3)
    }

    /**
     * Game sound
     */

    val gameWorldEnter = PlaySound().apply {
        soundCategory = SoundCategory.RECORDS
        sound = Sound.ENTITY_WITHER_SPAWN
        volume = 0.5F
        pitch = 0.5F
    }

    internal fun slayerWinMusic(players: Collection<Player>) {
        val sound = PlaySound().apply {
            soundCategory = SoundCategory.RECORDS
            sound = Sound.UI_TOAST_CHALLENGE_COMPLETE
            volume = 100F
            pitch = 0.8F
        }
        for (player in players) sound.play(player, player.location.addAxis(Axis.Y, 500))
    }

    internal fun sacrificerWinMusic(players: Collection<Player>) {
        val sound = PlaySound().apply {
            soundCategory = SoundCategory.RECORDS
            sound = Sound.UI_TOAST_CHALLENGE_COMPLETE
            volume = 100F
            pitch = 1.5F
        }
        for (player in players) sound.play(player, player.location.addAxis(Axis.Y, 500))
    }
}