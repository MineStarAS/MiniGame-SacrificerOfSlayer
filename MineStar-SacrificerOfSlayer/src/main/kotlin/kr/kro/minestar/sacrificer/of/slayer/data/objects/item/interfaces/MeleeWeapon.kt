package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces

import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldEvent
import org.bukkit.entity.Player
import org.bukkit.event.entity.EntityDamageByEntityEvent

abstract class MeleeWeapon : Weapon() {
    override fun hit(e: EntityDamageByEntityEvent, worldEvent: WorldEvent) {
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
}