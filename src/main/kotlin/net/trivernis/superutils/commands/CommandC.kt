package net.trivernis.superutils.commands

import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType

class CommandC: CommandExecutor {
    /**
     * Sets the users gamemode to spectator with nightvision or back to survival
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player && command.testPermission(sender)) {
            if (sender.gameMode != GameMode.SPECTATOR) {
                sender.gameMode = GameMode.SPECTATOR
                sender.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(1000000, 255))
            } else {
                sender.removePotionEffect(PotionEffectType.NIGHT_VISION)
                sender.gameMode = GameMode.SURVIVAL
            }
            return true;
        }
        return false;
    }
}