package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill

import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import org.bukkit.Material
import org.bukkit.event.Event
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.inventory.ItemStack

abstract class DamagedPassiveSkill : PassiveSkill() {


    override fun effectActivation(playerCreature: PlayerCreature, worldData: WorldData, e: Event?) {
        if (e !is EntityDamageEvent) return
        if (playerCreature.player != e.entity) return
        effect(playerCreature, worldData, e)
    }
}