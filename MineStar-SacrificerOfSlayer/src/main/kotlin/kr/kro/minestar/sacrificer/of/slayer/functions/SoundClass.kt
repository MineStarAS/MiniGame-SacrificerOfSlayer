package kr.kro.minestar.sacrificer.of.slayer.functions

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import kr.kro.minestar.utility.sound.PlaySound
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.SoundCategory
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
}