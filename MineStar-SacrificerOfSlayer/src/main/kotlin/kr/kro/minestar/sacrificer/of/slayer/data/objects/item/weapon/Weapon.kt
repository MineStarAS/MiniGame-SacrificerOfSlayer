@file:Suppress("DEPRECATION")

package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.weapon

import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.Item
import kr.kro.minestar.sacrificer.of.slayer.functions.SoundClass
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

abstract class Weapon : Item {
    abstract override val displayName: String
    protected abstract val damage: Double
    protected abstract val hitEffect: List<String>
    protected abstract val killEffect: List<String>

    fun hit(e: EntityDamageByEntityEvent) {
        e.isCancelled = true

        val target = e.entity
        val attacker = e.damager

        if (target !is Player) return
        if (attacker !is Player) return

        val weaponItem = attacker.inventory.itemInMainHand

        if (!isSameItem(weaponItem)) return

        val damage = (e.damager as Player).attackCooldown * damage
        if ((e.entity as Player).health <= damage) killEffect(e)
        e.damage = damage
        e.isCancelled = false
        hitEffect(e)
    }

    protected open fun hitEffect(e: EntityDamageByEntityEvent) {
    }

    protected open fun killEffect(e: EntityDamageByEntityEvent) {
        val target = e.entity as Player
        target.gameMode = GameMode.SPECTATOR
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