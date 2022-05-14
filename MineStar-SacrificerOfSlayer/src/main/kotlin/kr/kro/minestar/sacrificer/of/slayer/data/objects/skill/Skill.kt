package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill

import org.bukkit.event.Event

interface Skill {
    val name: String
    val description: MutableList<String>
    val skillType: SkillType

    fun codeName() = this.javaClass.simpleName ?: "NullSkill"
}