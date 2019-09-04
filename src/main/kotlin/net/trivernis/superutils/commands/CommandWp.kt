package net.trivernis.superutils.commands

import com.earth2me.essentials.Essentials
import com.earth2me.essentials.Trade
import com.earth2me.essentials.User
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerTeleportEvent

class CommandWp(private val essentials: Essentials): CommandExecutor, TabCompleter {

    /**
     * tab completion for warp command
     */
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        if (sender is Player) {
            return if (args.isNotEmpty()) {
                essentials.warps.list.filter{it.contains(args.first(), ignoreCase = true)}.toMutableList()
            } else {
                essentials.warps.list.toMutableList()
            }
        }
        return emptyList<String>().toMutableList();
    }

    /**
     * shortened warp command
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (args.isNotEmpty()) {
                val essUser: User = essentials.getUser(sender)
                val warp = essentials.warps.getWarp(args.first())
                if (warp != null) {
                    val teleportCost = Trade(essentials.settings.getCommandCost("warp"), essentials)
                    essUser.teleport.teleport(warp.block.location, teleportCost, PlayerTeleportEvent.TeleportCause.COMMAND)
                } else {
                    sender.sendMessage("Warp \"${args.first()}\" not found")
                }
            } else {
                sender.sendMessage("You need to specify a warp location.")
            }
        } else {
            sender.sendMessage("This command can only be executed by a player.");
        }
        return false
    }
}