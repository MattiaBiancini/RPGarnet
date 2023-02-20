package me.rpgarnet.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import me.rpgarnet.PluginViewModel;
import me.rpgarnet.RPGarnet;
import me.rpgarnet.data.PlayerData;
import me.rpgarnet.data.attribute.Stats;
import me.rpgarnet.utils.HexColor;
import me.rpgarnet.utils.StringUtils;

public class InfoPlayer implements CommandExecutor, TabCompleter {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(StringUtils.colorFixing("&4&LERROR&C: This command is executable only by &4&lPlayers&c!"));
			return true;
		}
		
		Player player = (Player) sender;
		PluginViewModel viewModel = RPGarnet.instance.getViewModel();
		
		if(args.length == 0) {
			
			player.sendMessage("§7===================[§6§lRPGarnet§e§lInfo§7]===================");
			player.sendMessage("§e/rpg §8§l» §7Shows this page");
			player.sendMessage("§e/rpg §e[stats] §8§l» §7Shows info on that stats");
			player.sendMessage("§7=====================================================");
			return true;
			
		}
		
		Stats stat = Stats.getStats(args[0]);
		if(stat == null) {
			sender.sendMessage(StringUtils.yamlString(viewModel.getMessage().getString("stats.not-found")));
			return true;
		}
		
		PlayerData data = viewModel.getPlayerData(player);
		if(data == null) {
			sender.sendMessage(StringUtils.yamlString(viewModel.getMessage().getString("stats.data-not-found")));
			return true;
		}
		
		player.sendMessage(HexColor.centeredMessage(StringUtils.colorFixing("&7--=[&6&l" + stat.getStatsName() + "&7]=--")));
		player.sendMessage(StringUtils.colorFixing("&7"));
		player.sendMessage(StringUtils.colorFixing("&7Attribute Value &8» &e" + data.getStats()[Stats.getIntValue(stat)].getAttributeValue()));
		player.sendMessage(StringUtils.colorFixing("&7Level &8» &e" + data.getStats()[Stats.getIntValue(stat)].getLevel()));
		player.sendMessage(StringUtils.colorFixing("&7Experience &8» &e" + data.getStats()[Stats.getIntValue(stat)].getExperience() + "&7/&6" + data.getStats()[Stats.getIntValue(stat)].getExpToLevel()));
		player.sendMessage(StringUtils.percentageExp(data, Stats.getIntValue(stat)));
		player.sendMessage(HexColor.centeredMessage(StringUtils.colorFixing("&7")));
		player.sendMessage(HexColor.centeredMessage(StringUtils.colorFixing("&7--=[]=--")));
		
		return true;
		
	}
	
	@Override
	public List<String>	onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		
		List<String> stats = new ArrayList<>();
		
		if(args.length == 1) {
			for(int i = 0; i < 7; i++)
				stats.add(Stats.getStats(i).getStatsName());
		}
		
		return stats;
		
	}

}
