package com.laurenshup.rockpaperscissors.match;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;

import com.laurenshup.rockpaperscissors.Core;
import com.laurenshup.rockpaperscissors.FileSystem;
import com.laurenshup.rockpaperscissors.signs.UpdateSign;

public class Round {
	
	public static HashMap<String, String> counter = new HashMap<String, String>();
	public static HashMap<String, String> time = new HashMap<String, String>();
	public static HashMap<String, String> selection = new HashMap<String, String>();
	public static HashMap<String, String> checktimer = new HashMap<String, String>();;
	
	public static void startRound(final int matchid) {
		final Player waitingPlayer0 = Bukkit.getPlayer(UUID.fromString(MatchSystem.matchplayers.get("match" + matchid).split(":")[0]));
		final Player waitingPlayer1 = Bukkit.getPlayer(UUID.fromString(MatchSystem.matchplayers.get("match" + matchid).split(":")[1]));
		ItemSelection.newInventory(waitingPlayer0, 18);
		ItemSelection.newInventory(waitingPlayer1, 18);
		time.remove("match" + matchid);
		time.put("match" + matchid, "18");
		selection.put(waitingPlayer0.getUniqueId().toString(), "nothing");
		selection.put(waitingPlayer1.getUniqueId().toString(), "nothing");
		int counterid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Core.plugin, new Runnable() {
			public void run() {
				int timenow = Integer.parseInt(time.get("match" + matchid));
				timenow--;
				String selection0 = selection.get(waitingPlayer0.getUniqueId().toString());
				String selection1 = selection.get(waitingPlayer1.getUniqueId().toString());
				if(selection0.equals("nothing")) {
					ItemSelection.newInventory(waitingPlayer0, timenow);
				}
				if(selection1.equals("nothing")) {
					ItemSelection.newInventory(waitingPlayer1, timenow);
				}
				if(!selection0.equals("nothing") && !selection1.equals("nothing")) {
					time.remove("match" + matchid);
					checktimer.put("match" + matchid, "did");
					stopCounter(matchid);
				}
				if(timenow==0) {
					time.remove("match" + matchid);
					checktimer.put("match" + matchid, "did");
					stopCounter(matchid);
				} else {
					if(!checktimer.containsKey("match" + matchid)) {
						time.remove("match" + matchid);
						time.put("match" + matchid, String.valueOf(timenow));
					} else {
						setCheck(matchid);
					}
				}
			} 
		}, 20, 20);
		counter.put("match" + matchid, String.valueOf(counterid));
	}
	
	public static void setCheck(int matchid) {
		time.remove("match" + matchid);
		time.put("match" + matchid, "18");
		checktimer.remove("match" + matchid);
	}
	
	public static void stopCounter(final int matchid) {
		final Configuration messageconfig = FileSystem.getConfig("messages");
		int counterid = Integer.parseInt(counter.get("match" + matchid));
		Bukkit.getScheduler().cancelTask(counterid);
		counter.remove("match" + matchid);
		final Player waitingPlayer0 = Bukkit.getPlayer(UUID.fromString(MatchSystem.matchplayers.get("match" + matchid).split(":")[0]));
		final Player waitingPlayer1 = Bukkit.getPlayer(UUID.fromString(MatchSystem.matchplayers.get("match" + matchid).split(":")[1]));
		String selection0 = selection.get(waitingPlayer0.getUniqueId().toString());
		String selection1 = selection.get(waitingPlayer1.getUniqueId().toString());
		int winner = 2;
		if(!selection0.equals("nothing") && !selection1.equals("nothing")) {
			String rockstring = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("select.rock"));
			String paperstring = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("select.paper"));
			String scissorsstring = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("select.scissors"));
			if(selection0.equals("rock") && selection1.equals("rock")) {
				String tiemessage = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("results.tie")
						.replaceFirst("%player%", waitingPlayer0.getDisplayName())
						.replaceFirst("%player%", waitingPlayer1.getDisplayName())
						.replaceFirst("%selection%", rockstring));
				waitingPlayer0.sendMessage(tiemessage);
				waitingPlayer1.sendMessage(tiemessage);
			} else if(selection0.equals("rock") && selection1.equals("paper")) {
				String winmessage = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("results.win")
						.replaceFirst("%player%", waitingPlayer0.getDisplayName())
						.replaceFirst("%selection%", rockstring)
						.replaceFirst("%player%", waitingPlayer1.getDisplayName())
						.replaceFirst("%selection%", paperstring)
						.replaceFirst("%winner%", waitingPlayer1.getDisplayName()));
				waitingPlayer0.sendMessage(winmessage);
				waitingPlayer1.sendMessage(winmessage);
				winner = 1;
			} else if(selection0.equals("rock") && selection1.equals("scissors")) {
				String winmessage = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("results.win")
						.replaceFirst("%player%", waitingPlayer0.getDisplayName())
						.replaceFirst("%selection%", rockstring)
						.replaceFirst("%player%", waitingPlayer1.getDisplayName())
						.replaceFirst("%selection%", scissorsstring)
						.replaceFirst("%winner%", waitingPlayer0.getDisplayName()));
				waitingPlayer0.sendMessage(winmessage);
				waitingPlayer1.sendMessage(winmessage);
				winner = 0;
			} else if(selection0.equals("paper") && selection1.equals("rock")) {
				String winmessage = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("results.win")
						.replaceFirst("%player%", waitingPlayer0.getDisplayName())
						.replaceFirst("%selection%", paperstring)
						.replaceFirst("%player%", waitingPlayer1.getDisplayName())
						.replaceFirst("%selection%", rockstring)
						.replaceFirst("%winner%", waitingPlayer0.getDisplayName()));
				waitingPlayer0.sendMessage(winmessage);
				waitingPlayer1.sendMessage(winmessage);
				winner = 0;
			} else if(selection0.equals("paper") && selection1.equals("paper")) {
				String tiemessage = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("results.tie")
						.replaceFirst("%player%", waitingPlayer0.getDisplayName())
						.replaceFirst("%player%", waitingPlayer1.getDisplayName())
						.replaceFirst("%selection%", paperstring));
				waitingPlayer0.sendMessage(tiemessage);
				waitingPlayer1.sendMessage(tiemessage);
			} else if(selection0.equals("paper") && selection1.equals("scissors")) {
				String winmessage = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("results.win")
						.replaceFirst("%player%", waitingPlayer0.getDisplayName())
						.replaceFirst("%selection%", paperstring)
						.replaceFirst("%player%", waitingPlayer1.getDisplayName())
						.replaceFirst("%selection%", scissorsstring)
						.replaceFirst("%winner%", waitingPlayer1.getDisplayName()));
				waitingPlayer0.sendMessage(winmessage);
				waitingPlayer1.sendMessage(winmessage);
				winner = 1;
			} else if(selection0.equals("scissors") && selection1.equals("rock")) {
				String winmessage = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("results.win")
						.replaceFirst("%player%", waitingPlayer0.getDisplayName())
						.replaceFirst("%selection%", scissorsstring)
						.replaceFirst("%player%", waitingPlayer1.getDisplayName())
						.replaceFirst("%selection%", rockstring)
						.replaceFirst("%winner%", waitingPlayer1.getDisplayName()));
				waitingPlayer0.sendMessage(winmessage);
				waitingPlayer1.sendMessage(winmessage);
				winner = 1;
			} else if(selection0.equals("scissors") && selection1.equals("paper")) {
				String winmessage = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("results.win")
						.replaceFirst("%player%", waitingPlayer0.getDisplayName())
						.replaceFirst("%selection%", scissorsstring)
						.replaceFirst("%player%", waitingPlayer1.getDisplayName())
						.replaceFirst("%selection%", paperstring)
						.replaceFirst("%winner%", waitingPlayer0.getDisplayName()));
				waitingPlayer0.sendMessage(winmessage);
				waitingPlayer1.sendMessage(winmessage);
				winner = 0;
			} else if(selection0.equals("scissors") && selection1.equals("scissors")) {
				String tiemessage = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("results.tie")
						.replaceFirst("%player%", waitingPlayer0.getDisplayName())
						.replaceFirst("%player%", waitingPlayer1.getDisplayName())
						.replaceFirst("%selection%", scissorsstring));
				waitingPlayer0.sendMessage(tiemessage);
				waitingPlayer1.sendMessage(tiemessage);
			}
		} else {
			if(selection0.equals("nothing") && selection1.equals("nothing")) {
				String nonemessage = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("results.noneboth"));
				waitingPlayer0.sendMessage(nonemessage);
				waitingPlayer1.sendMessage(nonemessage);
			} else if(selection0.equals("nothing")) {
				String nonemessage = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("results.none")
						.replaceFirst("%player%", waitingPlayer0.getDisplayName())
						.replaceFirst("%player%", waitingPlayer1.getDisplayName()));
				waitingPlayer0.sendMessage(nonemessage);
				waitingPlayer1.sendMessage(nonemessage);
				winner = 1;
			} else if(selection1.equals("nothing")) {
				String nonemessage = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("results.none")
						.replaceFirst("%player%", waitingPlayer1.getDisplayName())
						.replaceFirst("%player%", waitingPlayer0.getDisplayName()));
				waitingPlayer0.sendMessage(nonemessage);
				waitingPlayer1.sendMessage(nonemessage);
				winner = 0;
			}
		}
		boolean stop = false;
		if(winner==0) {
			String lives = MatchSystem.matchlives.get(waitingPlayer1.getUniqueId().toString());
			if(lives.equals("3lives")) {
				MatchSystem.matchlives.remove(waitingPlayer1.getUniqueId().toString());
				MatchSystem.matchlives.put(waitingPlayer1.getUniqueId().toString(), "2lives");
			} else if(lives.equals("2lives")) {
				MatchSystem.matchlives.remove(waitingPlayer1.getUniqueId().toString());
				MatchSystem.matchlives.put(waitingPlayer1.getUniqueId().toString(), "1live");
			} else if(lives.equals("1live")) {
				MatchSystem.matchlives.remove(waitingPlayer1.getUniqueId().toString());
				MatchSystem.matchlives.put(waitingPlayer1.getUniqueId().toString(), "nolives");
				stop = true;
			}
		} else if(winner==1) {
			String lives = MatchSystem.matchlives.get(waitingPlayer0.getUniqueId().toString());
			if(lives.equals("3lives")) {
				MatchSystem.matchlives.remove(waitingPlayer0.getUniqueId().toString());
				MatchSystem.matchlives.put(waitingPlayer0.getUniqueId().toString(), "2lives");
			} else if(lives.equals("2lives")) {
				MatchSystem.matchlives.remove(waitingPlayer0.getUniqueId().toString());
				MatchSystem.matchlives.put(waitingPlayer0.getUniqueId().toString(), "1live");
			} else if(lives.equals("1live")) {
				MatchSystem.matchlives.remove(waitingPlayer0.getUniqueId().toString());
				MatchSystem.matchlives.put(waitingPlayer0.getUniqueId().toString(), "nolives");
				stop = true;
			}
		}
		String livesleft0 = MatchSystem.matchlives.get(waitingPlayer0.getUniqueId().toString());
		String livesleft1 = MatchSystem.matchlives.get(waitingPlayer1.getUniqueId().toString());
		int lives0 = 3;
		int lives1 = 3;
		if(livesleft0.equals("3lives")) {
			lives0 = 3;
		} else if(livesleft0.equals("2lives")) {
			lives0 = 2;
		} else if(livesleft0.equals("1live")) {
			lives0 = 1;
		} else if(livesleft0.equals("nolives")) {
			lives0 = 0;
		}
		if(livesleft1.equals("3lives")) {
			lives1 = 3;
		} else if(livesleft1.equals("2lives")) {
			lives1 = 2;
		} else if(livesleft1.equals("1live")) {
			lives1 = 1;
		} else if(livesleft1.equals("nolives")) {
			lives1 = 0;
		}
		time.remove("match" + matchid);
		selection.remove(waitingPlayer0.getUniqueId().toString());
		selection.remove(waitingPlayer1.getUniqueId().toString());
		final int winnerint = winner;
		final int live0 = lives0;
		final int live1 = lives1;
		if(stop==false) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Core.plugin, new Runnable() {
				public void run() {
					time.put("match" + matchid, "18");
					startRound(matchid);
				}
			}, 100);
		} else {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Core.plugin, new Runnable() {
				public void run() {
					if(winnerint==0) {
						String youwin = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("results.youwin")
								.replaceFirst("%looser%", waitingPlayer1.getDisplayName())
								.replaceFirst("%lives%", String.valueOf(live0)));
						String youlose = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("results.youlose")
								.replaceFirst("%winner%", waitingPlayer0.getDisplayName())
								.replaceFirst("%lives%", String.valueOf(live0)));
						waitingPlayer0.sendMessage(youwin);
						waitingPlayer1.sendMessage(youlose);
					} else if(winnerint==1) {
						String youwin = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("results.youwin")
								.replaceFirst("%looser%", waitingPlayer0.getDisplayName())
								.replaceFirst("%lives%", String.valueOf(live1)));
						String youlose = ChatColor.translateAlternateColorCodes('&', messageconfig.getString("results.youlose")
								.replaceFirst("%winner%", waitingPlayer1.getDisplayName())
								.replaceFirst("%lives%", String.valueOf(live1)));
						waitingPlayer0.sendMessage(youlose);
						waitingPlayer1.sendMessage(youwin);
					}
					LivesCounter.removeTimer(waitingPlayer0);
					LivesCounter.removeTimer(waitingPlayer1);
					MatchSystem.matchlives.remove(waitingPlayer0.getUniqueId().toString());
					MatchSystem.matchlives.remove(waitingPlayer1.getUniqueId().toString());
					MatchSystem.matchplayers.remove("match" + matchid);
					boolean makeinvisible = messageconfig.getBoolean("join.makeinvisible");
					if(makeinvisible==true) {
						for(Player player : Bukkit.getOnlinePlayers()) {
							player.showPlayer(waitingPlayer0);
							player.showPlayer(waitingPlayer1);
							waitingPlayer0.showPlayer(player);
							waitingPlayer1.showPlayer(player);
						}
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
					UpdateSign.update();
				}
			}, 100);
		}
	}

}
