package kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.passive

import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerData
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import org.bukkit.event.Event

abstract class TickPassiveSkill : PassiveSkill() {
    abstract val period: Int

    protected fun canEffectActivation(playerData: PlayerData): Boolean {
        if (!playerData.canPassiveActivation()) {
            playerData.removePassivePeriod()
            return false
        }
        playerData.resetPassivePeriod(period)
        return true
    }

    override fun effectActivation(playerData: PlayerData, worldData: WorldData, e: Event?) {
        if (e != null) return
        effect(playerData, worldData, null)
    }
}