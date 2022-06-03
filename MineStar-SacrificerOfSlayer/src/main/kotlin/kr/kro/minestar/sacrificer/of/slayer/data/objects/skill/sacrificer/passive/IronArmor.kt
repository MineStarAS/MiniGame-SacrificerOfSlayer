package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.sacrificer.passive

import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.passive.DamagedPassiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.SkillType
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerData
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import kr.kro.minestar.utility.sound.PlaySound
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.event.Event
import org.bukkit.event.entity.EntityDamageEvent

object IronArmor : DamagedPassiveSkill() {
    override val name: String = "보호구"
    override val description = mutableListOf("모든 데미지를 50% 적게 받습니다.")
    override val skillType = SkillType.DEFENSE

    override fun effect(playerData: PlayerData, worldData: WorldData, e: Event?) {
        if (e !is EntityDamageEvent) return
        e.damage = e.damage / 2
        defensingSound.play(e.entity.location)
    }

    private val defensingSound = PlaySound().apply {
        soundCategory = SoundCategory.RECORDS
        sound = Sound.ITEM_ARMOR_EQUIP_IRON
        pitch = 2.0F
    }
}