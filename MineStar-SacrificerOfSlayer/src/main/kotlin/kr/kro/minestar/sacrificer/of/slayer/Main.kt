package kr.kro.minestar.sacrificer.of.slayer

import kr.kro.minestar.sacrificer.of.slayer.functions.AlwaysEvent
import kr.kro.minestar.sacrificer.of.slayer.functions.WorldClass
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class Main : JavaPlugin() {
    companion object {
        lateinit var pl: Main
        const val prefix = "§f[§9PLUGIN§f]"
    }

    override fun onEnable() {
        pl = this
        logger.info("$prefix §aEnable")
        getCommand("sos")?.setExecutor(Command)


        WorldClass.reloadEnable()

        if (!isReload()) {
            createReloadCheckFile()
            WorldClass.deleteRacingWorlds()
        }
    }

    override fun onDisable() {
        for (player in Bukkit.getOnlinePlayers()) {
            try {
                player.closeInventory()
            } catch (_: Exception) {
            }
        }
        WorldClass.saveDesignWorlds()

        AlwaysEvent
    }

    /**
     * Check reload function
     */
    private fun reloadCheckFile() = File(dataFolder, "on")

    private fun createReloadCheckFile() {
        val file = reloadCheckFile()
        if (file.exists()) return
        file.createNewFile()
        file.deleteOnExit()
    }

    private fun isReload() = reloadCheckFile().exists()
}