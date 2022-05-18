package kr.kro.minestar.sacrificer.of.slayer.data.objects.creature

import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.tool.MasterKey
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.tool.SwitchPosition
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.tool.Tool
import kr.kro.minestar.sacrificer.of.slayer.data.objects.item.weapon.Weapon
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.ActiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.PassiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.sacrificer.passive.IronArmor
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.sacrificer.passive.SlayerStep

enum class Sacrificer : Creature {
    KEY_MASTER("열쇠공", MasterKey),
    LISTENER("리스너", SlayerStep),
    EXCHANGER("익스체인저", SwitchPosition),
    IRON_KNIGHT("아이언 나이트", IronArmor),
    ;

    override val displayName: String
    override val weapon: Weapon?
    override val activeSkill: ActiveSkill?
    override val passiveSkill: PassiveSkill?
    override val tool: Tool?

    constructor(displayName: String) {
        this.displayName = displayName
        this.weapon = null
        this.activeSkill = null
        this.passiveSkill = null
        this.tool = null
    }

    constructor(displayName: String, weapon: Weapon) {
        this.displayName = displayName
        this.weapon = weapon
        this.activeSkill = null
        this.passiveSkill = null
        this.tool = null
    }

    constructor(displayName: String, activeSkill: ActiveSkill) {
        this.displayName = displayName
        this.weapon = null
        this.activeSkill = activeSkill
        this.passiveSkill = null
        this.tool = null
    }

    constructor(displayName: String, passiveSkill: PassiveSkill) {
        this.displayName = displayName
        this.weapon = null
        this.activeSkill = null
        this.passiveSkill = passiveSkill
        this.tool = null
    }

    constructor(displayName: String, tool: Tool) {
        this.displayName = displayName
        this.weapon = null
        this.activeSkill = null
        this.passiveSkill = null
        this.tool = tool
    }

    constructor(displayName: String, weapon: Weapon?, activeSkill: ActiveSkill?, passiveSkill: PassiveSkill?, tool: Tool?) {
        this.displayName = displayName
        this.weapon = weapon
        this.activeSkill = activeSkill
        this.passiveSkill = passiveSkill
        this.tool = tool
    }
}