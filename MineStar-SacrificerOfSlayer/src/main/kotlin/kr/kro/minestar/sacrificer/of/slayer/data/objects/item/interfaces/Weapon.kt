@file:Suppress("DEPRECATION")

package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces

import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldEvent
import kr.kro.minestar.sacrificer.of.slayer.functions.SoundClass
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityShootBowEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

abstract class Weapon : Item {
    abstract override val displayName: String
    protected abstract val damage: Double
    protected abstract val hitEffect: List<String>
    protected abstract val killEffect: List<String>

    abstract fun hit(e: EntityDamageByEntityEvent, worldEvent: WorldEvent)

    protected abstract fun hitEffect(e: EntityDamageByEntityEvent)

    protected open fun killEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        SoundClass.playerDeath.play(target.location)
        SoundClass.criticalHit.play(target.location)
    }

    override fun getItem(): ItemStack {
        val item = ItemStack(material)
        val itemMeta = item.itemMeta
        val lore = mutableListOf(" ")
        lore.add("§f§7§l:공격 효과:")
        for (s in hitEffect) lore.add("§f§7$s")
        lore.add(" ")
        lore.add("§f§7§l:처치 효과:")
        for (s in killEffect) lore.add("§f§7$s")
        lore.add(" ")
        lore.add("§f§7대미지 : $damage")
        itemMeta.setDisplayName("§f[§cWEAPON§f] $displayName")
        itemMeta.lore = lore
        for (flag in ItemFlag.values()) itemMeta.addItemFlags(flag)
        itemMeta.isUnbreakable = true
        item.itemMeta = itemMeta
        return item
    }
}