package net.trivernis.superutils.commands

import java.lang.Double.parseDouble
import net.trivernis.superutils.SuperUtils
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.lang.NumberFormatException

class CommandScheduleShutdown(private val superUtils: SuperUtils): CommandExecutor {
    /**
     * Command that schedules a server shutdown.
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        var scheduling: Double = 60.0   // 10 seconds to shutdown
        if (!args.isNullOrEmpty()) {
            try {
                 scheduling = parseDouble(args[0])
            } catch (e: NumberFormatException) {
                sender.sendMessage("Invalid number format for timing")
                return false
            }
        }
        sender.server.scheduler.scheduleSyncDelayedTask(superUtils, {
            sender.server.shutdown()
        }, (20*scheduling).toLong())
        sender.server.broadcastMessage("Server shutdown in $scheduling seconds!")
        return true
    }

}