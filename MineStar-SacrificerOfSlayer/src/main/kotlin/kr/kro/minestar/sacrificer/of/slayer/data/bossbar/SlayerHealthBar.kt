package kr.kro.minestar.sacrificer.of.slayer.data.bossbar

import kr.kro.minestar.sacrificer.of.slayer.Main.Companion.pl
import kr.kro.minestar.sacrificer.of.slayer.data.objects.creature.Slayer
import kr.kro.minestar.sacrificer.of.slayer.data.player.PlayerCreature
import org.bukkit.Bukkit
import org.bukkit.boss.BarColor
import org.bukkit.boss.BarStyle

class SlayerHealthBar(val playerCreature: PlayerCreature) {
    private val bar = Bukkit.createBossBar("", BarColor.BLUE, BarStyle.SEGMENTED_10).apply {
        if (playerCreature.creature !is Slayer) return@apply
        if (playerCreature.worldData.slayerHealthBar != null) playerCreature.worldData.slayerHealthBar?.disable()

        val percent = if (playerCreature.health() < 0) 0.0
        else if (playerCreature.health() > playerCreature.maxHealth()) 1.0
        else playerCreature.health() / playerCreature.maxHealth()

        progress = percent
        setTitle("§e${playerCreature.player.name} §c[${playerCreature.health().toInt()}/${playerCreature.maxHealth().toInt()}]")

        for (player in playerCreature.worldData.worldPlayers()) addPlayer(player)
        isVisible = true

        playerCreature.worldData.slayerHealthBar = this@SlayerHealthBar
        Bukkit.getScheduler().runTaskLater(pl, Runnable { disable() }, 20 * 3)
    }

    private fun disable() {
        bar.removeAll()
        if (playerCreature.worldData.slayerHealthBar == this)
            playerCreature.worldData.slayerHealthBar = null
    }
}