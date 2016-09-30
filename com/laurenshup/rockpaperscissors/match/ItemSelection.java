package com.laurenshup.rockpaperscissors.match;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.laurenshup.rockpaperscissors.FileSystem;

public class ItemSelection implements Listener {
	
	public static void newInventory(Player player, int timelefttimes) {
		Configuration messageconfig = FileSystem.getConfig("messages");
		String title = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("inventory.name"));
		Inventory inventory = Bukkit.createInventory(null, 27, title);
		
		ItemStack airglass = new ItemStack(Material.STAINED_GLASS_PANE, 1, Byte.parseByte("15"));
		ItemMeta airglassmeta = airglass.getItemMeta();
		airglassmeta.setDisplayName(" ");
		airglass.setItemMeta(airglassmeta);
		inventory.setItem(9, airglass);
		inventory.setItem(17, airglass);
		inventory.setItem(18, airglass);
		inventory.setItem(19, airglass);
		inventory.setItem(20, airglass);
		inventory.setItem(21, airglass);
		inventory.setItem(22, airglass);
		inventory.setItem(23, airglass);
		inventory.setItem(24, airglass);
		inventory.setItem(25, airglass);
		inventory.setItem(26, airglass);
		
		ItemStack whiteairglass = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemMeta whiteairglassmeta = airglass.getItemMeta();
		whiteairglassmeta.setDisplayName(" ");
		whiteairglass.setItemMeta(whiteairglassmeta);
		inventory.setItem(10, whiteairglass);
		inventory.setItem(12, whiteairglass);
		inventory.setItem(14, whiteairglass);
		inventory.setItem(16, whiteairglass);
		
		ItemStack rock = new ItemStack(Material.COBBLESTONE);
		ItemMeta rockmeta = airglass.getItemMeta();
		String rockstring = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("select.rock"));
		rockmeta.setDisplayName(rockstring);
		rock.setItemMeta(rockmeta);
		inventory.setItem(11, rock);
		
		ItemStack paper = new ItemStack(Material.PAPER);
		ItemMeta papermeta = airglass.getItemMeta();
		String paperstring = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("select.paper"));
		papermeta.setDisplayName(paperstring);
		paper.setItemMeta(papermeta);
		inventory.setItem(13, paper);
		
		ItemStack scissors = new ItemStack(Material.SHEARS);
		ItemMeta scissorsmeta = airglass.getItemMeta();
		String scissorsstring = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("select.scissors"));
		scissorsmeta.setDisplayName(scissorsstring);
		scissors.setItemMeta(scissorsmeta);
		inventory.setItem(15, scissors);
		
		ItemStack timeup = new ItemStack(Material.STAINED_GLASS_PANE, 1, Byte.parseByte("14"));
		ItemMeta timeupmeta = airglass.getItemMeta();
		timeupmeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("inventory.timeleft")));
		timeup.setItemMeta(timeupmeta);
		
		ItemStack time = new ItemStack(Material.STAINED_GLASS_PANE, 1, Byte.parseByte("5"));
		ItemMeta timemeta = airglass.getItemMeta();
		timemeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("inventory.timeleft")));
		time.setItemMeta(timemeta);
		
		int timeleft = (int) timelefttimes/2;
		if(timeleft==0) {
			inventory.setItem(0, timeup);
			inventory.setItem(1, timeup);
			inventory.setItem(2, timeup);
			inventory.setItem(3, timeup);
			inventory.setItem(4, timeup);
			inventory.setItem(5, timeup);
			inventory.setItem(6, timeup);
			inventory.setItem(7, timeup);
			inventory.setItem(8, timeup);
		} else if(timeleft==1) {
			inventory.setItem(0, time);
			inventory.setItem(1, timeup);
			inventory.setItem(2, timeup);
			inventory.setItem(3, timeup);
			inventory.setItem(4, timeup);
			inventory.setItem(5, timeup);
			inventory.setItem(6, timeup);
			inventory.setItem(7, timeup);
			inventory.setItem(8, timeup);
		} else if(timeleft==2) {
			inventory.setItem(0, time);
			inventory.setItem(1, time);
			inventory.setItem(2, timeup);
			inventory.setItem(3, timeup);
			inventory.setItem(4, timeup);
			inventory.setItem(5, timeup);
			inventory.setItem(6, timeup);
			inventory.setItem(7, timeup);
			inventory.setItem(8, timeup);
		} else if(timeleft==3) {
			inventory.setItem(0, time);
			inventory.setItem(1, time);
			inventory.setItem(2, time);
			inventory.setItem(3, timeup);
			inventory.setItem(4, timeup);
			inventory.setItem(5, timeup);
			inventory.setItem(6, timeup);
			inventory.setItem(7, timeup);
			inventory.setItem(8, timeup);
		} else if(timeleft==4) {
			inventory.setItem(0, time);
			inventory.setItem(1, time);
			inventory.setItem(2, time);
			inventory.setItem(3, time);
			inventory.setItem(4, timeup);
			inventory.setItem(5, timeup);
			inventory.setItem(6, timeup);
			inventory.setItem(7, timeup);
			inventory.setItem(8, timeup);
		} else if(timeleft==5) {
			inventory.setItem(0, time);
			inventory.setItem(1, time);
			inventory.setItem(2, time);
			inventory.setItem(3, time);
			inventory.setItem(4, time);
			inventory.setItem(5, timeup);
			inventory.setItem(6, timeup);
			inventory.setItem(7, timeup);
			inventory.setItem(8, timeup);
		} else if(timeleft==6) {
			inventory.setItem(0, time);
			inventory.setItem(1, time);
			inventory.setItem(2, time);
			inventory.setItem(3, time);
			inventory.setItem(4, time);
			inventory.setItem(5, time);
			inventory.setItem(6, timeup);
			inventory.setItem(7, timeup);
			inventory.setItem(8, timeup);
		} else if(timeleft==7) {
			inventory.setItem(0, time);
			inventory.setItem(1, time);
			inventory.setItem(2, time);
			inventory.setItem(3, time);
			inventory.setItem(4, time);
			inventory.setItem(5, time);
			inventory.setItem(6, time);
			inventory.setItem(7, timeup);
			inventory.setItem(8, timeup);
		} else if(timeleft==8) {
			inventory.setItem(0, time);
			inventory.setItem(1, time);
			inventory.setItem(2, time);
			inventory.setItem(3, time);
			inventory.setItem(4, time);
			inventory.setItem(5, time);
			inventory.setItem(6, time);
			inventory.setItem(7, time);
			inventory.setItem(8, timeup);
		} else if(timeleft==9) {
			inventory.setItem(0, time);
			inventory.setItem(1, time);
			inventory.setItem(2, time);
			inventory.setItem(3, time);
			inventory.setItem(4, time);
			inventory.setItem(5, time);
			inventory.setItem(6, time);
			inventory.setItem(7, time);
			inventory.setItem(8, timeup);
		}
		
		player.openInventory(inventory);
	}
	
	@EventHandler
	public void onClick(InventoryClickEvent event) {
		if(event.getInventory()!=null) {
			Inventory inventory = event.getInventory();
			Configuration messageconfig = FileSystem.getConfig("messages");
			String title = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("inventory.name"));
			if(inventory.getName().equals(title)) {
				event.setCancelled(true);
				if(event.getWhoClicked().getType().equals(EntityType.PLAYER)) {
					Player player = (Player) event.getWhoClicked();
					ItemStack item = event.getCurrentItem();
					String selectedmessage = messageconfig.getString("select.succes");
					if(item.getType().equals(Material.COBBLESTONE)) {
						Round.selection.remove(player.getUniqueId().toString());
						Round.selection.put(player.getUniqueId().toString(), "rock");
						player.closeInventory();
						String rockstring = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("select.rock"));
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', selectedmessage.replaceFirst("%selection%", rockstring)));
					} else if(item.getType().equals(Material.PAPER)) {
						Round.selection.remove(player.getUniqueId().toString());
						Round.selection.put(player.getUniqueId().toString(), "paper");
						player.closeInventory();
						String paperstring = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("select.paper"));
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', selectedmessage.replaceFirst("%selection%", paperstring)));
					} else if(item.getType().equals(Material.SHEARS)) {
						Round.selection.remove(player.getUniqueId().toString());
						Round.selection.put(player.getUniqueId().toString(), "scissors");
						player.closeInventory();
						String scissorsstring = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("select.scissors"));
						player.sendMessage(ChatColor.translateAlternateColorCodes('&', selectedmessage.replaceFirst("%selection%", scissorsstring)));
					}
				}
			}
		}
	}

}
