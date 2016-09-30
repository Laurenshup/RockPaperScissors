package com.laurenshup.rockpaperscissors.match;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import com.laurenshup.rockpaperscissors.Core;
import com.laurenshup.rockpaperscissors.FileSystem;

public class PreLobby {
	
	public static void setPreLobby(final int matchid) {
		Configuration messageconfig = FileSystem.getConfig("messages");
		final Player waitingPlayer0 = Bukkit.getPlayer(UUID.fromString(MatchSystem.matchplayers.get("match" + matchid).split(":")[0]));
		final Player waitingPlayer1 = Bukkit.getPlayer(UUID.fromString(MatchSystem.matchplayers.get("match" + matchid).split(":")[1]));
		final String starting = messageconfig.getString("join.starting");
		Bukkit.getScheduler().scheduleSyncDelayedTask(Core.plugin, new Runnable() {
			public void run() {
				waitingPlayer0.sendMessage(ChatColor.translateAlternateColorCodes('&', starting.replaceFirst("%seconds%", "5")));
				waitingPlayer1.sendMessage(ChatColor.translateAlternateColorCodes('&', starting.replaceFirst("%seconds%", "5")));
				Bukkit.getScheduler().scheduleSyncDelayedTask(Core.plugin, new Runnable() {
					public void run() {
						waitingPlayer0.sendMessage(ChatColor.translateAlternateColorCodes('&', starting.replaceFirst("%seconds%", "4")));
						waitingPlayer1.sendMessage(ChatColor.translateAlternateColorCodes('&', starting.replaceFirst("%seconds%", "4")));
						Bukkit.getScheduler().scheduleSyncDelayedTask(Core.plugin, new Runnable() {
							public void run() {
								waitingPlayer0.sendMessage(ChatColor.translateAlternateColorCodes('&', starting.replaceFirst("%seconds%", "3")));
								waitingPlayer1.sendMessage(ChatColor.translateAlternateColorCodes('&', starting.replaceFirst("%seconds%", "3")));
								Bukkit.getScheduler().scheduleSyncDelayedTask(Core.plugin, new Runnable() {
									public void run() {
										waitingPlayer0.sendMessage(ChatColor.translateAlternateColorCodes('&', starting.replaceFirst("%seconds%", "2")));
										waitingPlayer1.sendMessage(ChatColor.translateAlternateColorCodes('&', starting.replaceFirst("%seconds%", "2")));
										Bukkit.getScheduler().scheduleSyncDelayedTask(Core.plugin, new Runnable() {
											public void run() {
												waitingPlayer0.sendMessage(ChatColor.translateAlternateColorCodes('&', starting.replaceFirst("%seconds%", "1")));
												waitingPlayer1.sendMessage(ChatColor.translateAlternateColorCodes('&', starting.replaceFirst("%seconds%", "1")));
												setLobby(matchid);
											}
										}, 20);
									}
								}, 20);
							}
						}, 20);
					}
				}, 20);
			}
		}, 20);
		
	}
	
	private static void setLobby(int matchid) {
		Player waitingPlayer0 = Bukkit.getPlayer(UUID.fromString(MatchSystem.matchplayers.get("match" + matchid).split(":")[0]));
		Player waitingPlayer1 = Bukkit.getPlayer(UUID.fromString(MatchSystem.matchplayers.get("match" + matchid).split(":")[1]));
		MatchSystem.matchlives.put(waitingPlayer0.getUniqueId().toString(), "3lives");
		MatchSystem.matchlives.put(waitingPlayer1.getUniqueId().toString(), "3lives");
		LivesCounter.setTimer(waitingPlayer0);
		LivesCounter.setTimer(waitingPlayer1);
		Round.time.put("match" + matchid, "18");
		Round.startRound(matchid);
	}

}
