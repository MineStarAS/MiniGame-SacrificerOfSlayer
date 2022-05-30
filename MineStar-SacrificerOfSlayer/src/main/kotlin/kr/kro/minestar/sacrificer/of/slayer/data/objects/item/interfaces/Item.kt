package kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces

import kr.kro.minestar.sacrificer.of.slayer.data.objects.ObjectClass
import kr.kro.minestar.utility.item.isSameItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

abstract class Item : ObjectClass() {
    abstract val material: Material
    abstract val displayName: String

    abstract fun getItem() : ItemStack

    fun isSameItem(item: ItemStack) = getItem().isSameItem(item)
}