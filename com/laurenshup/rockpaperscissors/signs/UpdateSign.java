package com.laurenshup.rockpaperscissors.signs;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.laurenshup.rockpaperscissors.FileSystem;

public class UpdateSign {
	
	static HashMap<String, Integer> winplayers = new HashMap<String, Integer>();
	static HashMap<String, String> winplayersname = new HashMap<String, String>();
	static HashMap<String, Integer> gamesplayedplayers = new HashMap<String, Integer>();
	static HashMap<String, String> gamesplayedname = new HashMap<String, String>();
	
	public static void update() {
		File[] files = FileSystem.playerdata.listFiles();
		winplayers = new HashMap<String, Integer>();
		winplayersname = new HashMap<String, String>();
		gamesplayedplayers = new HashMap<String, Integer>();
		gamesplayedname = new HashMap<String, String>();
		for(int i=0; i<files.length; i++) {
			File currentfile = files[i];
			FileConfiguration config = new YamlConfiguration();
			String name = config.getString("name");
			try {
				config.load(currentfile);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
			String wins = config.getString("win");
			if(wins!=null) {
				int win = Integer.parseInt(wins);
				for(int i2=0; i2<winplayers.size(); i2++) {
					int currentwin = winplayers.get(i2);
					if(win>currentwin) {
						changeHashMap(winplayers, winplayersname, i2, name, win, "win");
						break;
					} else {
						continue;
					}
				}
			}
			String plays = config.getString("gamesplayed");
			if(plays!=null) {
				int play = Integer.parseInt(plays);
				for(int i2=0; i2<gamesplayedplayers.size(); i2++) {
					int currentplay = gamesplayedplayers.get(i2);
					if(play>currentplay) {
						changeHashMap(gamesplayedplayers, gamesplayedname, i2, name, play, "gamesplayed");
						break;
					} else {
						continue;
					}
				}
			}
		}
		for(int i=0; i<winplayersname.size(); i++) {
			List<String> winsigns = FileSystem.getConfig("signs").getStringList("win." + (i+1) + "wins");
			if(winsigns!=null) {
				for(int i2=0; i2<winsigns.size(); i2++) {
					String[] currentplace = winsigns.get(i).split(":");
					Block block = Bukkit.getWorld(currentplace[0]).getBlockAt(Integer.parseInt(currentplace[1]), Integer.parseInt(currentplace[2]), Integer.parseInt(currentplace[3]));
					Sign sign = (Sign) block.getState();
					List<String> configsign = FileSystem.getConfig("messages").getStringList("sign.win");
					sign.setLine(0, ChatColor.translateAlternateColorCodes('&', configsign.get(0).replaceFirst("%place%", String.valueOf(i+1))
							.replaceFirst("%name%", winplayersname.get(i).replaceFirst("%points%", String.valueOf(winplayers.get(i))))));
					sign.setLine(1, ChatColor.translateAlternateColorCodes('&', configsign.get(1).replaceFirst("%place%", String.valueOf(i+1))
							.replaceFirst("%name%", winplayersname.get(i).replaceFirst("%points%", String.valueOf(winplayers.get(i))))));
					sign.setLine(2, ChatColor.translateAlternateColorCodes('&', configsign.get(2).replaceFirst("%place%", String.valueOf(i+1))
							.replaceFirst("%name%", winplayersname.get(i).replaceFirst("%points%", String.valueOf(winplayers.get(i))))));
					sign.setLine(3, ChatColor.translateAlternateColorCodes('&', configsign.get(3).replaceFirst("%place%", String.valueOf(i+1))
							.replaceFirst("%name%", winplayersname.get(i).replaceFirst("%points%", String.valueOf(winplayers.get(i))))));
					sign.update();
				}
			}
		}
		for(int i=0; i<gamesplayedplayers.size(); i++) {
			List<String> winsigns = FileSystem.getConfig("signs").getStringList("gamesplayed." + (i+1) + "gamesplayed");
			if(winsigns!=null) {
				for(int i2=0; i2<winsigns.size(); i2++) {
					String[] currentplace = winsigns.get(i).split(":");
					Block block = Bukkit.getWorld(currentplace[0]).getBlockAt(Integer.parseInt(currentplace[1]), Integer.parseInt(currentplace[2]), Integer.parseInt(currentplace[3]));
					Sign sign = (Sign) block.getState();
					List<String> configsign = FileSystem.getConfig("messages").getStringList("sign.gamesplayed");
					sign.setLine(0, ChatColor.translateAlternateColorCodes('&', configsign.get(0).replaceFirst("%place%", String.valueOf(i+1))
							.replaceFirst("%name%", gamesplayedname.get(i)).replaceFirst("%points%", String.valueOf(gamesplayedplayers.get(i)))));
					sign.setLine(1, ChatColor.translateAlternateColorCodes('&', configsign.get(1).replaceFirst("%place%", String.valueOf(i+1))
							.replaceFirst("%name%", gamesplayedname.get(i)).replaceFirst("%points%", String.valueOf(gamesplayedplayers.get(i)))));
					sign.setLine(2, ChatColor.translateAlternateColorCodes('&', configsign.get(2).replaceFirst("%place%", String.valueOf(i+1))
							.replaceFirst("%name%", gamesplayedname.get(i)).replaceFirst("%points%", String.valueOf(gamesplayedplayers.get(i)))));
					sign.setLine(3, ChatColor.translateAlternateColorCodes('&', configsign.get(3).replaceFirst("%place%", String.valueOf(i+1))
							.replaceFirst("%name%", gamesplayedname.get(i)).replaceFirst("%points%", String.valueOf(gamesplayedplayers.get(i)))));
					sign.update();
				}
			}
		}
	}
	
	public static void changeHashMap(HashMap<String, Integer> current, HashMap<String, String> names, int addat, String addname, int addint, String type) {
		HashMap<String, Integer> newcurrentMap = new HashMap<String, Integer>();
		HashMap<String, String> newnamesMap = new HashMap<String, String>();
		for(int i=0; i<current.size(); i++) {
			if(i==addat) {
				break;
			} else {
				newcurrentMap.put(names.get(i), current.get(i));
				newnamesMap.put(names.get(i), names.get(i));
				continue;
			}
		}
		newcurrentMap.put(addname, addint);
		newnamesMap.put(addname, addname);
		for(int i=addint; i<current.size()+1; i++) {
			newcurrentMap.put(names.get(i), current.get(i));
			newnamesMap.put(names.get(i), names.get(i));
		}
		if(type=="win") {
			winplayers = newcurrentMap;
			winplayersname = newnamesMap;
		} else if(type=="gamesplayed") {
			gamesplayedplayers = newcurrentMap;
			gamesplayedname = newnamesMap;
		}
	}
	
	public static void oldupdate() {
		File[] files = FileSystem.playerdata.listFiles();
		int maxgamesplayed = 0;
		String maxgamesplayedname = "";
		int maxwins = 0;
		String maxwinsname = "";
		for(int i=0; i<files.length; i++) {
			File currentfile = files[i];
			FileConfiguration config = new YamlConfiguration();
			try {
				config.load(currentfile);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
			String plays = config.getString("gamesplayed");
			if(plays!=null) {
				int play = Integer.parseInt(plays);
				if(play>maxgamesplayed) {
					maxgamesplayed = play;
					maxgamesplayedname = config.getString("name");
				}
			}
			String wins = config.getString("win");
			if(wins!=null) {
				int win = Integer.parseInt(wins);
				if(win>maxwins) {
					maxwins = win;
					maxwinsname = config.getString("name");
				}
			}
		}
		List<String> gamesplayedsigns = FileSystem.getConfig("signs").getStringList("gamesplayed");
		for(int i=0; i<gamesplayedsigns.size(); i++) {
			String[] currentplace = gamesplayedsigns.get(i).split(":");
			Block block = Bukkit.getWorld(currentplace[0]).getBlockAt(Integer.parseInt(currentplace[1]), Integer.parseInt(currentplace[2]), Integer.parseInt(currentplace[3]));
			Sign sign = (Sign) block.getState();
			sign.setLine(0, ChatColor.GRAY + "" + ChatColor.MAGIC + "i" + ChatColor.RESET + "" + ChatColor.WHITE + "MostPlays" + ChatColor.GRAY + "" + ChatColor.MAGIC + "i");
			sign.setLine(1, ChatColor.DARK_GRAY + maxgamesplayedname);
			sign.setLine(2, ChatColor.DARK_GRAY + String.valueOf(maxgamesplayed));
			sign.setLine(3, " ");
			sign.update();
		}
		List<String> winsigns = FileSystem.getConfig("signs").getStringList("win");
		for(int i=0; i<winsigns.size(); i++) {
			String[] currentplace = winsigns.get(i).split(":");
			Block block = Bukkit.getWorld(currentplace[0]).getBlockAt(Integer.parseInt(currentplace[1]), Integer.parseInt(currentplace[2]), Integer.parseInt(currentplace[3]));
			Sign sign = (Sign) block.getState();
			sign.setLine(0, ChatColor.GRAY + "" + ChatColor.MAGIC + "i" + ChatColor.RESET + "" + ChatColor.WHITE + "MostWins" + ChatColor.GRAY + "" + ChatColor.MAGIC + "i");
			sign.setLine(1, ChatColor.DARK_GRAY + maxwinsname);
			sign.setLine(2, ChatColor.DARK_GRAY + String.valueOf(maxwins));
			sign.setLine(3, " ");
			sign.update();
		}
	}

}
