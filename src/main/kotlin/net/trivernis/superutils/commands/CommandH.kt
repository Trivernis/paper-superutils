package net.trivernis.superutils.commands

import com.earth2me.essentials.Essentials
import com.earth2me.essentials.UserData
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent

class CommandH(private var essentials: Essentials) : CommandExecutor {

    /**
     * Teleports the user to the default home or a specified one
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player && command.testPermission(sender)) {
            val essUser = essentials.getUser(sender)
            val userHome: Location
            userHome = if (args.isNotEmpty()) {
                essUser.getHome(args[0])
            } else {
                essUser.getHome("home")
            }
            essUser.teleport.teleport(userHome.block.location, null, PlayerTeleportEvent.TeleportCause.COMMAND)
            essUser.sendMessage("You have been teleported home.")
            return true
        }
        return false
    }
}