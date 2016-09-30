package com.laurenshup.rockpaperscissors.match;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.laurenshup.rockpaperscissors.FileSystem;

public class JoinMatch implements Listener {
	
	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		Configuration messageconfig = FileSystem.getConfig("messages");
		if(event.getAction()==Action.LEFT_CLICK_BLOCK || event.getAction()==Action.RIGHT_CLICK_BLOCK) {
			if(event.getClickedBlock()!=null) {
				Material material = event.getClickedBlock().getType();
				if(material.equals(Material.SIGN) || material.equals(Material.SIGN_POST) || material.equals(Material.WALL_SIGN)) {
					Configuration config = FileSystem.getConfig("signs");
					String world = event.getClickedBlock().getLocation().getWorld().getName();
					int X = event.getClickedBlock().getLocation().getBlockX();
					int Y = event.getClickedBlock().getLocation().getBlockY();
					int Z = event.getClickedBlock().getLocation().getBlockZ();
					String all = world + ":" + X + ":" + Y + ":" + Z;
					boolean isjoinsign = false;
					List<String> joinsigns = config.getStringList("join");
					for(int i=0; i<joinsigns.size(); i++) {
						String current = joinsigns.get(i);
						if(all.equals(current)) {
							isjoinsign = true;
						}
					}
					if(isjoinsign==true) {
						boolean already = false;
						for(int i=0; i<MatchSystem.matchplayers.size(); i++) {
							String players = MatchSystem.matchplayers.get("match" + i);
							if(players.contains(":")) {
								if(players.split(":")[0].equals(event.getPlayer().getUniqueId().toString()))  {
									already = true;
								} else if(players.split(":")[1].equals(event.getPlayer().getUniqueId().toString()))  {
									already = true;
								}
							} else {
								if(players.equals(event.getPlayer().getUniqueId().toString()))  {
									already = true;
								}
							}
						}
						if(already==true) {
							event.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("join.already")));
						} else {
							MatchSystem.addPlayer(event.getPlayer());
						}
					}
				}
			}
		}
	}

}
