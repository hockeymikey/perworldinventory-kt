package me.ebonjaeger.perworldinventory.serialization

import com.google.common.collect.Multimap
import org.bukkit.NamespacedKey
import org.bukkit.Tag
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.damage.DamageType
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemRarity
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.components.*
import org.bukkit.inventory.meta.components.consumable.ConsumableComponent
import org.bukkit.inventory.meta.tags.CustomItemTagContainer
import org.bukkit.persistence.PersistentDataContainer

/**
 * Implementation of [ItemMeta] for usage in tests.
 *
 * As a stand-in for state, a [map][providedMap] is maintained, which should be preserved during
 * serialization and deserialization.
 */
class ItemMetaTestImpl : ItemMeta, Damageable {

    val providedMap: MutableMap<String, Any>

    private var version = 0

    /**
     * Default constructor.
     */
    constructor() {
        this.providedMap = mutableMapOf()
    }

    /**
     * Deserialization constructor, as used in
     * [org.bukkit.configuration.serialization.ConfigurationSerialization.getConstructor].
     */
    constructor(map: MutableMap<String, Any>) {
        this.providedMap = map
    }

    override fun serialize(): MutableMap<String, Any> =
            HashMap(this.providedMap)

    override fun clone(): ItemMetaTestImpl {
        val mapClone = HashMap(providedMap)
        return ItemMetaTestImpl(mapClone)
    }

    override fun setDisplayName(name: String?) {
        throw NotImplementedError("not implemented")
    }

    override fun hasItemName(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getItemName(): String {
        TODO("Not yet implemented")
    }

    override fun setItemName(name: String?) {
        TODO("Not yet implemented")
    }

    override fun getCustomModelDataComponent(): CustomModelDataComponent {
        TODO("Not yet implemented")
    }

    override fun setCustomModelDataComponent(customModelData: CustomModelDataComponent?) {
        TODO("Not yet implemented")
    }

    override fun hasEnchantable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getEnchantable(): Int {
        TODO("Not yet implemented")
    }

    override fun setEnchantable(enchantable: Int?) {
        TODO("Not yet implemented")
    }

    override fun removeEnchantments() {
        TODO("Not yet implemented")
    }

    override fun isHideTooltip(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setHideTooltip(hideTooltip: Boolean) {
        TODO("Not yet implemented")
    }

    override fun hasTooltipStyle(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getTooltipStyle(): NamespacedKey? {
        TODO("Not yet implemented")
    }

    override fun setTooltipStyle(tooltipStyle: NamespacedKey?) {
        TODO("Not yet implemented")
    }

    override fun hasItemModel(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getItemModel(): NamespacedKey? {
        TODO("Not yet implemented")
    }

    override fun setItemModel(itemModel: NamespacedKey?) {
        TODO("Not yet implemented")
    }

    override fun getLore(): MutableList<String> {
        throw NotImplementedError("not implemented")
    }

    override fun setLore(lore: MutableList<String>?) {
        throw NotImplementedError("not implemented")
    }

    override fun hasEnchants(): Boolean {
        throw NotImplementedError("not implemented")
    }

    override fun setLocalizedName(name: String?) {
        throw NotImplementedError("not implemented")
    }

    override fun hasLore(): Boolean {
        throw NotImplementedError("not implemented")
    }

    override fun addItemFlags(vararg itemFlags: ItemFlag?) {
        throw NotImplementedError("not implemented")
    }

    override fun hasDisplayName(): Boolean {
        throw NotImplementedError("not implemented")
    }

    override fun getItemFlags(): MutableSet<ItemFlag> {
        throw NotImplementedError("not implemented")
    }

    override fun setUnbreakable(unbreakable: Boolean) {
        throw NotImplementedError("not implemented")
    }

    override fun hasEnchantmentGlintOverride(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getEnchantmentGlintOverride(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setEnchantmentGlintOverride(override: Boolean?) {
        TODO("Not yet implemented")
    }

    override fun isGlider(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setGlider(glider: Boolean) {
        TODO("Not yet implemented")
    }

    override fun isFireResistant(): Boolean {
        TODO("Not yet implemented")
    }

    override fun setFireResistant(fireResistant: Boolean) {
        TODO("Not yet implemented")
    }

    override fun hasDamageResistant(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getDamageResistant(): Tag<DamageType>? {
        TODO("Not yet implemented")
    }

    override fun setDamageResistant(tag: Tag<DamageType>?) {
        TODO("Not yet implemented")
    }

    override fun hasMaxStackSize(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getMaxStackSize(): Int {
        TODO("Not yet implemented")
    }

    override fun setMaxStackSize(max: Int?) {
        TODO("Not yet implemented")
    }

    override fun hasRarity(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getRarity(): ItemRarity {
        TODO("Not yet implemented")
    }

    override fun setRarity(rarity: ItemRarity?) {
        TODO("Not yet implemented")
    }

    override fun hasUseRemainder(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getUseRemainder(): ItemStack? {
        TODO("Not yet implemented")
    }

    override fun setUseRemainder(remainder: ItemStack?) {
        TODO("Not yet implemented")
    }

    override fun hasUseCooldown(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getUseCooldown(): UseCooldownComponent {
        TODO("Not yet implemented")
    }

    override fun setUseCooldown(cooldown: UseCooldownComponent?) {
        TODO("Not yet implemented")
    }

    override fun hasFood(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getFood(): FoodComponent {
        TODO("Not yet implemented")
    }

    override fun setFood(food: FoodComponent?) {
        TODO("Not yet implemented")
    }

    override fun hasConsumable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getConsumable(): ConsumableComponent {
        TODO("Not yet implemented")
    }

    override fun setConsumable(consumable: ConsumableComponent?) {
        TODO("Not yet implemented")
    }

    override fun hasTool(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getTool(): ToolComponent {
        TODO("Not yet implemented")
    }

    override fun setTool(tool: ToolComponent?) {
        TODO("Not yet implemented")
    }

    override fun hasEquippable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getEquippable(): EquippableComponent {
        TODO("Not yet implemented")
    }

    override fun setEquippable(equippable: EquippableComponent?) {
        TODO("Not yet implemented")
    }

    override fun hasJukeboxPlayable(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getJukeboxPlayable(): JukeboxPlayableComponent? {
        TODO("Not yet implemented")
    }

    override fun setJukeboxPlayable(jukeboxPlayable: JukeboxPlayableComponent?) {
        TODO("Not yet implemented")
    }

    override fun getDisplayName(): String {
        throw NotImplementedError("not implemented")
    }

    override fun getEnchants(): MutableMap<Enchantment, Int> {
        throw NotImplementedError("not implemented")
    }

    override fun getLocalizedName(): String {
        throw NotImplementedError("not implemented")
    }

    override fun isUnbreakable(): Boolean {
        throw NotImplementedError("not implemented")
    }

    override fun removeItemFlags(vararg itemFlags: ItemFlag?) {
        throw NotImplementedError("not implemented")
    }

    override fun hasLocalizedName(): Boolean {
        throw NotImplementedError("not implemented")
    }

    override fun getDamage(): Int
    {
        return 0
    }

    override fun hasDamage(): Boolean
    {
        throw NotImplementedError("not implemented")
    }

    override fun setDamage(damage: Int)
    {
        throw NotImplementedError("not implemented")
    }

    override fun hasMaxDamage(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getMaxDamage(): Int {
        TODO("Not yet implemented")
    }

    override fun setMaxDamage(maxDamage: Int?) {
        TODO("Not yet implemented")
    }

    override fun addEnchant(ench: Enchantment, level: Int, ignoreLevelRestriction: Boolean): Boolean {
        throw NotImplementedError("not implemented")
    }

    override fun hasConflictingEnchant(ench: Enchantment): Boolean {
        throw NotImplementedError("not implemented")
    }

    override fun getCustomModelData(): Int {
        throw NotImplementedError("not implemented")
    }

    override fun setAttributeModifiers(attributeModifiers: Multimap<Attribute, AttributeModifier>?) {
        throw NotImplementedError("not implemented")
    }

    override fun removeAttributeModifier(attribute: Attribute): Boolean {
        throw NotImplementedError("not implemented")
    }

    override fun removeAttributeModifier(slot: EquipmentSlot): Boolean {
        throw NotImplementedError("not implemented")
    }

    override fun removeAttributeModifier(attribute: Attribute, modifier: AttributeModifier): Boolean {
        throw NotImplementedError("not implemented")
    }

    override fun getAsString(): String {
        TODO("Not yet implemented")
    }

    override fun getAsComponentString(): String {
        TODO("Not yet implemented")
    }

    override fun hasCustomModelData(): Boolean {
        throw NotImplementedError("not implemented")
    }

    override fun addAttributeModifier(attribute: Attribute, modifier: AttributeModifier): Boolean {
        throw NotImplementedError("not implemented")
    }

    override fun getEnchantLevel(ench: Enchantment): Int {
        throw NotImplementedError("not implemented")
    }

    override fun setVersion(version: Int) {
        this.version = version
    }

    override fun hasEnchant(ench: Enchantment): Boolean {
        throw NotImplementedError("not implemented")
    }

    override fun setCustomModelData(data: Int?) {
        throw NotImplementedError("not implemented")
    }

    override fun hasAttributeModifiers(): Boolean {
        throw NotImplementedError("not implemented")
    }

    override fun getCustomTagContainer(): CustomItemTagContainer {
        throw NotImplementedError("not implemented")
    }

    override fun hasItemFlag(flag: ItemFlag): Boolean {
        throw NotImplementedError("not implemented")
    }

    override fun removeEnchant(ench: Enchantment): Boolean {
        throw NotImplementedError("not implemented")
    }

    override fun getAttributeModifiers(): Multimap<Attribute, AttributeModifier>? {
        throw NotImplementedError("not implemented")
    }

    override fun getAttributeModifiers(slot: EquipmentSlot): Multimap<Attribute, AttributeModifier> {
        throw NotImplementedError("not implemented")
    }

    override fun getAttributeModifiers(attribute: Attribute): MutableCollection<AttributeModifier> {
        throw NotImplementedError("not implemented")
    }

    override fun getPersistentDataContainer(): PersistentDataContainer {
        throw NotImplementedError("not implemented")
    }
}
