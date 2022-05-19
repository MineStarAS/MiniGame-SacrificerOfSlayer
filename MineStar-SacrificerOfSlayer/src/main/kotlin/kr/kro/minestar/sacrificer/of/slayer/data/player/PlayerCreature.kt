package kr.kro.minestar.sacrificer.of.slayer.data.player

import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Creature
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Sacrificer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces.TickPassiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import kr.kro.minestar.utility.number.round
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.player.PlayerInteractEvent

@Suppress("DEPRECATION")
class PlayerCreature(val player: Player, val worldData: WorldData, val creature: Creature) {
    companion object {
        internal fun randomSlayer(player: Player, worldData: WorldData): PlayerCreature {
            val creature = Slayer.values().random()
            return PlayerCreature(player, worldData, creature)
        }

        internal fun randomSacrificer(player: Player, worldData: WorldData): PlayerCreature {
            val creature = Sacrificer.values().random()
            return PlayerCreature(player, worldData, creature)
        }
    }

    init {
        player.health = player.maxHealth
        for (potionEffect in player.activePotionEffects) player.removePotionEffect(potionEffect.type)
        val inventory = player.inventory
        inventory.clear()
        inventory.setItem(0, creature.weapon?.getItem())
        inventory.setItem(1, creature.activeSkill?.getItem())
        inventory.setItem(2, creature.passiveSkill?.getItem())
        inventory.setItem(3, creature.tool?.getItem())

        val creatureType = if (creature is Slayer) "§cSlayer"
        else "§9Sacrificer"

        player.sendTitle(creatureType, "§7당신의 역할은 §e${creature.displayName} §7입니다")
    }

    /**
     * Health function
     */
    private var maxHealth = 0.0
    fun maxHealth() = maxHealth

    private var health = maxHealth
    fun health() = health

    fun addHealth(double: Double) {
        if (double <= 0) return
        this.health += health
        if (health > maxHealth) health = maxHealth
        player.health = health / maxHealth * 20
    }

    fun removeHealth(double: Double) {
        if (double <= 0) return
        health -= double
        if (health < 0) health = 0.0
        player.health = health / maxHealth * 20
    }

    /**
     * Active coolTime function
     */
    private var activeCoolTime = creature.activeSkill?.startCoolTime ?: 0
    fun removeActiveCoolTime() {
        if (activeCoolTime <= 0) return
        activeCoolTime--
        if (activeCoolTime == 0) player.sendTitle(" ", "§9액티브 스킬 준비완료", 5, 10, 5)
    }

    fun resetActiveCoolTime(coolTime: Int) {
        activeCoolTime = coolTime
    }

    private fun canUseActiveSkill(): Boolean = activeCoolTime <= 0

    fun useActiveSkill() {
        val activeSkill = creature.activeSkill ?: return
        if (!canUseActiveSkill()) {
            val displayCoolTime = (activeCoolTime / 20.0).round(1)
            player.sendTitle(" ", "§8재사용 대기시간: §7$displayCoolTime §8초", 5, 10, 5)
            return
        }
        activeSkill.useActiveSkill(this, worldData)
    }

    /**
     * Passive period function
     */
    private var passivePeriod = if (creature.passiveSkill is TickPassiveSkill) (creature.passiveSkill!! as TickPassiveSkill).period
    else 0

    fun removePassivePeriod() {
        if (passivePeriod <= 0) return
        passivePeriod--
    }

    fun resetPassivePeriod(period: Int) {
        passivePeriod = period
    }

    fun canPassiveActivation(): Boolean = passivePeriod <= 0

    fun passiveActivation(e: Event?) = creature.passiveSkill?.effectActivation(this, worldData, e)

    /**
     * Tool function
     */
    fun useTool(e: PlayerInteractEvent) = creature.tool?.use(e)
}