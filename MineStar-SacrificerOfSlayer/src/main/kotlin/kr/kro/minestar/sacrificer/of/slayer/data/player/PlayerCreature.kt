package kr.kro.minestar.sacrificer.of.slayer.data.player

import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Sacrificer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Creature
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.GameWorld
import org.bukkit.entity.Player
import org.reflections.Reflections
import org.reflections.scanners.SubTypesScanner
import java.util.stream.Collectors

class PlayerCreature(val player: Player, val creature: Creature, val gameWorld: GameWorld) {
    companion object {
        /**
         * Field
         */
        private const val slayerPackage = "kr.kro.minestar.sfos.objects.creature.slayer"
        private const val sacrificerPackage = "kr.kro.minestar.sfos.objects.creature.sacrificer"

        private val slayerArray = packageInClasses(slayerPackage)!!.toTypedArray()
        private val sacrificerArray = packageInClasses(sacrificerPackage)!!.toTypedArray()

        /**
         * Constructor function
         */
        internal fun randomSlayer(player: Player, gameWorld: GameWorld): PlayerCreature {
            val creature: Creature? = null
            return PlayerCreature(player, creature!!, gameWorld)
        }

        /**
         * Creature function
         */
        private fun packageInClasses(packageName: String?): Set<Class<*>>? {
            val reflections = Reflections(packageName, SubTypesScanner(false))
            return reflections.getSubTypesOf(Any::class.java).stream().collect(Collectors.toSet())
        }

        private fun getSlayerList(): List<String> {
            val list: MutableList<String> = mutableListOf()
            for (clazz in slayerArray) list.add(clazz.simpleName)
            return list
        }

        private fun getSacrificerList(): List<String> {
            val list: MutableList<String> = mutableListOf()
            for (c in sacrificerArray) list.add(c.simpleName)
            return list
        }

        private fun getSlayer(player: Player, slayerName: String): Slayer? {
            val slayerClass = Class.forName("${slayerPackage}.$slayerName") ?: return null
            val constructor = slayerClass.constructors ?: return null
            return constructor.first().newInstance(player) as Slayer
        }

        private fun getSacrificer(player: Player, sacrificerName: String): Sacrificer? {
            val slayerClass = Class.forName("${sacrificerPackage}.$sacrificerName") ?: return null
            val constructor = slayerClass.constructors
            return constructor.first().newInstance(player) as Sacrificer
        }

        private fun randomSlayer(player: Player): Slayer {
            val slayerClass: Class<*>? = Class.forName("${slayerPackage}." + slayerArray.random().simpleName)
            val constructor = slayerClass!!.constructors
            return constructor.first().newInstance(player) as Slayer
        }

        private fun randomSacrificer(player: Player): Sacrificer {
            val sacrificerClass: Class<*>? = Class.forName("${sacrificerPackage}." + sacrificerArray.random().simpleName)
            val constructor = sacrificerClass!!.constructors
            return constructor.first().newInstance(player) as Sacrificer
        }
    }

    var activeCoolDown = creature.activeSkill?.startCoolTime ?: 0
    var passivePeriod = 0
}