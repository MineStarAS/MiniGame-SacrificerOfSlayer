package kr.kro.minestar.sacrificer.of.slayer.data.objects.creature

import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.tool.JumpBomb
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.tool.Tool
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.weapon.BlastHammer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.weapon.HiddenDagger
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.weapon.MurderAxe
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.weapon.Weapon
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.ActiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.PassiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.active.Dash
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.active.ImpactGrenade
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.active.ThrowDagger
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.passive.BangBangBomb
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.passive.Chase
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.passive.Hide

enum class Slayer : Creature {
    SLAUGHTER("도살자", MurderAxe, Dash, Chase),
    ASSASSIN("암살자", HiddenDagger, ThrowDagger, Hide),
    BLAST_MANIAC("폭파광", BlastHammer, ImpactGrenade, BangBangBomb, JumpBomb),
    ;

    override val displayName: String
    override val weapon: Weapon
    override val activeSkill: ActiveSkill
    override val passiveSkill: PassiveSkill
    override val tool: Tool?

    constructor(displayName: String, weapon: Weapon, activeSkill: ActiveSkill, passiveSkill: PassiveSkill) {
        this.displayName = displayName
        this.weapon = weapon
        this.activeSkill = activeSkill
        this.passiveSkill = passiveSkill
        this.tool = null
    }

    constructor(displayName: String, weapon: Weapon, activeSkill: ActiveSkill, passiveSkill: PassiveSkill, tool: Tool) {
        this.displayName = displayName
        this.weapon = weapon
        this.activeSkill = activeSkill
        this.passiveSkill = passiveSkill
        this.tool = tool
    }
}