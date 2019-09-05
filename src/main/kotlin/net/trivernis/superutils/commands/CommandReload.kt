package net.trivernis.superutils.commands

import net.trivernis.superutils.SuperUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class CommandReload(val superUtils: SuperUtils): CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.testPermission(sender)) {
            superUtils.reloadConfig()
            sender.sendMessage("Config for superutils reloaded.")
        }
        return true
    }

}