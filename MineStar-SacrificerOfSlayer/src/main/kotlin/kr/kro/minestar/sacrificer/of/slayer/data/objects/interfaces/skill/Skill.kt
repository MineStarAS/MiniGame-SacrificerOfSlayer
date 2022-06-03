package kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill

import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.ObjectClass

abstract class Skill : ObjectClass() {
    abstract val name: String
    abstract val description: MutableList<String>
    abstract val skillType: SkillType
}