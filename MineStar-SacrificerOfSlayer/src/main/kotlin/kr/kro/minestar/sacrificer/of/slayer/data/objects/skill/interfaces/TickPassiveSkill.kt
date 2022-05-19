package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces

import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import org.bukkit.event.Event

abstract class TickPassiveSkill : PassiveSkill() {
    abstract val period: Int

    protected fun canEffectActivation(playerCreature: PlayerCreature): Boolean {
        if (!playerCreature.canPassiveActivation()) {
            playerCreature.removePassivePeriod()
            return false
        }
        playerCreature.resetPassivePeriod(period)
        return true
    }

    override fun effectActivation(playerCreature: PlayerCreature, worldData: WorldData, e: Event?) {
        if (e != null) return
        effect(playerCreature, worldData, null)
    }
}