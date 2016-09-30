package com.laurenshup.rockpaperscissors;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class FileSystem {
	
	private static File root = new File(Bukkit.getPluginManager().getPlugin("RockPaperScissors").getDataFolder().getAbsolutePath());
	private static File messages = new File(root, "messages.yml");
	public static File playerdata = new File(root, "playerdata");
	private static File signs = new File(root, "signs.yml");
	private static FileConfiguration config;
	
	public static void checkFiles() {
		if(!root.exists()) {
			root.mkdirs();
		}
		if(!messages.exists()) {
			try {
				messages.createNewFile();
				config = new YamlConfiguration();
				config.load(messages);
				
				config.set("results.win", "&f%player%&7 chose &8%selection% &7and &f%player% &7chose &8%selection%&7. &f%winner%&7 has won this round!");
				config.set("results.tie", "&f%player%&7 and &f%player%&7 both chose &8%selection%&7. It is a tie!");
				config.set("results.youwin", "&9RPS &8» &fYou&7 beat &f%looser%&7 with &8%lives%&7 lives left.");
				config.set("results.youlose", "&9RPS &8» &7You lost the game. &f%winner%&7 has won with &8%lives%&7 lives left.");
				config.set("results.force", "&f%player%&7 has left the match. &7You won!");
				config.set("results.none", "&f%player% &7did not made a selection, &f%player% &7automatically won this round!");
				config.set("results.noneboth", "&7You both did not made a selection!");
				
				config.set("lives.3lives", "&4❤ &4❤ &4❤");
				config.set("lives.2lives", "&8❤ &4❤ &4❤");
				config.set("lives.1live", "&8❤ &8❤ &4❤");
				config.set("lives.nolives", "&8❤ &8❤ &8❤");
				config.set("lives.gameover", "&4&lGame Over");
				
				config.set("join.direct", "&6RPS &8» &7You have joined a random game against: &f%player%&7.");
				config.set("join.wait", "&6RPS &8» &7You have joined a random game. Waiting for someone else to join...");
				config.set("join.other", "&6RPS &8» &f%player% &7joined your match. Starting...");
				config.set("join.makeinvisible", false);
				config.set("join.starting", "&6RPS &8» &7Starting in &f%seconds%&7...");
				config.set("join.already", "&4You are already ingame!");
				
				config.set("leave.leave", "&6RPS &8» &7You have left the match.");
				config.set("leave.confirm", "&6RPS &8» &7Do &6/rps quit&7 again to leave the match. Doing this will make you loose!");
				config.set("leave.none", "&cYou are not playing");
				
				config.set("select.succes", "&7You have selected &8%selection%.");
				config.set("select.rock", "&7Rock");
				config.set("select.paper", "&fPaper");
				config.set("select.scissors", "&4Scissors");
				
				config.set("inventory.name", "&6RPS");
				config.set("inventory.timeleft", "&aTime Left: &6%seconds%");
				
				config.set("signs.noremove", "&4You have to be a player to remove signs!");
				config.set("signs.click", "&4Click on a sign to make a Rock Paper Scissors sign!");
				config.set("signs.invalidtype", "&4That is not a valid RPS sign type");
				config.set("signs.notrps", "&4That is not a RPS sign");
				config.set("signs.nosign", "&4That is not a sign");
				config.set("signs.removed", "&aRemoved the sign");
				config.set("signs.noremove", "&4You can't break a Rock Paper Scissors signs");
				config.set("signs.needplace", "&4You need to specify a place");
				config.set("signs.invalidplace", "&4That's not a valid number");
				config.set("signs.placed", "&aAdded sign");
				
				List<String> joinsign = new ArrayList<String>();
				joinsign.add("&fR&7-&fP&7-&fS");
				joinsign.add("&fJoin Game");
				joinsign.add(" ");
				joinsign.add("&7&nClick to Join");
				config.set("sign.join", joinsign);
				List<String> winsign = new ArrayList<String>();
				winsign.add("&7&ki&r&fTop Wins&7&ki");
				winsign.add("&7Place: &f%place%");
				winsign.add("&7%name%");
				winsign.add("&7%points%");
				config.set("sign.win", winsign);
				List<String> gamesplayedsign = new ArrayList<String>();
				gamesplayedsign.add("&7&ki&r&fTop GamesPlayed&7&ki");
				gamesplayedsign.add("&7Place: &f%place%");
				gamesplayedsign.add("&7%name%");
				gamesplayedsign.add("&7%points%");
				config.set("sign.gamesplayed", gamesplayedsign);
				
				config.set("nopermissions", "&4You don't have enough permissions to do this");
				
				config.set("command.stats.wrong", "&4Please use /rps stats <player>");
				config.set("command.stats.notfound", "&4%player% isn't a player or did never played!");
				config.set("command.wrong", "&4%argument% isn't a argument. Type: /rps help for all arguments.");
				config.set("command.manyarguments", "&4Too many arguments!");
				
				config.save(messages);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
		if(!playerdata.exists()) {
			playerdata.mkdirs();
		}
		if(!signs.exists()) {
			try {
				signs.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static FileConfiguration getConfig(String configfile) {
		if(configfile=="signs") {
			try {
				config = new YamlConfiguration();
				config.load(signs);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
			return config;
		} if(configfile=="messages") {
			try {
				config = new YamlConfiguration();
				config.load(messages);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
			return config;
		}
		return null;
	}
	
	public static void saveConfig(String configfile) {
		if(configfile=="signs") {
			try {
				config.save(signs);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if(configfile=="messages") {
			try {
				config.save(messages);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static FileConfiguration getPConfig(Player player) {
		File playerfile = new File(playerdata, player.getUniqueId().toString() + ".yml");
		try {
			if(!playerfile.exists()) {
				playerfile.createNewFile();
			}
			config = new YamlConfiguration();
			config.load(playerfile);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
		return config;
	}
	
	public static FileConfiguration savePConfig(Player player) {
		File playerfile = new File(playerdata, player.getUniqueId().toString() + ".yml");
		try {
			config.save(playerfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return config;
	}
	
	public static boolean checkPConfig(UUID uuid) {
		File playerfile = new File(playerdata, uuid.toString() + ".yml");
		if(playerfile.exists()) {
			return true;
		} else {
			return false;
		}
	}

}
