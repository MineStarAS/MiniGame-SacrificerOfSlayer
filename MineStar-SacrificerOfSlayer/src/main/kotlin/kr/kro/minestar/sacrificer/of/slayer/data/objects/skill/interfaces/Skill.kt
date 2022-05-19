package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces

interface Skill {
    val name: String
    val description: MutableList<String>
    val skillType: SkillType

    fun codeName() = this.javaClass.simpleName ?: "NullSkill"
}