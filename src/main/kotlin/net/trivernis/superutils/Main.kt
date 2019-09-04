package net.trivernis.superutils

import com.earth2me.essentials.Essentials
import com.onarandombox.MultiverseCore.MultiverseCore
import net.trivernis.superutils.commands.CommandC
import net.trivernis.superutils.commands.CommandH
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    /**
     * Executed on plugin enable
     */
    override fun onEnable() {
        logger.info("SuperUtils enabled.")
        getCommand("c")?.setExecutor(CommandC(getMultiverseCore()))

        val essentials = getEssentials()
        if (essentials != null) {
            logger.info("Registering short forms for Essentials plugin features.")
            getCommand("h")?.setExecutor(CommandH(essentials))
        }
    }

    /**
     * Executed on plugin disable
     */
    override fun onDisable() {

    }

    /**
     * Returns instance of the essentials plugin
     */
    private fun getEssentials(): Essentials? {
        val essentials = server.pluginManager.getPlugin("Essentials");
        return if (essentials != null && essentials is Essentials)
            essentials;
        else
            null;
    }

    /**
     * Returns instance of multiverse plugin
     */
    private fun getMultiverseCore(): MultiverseCore? {
        val multiverseCore = server.pluginManager.getPlugin("Multiverse-Core")
        return if (multiverseCore != null && multiverseCore is MultiverseCore)
            multiverseCore
        else
            null
    }
}
