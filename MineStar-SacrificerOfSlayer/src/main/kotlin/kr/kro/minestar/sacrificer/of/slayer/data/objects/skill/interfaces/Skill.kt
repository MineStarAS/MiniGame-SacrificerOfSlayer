package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces

import kr.kro.minestar.sacrificer.of.slayer.data.objects.ObjectClass

abstract class Skill : ObjectClass() {
    abstract val name: String
    abstract val description: MutableList<String>
    abstract val skillType: SkillType
}