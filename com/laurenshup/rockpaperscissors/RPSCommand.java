package com.laurenshup.rockpaperscissors;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import com.laurenshup.rockpaperscissors.match.LivesCounter;
import com.laurenshup.rockpaperscissors.match.MatchSystem;
import com.laurenshup.rockpaperscissors.match.Round;
import com.laurenshup.rockpaperscissors.signs.AddSign;
import com.laurenshup.rockpaperscissors.signs.UpdateSign;

public class RPSCommand implements CommandExecutor {
	
	private static HashMap<String, String> quit = new HashMap<String, String>();

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Configuration messageconfig = FileSystem.getConfig("messages");
		if(cmd.getName().equalsIgnoreCase("rps")) {
			if(args.length==0) {
				sender.sendMessage(ChatColor.AQUA + "=====RockPaperScissors=====");
				sender.sendMessage(ChatColor.AQUA + "/rps help" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Shows this menu");
				sender.sendMessage(ChatColor.AQUA + "/rps quit" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Leave a match");
				sender.sendMessage(ChatColor.AQUA + "/rps stats <player>" + ChatColor.GRAY + " - " + ChatColor.GREEN + "See the stats of a player");
				if(sender.hasPermission("rps.sign")) {
					sender.sendMessage(ChatColor.AQUA + "/rps sign" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Place signs");
				}
			} else if(args.length==1) {
				if(args[0].equalsIgnoreCase("help")) {
					sender.sendMessage(ChatColor.AQUA + "=====RockPaperScissors=====");
					sender.sendMessage(ChatColor.AQUA + "/rps help" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Shows this menu");
					sender.sendMessage(ChatColor.AQUA + "/rps quit" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Leave a match");
					sender.sendMessage(ChatColor.AQUA + "/rps stats <player>" + ChatColor.GRAY + " - " + ChatColor.GREEN + "See the stats of a player");
					if(sender.hasPermission("rps.sign")) {
						sender.sendMessage(ChatColor.AQUA + "/rps sign" + ChatColor.GRAY + " - " + ChatColor.GREEN + "Place signs");
					}
				} else if(args[0].equalsIgnoreCase("sign")) {
					if(sender instanceof Player) {
						AddSign.remove((Player) sender);
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("signs.noremove")));
					}
				} else if(args[0].equalsIgnoreCase("quit")) {
					if(sender instanceof Player) {
						final Player player = (Player) sender;
						boolean playing = false;
						int matchid = 0;
						for(int i=0; i<MatchSystem.matchplayers.size(); i++) {
							String players = MatchSystem.matchplayers.get("match" + i);
							if(players.contains(":")) {
								if(players.split(":")[0].equals(player.getUniqueId().toString()))  {
									playing = true;
									matchid = i;
								} else if(players.split(":")[1].equals(player.getUniqueId().toString()))  {
									playing = true;
									matchid = i;
								}
							} else {
								if(players.equals(player.getUniqueId().toString()))  {
									playing = true;
									matchid = i;
								}
							}
						}
						if(playing==true) {
							if(quit.containsKey(player.getUniqueId().toString())) {
								String left = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("leave.leave"));
								player.sendMessage(left);
								String force = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("results.force")
										.replaceFirst("%player%", player.getName()));
								Player waitingPlayer0 = Bukkit.getPlayer(UUID.fromString(MatchSystem.matchplayers.get("match" + matchid).split(":")[0]));
								Player waitingPlayer1 = null;
								if(LivesCounter.counters.containsKey(player.getUniqueId().toString())) {
									waitingPlayer1 = Bukkit.getPlayer(UUID.fromString(MatchSystem.matchplayers.get("match" + matchid).split(":")[1]));
								}
								Round.time.remove("match" + matchid);
								if(LivesCounter.counters.containsKey(player.getUniqueId().toString())) {
									Round.selection.remove(waitingPlayer0.getUniqueId().toString());
									Round.selection.remove(waitingPlayer1.getUniqueId().toString());
									if(Round.counter.containsKey("match" + matchid)) {
										int counterid = Integer.parseInt(Round.counter.get("match" + matchid));
										Bukkit.getScheduler().cancelTask(counterid);
										Round.counter.remove("match" + matchid);
									}
								}
								if(LivesCounter.counters.containsKey(player.getUniqueId().toString())) {
									MatchSystem.matchlives.remove(waitingPlayer0.getUniqueId().toString());
									MatchSystem.matchlives.remove(waitingPlayer1.getUniqueId().toString());
								}
								MatchSystem.matchplayers.remove("match" + matchid);
								boolean makeinvisible = messageconfig.getBoolean("join.makeinvisible");
								if(makeinvisible==true) {
									for(Player currentplayer : Bukkit.getOnlinePlayers()) {
										currentplayer.showPlayer(waitingPlayer0);
										if(LivesCounter.counters.containsKey(player.getUniqueId().toString())) {
											currentplayer.showPlayer(waitingPlayer1);
										}
										waitingPlayer0.showPlayer(currentplayer);
										if(LivesCounter.counters.containsKey(player.getUniqueId().toString())) {
											waitingPlayer1.showPlayer(currentplayer);
										}
									}
								}
								if(LivesCounter.counters.containsKey(player.getUniqueId().toString())) {
									int winnerint = 2;
									if(waitingPlayer0.getUniqueId().equals(player.getUniqueId())) {
										winnerint = 1;
										waitingPlayer1.sendMessage(force);
									} else {
										winnerint = 0;
										waitingPlayer0.sendMessage(force);
									}
									if(winnerint==0) {
										Configuration playerconfig = FileSystem.getPConfig(waitingPlayer0);
										playerconfig.set("name", waitingPlayer0.getDisplayName());
										String wins = playerconfig.getString("win");
										int win = playerconfig.getInt("win");
										if(wins==null) {
											win = 0;
										}
										win++;
										playerconfig.set("win", win);
										String plays = playerconfig.getString("gamesplayed");
										int play = playerconfig.getInt("gamesplayed");
										if(plays==null) {
											play = 0;
										}
										play++;
										playerconfig.set("gamesplayed", play);
										FileSystem.savePConfig(waitingPlayer0);
										
										Configuration playerconfig1 = FileSystem.getPConfig(waitingPlayer1);
										playerconfig1.set("name", waitingPlayer1.getDisplayName());
										String plays1 = playerconfig1.getString("gamesplayed");
										int play1 = playerconfig1.getInt("gamesplayed");
										if(plays1==null) {
											play1 = 0;
										}
										play1++;
										playerconfig1.set("gamesplayed", play1);
										FileSystem.savePConfig(waitingPlayer1);
									} else if(winnerint==1) {
										Configuration playerconfig = FileSystem.getPConfig(waitingPlayer1);
										playerconfig.set("name", waitingPlayer1.getDisplayName());
										String wins = playerconfig.getString("win");
										int win = playerconfig.getInt("win");
										if(wins==null) {
											win = 0;
										}
										win++;
										playerconfig.set("win", win);
										String plays = playerconfig.getString("gamesplayed");
										int play = playerconfig.getInt("gamesplayed");
										if(plays==null) {
											play = 0;
										}
										play++;
										playerconfig.set("gamesplayed", play);
										FileSystem.savePConfig(waitingPlayer1);
										
										Configuration playerconfig1 = FileSystem.getPConfig(waitingPlayer0);
										playerconfig1.set("name", waitingPlayer0.getDisplayName());
										String plays1 = playerconfig1.getString("gamesplayed");
										int play1 = playerconfig1.getInt("gamesplayed");
										if(plays1==null) {
											play1 = 0;
										}
										play1++;
										playerconfig1.set("gamesplayed", play1);
										FileSystem.savePConfig(waitingPlayer0);
									}
								}
								if(LivesCounter.counters.containsKey(player.getUniqueId().toString())) {
									LivesCounter.removeTimer(waitingPlayer0);
									LivesCounter.removeTimer(waitingPlayer1);
								}
								UpdateSign.update();
							} else {
								quit.put(player.getUniqueId().toString(), "quit");
								String again = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("leave.confirm"));
								player.sendMessage(again);
								Bukkit.getScheduler().scheduleSyncDelayedTask(Core.plugin, new Runnable() {
									public void run() {
										quit.remove(player.getUniqueId().toString());
									}
								}, 60);
							}
						} else {
							sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("leave.none")));
						}
					}
				} else if(args[0].equalsIgnoreCase("stats")) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("command.stats.wrong")));
				} else {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("command.wrong"))
							.replaceFirst("%argument%", args[0]));
				}
			} else if(args.length==2) {
				if(args[0].equalsIgnoreCase("stats")) {
					OfflinePlayer offlineplayer = Bukkit.getOfflinePlayer(args[1]);
					boolean exists = FileSystem.checkPConfig(offlineplayer.getUniqueId());
					if(exists) {
						Configuration config = FileSystem.getPConfig(offlineplayer.getPlayer());
						String wins = config.getString("win");
						int win = config.getInt("win");
						if(wins==null) {
							win = 0;
						}
						String gamesplayes = config.getString("gamesplayed");
						int gamesplayed = config.getInt("gamesplayed");
						if(gamesplayes==null) {
							gamesplayed = 0;
						}
						String name = config.getString("name");
						sender.sendMessage(ChatColor.AQUA + "=====" + name + "'s Stats=====");
						sender.sendMessage(ChatColor.AQUA + "Wins: " + ChatColor.GREEN + win);
						sender.sendMessage(ChatColor.AQUA + "Games Played: " + ChatColor.GREEN + gamesplayed);
					} else {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("command.stats.notfound"))
								.replaceFirst("%player%", args[1]));
					}
				} else 
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("command.manyarguments")));
				}
			} else {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messageconfig.getString("command.manyarguments")));
			}
		return true;
	}

}
