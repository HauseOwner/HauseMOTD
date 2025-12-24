package com.HauseOwner.plugins.HauseMOTD;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class MotdListener implements Listener {
	
	private final Main plugin;
	
	public MotdListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onServerPing(ServerListPingEvent event) {
		event.setMotd(plugin.getCurrentMotd());
	}

}
