
package com.mythictoolsy.gui;

import com.mythictoolsy.MythicToolsyPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;

public final class ItemCreatorGUI implements Listener {

    private final MythicToolsyPlugin plugin;

    private final NamespacedKey actionKey;
    private final NamespacedKey itemKey;

    private final Map<UUID, String> editingItems = new HashMap<>();

    private static final String CREATE_TITLE =
            ChatColor.DARK_PURPLE + "MythicToolsy » Create";

    private static final String EDIT_TITLE =
            ChatColor.DARK_PURPLE + "MythicToolsy » Editor";

    private static final String TEXTURE_TITLE =
            ChatColor.DARK_PURPLE + "MythicToolsy » Textures";

    private static final String POWER_TITLE =
            ChatColor.DARK_PURPLE + "MythicToolsy » Powers";

    public ItemCreatorGUI(MythicToolsyPlugin plugin) {
        this.plugin = plugin;
        this.actionKey = new NamespacedKey(plugin, "gui_action");
        this.itemKey = new NamespacedKey(plugin, "gui_item");
    }

    public void openCreate(Player player, String itemId) {
        String id = itemId.toLowerCase(Locale.ROOT);

        editingItems.put(player.getUniqueId(), id);

        Inventory inventory = Bukkit.createInventory(
                null, 27, CREATE_TITLE
        );

        inventory.setItem(11, button(
                Material.DIAMOND_SWORD,
                "§bWeapons",
                "category_weapon"
        ));

        inventory.setItem(13, button(
                Material.DIAMOND_CHESTPLATE,
                "§bArmor",
                "category_armor"
        ));

        inventory.setItem(15, button(
                Material.DIAMOND_PICKAXE,
                "§bTools",
                "category_tool"
        ));

        inventory.setItem(22, button(
                Material.NETHER_STAR,
                "§dCustom Item",
                "category_custom"
        ));

        player.openInventory(inventory);
    }

    public void openEditor(Player player, String itemId) {
        String id = itemId.toLowerCase(Locale.ROOT);

        editingItems.put(player.getUniqueId(), id);

        ConfigurationSection section =
                plugin.getConfig().getConfigurationSection("items." + id);

        Material icon = Material.DIAMOND_SWORD;

        if (section != null) {
            String materialName = section.getString(
                    "material", "DIAMOND_SWORD"
            );

            Material parsed = Material.matchMaterial(materialName);

            if (parsed != null) {
                icon = parsed;
            }
        }

        Inventory inventory = Bukkit.createInventory(
                null, 45, EDIT_TITLE
        );

        inventory.setItem(4, button(
                icon,
                "§eItem: §f" + id,
                "item_info"
        ));

        inventory.setItem(10, button(
                Material.NAME_TAG,
                "§bDisplay Name",
                "edit_name"
        ));

        inventory.setItem(12, button(
                Material.IRON_SWORD,
                "§cDamage",
                "edit_damage"
        ));

        inventory.setItem(14, button(
                Material.IRON_CHESTPLATE,
                "§aDefense",
                "edit_defense"
        ));

        inventory.setItem(16, button(
                Material.EMERALD,
                "§6Rarity",
                "edit_rarity"
        ));

        inventory.setItem(20, button(
                Material.PAINTING,
                "§dTexture / Model",
                "open_textures"
        ));

        inventory.setItem(22, button(
                Material.ENCHANTED_BOOK,
                "§5Mythic Enchantments",
                "open_enchantments"
        ));

        inventory.setItem(24, button(
                Material.NETHER_STAR,
                "§6Powers / Abilities",
                "open_powers"
        ));

        inventory.setItem(40, button(
                Material.BARRIER,
                "§cDelete Item",
                "delete_item"
        ));

        inventory.setItem(44, button(
                Material.LIME_CONCRETE,
                "§aSave Item",
                "save_item"
        ));

        player.openInventory(inventory);
    }

    public void openTextures(Player player) {
        Inventory inventory = Bukkit.createInventory(
                null, 54, TEXTURE_TITLE
        );

        Material[] materials = {
                Material.DIAMOND_SWORD,
                Material.NETHERITE_SWORD,
                Material.GOLDEN_SWORD,
                Material.IRON_SWORD,
                Material.DIAMOND_AXE,
                Material.NETHERITE_AXE,
                Material.DIAMOND_CHESTPLATE,
                Material.NETHERITE_CHESTPLATE,
                Material.DIAMOND_HELMET,
                Material.NETHERITE_HELMET,
                Material.DIAMOND_LEGGINGS,
                Material.NETHERITE_LEGGINGS,
                Material.DIAMOND_BOOTS,
                Material.NETHERITE_BOOTS,
                Material.BOW,
                Material.CROSSBOW,
                Material.TRIDENT,
                Material.MACE,
                Material.STICK,
                Material.NETHER_STAR
        };

        int slot = 0;

        for (Material material : materials) {
            inventory.setItem(slot++, button(
                    material,
                    "§b" + material.name(),
                    "select_texture:" + material.name()
            ));
        }

        inventory.setItem(49, button(
                Material.ARROW,
                "§eBack to Editor",
                "back_editor"
        ));

        player.openInventory(inventory);
    }

    public void openPowers(Player player) {
        Inventory inventory = Bukkit.createInventory(
                null, 45, POWER_TITLE
        );

        inventory.setItem(10, button(
                Material.BLAZE_POWDER,
                "§cFire Power",
                "power_fire"
        ));

        inventory.setItem(12, button(
                Material.LIGHTNING_ROD,
                "§eLightning",
                "power_lightning"
        ));

        inventory.setItem(14, button(
                Material.ENDER_PEARL,
                "§dDash",
                "power_dash"
        ));

        inventory.setItem(16, button(
                Material.GHAST_TEAR,
                "§aLifesteal",
                "power_lifesteal"
        ));

        inventory.setItem(20, button(
                Material.SNOWBALL,
                "§bFreeze",
                "power_freeze"
        ));

        inventory.setItem(22, button(
                Material.WIND_CHARGE,
                "§fWind Burst",
                "power_wind"
        ));

        inventory.setItem(24, button(
                Material.TOTEM_OF_UNDYING,
                "§6Second Chance",
                "power_secondchance"
        ));

        inventory.setItem(40, button(
                Material.ARROW,
                "§eBack to Editor",
                "back_editor"
        ));

        player.openInventory(inventory);
    }

    private ItemStack button(
            Material material,
            String name,
            String action
    ) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(name);

            meta.getPersistentDataContainer().set(
                    actionKey,
                    PersistentDataType.STRING,
                    action
            );

            meta.getPersistentDataContainer().set(
                    itemKey,
                    PersistentDataType.STRING,
                    editingItems.getOrDefault(
                            UUID.randomUUID(), ""
                    )
            );

            item.setItemMeta(meta);
        }

        return item;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) {
            return;
        }

        String title = event.getView().getTitle();

        if (!title.equals(CREATE_TITLE)
                && !title.equals(EDIT_TITLE)
                && !title.equals(TEXTURE_TITLE)
                && !title.equals(POWER_TITLE)) {
            return;
        }

        event.setCancelled(true);

        if (event.getCurrentItem() == null
                || !event.getCurrentItem().hasItemMeta()) {
            return;
        }

        ItemMeta meta = event.getCurrentItem().getItemMeta();

        String action = meta.getPersistentDataContainer().get(
                actionKey,
                PersistentDataType.STRING
        );

        if (action == null) {
            return;
        }

        String id = editingItems.get(player.getUniqueId());

        if (id == null) {
            player.closeInventory();
            return;
        }

        if (action.startsWith("category_")) {
            createItem(player, id, action.substring(9));
            return;
        }

        if (action.equals("open_textures")) {
            openTextures(player);
            return;
        }

        if (action.equals("open_powers")) {
            openPowers(player);
            return;
        }

        if (action.equals("back_editor")) {
            openEditor(player, id);
            return;
        }

        if (action.startsWith("select_texture:")) {
            String materialName = action.substring(
                    "select_texture:".length()
            );

            plugin.getConfig().set(
                    "items." + id + ".material",
                    materialName
            );

            plugin.saveConfig();

            player.sendMessage(
                    "§d[MythicToolsy] §aTexture icon selected: "
                            + materialName
            );

            openEditor(player, id);
            return;
        }

        if (action.startsWith("power_")) {
            togglePower(player, id, action.substring(6));
            return;
        }

        if (action.equals("save_item")) {
            plugin.saveConfig();

            player.sendMessage(
                    "§d[MythicToolsy] §aItem saved: " + id
            );

            player.closeInventory();
            return;
        }

        if (action.equals("delete_item")) {
            plugin.getConfig().set("items." + id, null);
            plugin.saveConfig();

            player.sendMessage(
                    "§d[MythicToolsy] §cItem deleted: " + id
            );

            player.closeInventory();
        }
    }

    private void createItem(
            Player player,
            String id,
            String category
    ) {
        Material material = switch (category) {
            case "weapon" -> Material.DIAMOND_SWORD;
            case "armor" -> Material.DIAMOND_CHESTPLATE;
            case "tool" -> Material.DIAMOND_PICKAXE;
            default -> Material.NETHER_STAR;
        };

        plugin.getConfig().set(
                "items." + id + ".material",
                material.name()
        );

        plugin.getConfig().set(
                "items." + id + ".category",
                category
        );

        plugin.getConfig().set(
                "items." + id + ".display-name",
                "§d" + id
        );

        plugin.getConfig().set(
                "items." + id + ".damage",
                10
        );

        plugin.getConfig().set(
                "items." + id + ".defense",
                0
        );

        plugin.getConfig().set(
                "items." + id + ".rarity",
                "MYTHIC"
        );

        plugin.saveConfig();

        openEditor(player, id);
    }

    private void togglePower(
            Player player,
            String id,
            String power
    ) {
        String path = "items." + id + ".powers." + power;

        boolean enabled = plugin.getConfig().getBoolean(path);

        plugin.getConfig().set(path, !enabled);
        plugin.saveConfig();

        player.sendMessage(
                "§d[MythicToolsy] §aPower "
                        + power + " "
                        + (!enabled ? "enabled" : "disabled")
        );

        openPowers(player);
    }
          }
      
