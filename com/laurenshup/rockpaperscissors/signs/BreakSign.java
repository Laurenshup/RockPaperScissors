package com.laurenshup.rockpaperscissors.signs;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.laurenshup.rockpaperscissors.FileSystem;

public class BreakSign implements Listener {
	
	@EventHandler
	public void onBreak(BlockBreakEvent event) {
		Configuration messageconfig = FileSystem.getConfig("messages");
		if(event.getBlock()!=null) {
			Material material = event.getBlock().getType();
			if(material.equals(Material.SIGN) || material.equals(Material.SIGN_POST) || material.equals(Material.WALL_SIGN)) {
				Configuration config = FileSystem.getConfig("signs");
				String world = event.getBlock().getLocation().getWorld().getName();
				int X = event.getBlock().getLocation().getBlockX();
				int Y = event.getBlock().getLocation().getBlockY();
				int Z = event.getBlock().getLocation().getBlockZ();
				String all = world + ":" + X + ":" + Y + ":" + Z;
				List<String> joinsigns = config.getStringList("join");
				List<String> gamesplayedsigns = config.getStringList("gamesplayed");
				List<String> winsigns = config.getStringList("win");
				Player player = event.getPlayer();
				if(joinsigns!=null) {
					for(int i=0; i<joinsigns.size(); i++) {
						String now = joinsigns.get(i);
						if(all.equals(now)) {
							if(player.hasPermission("rps.sign")) {
								joinsigns.remove(i);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.removed")));
							} else {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.noremove")));
								event.setCancelled(true);
							}
						}
					}
				}
				if(gamesplayedsigns!=null) {
					for(int i=0; i<gamesplayedsigns.size(); i++) {
						String now = gamesplayedsigns.get(i);
						if(all.equals(now)) {
							if(player.hasPermission("rps.sign")) {
								gamesplayedsigns.remove(i);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.removed")));
							} else {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.noremove")));
								event.setCancelled(true);
							}
						}
					}
				}
				if(winsigns!=null) {
					for(int i=0; i<winsigns.size(); i++) {
						String now = winsigns.get(i);
						if(all.equals(now)) {
							if(player.hasPermission("rps.sign")) {
								winsigns.remove(i);
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.removed")));
							} else {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.noremove")));
								event.setCancelled(true);
							}
						}
					}
				}
				config.set("join", joinsigns);
				config.set("gamesplayed", gamesplayedsigns);
				config.set("win", winsigns);
				FileSystem.saveConfig("signs");
			}
		}
	}
	
}
