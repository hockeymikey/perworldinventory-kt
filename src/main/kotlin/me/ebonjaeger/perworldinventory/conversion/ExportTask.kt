package me.ebonjaeger.perworldinventory.conversion

import com.onarandombox.multiverseinventories.MultiverseInventories
import com.onarandombox.multiverseinventories.WorldGroup
import com.onarandombox.multiverseinventories.profile.PlayerProfile
import com.onarandombox.multiverseinventories.profile.ProfileType
import com.onarandombox.multiverseinventories.profile.ProfileTypes
import com.onarandombox.multiverseinventories.profile.container.ContainerType
import com.onarandombox.multiverseinventories.share.Sharables
import me.ebonjaeger.perworldinventory.ConsoleLogger
import me.ebonjaeger.perworldinventory.Group
import me.ebonjaeger.perworldinventory.GroupManager
import me.ebonjaeger.perworldinventory.data.PlayerDefaults
import me.ebonjaeger.perworldinventory.data.ProfileKey
import me.ebonjaeger.perworldinventory.data.ProfileManager
import me.ebonjaeger.perworldinventory.data.migration.ENDER_CHEST_SLOTS
import me.ebonjaeger.perworldinventory.data.migration.INVENTORY_SLOTS
import me.ebonjaeger.perworldinventory.serialization.InventoryHelper
import me.ebonjaeger.perworldinventory.serialization.PotionSerializer
import net.minidev.json.JSONObject
import net.minidev.json.JSONStyle
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.OfflinePlayer
import org.bukkit.command.CommandSender
import org.bukkit.configuration.serialization.ConfigurationSerialization
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.scheduler.BukkitRunnable
import java.io.File
import java.io.FileWriter
import java.nio.file.Files
import java.util.*

const val MAX_EXPORTS_PER_TICK = 10

/**
 * Task to convert player data from MultiVerse-Inventories to PWI.
 *
 * @property convertService The [ConvertService] running this task.
 * @property groupManager The PerWorldInventory [GroupManager].
 * @property sender The [CommandSender] that started the conversion.
 * @property offlinePlayers All [OfflinePlayer]s on the server.
 * @property multiVerseGroups Groups from MultiVerse-Inventories.
 * @property profileManager The PerWorldInventory [ProfileManager]
 * @property dataDirectory The directory where player data is stored.
 */
class ExportTask (private val exportService: ExportService,
                  private val groupManager: GroupManager,
                  private val sender: CommandSender,
                  private val offlinePlayers: Array<out OfflinePlayer>,
                  private val mvInventory: MultiverseInventories,
                  private val profileManager: ProfileManager,
                  private val dataDirectory: File) : BukkitRunnable() {

    private val queue: Queue<OfflinePlayer> = LinkedList<OfflinePlayer>()

    private var index = 0
    private var converted = 0

    override fun run() {
        // Calculate our stopping index for this run
        val stopIndex =  if (index + MAX_EXPORTS_PER_TICK < offlinePlayers.size) { // Use index + constant if the result isn't more than the total number of players
            index + MAX_EXPORTS_PER_TICK
        } else { // Index would be greater than number of players, so just use the size of the array
            offlinePlayers.size
        }

        if (index >= offlinePlayers.size) { // No more players to migrate
            exportService.finishConversion(converted)
            cancel()
        }

        // Add players to a queue to be migrated
        while (index < stopIndex) {
            queue.offer(offlinePlayers[index])
            index++
        }

        while (queue.isNotEmpty()) { // Iterate over the queue
            val player = queue.poll()
            convert(player)
        }

        if (index % 100 == 0) { // Print migration status every 100 players (about every 5 seconds)
            ConsoleLogger.info("Conversion progress: $index/${offlinePlayers.size}")
        }
    }

    /**
     * Converts data from the MultiVerse-Inventories format to the PWI format.
     *
     * @param player The player to convert.
     */
    private fun convert(player: OfflinePlayer) {
        val profileTypes = arrayOf(ProfileTypes.ADVENTURE,
                ProfileTypes.CREATIVE,
                ProfileTypes.SURVIVAL)

        groupManager.groups.values.forEach { pwiGroup ->
            val mvGroup = mvInventory.groupManager.getGroup(pwiGroup.name)
            val pwiGroup = groupManager.getGroup(pwiGroup.name)
            if (mvGroup == null || pwiGroup == null) {
                ConsoleLogger.warning("Trying to convert to a group that doesn't exist! Not converting to group '${pwiGroup?.name}'!")
                return
            }

            profileTypes.forEach { type ->
                val gameMode = GameMode.valueOf(type.name)

                try {
                    val playerProfile = profileManager.getOfflinePlayerData(player, pwiGroup, gameMode)

                    if (playerProfile != null) {
                        val data = pwiToMv(player, playerProfile, gameMode, pwiGroup)

                        mvGroup.groupProfileContainer.addPlayerData(data)
                    }
                } catch (ex: Exception) {
                    ConsoleLogger.severe("Error converting data for '${player.name}' in group '${pwiGroup.name}' for GameMode '$gameMode", ex)
                }
            }
        }
    }

    fun getProfileType(gameMode: GameMode): ProfileType {
        return when (gameMode) {
            GameMode.ADVENTURE -> ProfileTypes.ADVENTURE
            GameMode.CREATIVE -> ProfileTypes.CREATIVE
            GameMode.SURVIVAL -> ProfileTypes.SURVIVAL
            else -> ProfileTypes.SURVIVAL
        }
    }


    private fun pwiToMv(player: OfflinePlayer, profile: me.ebonjaeger.perworldinventory.data.PlayerProfile, gameMode: GameMode, pwiGroup: Group): PlayerProfile? {

        val profileType = getProfileType(gameMode)

        val mvp = PlayerProfile.createPlayerProfile(ContainerType.GROUP, pwiGroup.name, profileType, player)

        mvp.set(Sharables.INVENTORY, profile.inventory)
        mvp.set(Sharables.ARMOR, profile.armor)
        mvp.set(Sharables.ENDER_CHEST, profile.enderChest)
        mvp.set(Sharables.POTIONS, profile.potionEffects.toTypedArray())
        mvp.set(Sharables.ECONOMY, profile.balance)

        mvp.set(Sharables.EXHAUSTION, profile.exhaustion)
        mvp.set(Sharables.EXPERIENCE, profile.experience)
        mvp.set(Sharables.FOOD_LEVEL, profile.foodLevel)
        mvp.set(Sharables.HEALTH, profile.health)
        mvp.set(Sharables.LEVEL, profile.level)
        mvp.set(Sharables.SATURATION, profile.saturation)
        mvp.set(Sharables.FALL_DISTANCE, profile.fallDistance)
        mvp.set(Sharables.FIRE_TICKS, profile.fireTicks)
        mvp.set(Sharables.MAXIMUM_AIR, profile.maximumAir)
        mvp.set(Sharables.REMAINING_AIR, profile.remainingAir)
        mvp.set(Sharables.HEALTH, profile.health)
        return mvp
    }
}
