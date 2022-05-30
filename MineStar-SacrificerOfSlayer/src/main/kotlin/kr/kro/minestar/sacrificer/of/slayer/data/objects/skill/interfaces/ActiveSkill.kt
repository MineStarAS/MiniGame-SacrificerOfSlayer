package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces

import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerData
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

abstract class ActiveSkill : Skill {
    protected abstract val coolTime: Int
    abstract val startCoolTime: Int
    protected abstract val duration: Int

    fun useActiveSkill(playerData: PlayerData, worldData: WorldData) {
        playerData.resetActiveCoolTime(coolTime)
        activeEffect(playerData, worldData)
    }

    protected abstract fun activeEffect(playerData: PlayerData, worldData: WorldData)

    fun getItem(): ItemStack {
        val item = ItemStack(Material.IRON_INGOT)
        val itemMeta = item.itemMeta
        val lore = mutableListOf(" ")
        for (s in description) lore.add("§f§7$s")
        lore.add(" ")
        lore.add("§f§7재사용 대기시간 : ${coolTime / 20} 초")
        itemMeta.setDisplayName("§f[§9ACTIVE§f] $name")
        itemMeta.lore = lore
        item.itemMeta = itemMeta
        return item
    }
}