package kr.kro.minestar.sacrificer.of.slayer.data.objects.skill

import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import kr.kro.minestar.sacrificer.of.slayer.data.worlds.GameWorld
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

interface ActiveSkill : Skill {
    val coolTime: Int
    val startCoolTime: Int
    val duration: Int

    fun active(playerCreature: PlayerCreature, gameWorld: GameWorld)

    /*
    fun coolDownLock(): Boolean {
        if (coolDownTimer == null) return false
        player.sendTitle(" ", "§8재사용 대기시간: §7${coolDown / 20.0} §8초", 5, 10, 5)
        return true
    }

    fun coolDownTimer() {
        coolDown = coolTime
        coolDownTimer = Bukkit.getScheduler().runTaskTimer(pl, Runnable {
            --coolDown
            if (coolDown == 0) {
                player.sendTitle(" ", "§9액티브 스킬 준비완료", 5, 10, 5)
                coolDownTimer?.cancel() ?: "is null".toPlayer(player)
                coolDownTimer = null
            }
        }, 0, 1)
    }

    fun startCoolDownTimer() {
        if (coolDown == 0) return
        coolDownTimer = Bukkit.getScheduler().runTaskTimer(pl, Runnable {
            --coolDown
            if (coolDown == 0) {
                player.sendTitle(" ", "§9액티브 스킬 준비완료", 5, 10, 5)
                coolDownTimer?.cancel()?: "is null".toPlayer(player)
                coolDownTimer = null
            }
        }, 0, 1)
    }*/

    fun getItem(): ItemStack {
        val item = ItemStack(Material.IRON_INGOT)
        val itemMeta = item.itemMeta
        val lore = mutableListOf(" ")
        for (s in description) lore.add("§f§7$s")
        lore.add(" ")
        lore.add("§f§7재사용 대기시간 : ${coolTime / 20} 초")
        itemMeta.setDisplayName("§f[§9ACTIVE§f] $name")
        itemMeta.lore = lore
        item.itemMeta = itemMeta
        return item
    }
}