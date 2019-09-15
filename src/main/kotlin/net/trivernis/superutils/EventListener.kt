package net.trivernis.superutils

import com.earth2me.essentials.Essentials
import com.onarandombox.MultiverseCore.MultiverseCore
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ComponentBuilder
import net.trivernis.superutils.commands.CommandC
import org.bukkit.Server
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.event.player.PlayerGameModeChangeEvent
import org.bukkit.event.world.WorldSaveEvent
import org.bukkit.potion.PotionEffectType
import net.md_5.bungee.api.chat.TextComponent
import net.trivernis.superutils.lib.ChunkGenerationManager
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class EventListener(private val config: FileConfiguration, private val essentials: Essentials?,
                    private val commandC: CommandC, private val server: Server): Listener {
    /**
     * Removes the night vision effect from the player if given by /c command
     */
    @EventHandler fun onPlayerGameModeChange(event: PlayerGameModeChangeEvent) {
        if (commandC.spectatorPlayers.contains(event.player)) {
            event.player.removePotionEffect(PotionEffectType.NIGHT_VISION)
            commandC.spectatorPlayers.remove(event.player)
        }
    }

    /**
     * Rewardes player advancements.
     */
    @EventHandler fun onPlayerAdvancement(event: PlayerAdvancementDoneEvent) {
        if (essentials != null) {
            val payout = config.getDouble("advancement-payout")
            if (payout > 0) {
                essentials.getUser(event.player).giveMoney(payout.toBigDecimal())
            }
        }
    }

    /**
     * Broadcasts that the world has been saved.
     * If multiverse core is used, the message is broadcasted for the spawn world.
     * Else it is broadcasted for the world with the name "world"
     */
    @EventHandler fun onWorldSave(event: WorldSaveEvent) {
        if (config.getBoolean("save-notification")) {
            server.consoleSender.sendMessage("The ${event.world.name} has been saved.")
            server.operators.forEach {
                val player = it.player
                val message = ComponentBuilder("World ")
                        .append(event.world.name).color(ChatColor.RED)
                        .append(" has been saved").color(ChatColor.WHITE).create()
                if (player?.world == event.world) {
                    player.spigot().sendMessage(*message)
                }
            }
        }
    }
}