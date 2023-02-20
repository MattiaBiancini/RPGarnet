package me.rpgarnet.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rpgarnet.PluginViewModel;
import me.rpgarnet.RPGarnet;
import me.rpgarnet.utils.StringUtils;

public class Sethome implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(StringUtils.colorFixing("&4&LERROR&C: This command is executable only by &4&lPlayers&c!"));
			return true;
		}
		
		Player player = (Player) sender;
		PluginViewModel viewModel = RPGarnet.instance.getViewModel();
		
		viewModel.addHome(player, player.getLocation());
		player.sendMessage(StringUtils.yamlString(viewModel.getMessage().getString("home.sethome")));
	
		return true;
	}

}
