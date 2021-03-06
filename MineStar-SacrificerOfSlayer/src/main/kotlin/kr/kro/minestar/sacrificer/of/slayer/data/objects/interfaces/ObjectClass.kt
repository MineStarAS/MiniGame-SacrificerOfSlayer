package kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces

abstract class ObjectClass {

    init {
        if (this is Grenade) init(className())
    }

    fun className() = javaClass.simpleName ?: ""
}