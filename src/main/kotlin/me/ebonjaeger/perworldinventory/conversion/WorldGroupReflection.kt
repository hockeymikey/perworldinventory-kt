package me.ebonjaeger.perworldinventory.conversion

import com.onarandombox.multiverseinventories.MultiverseInventories
import com.onarandombox.multiverseinventories.WorldGroup
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException

object WorldGroupReflection {

    fun createWorldGroup(plugin: MultiverseInventories, name: String): WorldGroup? {
        return try {
            val constructor: Constructor<WorldGroup> =
                WorldGroup::class.java.getDeclaredConstructor(MultiverseInventories::class.java, String::class.java)
            constructor.isAccessible = true
            constructor.newInstance(plugin, name)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
            null
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
            null
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
            null
        } catch (e: InstantiationException) {
            e.printStackTrace()
            null
        }
    }
}