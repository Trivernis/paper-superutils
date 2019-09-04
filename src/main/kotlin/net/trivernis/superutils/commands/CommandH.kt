package net.trivernis.superutils.commands

import com.earth2me.essentials.Essentials
import com.earth2me.essentials.UserData
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent
import java.lang.Exception

class CommandH(private var essentials: Essentials) : CommandExecutor, TabCompleter {

    /**
     * Tab completion suggestions for homes
     */
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        return if (sender is Player && command.testPermission(sender)) {
            if (args.isNotEmpty()) {
                essentials.getUser(sender).homes.filter {it.contains(args.first())}.toMutableList()
            } else {
                essentials.getUser(sender).homes
            }
        } else {
            emptyList<String>().toMutableList()
        }
    }

    /**
     * Teleports the user to the default home or a specified one
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player && command.testPermission(sender)) {
            val essUser = essentials.getUser(sender)
            val userHome: Location
            userHome = if (args.isNotEmpty()) {
                try {
                    essUser.getHome(args[0])
                } catch (e: Exception) {
                    null
                } as Location
            } else {
                essUser.getHome("home")
            }
            if (userHome != null) {
                essUser.teleport.teleport(userHome.block.location, null, PlayerTeleportEvent.TeleportCause.COMMAND)
                essUser.sendMessage("You have been teleported home.")
            } else {
                essUser.sendMessage("The specified home was not found.")
            }
            return true
        } else {
            sender.sendMessage("This command can only be executed by a player.");
        }
        return false
    }
}