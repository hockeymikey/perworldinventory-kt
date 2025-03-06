package me.ebonjaeger.perworldinventory.serialization

import me.ebonjaeger.perworldinventory.data.PlayerProfile
import net.minidev.json.JSONArray
import net.minidev.json.JSONObject
import org.bukkit.GameMode
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.util.NumberConversions

object PlayerSerializer
{

    fun deserialize(data: JSONObject, playerName: String, inventorySize: Int, eChestSize: Int): PlayerProfile
    {
        // Get the data format being used
        var format = 3
        if (data.containsKey("data-format"))
        {
            format = data["data-format"] as Int
        }

        val inventory = data["inventory"] as JSONObject
        val items = InventoryHelper.deserialize(inventory["inventory"] as JSONArray,
                inventorySize,
                format)
        val armor = InventoryHelper.deserialize(inventory["armor"] as JSONArray, 4, format)
        val enderChest = InventoryHelper.deserialize(data["ender-chest"] as JSONArray,
                eChestSize,
                format)
        val stats = StatSerializer.validateStats(data["stats"] as JSONObject, playerName)
        var potionEffects: MutableCollection<PotionEffect>? = null
        if (stats["potion-effects"] is String) {
            potionEffects = mutableListOf<PotionEffect>()
        } else {
            potionEffects = PotionSerializer.deserialize(stats["potion-effects"] as JSONArray)
        }
        val balance = if (data.containsKey("economy"))
        {
            EconomySerializer.deserialize(data["economy"] as JSONObject)
        } else
        {
            0.0
        }

        return PlayerProfile(armor,
                enderChest,
                items,
                stats["can-fly"] as Boolean,
                stats["display-name"] as String,
                stats["exhaustion"] as Float,
                stats["exp"] as Float,
                stats["flying"] as Boolean,
                stats["food"] as Int,
                NumberConversions.toDouble(stats["max-health"]),
                NumberConversions.toDouble(stats["health"]),
                GameMode.valueOf(stats["gamemode"] as String),
                stats["level"] as Int,
                stats["saturation"] as Float,
                potionEffects,
                stats["fallDistance"] as Float,
                stats["fireTicks"] as Int,
                stats["maxAir"] as Int,
                stats["remainingAir"] as Int,
                balance)
    }

    fun flexibleDeserialize(data: JSONObject, playerName: String): PlayerProfile
    {
        // Get the data format being used
        var format = 3
        if (data.containsKey("data-format"))
        {
            format = data["data-format"] as Int
        }

        val inventory = data["inventory"] as JSONObject

        var items: Array<out ItemStack> = Array(36) { ItemStack(Material.AIR) }
        if (inventory["inventory"] != null) {
            items = InventoryHelper.flexiableDeserialize(inventory["inventory"] as JSONArray,  format)
        }

        var armor: Array<out ItemStack> = Array(4) { ItemStack(Material.AIR) }
        if (inventory["armor"] != null) {
            armor = InventoryHelper.deserialize(inventory["armor"] as JSONArray, 4, format)
        }

        var enderChest: Array<out ItemStack> = Array(27) { ItemStack(Material.AIR) }
        if (inventory["ender-chest"] != null) {
            items = InventoryHelper.flexiableDeserialize(inventory["ender-chest"] as JSONArray,  format)
        }

        val stats = StatSerializer.validateStats(data["stats"] as JSONObject, playerName)
        var potionEffects: MutableCollection<PotionEffect>? = null
        if (stats["potion-effects"] is String || stats["potion-effects"] == null) {
            potionEffects = mutableListOf<PotionEffect>()
        } else {
            potionEffects = PotionSerializer.deserialize(stats["potion-effects"] as JSONArray)
        }
        val balance = if (data.containsKey("economy"))
        {
            EconomySerializer.deserialize(data["economy"] as JSONObject)
        } else
        {
            0.0
        }

        if (stats["exp"] is Int) {
            stats["exp"] = (stats["exp"] as Int).toFloat()
        }

        if (stats["saturation"] is Int) {
            stats["saturation"] = (stats["saturation"] as Int).toFloat()
        }

        if (stats["exhaustion"] is Int) {
            stats["exhaustion"] = (stats["exhaustion"] as Int).toFloat()
        }

        return PlayerProfile(armor,
            enderChest,
            items,
            stats["can-fly"] as Boolean,
            stats["display-name"] as String,
            stats["exhaustion"] as Float,
            stats["exp"] as Float,
            stats["flying"] as Boolean,
            stats["food"] as Int,
            NumberConversions.toDouble(stats["max-health"]),
            NumberConversions.toDouble(stats["health"]),
            GameMode.valueOf(stats["gamemode"] as String),
            stats["level"] as Int,
            stats["saturation"] as Float,
            potionEffects,
            stats["fallDistance"] as Float,
            stats["fireTicks"] as Int,
            stats["maxAir"] as Int,
            stats["remainingAir"] as Int,
            balance)
    }
}
