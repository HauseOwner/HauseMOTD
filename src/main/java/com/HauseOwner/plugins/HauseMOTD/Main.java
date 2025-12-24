package com.HauseOwner.plugins.HauseMOTD;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class Main extends JavaPlugin {
	
	private int currentIndex = 0;
	
	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		getCommand("motd").setExecutor(new MotdCommand(this));
		Bukkit.getPluginManager().registerEvents(new MotdListener(this), this);
		
		startMotdTask();
		getLogger().info("HauseMOTD enabled!");
	}
	
	private void startMotdTask() {
		Bukkit.getScheduler().runTaskTimer(this, () -> {
			List<String> motds = getConfig().getStringList("motds");
			
			if (motds.isEmpty()) return;
			
			currentIndex++;
			if (currentIndex >= motds.size()) {
				currentIndex = 0;
			}
			
		}, 0L, 20L * 60L * 60L); // 1 hour
	}
	
	public String getCurrentMotd() {
		List<String> motds = getConfig().getStringList("motds");
		
		if (motds.isEmpty()) {
			return "fuck";
		}
		
		
		return motds.get(currentIndex);
	}
	
	@Override
	public void onDisable() {
		getLogger().info("HauseMOTD disabled!");
	}

}
