package net.trivernis.superutils

import com.earth2me.essentials.Essentials
import com.onarandombox.MultiverseCore.MultiverseCore
import net.trivernis.superutils.commands.CommandC
import net.trivernis.superutils.commands.CommandH
import net.trivernis.superutils.commands.CommandReload
import net.trivernis.superutils.commands.CommandWp
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.entity.Minecart
import org.bukkit.plugin.java.JavaPlugin

class SuperUtils : JavaPlugin() {

    /**
     * Executed on plugin enable
     */
    override fun onEnable() {
        configure()
        val essentials = getEssentials()
        val commandC = CommandC(getMultiverseCore(), essentials)
        server.pluginManager.registerEvents(EventListener(config, essentials, commandC), this)
        getCommand("reload")?.setExecutor(CommandReload(this))
        getCommand("c")?.setExecutor(commandC)

        if (essentials != null) {
            logger.info("Registering short forms for Essentials plugin features.")
            getCommand("h")?.setExecutor(CommandH(essentials))
            getCommand("wp")?.setExecutor(CommandWp(essentials))
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

    private fun configure() {
        config.addDefault("advancement-payout", 50)
        config.options().copyDefaults(true)
    }
}
