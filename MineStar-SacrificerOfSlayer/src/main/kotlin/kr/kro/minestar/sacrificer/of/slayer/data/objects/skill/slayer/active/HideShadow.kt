package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.active

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.SkillType
import kr.kro.minestar.sacrificer.of.slayer.data.objects.interfaces.skill.active.ActiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerData
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
import kr.kro.minestar.sacrificer.of.slayer.functions.UtilityClass
import kr.kro.minestar.sacrificer.of.slayer.functions.UtilityClass.effect
import kr.kro.minestar.sacrificer.of.slayer.functions.UtilityClass.give
import kr.kro.minestar.utility.location.look
import kr.kro.minestar.utility.location.offset
import kr.kro.minestar.utility.scheduler.Scheduler
import kr.kro.minestar.utility.scheduler.objectes.later.RunLater
import kr.kro.minestar.utility.scheduler.objectes.now.RunNow
import kr.kro.minestar.utility.sound.PlaySound
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType

object HideShadow : ActiveSkill() {

    override val name: String = "그림자 숨기"
    override val description = mutableListOf(
        "20블럭 이내에 있는 플레이어를 바라보고",
        "스킬을 사용하면 '투명화'상태로 대상의 그림자에 숨어듭니다"
    )
    override val coolTime = 20 * 20
    override val startCoolTime = coolTime
    override val skillType = SkillType.ATTACK

    override fun activeEffect(playerData: PlayerData, worldData: WorldData): Boolean {
        val player = playerData.player
        val target = UtilityClass.aimTargeting(player, 30.0) ?: return false
        target.setPassenger(player)
        return true
    }
}