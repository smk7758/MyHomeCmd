package com.github.smk7758.MyHomeCmd;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExcuter implements CommandExecutor {
	// private static final CommandExcuter instance = new CommandExcuter();
	// private CommandExcuter() {
	// }
	// public static CommandExcuter getInstance() {
	// return instance;
	// }
	// Main plugin = Main.getInstance();

	private Main plugin;

	public CommandExcuter(Main instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("SetHome")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Must send from Player.");
				return false;
			}
			Player player = (Player) sender;
			if (!sender.hasPermission("MyHomeCmd.set")) {
				plugin.cLog.sendPermissionErrorMessage(sender, "MyHomeCmd.set");
				return false;
			}
			if (args.length == 0) {
				plugin.cLog.sendMessage(sender, "Please set home name.", 2);
				return false;
			}
			if (args.length > 0) {
				String path = player.getName() + "." + args[0];
				if (plugin.home_player.containsKey(path)) {
					if (plugin.getConfig().contains(path)) {
						plugin.cLog.sendMessage(sender, args[0] + " is already setted.", 2);
						return false;
					}
				}
				Location loc = player.getLocation();
				plugin.home_player.put(path, loc);
				plugin.cLog.sendMessage(sender, args[0] + " has been set.", 0);
				return true;
			}
		}
		if (cmd.getName().equalsIgnoreCase("ListHome")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Must send from Player.");
				return false;
			}
			Player player = (Player) sender;
			if (!sender.hasPermission("MyHomeCmd.set")) {
				plugin.cLog.sendPermissionErrorMessage(sender, "MyHomeCmd.set");
				return false;
			}
			if (args.length == 0) {
				plugin.cLog.sendMessage(sender, "<Home List>", 0);
				for (String home_player_ : plugin.home_player.keySet()) {
					player.sendMessage(home_player_);
					player.sendMessage(home_player_.split("."));
					String home_player_name = home_player_.split(".")[1];
					if (player.getName().equals(home_player_name)) {
						plugin.cLog.sendMessage(sender, home_player_.split(".")[1], 0);
					}
				}
				return true;
			}
		}
		if (cmd.getName().equalsIgnoreCase("Home")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Must send from Player.");
				return false;
			}
			Player player = (Player) sender;
			if (!sender.hasPermission("MyHomeCmd.home")) {
				plugin.cLog.sendPermissionErrorMessage(sender, "MyHomeCmd.home");
				return false;
			}
			if (args.length == 0) {
				plugin.cLog.sendMessage(sender, "Please set home name.", 2);
				return false;
			}
			if (args.length > 0) {
				String path = player.getName() + "." + args[0];
				if (!plugin.home_player.containsKey(path) || plugin.home_player.get(path) == null) {
					if (plugin.getConfig().contains(path)) {
						plugin.home_player.put(path, (Location) plugin.getConfig().get(path));
					} else {
						plugin.cLog.sendMessage(sender, "It doesn't exist.", 2);
						return false;
					}
				}
				if (plugin.home_player.containsKey(path) || plugin.home_player.get(path) != null) {
					Location loc = plugin.home_player.get(path);
					player.teleport(loc);
					plugin.cLog.sendMessage(sender, "You have been teleport.", 0);
					player.sendMessage(loc.toString());
					return true;
				}
			}
		}
		return false;
	}

}
