package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces

import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Sacrificer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldEvent
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.entity.EntityDamageByEntityEvent

abstract class RangedWeapon : Weapon() {
    override fun hit(e: EntityDamageByEntityEvent, worldEvent: WorldEvent) {
        e.isCancelled = true

        if (e.damager !is Projectile) return
        if (e.entity !is Player) return

        val attacker = (e.damager as Projectile).shooter as Player
        val attackerCreature = worldEvent.getCreature(attacker) ?: return

        val target = e.entity as Player
        val targetCreature = worldEvent.getCreature(target) ?: return

        if (attackerCreature.creature is Slayer && targetCreature.creature is Slayer) return
        if (attackerCreature.creature is Sacrificer && targetCreature.creature is Sacrificer) return

        val weaponItem = attacker.inventory.itemInMainHand

        if (!isSameItem(weaponItem)) return

        val damage = this.damage
        if ((e.entity as Player).health <= damage) killEffect(e)
        e.damage = damage
        e.isCancelled = false
        hitEffect(e)
    }
}