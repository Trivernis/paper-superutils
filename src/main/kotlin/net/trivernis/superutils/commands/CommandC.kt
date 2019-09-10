package net.trivernis.superutils.commands

import com.earth2me.essentials.Essentials
import com.onarandombox.MultiverseCore.MultiverseCore
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ComponentBuilder
import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType

class CommandC(private var multiverseCore: MultiverseCore?, private var essentials: Essentials?): CommandExecutor {
    var spectatorPlayers: HashSet<Player> = emptyList<Player>().toHashSet()
    /**
     * Sets the users gamemode to spectator with nightvision or back to survival
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player && command.testPermission(sender)) {
            if (sender.gameMode != GameMode.SPECTATOR) {
                if (essentials != null) {
                    val commandCost = essentials!!.settings.getCommandCost("c")
                    val essUser = essentials!!.getUser(sender)
                    if ((essUser.money - commandCost) > essentials!!.settings.minMoney) {
                        sender.gameMode = GameMode.SPECTATOR
                        sender.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(1000000, 255))
                        essUser.takeMoney(commandCost)
                    } else {
                        sender.spigot().sendMessage(*ComponentBuilder("You do not have enough money for this command")
                                .color(ChatColor.RED).create())
                    }
                } else {
                    sender.gameMode = GameMode.SPECTATOR
                    sender.addPotionEffect(PotionEffectType.NIGHT_VISION.createEffect(1000000, 255))
                }
            } else {
                sender.removePotionEffect(PotionEffectType.NIGHT_VISION)
                if (multiverseCore != null) {
                    // get the multiverse gamemode of the world
                    val worldGameMode: GameMode = multiverseCore!!.mvWorldManager.getMVWorld(sender.world).gameMode
                    sender.gameMode = worldGameMode
                    spectatorPlayers.add(sender)
                } else {
                    sender.gameMode = GameMode.SURVIVAL
                    spectatorPlayers.remove(sender)
                }
            }
            return true
        } else {
            sender.sendMessage("This command can only be executed by a player.");
        }
        return false
    }
}