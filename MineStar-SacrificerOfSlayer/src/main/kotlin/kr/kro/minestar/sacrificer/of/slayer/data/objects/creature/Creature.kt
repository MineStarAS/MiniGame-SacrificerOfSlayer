package kr.kro.minestar.sacrificer.of.slayer.data.objects.creature

import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.item.tool.Tool
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.item.weapon.Weapon
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.active.ActiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.passive.PassiveSkill

interface Creature {
    val displayName: String
    val weapon: Weapon?
    val activeSkill: ActiveSkill?
    val passiveSkill: PassiveSkill?
    val tool: Tool?
}