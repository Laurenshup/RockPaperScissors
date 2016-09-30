package com.laurenshup.rockpaperscissors;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.laurenshup.rockpaperscissors.match.ItemSelection;
import com.laurenshup.rockpaperscissors.match.JoinMatch;
import com.laurenshup.rockpaperscissors.signs.AddSign;
import com.laurenshup.rockpaperscissors.signs.BreakSign;

public class Core extends JavaPlugin {
	
	public static Core plugin = null;
	
	public void onEnable() {
		plugin = this;
		FileSystem.checkFiles();
		getCommand("rps").setExecutor(new RPSCommand());
		Bukkit.getPluginManager().registerEvents(new AddSign(), this);
		Bukkit.getPluginManager().registerEvents(new BreakSign(), this);
		Bukkit.getPluginManager().registerEvents(new JoinMatch(), this);
		Bukkit.getPluginManager().registerEvents(new ItemSelection(), this);
		
		System.out.println("[RockPaperScissors] Succesfully started!");
		System.out.println("[RockPaperScissors] Made by: Laurenshup");
	}
	
}
