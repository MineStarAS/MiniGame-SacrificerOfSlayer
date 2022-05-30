package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces

import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Sacrificer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldEvent
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityShootBowEvent

abstract class RangedWeapon : Weapon() {

    abstract val shootEffect: List<String>

    abstract fun shootEffect(e: EntityShootBowEvent)

    override fun hit(e: EntityDamageByEntityEvent, worldEvent: WorldEvent) {
        e.isCancelled = true

        if (e.damager !is Projectile) return
        if (e.entity !is Player) return

        val attacker = (e.damager as Projectile).shooter as Player
        val attackerData = worldEvent.getCreature(attacker) ?: return

        val target = e.entity as Player
        val targetCreature = worldEvent.getCreature(target) ?: return

        if (attackerData.creature is Slayer && targetCreature.creature is Slayer) return
        if (attackerData.creature is Sacrificer && targetCreature.creature is Sacrificer) return

        val weaponItem = attacker.inventory.itemInMainHand

        if (!isSameItem(weaponItem)) return

        val force = e.damager.customName?.toDoubleOrNull() ?: 1.0
        val damage = this.damage * force
        if ((e.entity as Player).health <= damage) killEffect(e)
        e.damage = damage
        e.isCancelled = false
        hitEffect(e)
    }
}