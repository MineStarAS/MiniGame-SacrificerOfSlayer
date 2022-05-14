package kr.kro.minestar.sacrificer.of.slayer.data.objects.item

import kr.kro.minestar.utility.item.isSameItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

interface Item {
    val material: Material
    val displayName: String

    fun getItem() : ItemStack

    fun isSameItem(item: ItemStack) = getItem().isSameItem(item)
}