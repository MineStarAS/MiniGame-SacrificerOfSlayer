package kr.kro.minestar.sacrificer.of.slayer.data.objects.creature

import kr.kro.minestar.sacrificer.of.slayer.data.objects.ObjectClass
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces.Tool
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces.Weapon
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces.ActiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces.PassiveSkill

interface Creature {
    val displayName: String
    val weapon: Weapon?
    val activeSkill: ActiveSkill?
    val passiveSkill: PassiveSkill?
    val tool: Tool?
}