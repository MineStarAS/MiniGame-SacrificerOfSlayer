package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill

import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import org.bukkit.Material
import org.bukkit.event.Event
import org.bukkit.inventory.ItemStack

abstract class PassiveSkill : Skill {

    fun getItem(): ItemStack {
        val item = ItemStack(Material.GOLD_INGOT)
        val itemMeta = item.itemMeta
        val lore = mutableListOf(" ")
        for (s in description) lore.add("§f§7$s")
        itemMeta.setDisplayName("§f[§aPASSIVE§f] $name")
        itemMeta.lore = lore
        item.itemMeta = itemMeta
        return item
    }

    abstract fun effectActivation(playerCreature: PlayerCreature, worldData: WorldData, e: Event?)

    protected abstract fun effect(playerCreature: PlayerCreature, worldData: WorldData, e: Event?)
}