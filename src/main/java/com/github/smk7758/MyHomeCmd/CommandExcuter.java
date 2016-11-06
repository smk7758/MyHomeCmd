package com.github.smk7758.MyHomeCmd;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandExcuter implements CommandExecutor {
	private Main plugin;

	public CommandExcuter(Main instance) {
		plugin = instance;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("MyHomeCmd")) {
			if (args.length == 0) {
				sendCommandList(sender);
				return true;
			}
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("reload")) {
					if (!sender.hasPermission("MyHomeCmd.reload") && (sender instanceof Player)) {
						plugin.cLog.sendPermissionErrorMessage(sender, "MyHomeCmd.reload");
						return false;
					}
					plugin.reloadConfig();
					return true;
					//これ必要なのか分からない。
				}
				if (args[0].equalsIgnoreCase("help")) {
					plugin.cLog.sendMessage(sender, "This plugin can set your Home. And you can set the name of Home.", 0);
					sendCommandList(sender);
					return true;
				}
				if (args[0].equalsIgnoreCase("debug")) {
					if (!sender.hasPermission("MyHomeCmd.debug") && (sender instanceof Player)) {
						plugin.cLog.sendPermissionErrorMessage(sender, "MyHomeCmd.debug");
						return false;
					}
					if (plugin.DebugMode) {
						plugin.DebugMode = false;
						plugin.cLog.sendMessage(sender, "DebugMode has been false.", 0);
					} else {
						plugin.DebugMode = true;
						plugin.cLog.sendMessage(sender, "DebugMode has been true.", 0);
					}
					plugin.cLog.sendMessage(sender, "This is a check. DebugMode is true.", 3);
					return true;
				}
				if (args[0].equalsIgnoreCase("show_all_list")) {
					if (!(sender instanceof Player) && !sender.hasPermission("MyHomeCmd.show_all_list")) {
						plugin.cLog.sendPermissionErrorMessage(sender, "MyHomeCmd.show_all_list");
						return false;
					}
					plugin.cLog.sendMessage(sender, "<HomeList>", 0);
					for (String path : plugin.home_player.keySet()) { //HashMapより。
							plugin.cLog.sendMessage(sender, path, 0);
					}
					for (String name : plugin.getConfig().getConfigurationSection("HomeContents").getKeys(true)) {
						plugin.cLog.sendMessage(sender, name, 0);
					}
					return true;
				}
			}
			sendCommandList(sender);
		}
		if (cmd.getName().equalsIgnoreCase("Home")) {
			if (!(sender instanceof Player)) {
				plugin.cLog.sendMessage(sender, "Must send from Player.", 2);
				return false;
			}
			Player player = (Player) sender;
			if (!sender.hasPermission("MyHomeCmd.home")) {
				plugin.cLog.sendPermissionErrorMessage(sender, "MyHomeCmd.home");
				return false;
			}
			if (args.length == 0) {
				plugin.cLog.sendMessage(sender, "Please set Home name.", 2);
				return false;
			}
			if (args.length > 0) {
				String path = "HomeContents." + player.getName() + "." + args[0];
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
		if (cmd.getName().equalsIgnoreCase("SetHome")) {
			if (!(sender instanceof Player)) {
				plugin.cLog.sendMessage(sender, "Must send from Player.", 2);
				return false;
			}
			Player player = (Player) sender;
			if (!sender.hasPermission("MyHomeCmd.set")) {
				plugin.cLog.sendPermissionErrorMessage(sender, "MyHomeCmd.set");
				return false;
			}
			if (args.length == 0) {
				plugin.cLog.sendMessage(sender, "Please set Home name.", 2);
				return false;
			}
			if (args.length > 0) {
				if (args[0].contains(".")) {
					plugin.cLog.sendMessage(sender, "Sorry you can't set \".\" inside the Home.", 2);
					return false;
				}
				String path = "HomeContents." + player.getName() + "." + args[0];
				if (plugin.home_player.containsKey(path) || plugin.getConfig().contains(path)) { //ちょっと修正している。
					plugin.cLog.sendMessage(sender, args[0] + " is already setted.", 2);
					return false;
				}
				Location loc = player.getLocation();
				plugin.home_player.put(path, loc);
				plugin.cLog.sendMessage(sender, args[0] + " has been set.", 0);
				return true;
			}
		}
		if (cmd.getName().equalsIgnoreCase("DeleteHome")) {
			if (!(sender instanceof Player)) {
				plugin.cLog.sendMessage(sender, "Must send from Player.", 2);
				return false;
			}
			Player player = (Player) sender;
			if (!sender.hasPermission("MyHomeCmd.delete")) {
				plugin.cLog.sendPermissionErrorMessage(sender, "MyHomeCmd.delete");
				return false;
			}
			if (args.length == 0) {
				plugin.cLog.sendMessage(sender, "Please set Home name.", 2);
				return false;
			}
			if (args.length > 0) {
				if (args[0].contains(".")) {
					plugin.cLog.sendMessage(sender, "Sorry you can't set \".\" inside the Home.", 2);
					return false;
				}
				String path = "HomeContents." + player.getName() + "." + args[0];
				if (plugin.home_player.containsKey(path) || plugin.getConfig().contains(path)) {
					plugin.home_player.remove(path);
					plugin.getConfig().set(path, null);
					plugin.saveConfig();
					plugin.cLog.sendMessage(sender, args[0] + " has been delete.", 0);
					return true;
				} else {
					plugin.cLog.sendMessage(sender, args[0] + " does not exist.", 2);
					return false;
				}
			}
		}
		if (cmd.getName().equalsIgnoreCase("ListHome")) {
			if (!(sender instanceof Player)) {
				plugin.cLog.sendMessage(sender, "Must send from Player.", 2);
				return false;
			}
			Player player = (Player) sender;
			if (!sender.hasPermission("MyHomeCmd.list")) {
				plugin.cLog.sendPermissionErrorMessage(sender, "MyHomeCmd.list");
				return false;
			}
			plugin.cLog.sendMessage(sender, "<HomeList>", 0);
			for (String path : plugin.home_player.keySet()) { //HashMapより。
				if (player.getName().equals(path.split("\\.")[1])) {
					plugin.cLog.sendMessage(sender, path.split("\\.")[2], 0);
				}
			}
			String path_ = "HomeContents." + player.getName();
			if (plugin.getConfig().contains(path_)) { //Configより。
				for (String name : plugin.getConfig().getConfigurationSection(path_).getKeys(false)) {
					plugin.cLog.sendMessage(sender, name, 0);
				}
			}
			return true;
		}
		return false;
	}

	public void sendCommandList(CommandSender sender) {
		plugin.cLog.sendMessage(sender, "Command List!!", 0);
		plugin.cLog.sendMessage(sender, "TP Home: /home", 0);
		plugin.cLog.sendMessage(sender, "Set Home: /sethome, /homeset", 0);
		plugin.cLog.sendMessage(sender, "Delete Home: /deletehome, /homedelete, /delhome, /homedel", 0);
		plugin.cLog.sendMessage(sender, "Show Home List: /listhome, /homelist", 0);
		plugin.cLog.sendMessage(sender, "Other: /MyHomeCmd, /mhc", 0);
	}
}
