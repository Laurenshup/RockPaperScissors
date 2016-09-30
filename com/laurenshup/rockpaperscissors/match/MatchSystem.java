package com.laurenshup.rockpaperscissors.match;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class MatchSystem {
	
	public static HashMap<String, String> matchplayers = new HashMap<String, String>();
	public static HashMap<String, String> matchlives = new HashMap<String, String>();
	
	public static int addPlayer(Player player) {
		boolean added = false;
		int id = 0;
		for(int i=0; i<matchplayers.size(); i++) {
			String match = matchplayers.get("match" + i);
			if(!match.contains(":")) {
				id = i;
				match += ":" + player.getUniqueId().toString();
				matchplayers.remove("match" + i);
				matchplayers.put("match" + i, match);
				added = true;
				BeforeMatch.setMatch(id);
				break;
			}
		}
		if(added==false) {
			matchplayers.put("match" + matchplayers.size(), player.getUniqueId().toString());
			id = matchplayers.size();
			BeforeMatch.setOnePlayer(player, matchplayers.size());
		}
		return id;
	}

}
