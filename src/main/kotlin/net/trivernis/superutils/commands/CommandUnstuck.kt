package net.trivernis.superutils.commands

import com.earth2me.essentials.Essentials
import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent
import net.md_5.bungee.api.chat.TextComponent

class CommandUnstuck(private val essentials: Essentials): CommandExecutor {
    /**
     * Teleports the player to the next air block above.
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        return if (command.testPermission(sender) && sender is Player) {
            val essUser = essentials.getUser(sender)
            val location = sender.location
            while (location.block.type != Material.AIR)
                location.add(.0, 1.0, .0)
            essUser.teleport.teleport(location, null, PlayerTeleportEvent.TeleportCause.PLUGIN)
            true
        } else {
            val message = TextComponent("You do not have permission!")
            message.color = ChatColor.RED
            sender.spigot().sendMessage(message)
            true
        }
    }
}