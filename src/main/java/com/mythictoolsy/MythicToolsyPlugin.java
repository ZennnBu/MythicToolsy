
package com.mythictoolsy;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public final class MythicToolsyPlugin extends JavaPlugin {

    private static final String PREFIX =
            ChatColor.DARK_PURPLE + "[MythicToolsy] "
            + ChatColor.LIGHT_PURPLE;

    @Override
    public void onEnable() {
        getLogger().info("MythicToolsy has been enabled!");

        if (getCommand("mw") != null) {
            getCommand("mw").setExecutor(this::onCommand);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("MythicToolsy has been disabled!");
    }

    private boolean onCommand(
            CommandSender sender,
            Command command,
            String label,
            String[] args
    ) {
        if (args.length == 0) {
            sender.sendMessage(PREFIX + "MythicToolsy v"
                    + getDescription().getVersion());
            sender.sendMessage(PREFIX + "Commands:");
            sender.sendMessage(ChatColor.GRAY + "/mw create <item>");
            sender.sendMessage(ChatColor.GRAY + "/mw edit <item>");
            sender.sendMessage(ChatColor.GRAY + "/mw help");
            return true;
        }

        if (args[0].equalsIgnoreCase("help")) {
            sender.sendMessage(PREFIX + "Help:");
            sender.sendMessage(ChatColor.GRAY + "/mw create <item>");
            sender.sendMessage(ChatColor.GRAY + "/mw edit <item>");
            sender.sendMessage(ChatColor.GRAY + "/mw list");
            return true;
        }

        sender.sendMessage(PREFIX
                + "This feature is coming in the next build!");
        return true;
    }
                               }
