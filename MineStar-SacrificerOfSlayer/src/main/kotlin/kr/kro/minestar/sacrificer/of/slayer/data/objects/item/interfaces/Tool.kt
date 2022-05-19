package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces

import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces.SkillType
import kr.kro.minestar.utility.item.addLore
import kr.kro.minestar.utility.item.amount
import kr.kro.minestar.utility.item.display
import kr.kro.minestar.utility.material.item
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

interface Tool : Item {
    val skillType: SkillType
    val description: List<String>
    var amount: Int

    fun use(e: PlayerInteractEvent) {
        if (e.hand != EquipmentSlot.HAND) return
        val item = e.player.inventory.itemInMainHand
        if (isSameItem(item)) if(used(e)) --item.amount
    }

    fun used(e: PlayerInteractEvent): Boolean

    override fun getItem(): ItemStack {
        val item = material.item().display("§f[§cTOOL§f] $displayName").amount(amount)
        for (s in description) item.addLore("§f§7$s")
        return item
    }
}