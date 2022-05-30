package kr.kro.minestar.sacrificer.of.slayer.data.objects

import org.bukkit.event.entity.ProjectileHitEvent

interface Grenade {
    companion object {
        private val map = hashMapOf<String, Grenade>()

        fun get(string: String) = map[string]
    }

    fun init(className: String) {
        map[className] = this
    }

    fun hitEffect(e: ProjectileHitEvent)
}