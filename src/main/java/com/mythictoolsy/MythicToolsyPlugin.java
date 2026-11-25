
package com.mythictoolsy;

import com.mythictoolsy.gui.ItemCreatorGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Locale;

public final class MythicToolsyPlugin extends JavaPlugin {

    private static final String PREFIX =
            ChatColor.DARK_PURPLE + "[MythicToolsy] "
                    + ChatColor.LIGHT_PURPLE;

    private ItemCreatorGUI itemCreatorGUI;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        itemCreatorGUI = new ItemCreatorGUI(this);
        getServer().getPluginManager()
                .registerEvents(itemCreatorGUI, this);

        if (getCommand("mw") != null) {
            getCommand("mw").setExecutor(this::onCommand);
        }

        getLogger().info("MythicToolsy enabled successfully!");
    }

    @Override
    public void onDisable() {
        saveConfig();
        getLogger().info("MythicToolsy disabled!");
    }

    private boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
            sendHelp(sender);
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage("This command can only be used in-game.");
            return true;
        }

        if (!player.hasPermission("mythictoolsy.admin")) {
            player.sendMessage(PREFIX + ChatColor.RED
                    + "You don't have permission!");
            return true;
        }

        switch (args[0].toLowerCase(Locale.ROOT)) {

            case "create" -> {
                if (args.length < 2) {
                    player.sendMessage(PREFIX
                            + "Usage: /mw create <item>");
                    return true;
                }

                String itemId = args[1].toLowerCase(Locale.ROOT);

                if (itemId.length() > 32
                        || !itemId.matches("[a-z0-9_-]+")) {
                    player.sendMessage(PREFIX + ChatColor.RED
                            + "Use only letters, numbers, _ or -");
                    return true;
                }

                if (getConfig().contains("items." + itemId)) {
                    player.sendMessage(PREFIX + ChatColor.RED
                            + "This item already exists!");
                    return true;
                }

                itemCreatorGUI.openCreate(player, itemId);
                player.sendMessage(PREFIX
                        + "Creating item: " + itemId);
                return true;
            }

            case "edit" -> {
                if (args.length < 2) {
                    player.sendMessage(PREFIX
                            + "Usage: /mw edit <item>");
                    return true;
                }

                String itemId = args[1].toLowerCase(Locale.ROOT);

                if (!getConfig().contains("items." + itemId)) {
                    player.sendMessage(PREFIX + ChatColor.RED
                            + "Item not found: " + itemId);
                    return true;
                }

                itemCreatorGUI.openEditor(player, itemId);
                return true;
            }

            case "list" -> {
                if (!getConfig().isConfigurationSection("items")) {
                    player.sendMessage(PREFIX + "No items created yet.");
                    return true;
                }

                player.sendMessage(PREFIX + "Created items:");

                for (String item :
                        getConfig().getConfigurationSection("items")
                                .getKeys(false)) {
                    player.sendMessage(ChatColor.GRAY + " - " + item);
                }

                return true;
            }

            case "reload" -> {
                reloadConfig();
                player.sendMessage(PREFIX + ChatColor.GREEN
                        + "Configuration reloaded!");
                return true;
            }

            default -> {
                sendHelp(sender);
                return true;
            }
        }
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(PREFIX + "§eCommands:");
        sender.sendMessage("§7/mw create <item>");
        sender.sendMessage("§7/mw edit <item>");
        sender.sendMessage("§7/mw list");
        sender.sendMessage("§7/mw reload");
        sender.sendMessage("§7/mw help");
    }
}
