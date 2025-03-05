package me.ebonjaeger.perworldinventory.command

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import com.onarandombox.multiverseinventories.MultiverseInventories
import me.ebonjaeger.perworldinventory.conversion.ConvertService
import me.ebonjaeger.perworldinventory.conversion.ExportService
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.PluginManager
import javax.inject.Inject

@CommandAlias("perworldinventory|pwi")
class ExportCommand @Inject constructor(private val pluginManager: PluginManager,
                                        private val exportService: ExportService
) : BaseCommand() {

    @Subcommand("export")
    @CommandPermission("perworldinventory.export")
    @Description("Export inventory data from PWI to Multiverse Inventories")
    fun onConvert(sender: CommandSender) {
        if (exportService.isConverting()) {
            sender.sendMessage("${ChatColor.DARK_RED}» ${ChatColor.GRAY}A data export is already in progress!")
            return
        }

        if (!pluginManager.isPluginEnabled("MultiVerse-Inventories")) {
            sender.sendMessage("${ChatColor.DARK_RED}» ${ChatColor.GRAY}MultiVerse-Inventories is not installed! Cannot export data!")
            return
        }

        val mvi = pluginManager.getPlugin("MultiVerse-Inventories")
        if (mvi == null) {
            sender.sendMessage("${ChatColor.DARK_RED}» ${ChatColor.GRAY}Unable to get MultiVerse-Inventories instance!")
            return
        }

        exportService.beginConverting(sender, mvi as MultiverseInventories)
    }
}
