package com.laurenshup.rockpaperscissors.signs;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import com.laurenshup.rockpaperscissors.FileSystem;

public class AddSign implements Listener {
	
	private static HashMap<Player, String> players = new HashMap<Player, String>();
	
	public static void remove(Player player) {
		Configuration messageconfig = FileSystem.getConfig("messages");
		if(player.hasPermission("rps.sign")) {
			players.put(player, "selecting");
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.click")));
		} else {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("nopermissions")));
		}
	}
	
	@EventHandler
	public void onClick(PlayerInteractEvent event) {
		Configuration messageconfig = FileSystem.getConfig("messages");
		if(event.getAction()==Action.RIGHT_CLICK_BLOCK || event.getAction()==Action.LEFT_CLICK_BLOCK) {
			Player player = event.getPlayer();
			if(players.containsKey(player)) {
				if(event.getClickedBlock()!=null) {
					Material material = event.getClickedBlock().getType();
					if(material.equals(Material.SIGN) || material.equals(Material.SIGN_POST) || material.equals(Material.WALL_SIGN)) {
						Configuration config = FileSystem.getConfig("signs");
						Sign sign = (Sign) event.getClickedBlock().getState();
						if(sign.getLine(0).equals("[RPS]")) {
							if(sign.getLine(1).equals("Wins")) {
								if(sign.getLine(2)!=null) {
									if(checkNumber(sign.getLine(2))) {
										if(Integer.parseInt(sign.getLine(2))<=0) {
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.invalidplace")));
										} else {
											List<String> currentwins = config.getStringList("win." + sign.getLine(2) + "wins");
											String world = event.getClickedBlock().getLocation().getWorld().getName();
											int X = event.getClickedBlock().getLocation().getBlockX();
											int Y = event.getClickedBlock().getLocation().getBlockY();
											int Z = event.getClickedBlock().getLocation().getBlockZ();
											currentwins.add(world + ":" + X + ":" + Y + ":" + Z);
											config.set("win." + sign.getLine(2) + "wins", currentwins);
											FileSystem.saveConfig("signs");
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.placed")));
											List<String> lines = messageconfig.getStringList("sign.win");
											sign.setLine(0, ChatColor.translateAlternateColorCodes('&', lines.get(0).replaceFirst("%place%", sign.getLine(2))
													.replaceFirst("%name%", "---").replaceFirst("%points%", "---")));
											sign.setLine(1, ChatColor.translateAlternateColorCodes('&', lines.get(1).replaceFirst("%place%", sign.getLine(2))
													.replaceFirst("%name%", "---").replaceFirst("%points%", "---")));
											sign.setLine(2, ChatColor.translateAlternateColorCodes('&', lines.get(2).replaceFirst("%place%", sign.getLine(2))
													.replaceFirst("%name%", "---").replaceFirst("%points%", "---")));
											sign.setLine(3, ChatColor.translateAlternateColorCodes('&', lines.get(3).replaceFirst("%place%", sign.getLine(2))
													.replaceFirst("%name%", "---").replaceFirst("%points%", "---")));
											sign.update();
										}
									} else {
										player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.invalidplace")));
									}
								} else {
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.needplace")));
								}
							} else if(sign.getLine(1).equals("GamesPlayed")) {
								if(sign.getLine(2)!=null) {
									if(checkNumber(sign.getLine(2))) {
										if(Integer.parseInt(sign.getLine(2))<=0) {
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.invalidplace")));
										} else {
											List<String> currentgamesplayed = config.getStringList("gamesplayed." + sign.getLine(2) + "gamesplayed");
											String world = event.getClickedBlock().getLocation().getWorld().getName();
											int X = event.getClickedBlock().getLocation().getBlockX();
											int Y = event.getClickedBlock().getLocation().getBlockY();
											int Z = event.getClickedBlock().getLocation().getBlockZ();
											currentgamesplayed.add(world + ":" + X + ":" + Y + ":" + Z);
											config.set("gamesplayed." + sign.getLine(2) + "gamesplayed", currentgamesplayed);
											FileSystem.saveConfig("signs");
											player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.placed")));
											List<String> lines = messageconfig.getStringList("sign.gamesplayed");
											sign.setLine(0, ChatColor.translateAlternateColorCodes('&', lines.get(0).replaceFirst("%place%", sign.getLine(2))
													.replaceFirst("%name%", "---").replaceFirst("%points%", "---")));
											sign.setLine(1, ChatColor.translateAlternateColorCodes('&', lines.get(1).replaceFirst("%place%", sign.getLine(2))
													.replaceFirst("%name%", "---").replaceFirst("%points%", "---")));
											sign.setLine(2, ChatColor.translateAlternateColorCodes('&', lines.get(2).replaceFirst("%place%", sign.getLine(2))
													.replaceFirst("%name%", "---").replaceFirst("%points%", "---")));
											sign.setLine(3, ChatColor.translateAlternateColorCodes('&', lines.get(3).replaceFirst("%place%", sign.getLine(2))
													.replaceFirst("%name%", "---").replaceFirst("%points%", "---")));
											sign.update();
										}
									} else {
										player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.invalidplace")));
									}
								} else {
									player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.needplace")));
								}
							} else if(sign.getLine(1).equals("Join")) {
								List<String> wins = config.getStringList("join");
								String world = event.getClickedBlock().getLocation().getWorld().getName();
								int X = event.getClickedBlock().getLocation().getBlockX();
								int Y = event.getClickedBlock().getLocation().getBlockY();
								int Z = event.getClickedBlock().getLocation().getBlockZ();
								wins.add(world + ":" + X + ":" + Y + ":" + Z);
								config.set("join", wins);
								FileSystem.saveConfig("signs");
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.placed")));
								List<String> lines = messageconfig.getStringList("sign.join");
								sign.setLine(0, ChatColor.translateAlternateColorCodes('&', lines.get(0)));
								sign.setLine(1, ChatColor.translateAlternateColorCodes('&', lines.get(1)));
								sign.setLine(2, ChatColor.translateAlternateColorCodes('&', lines.get(2)));
								sign.setLine(3, ChatColor.translateAlternateColorCodes('&', lines.get(3)));
								sign.update();
							} else {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.invalidtype")));
							}
						} else {
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.notrps")));
						}
					} else {
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.nosign")));
					}
				}
				players.remove(player);
			}
		}
	}
	
	public static boolean checkNumber(String number) {
		try {
			Integer.parseInt(number);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
