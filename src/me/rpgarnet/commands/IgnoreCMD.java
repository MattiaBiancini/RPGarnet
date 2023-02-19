package me.rpgarnet.commands;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rpgarnet.RPGarnet;
import me.rpgarnet.utils.StringUtils;

public class IgnoreCMD implements CommandExecutor {

	public static final ArrayList<String> ingoreAll = new ArrayList<String>();
	public static final HashMap<Player, ArrayList<String>> ingorePlayer = new HashMap<Player, ArrayList<String>>();

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if(!(sender instanceof Player)) {
			sender.sendMessage(StringUtils.colorFixing("&4&LERROR&C: This command is executable only by &4&lPlayers&c!"));
			return true;
		}

		Player player = (Player) sender;

		if(args.length == 0) {                                                
			sender.sendMessage("§7===================[§6§lCHAT-TAG§e§lHELP§7]===================");
			sender.sendMessage("§6/ignore all §8§l» §7Non ricevi piú i ping quando ti taggano in chat.");
			sender.sendMessage("§6/ignore §e[player] §8§l» §7Non ricevi piú i ping di quel player.");
			sender.sendMessage("§8§l   » §7§oRiusare un comando annullerá l'azione");
			sender.sendMessage("§7=====================================================");
			
			return true;
		}
		
		if(args[0].equalsIgnoreCase("all")) {
			if(IgnoreCMD.ingoreAll.contains(player.getName())) {
				IgnoreCMD.ingoreAll.remove(player.getName());
				player.sendMessage(StringUtils.yamlString(RPGarnet.instance.getViewModel().getMessage().getString("command.ignore-nobody")));
			}
			else {
				IgnoreCMD.ingoreAll.add(player.getName());
				player.sendMessage(StringUtils.yamlString(RPGarnet.instance.getViewModel().getMessage().getString("command.ignore-all")));
			}

			return true;

		}

		if(Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(args[0]))) {

			if(IgnoreCMD.ingorePlayer.containsKey(player)) {
				ArrayList<String> ignored;
				if(IgnoreCMD.ingorePlayer.get(player) == null)
					ignored = new ArrayList<String>();
				else
					ignored = IgnoreCMD.ingorePlayer.get(player);

				if(ignored.contains(args[0])) {
					ignored.remove(args[0]);
					sender.sendMessage(StringUtils.yamlString(RPGarnet.instance.getViewModel().getMessage().getString("command.ignore-player-no")
							.replace("%player%", args[0])));
				}
				else {
					ignored.add(args[0]);
					sender.sendMessage(StringUtils.yamlString(RPGarnet.instance.getViewModel().getMessage().getString("command.ignore-player")
							.replace("%player%", args[0])));
				}
				IgnoreCMD.ingorePlayer.put(player, ignored);
				return true;
			}
			
			else {
				ArrayList<String> ignored = new ArrayList<String>();
				ignored.add(args[0]);
				sender.sendMessage(StringUtils.yamlString(RPGarnet.instance.getViewModel().getMessage().getString("command.ignore-player")
						.replace("%player%", args[0])));
				IgnoreCMD.ingorePlayer.put(player, ignored);
				
				return true;
			}

		}

		return true;

	}

}
