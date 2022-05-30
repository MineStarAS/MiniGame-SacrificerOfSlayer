package kr.kro.minestar.sacrificer.of.slayer.data.bossbar

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerData
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle

class SlayerHealthBar(val playerData: PlayerData) {
    private val bar = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SEGMENTED_10).apply {
        if (playerData.creature !is Slayer) return@apply
        if (playerData.worldData.slayerHealthBar != null) playerData.worldData.slayerHealthBar?.disable()

        val percent = if (playerData.health() < 0) 0.0
        else if (playerData.health() > playerData.maxHealth()) 1.0
        else playerData.health() / playerData.maxHealth()

        progress = percent
        setTitle("§e${playerData.player.name} §c[${playerData.health().toInt()}/${playerData.maxHealth().toInt()}]")

        for (player in playerData.worldData.worldPlayers()) addPlayer(player)
        isVisible = true

        playerData.worldData.slayerHealthBar = this@SlayerHealthBar
        Bukkit.getScheduler().runTaskLater(pl, Runnable { disable() }, 20 * 3)
    }

    private fun disable() {
        bar.removeAll()
        if (playerData.worldData.slayerHealthBar == this)
            playerData.worldData.slayerHealthBar = null
    }
}