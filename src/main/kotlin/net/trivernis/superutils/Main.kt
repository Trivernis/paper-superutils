package net.trivernis.superutils

import com.earth2me.essentials.Essentials
import net.trivernis.superutils.commands.CommandC
import net.trivernis.superutils.commands.CommandH
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    /**
     * Executed on plugin enable
     */
    override fun onEnable() {
        val c = getCommand("c")
        if (c != null) {
            c.setExecutor(CommandC())
        }

        val essentials = getEssentials()
        if (essentials != null) {
            val h = getCommand("h")
            if (h != null) {
                h.setExecutor(CommandH(essentials))
            }
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
}
