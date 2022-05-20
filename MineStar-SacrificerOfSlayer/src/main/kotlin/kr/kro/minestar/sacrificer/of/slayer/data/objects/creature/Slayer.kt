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
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.active.ThrowDagger
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.passive.BangBangBomb
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.passive.Chase
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.passive.Hide

enum class Slayer : Creature {
    SLAUGHTERER(
        "도살자", MurderAxe, Dash, Chase,
        120, 20
    ),
    ASSASSIN(
        "암살자", PoisonBlade, ThrowDagger, Hide,
        60, 10
    ),
    BLAST_MANIAC(
        "폭파광", BlastHammer, ImpactGrenade, BangBangBomb, JumpBomb,
        80, 10
    ),
    MADDEST_DOCTOR(
        "미치광이 의사", DangerousSyringe, ImpactGrenade, BangBangBomb, TestSubjectBlood,
        50, 20
    ),
    ;

    val baseMaxHealth: Int
    val extraMaxHealth: Int
    override val displayName: String
    override val weapon: Weapon
    override val activeSkill: ActiveSkill
    override val passiveSkill: PassiveSkill
    override val tool: Tool?

    constructor(
        displayName: String, weapon: Weapon, activeSkill: ActiveSkill, passiveSkill: PassiveSkill,
        baseMaxHealth: Int, extraMaxHealth: Int
    ) {
        this.baseMaxHealth = baseMaxHealth
        this.extraMaxHealth = extraMaxHealth
        this.displayName = displayName
        this.weapon = weapon
        this.activeSkill = activeSkill
        this.passiveSkill = passiveSkill
        this.tool = null
    }

    constructor(
        displayName: String, weapon: Weapon, activeSkill: ActiveSkill, passiveSkill: PassiveSkill, tool: Tool,
        baseMaxHealth: Int, extraMaxHealth: Int
    ) {
        this.baseMaxHealth = baseMaxHealth
        this.extraMaxHealth = extraMaxHealth
        this.displayName = displayName
        this.weapon = weapon
        this.activeSkill = activeSkill
        this.passiveSkill = passiveSkill
        this.tool = tool
    }
}