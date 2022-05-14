package kr.kro.minestar.sacrificer.of.slayer.data.objects.creature

import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.tool.Tool
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.weapon.Weapon
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.ActiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.PassiveSkill

interface Creature {
    val displayName: String
    val weapon: Weapon?
    val activeSkill: ActiveSkill?
    val passiveSkill: PassiveSkill?
    val tool: Tool?
}