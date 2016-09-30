package com.laurenshup.rockpaperscissors.match;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import com.laurenshup.rockpaperscissors.FileSystem;

public class BeforeMatch {
	
	public static void setOnePlayer(Player waitingPlayer, int matchid) {
		Configuration messageconfig = FileSystem.getConfig("messages");
		String waitmessage = messageconfig.getString("join.wait");
		waitingPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', waitmessage));
		boolean makeinvisible = messageconfig.getBoolean("join.makeinvisible");
		if(makeinvisible==true) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				player.hidePlayer(waitingPlayer);
				waitingPlayer.hidePlayer(player);
			}
		}
	}
	
	public static void setMatch(int matchid) {
		Configuration messageconfig = FileSystem.getConfig("messages");
		Player waitingPlayer0 = Bukkit.getPlayer(UUID.fromString(MatchSystem.matchplayers.get("match" + matchid).split(":")[0]));
		Player waitingPlayer1 = Bukkit.getPlayer(UUID.fromString(MatchSystem.matchplayers.get("match" + matchid).split(":")[1]));
		boolean makeinvisible = messageconfig.getBoolean("join.makeinvisible");
		if(makeinvisible==true) {
			for(Player player : Bukkit.getOnlinePlayers()) {
				player.hidePlayer(waitingPlayer1);
				waitingPlayer1.hidePlayer(player);
			}
			waitingPlayer0.showPlayer(waitingPlayer1);
			waitingPlayer1.showPlayer(waitingPlayer0);
		}
		String joinedwith = messageconfig.getString("join.other");
		waitingPlayer0.sendMessage(ChatColor.translateAlternateColorCodes('&', joinedwith.replaceFirst("%player%", waitingPlayer1.getDisplayName())));
		String joineddirect = messageconfig.getString("join.direct");
		waitingPlayer1.sendMessage(ChatColor.translateAlternateColorCodes('&', joineddirect.replaceFirst("%player%", waitingPlayer0.getDisplayName())));
		PreLobby.setPreLobby(matchid);
	}

}
