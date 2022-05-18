package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.slayer.active

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.ActiveSkill
import kr.kro.minestar.sacrificer.of.slayer.data.objects.skill.SkillType
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.WorldData
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
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

object ThrowDagger : ActiveSkill() {

    override val name: String = "단검 투척"
    override val description = mutableListOf(
        "5블럭 이내에 있는 플레이어에게",
        "단검을 던져 '독 II'을 5초간 부여 합니다"
    )
    override val coolTime = 20 * 20
    override val startCoolTime = coolTime
    override val duration = 20 * 5
    override val skillType = SkillType.ATTACK

    override fun activeEffect(playerCreature: PlayerCreature, worldData: WorldData) {
        val player = playerCreature.player
        val players = player.location.getNearbyPlayers(5.0)
        for (p in players) {
            if (p != player) {
                var distance = 0.0
                val location = player.eyeLocation.look(p.location)
                while (true) {
                    if (distance > 5) break
                    val offsetLocation = location.clone().offset(distance)
                    distance += 0.1
                    if (offsetLocation.block.type != Material.AIR) break
                    val targets = offsetLocation.getNearbyPlayers(0.0)
                    if (targets.toTypedArray().isNotEmpty())
                        for (target in targets) if (target != player)
                            if (target.gameMode != GameMode.SPECTATOR) {
                                target.addPotionEffect(PotionEffect(PotionEffectType.POISON, duration, 1, false, false, true))
                                break
                            }
                }
            }
        }
        throwDaggerSound(player)
    }

    private fun throwDaggerSound(player: Player) {
        val scheduler = Scheduler(pl)
        val interval = 3L
        val sound = PlaySound().apply {
            soundCategory = SoundCategory.RECORDS
            sound = Sound.ENTITY_PLAYER_ATTACK_SWEEP
            volume = 0.7F
            pitch = 1.2F
        }
        scheduler.addRun(RunNow { sound.play(player.location) })
        scheduler.addRun(RunLater({ sound.play(player.location) }, interval))
        scheduler.addRun(RunLater({ sound.play(player.location) }, interval))
        scheduler.play()
    }
}