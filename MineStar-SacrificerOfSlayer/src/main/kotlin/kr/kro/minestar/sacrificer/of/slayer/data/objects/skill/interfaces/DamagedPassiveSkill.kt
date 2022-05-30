package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces

import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerData
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import org.bukkit.event.Event
import org.bukkit.event.entity.EntityDamageEvent

abstract class DamagedPassiveSkill : PassiveSkill() {


    override fun effectActivation(playerData: PlayerData, worldData: WorldData, e: Event?) {
        if (e !is EntityDamageEvent) return
        if (playerData.player != e.entity) return
        effect(playerData, worldData, e)
    }
}