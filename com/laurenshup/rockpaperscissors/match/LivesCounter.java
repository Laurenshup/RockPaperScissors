package com.laurenshup.rockpaperscissors.match;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import com.connorlinfoot.titleapi.TitleAPI;
import com.laurenshup.rockpaperscissors.Core;
import com.laurenshup.rockpaperscissors.FileSystem;

public class LivesCounter {
	
	public static HashMap<String, String> counters = new HashMap<String, String>();
	
	public static void setTimer(final Player player) {
		int counter = Bukkit.getScheduler().scheduleSyncRepeatingTask(Core.plugin, new Runnable() {
			public void run() {
				Configuration messageconfig = FileSystem.getConfig("messages");
				String lives = MatchSystem.matchlives.get(player.getUniqueId().toString());
				if(lives.equals("3lives")) {
					String livesmessage = messageconfig.getString("lives.3lives");
					TitleAPI.sendTitle(player, 0, 5, 3, " ", ChatColor.translateAlternateColorCodes('&', livesmessage));
				} else if(lives.equals("2lives")) {
					String livesmessage = messageconfig.getString("lives.2lives");
					TitleAPI.sendTitle(player, 0, 5, 3, " ", ChatColor.translateAlternateColorCodes('&', livesmessage));
				} else if(lives.equals("1live")) {
					String livesmessage = messageconfig.getString("lives.1live");
					TitleAPI.sendTitle(player, 0, 5, 3, " ", ChatColor.translateAlternateColorCodes('&', livesmessage));
				} else if(lives.equals("nolives")) {
					String gameovermessage = messageconfig.getString("lives.gameover");
					String livesmessage = messageconfig.getString("lives.nolives");
					TitleAPI.sendTitle(player, 0, 5, 3, ChatColor.translateAlternateColorCodes('&', gameovermessage), ChatColor.translateAlternateColorCodes('&', livesmessage));
				}
			}
		}, 1, 1);
		counters.put(player.getUniqueId().toString(), String.valueOf(counter));
	}
	
	public static void removeTimer(Player player) {
		int counter = Integer.parseInt(counters.get(player.getUniqueId().toString()));
		Bukkit.getScheduler().cancelTask(counter);
		counters.remove(player.getUniqueId().toString());
	}

}
