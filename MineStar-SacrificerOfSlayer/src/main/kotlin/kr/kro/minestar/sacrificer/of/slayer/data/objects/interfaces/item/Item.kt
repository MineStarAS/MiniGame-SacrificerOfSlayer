package kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.item

import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.ObjectClass
import kr.kro.minestar.utility.item.isSameItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

abstract class Item : ObjectClass() {
    abstract val material: Material
    abstract val displayName: String

    abstract fun getItem() : ItemStack

    fun isSameItem(item: ItemStack) = getItem().isSameItem(item)
}