package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill

import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.GameWorld
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

interface PassiveSkill : Skill {

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

    fun effect(playerCreature: PlayerCreature, gameWorld: GameWorld)
}