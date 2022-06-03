package kr.kro.minestar.sacrificer.of.slayer.functions

import kr.kro.minestar.utility.particle.ParticleData
import org.bukkit.Particle

object ParticleClass {
    fun explode(count: Int, radius: Double): ParticleData {
        val particle = ParticleData()
        particle.particle = Particle.EXPLOSION_LARGE
        particle.count = count
        particle.offset(radius)
        return particle
    }
}