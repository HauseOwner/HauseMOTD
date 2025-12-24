package com.HauseOwner.plugins.HauseMOTD;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.ChatColor;

import java.util.List;

public class MotdCommand implements CommandExecutor {
	
	private final Main plugin;
	
	public MotdCommand(Main plugin) {
		this.plugin = plugin;
		
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (!sender.hasPermission("hausemotd.use")) {
			sender.sendMessage("§cYou do not have permission to use this command.");
			return true;
		}
		
		// /motd list
		if (args.length == 1 && args[0].equalsIgnoreCase("list")) {
			List<String> motds = plugin.getConfig().getStringList("motds");
			
			sender.sendMessage("§6--- MOTDs ---");
			
			if (motds.isEmpty()) {
				sender.sendMessage("§cNo MOTDs set.");
				return true;
			}
			
			for (int i = 0; i < motds.size(); i++) {
				sender.sendMessage("§2" + (i + 1) + ". " + motds.get(i));
			}
			
			
			return true;
		}
		
		// /motd remove <number>
		if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
			List<String> motds = plugin.getConfig().getStringList("motds");
			
			if (motds.isEmpty()) {
				sender.sendMessage("§cThere are no MOTDs to remove.");
				return true;
			}
			
			int index;
			try {
				index = Integer.parseInt(args[1]) - 1; // convert to 0 based
			} catch (NumberFormatException e) {
				sender.sendMessage("§cPlease provide a valid number.");
				return true;
			}
			
			if (index < 0 || index >= motds.size()) {
				sender.sendMessage("§cThere are only " + index + " MOTDs.");
				return true;
			}
			
			String removed = motds.remove(index);
			
			plugin.getConfig().set("motds", motds);
			plugin.saveConfig();
			
			sender.sendMessage("§aRemoved MOTD: §r" + removed);
			return true;
		}
		
		// in case of blank arguments
		if (args.length == 0) {
			sender.sendMessage("§cUsage: /motd <message> | /motd list | /motd remove <number>");
			return true;
		}
		
		@SuppressWarnings("deprecation")
		String message = ChatColor.translateAlternateColorCodes('&',
				String.join(" ", args)
		);
		
		List<String> motds = plugin.getConfig().getStringList("motds");
		motds.add(message);
		
		plugin.getConfig().set("motds", motds);
		plugin.saveConfig();
		
		sender.sendMessage("§aMOTD added!");
		return true;
	} 

}
