package kr.kro.minestar.sacrificer.of.slayer.data.objects.creature

import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces.Tool
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.interfaces.Weapon
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.tool.JumpBomb
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.tool.TestSubjectBlood
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.weapon.BlastHammer
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.weapon.DangerousSyringe
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.weapon.MurderAxe
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.slayer.weapon.PoisonBlade
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces.ActiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.interfaces.PassiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.active.Dash
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.active.ImpactGrenade
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.active.ParalysisGasGrenade
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.active.ThrowDagger
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.passive.BangBangBomb
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.passive.Chase
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.passive.Hide

enum class Slayer : Creature {
    SLAUGHTERER(
        "도살자", MurderAxe, Dash, Chase,
        150
    ),
    ASSASSIN(
        "암살자", PoisonBlade, ThrowDagger, Hide,
        100
    ),
    BLAST_MANIAC(
        "폭파광", BlastHammer, ImpactGrenade, BangBangBomb, JumpBomb,
        130
    ),
    MADDEST_DOCTOR(
        "미치광이 의사", DangerousSyringe, ParalysisGasGrenade, BangBangBomb, TestSubjectBlood,
        120
    ),
    SHADOW(
        "그림자", DangerousSyringe, ImpactGrenade, BangBangBomb, TestSubjectBlood,
        100
    ),
    ;

    val maxHealth: Int
    override val displayName: String
    override val weapon: Weapon
    override val activeSkill: ActiveSkill
    override val passiveSkill: PassiveSkill
    override val tool: Tool?

    constructor(
        displayName: String, weapon: Weapon, activeSkill: ActiveSkill, passiveSkill: PassiveSkill,
        maxHealth: Int
    ) {
        this.maxHealth = maxHealth
        this.displayName = displayName
        this.weapon = weapon
        this.activeSkill = activeSkill
        this.passiveSkill = passiveSkill
        this.tool = null
    }

    constructor(
        displayName: String, weapon: Weapon, activeSkill: ActiveSkill, passiveSkill: PassiveSkill, tool: Tool,
        maxHealth: Int
    ) {
        this.maxHealth = maxHealth
        this.displayName = displayName
        this.weapon = weapon
        this.activeSkill = activeSkill
        this.passiveSkill = passiveSkill
        this.tool = tool
    }
}