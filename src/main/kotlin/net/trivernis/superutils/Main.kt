package net.trivernis.superutils

import com.earth2me.essentials.Essentials
import com.earth2me.essentials.Trade
import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.potion.PotionEffectType

class Main : JavaPlugin() {

    override fun onEnable() {

    }

    override fun onDisable() {

    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            if (command.name.equals("c", ignoreCase = true) && sender.hasPermission("superutils.c")) {
                if (sender.gameMode != GameMode.SPECTATOR) {
                    sender.gameMode = GameMode.SPECTATOR
                    sender.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(1000000, 255))
                } else {
                    sender.removePotionEffect(PotionEffectType.NIGHT_VISION)
                    sender.gameMode = GameMode.SURVIVAL
                }
                return true
            } else if (command.name.equals("h", ignoreCase = true) && sender.hasPermission("superutils.h")) {
                val essentials = getEssentials()
                if (essentials != null) {
                    val essUser = essentials.getUser(sender);
                    val userHome = essUser.getHome("home");
                    essUser.teleport.teleport(userHome.block.location, null, PlayerTeleportEvent.TeleportCause.COMMAND);
                    essUser.sendMessage("You have been teleported home.");
                }
            }
        }
        return false;
    }

    private fun getEssentials(): Essentials? {
        val essentials = server.pluginManager.getPlugin("Essentials");
        return if (essentials != null && essentials is Essentials)
            essentials;
        else
            null;
    }
}
