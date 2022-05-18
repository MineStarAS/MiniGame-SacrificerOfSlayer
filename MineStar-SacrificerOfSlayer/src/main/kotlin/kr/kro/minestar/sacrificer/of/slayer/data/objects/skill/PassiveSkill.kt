package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill

import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.sacrificer.passive.SlayerStep
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import kr.kro.minestar.utility.string.toPlayer
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

abstract class PassiveSkill : Skill {
    abstract val period: Int

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

    abstract fun effect(playerCreature: PlayerCreature, worldData: WorldData)

    protected fun canEffectActivation(playerCreature: PlayerCreature): Boolean {
        if (!playerCreature.canPassiveActivation()) {
            playerCreature.removePassivePeriod()
            return false
        }
        playerCreature.resetPassivePeriod(period)
        return true
    }
}