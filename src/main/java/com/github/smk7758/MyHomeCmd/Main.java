package com.github.smk7758.MyHomeCmd;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.github.smk7758.MyHomeCmd.ConsoleLog;

public class Main extends JavaPlugin implements Listener {
	public ConsoleLog cLog = new ConsoleLog(this);

	public HashMap<String, Location> home_player = new HashMap<>(); // PlayerName.HomeName | Location
	public String PluginName = getDescription().getName();
	public boolean DebugMode = false;
	public String PluginPrefix = "[" + ChatColor.GREEN + PluginName + ChatColor.RESET + "] ";
	public String cPrefix = "[" + PluginName + "] ";
	public String pInfo = "[" + ChatColor.RED + "Info" + ChatColor.RESET + "] ";
	public String pError = "[" + ChatColor.RED + "ERROR" + ChatColor.RESET + "] ";

	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		getCommand("MyHomeCmd").setExecutor(new CommandExcuter(this));
		getCommand("SetHome").setExecutor(new CommandExcuter(this));
		getCommand("ListHome").setExecutor(new CommandExcuter(this));
		getCommand("Home").setExecutor(new CommandExcuter(this));
		saveDefaultConfig();
		reloadConfig();
	}

	@Override
	public void onDisable() {
		saveHome();
	}

	public void saveHome() {
		for (String home_player_ : home_player.keySet()) {
			getConfig().set(home_player_, home_player.get(home_player_));
		}
		saveConfig();
	}
}
