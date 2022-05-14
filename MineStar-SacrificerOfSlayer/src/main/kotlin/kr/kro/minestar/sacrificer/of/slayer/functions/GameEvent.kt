package kr.kro.minestar.sacrificer.of.slayer.functions

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.entity.FoodLevelChangeEvent

object GameEvent : Listener {
    private var enable = false
    fun isEnable() = enable
    var damageCancel = true

    fun enableLock() {
        Bukkit.getPluginManager().registerEvents(this, pl)
        enable = true
    }

    fun disableLock() {
        HandlerList.unregisterAll(this)
        enable = false
    }

    @EventHandler
    fun blockPlace(e: BlockPlaceEvent) {
        e.isCancelled = true
    }

    @EventHandler
    fun blockBreak(e: BlockBreakEvent) {
        e.isCancelled = true
    }

    @EventHandler
    fun damageCancel(e: EntityDamageEvent) {
        if (damageCancel) e.isCancelled = true
    }

    @EventHandler
    fun hitCancel(e: EntityDamageByEntityEvent) {
//        if (CreatureClass.creatureMap[e.damager] !is Slayer) e.isCancelled = true
    }

    @EventHandler
    fun deathCancel(e: EntityDeathEvent) {
        val entity = e.entity
        if (entity !is Player) return
        e.isCancelled = true
        entity.gameMode = GameMode.SPECTATOR
    }

    @EventHandler
    fun foodLevelChange(e: FoodLevelChangeEvent) {
        e.isCancelled = true
    }
}