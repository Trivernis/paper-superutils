package net.trivernis.superutils.commands

import net.trivernis.superutils.SuperUtils
import net.trivernis.superutils.lib.ChunkGenerationManager
import org.bukkit.Server
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandGenerateChunks(private val superUtils: SuperUtils, private val server: Server): CommandExecutor {
    private var generationManager: ChunkGenerationManager? = null
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player) {
            if (generationManager != null && generationManager!!.getRunning()) {
                generationManager?.stop()
                sender.sendMessage("Stopped chunk generation.")
            }
            if (args.size == 4) {
                generationManager = ChunkGenerationManager(superUtils, server, sender.world)
                generationManager?.start(Pair(args[0].toInt(), args[1].toInt()), Pair(args[2].toInt(), args[3].toInt())) {
                    sender.sendMessage("Finished Generation.")
                }
                sender.sendMessage("Starting chunk generation...")
            }
        }
        return true;
    }
}