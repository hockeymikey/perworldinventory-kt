package me.ebonjaeger.perworldinventory.conversion

import ch.jalu.injector.annotations.NoMethodScan
import com.onarandombox.multiverseinventories.MultiverseInventories
import com.onarandombox.multiverseinventories.WorldGroup
import com.onarandombox.multiverseinventories.profile.WorldGroupManager
import me.ebonjaeger.perworldinventory.ConsoleLogger
import me.ebonjaeger.perworldinventory.Group
import me.ebonjaeger.perworldinventory.GroupManager
import me.ebonjaeger.perworldinventory.PerWorldInventory
import me.ebonjaeger.perworldinventory.data.ProfileManager
import me.ebonjaeger.perworldinventory.initialization.DataDirectory
import me.ebonjaeger.perworldinventory.service.BukkitService
import org.bukkit.ChatColor
import org.bukkit.GameMode
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.PluginManager
import java.io.File
import javax.inject.Inject

/**
 * Initiates export tasks.
 */
@NoMethodScan
class ExportService @Inject constructor(private val plugin: PerWorldInventory,
                                        private val bukkitService: BukkitService,
                                        private val groupManager: GroupManager,
                                        private val pluginManager: PluginManager,
                                        private val profileManager: ProfileManager,
                                        @DataDirectory private val dataDirectory: File) {

    private var converting = false
    private var sender: CommandSender? = null

    fun isConverting(): Boolean {
        return converting
    }

    fun beginConverting(sender: CommandSender, mvInventory: MultiverseInventories) {
        val offlinePlayers = bukkitService.getOfflinePlayers()

        if (isConverting()) {
            return
        }

        this.sender = sender

        if (sender !is ConsoleCommandSender) { // No need to send a message to console when console did the command
            ConsoleLogger.info("Beginning export to MultiVerse-Inventories.")
        }

        converting = true

        convertGroups(mvInventory, groupManager.groups.values.toList())

        val task = ExportTask(this, groupManager, sender, offlinePlayers, mvInventory, profileManager, dataDirectory)
        task.runTaskTimerAsynchronously(plugin, 0, 20)
    }

    fun finishConversion(converted: Int) {
        converting = false

        val mvInventory = pluginManager.getPlugin("Multiverse-Inventories")
        if (mvInventory != null && pluginManager.isPluginEnabled(mvInventory)) {
            pluginManager.disablePlugin(mvInventory)
        }

        ConsoleLogger.info("Data conversion has been completed! Converted $converted profiles.")
        if (sender != null && sender is Player) {
            if ((sender as Player).isOnline) {
                (sender as Player).sendMessage("${ChatColor.GREEN}» ${ChatColor.GRAY}Data conversion has been completed!")
            }
        }
    }

    private fun convertGroups(mvInventory: MultiverseInventories, groups: List<Group>) {
        val mvgm = mvInventory.groupManager
        groups.forEach { group ->
            // Ensure that the group exists first, otherwise you get nulls down the road
            val mvg = mvgm.getGroup(group.name)

            if (mvg == null) {
                val worldGroup = WorldGroupReflection.createWorldGroup(mvInventory, group.name)
                worldGroup?.addWorlds(group.worlds)
                worldGroup?.spawnWorld = group.respawnWorld
                mvgm.updateGroup(worldGroup)
            } else {
                mvg.addWorlds(group.worlds)
                mvg.spawnWorld = group.respawnWorld
            }
        }
    }
}
