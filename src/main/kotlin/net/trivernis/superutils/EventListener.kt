package net.trivernis.superutils

import com.earth2me.essentials.Essentials
import net.trivernis.superutils.commands.CommandC
import org.bukkit.Server
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.event.player.PlayerGameModeChangeEvent
import org.bukkit.event.world.WorldSaveEvent
import org.bukkit.potion.PotionEffectType

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
     * Broadcasts that the world has been saved
     */
    @EventHandler fun onWorldSave(event: WorldSaveEvent) {
        if (config.getBoolean("save-notification")) {
            server.broadcastMessage("The world has been saved.")
        }
    }
}