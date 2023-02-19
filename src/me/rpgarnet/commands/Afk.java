package me.rpgarnet.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.rpgarnet.PluginViewModel;
import me.rpgarnet.RPGarnet;
import me.rpgarnet.data.PlayerData;
import me.rpgarnet.listener.PlayerExperience;
import me.rpgarnet.utils.StringUtils;

public class Afk implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(sender instanceof Player) {
			
			Player player = (Player) sender;
			PluginViewModel viewModel = RPGarnet.instance.getViewModel();
			PlayerData data = viewModel.getPlayerData(player);
			
			if(viewModel.isAfk(player)) {
				player.setGameMode(GameMode.SURVIVAL);
				player.sendMessage(StringUtils.yamlString(viewModel.getMessage().getString("afk-off"), data));
				Bukkit.getServer().broadcastMessage(StringUtils.yamlString(viewModel.getMessage().getString("afk-broadcast-off"), data));
				if(PlayerExperience.warning.containsKey(player))
					PlayerExperience.warning.remove(player);
			}
			else {
				player.setGameMode(GameMode.SPECTATOR);
				player.sendMessage(StringUtils.yamlString(viewModel.getMessage().getString("afk-on"), data));
				player.sendMessage(StringUtils.yamlString(viewModel.getMessage().getString("afk-cancel"), data));
				Bukkit.getServer().broadcastMessage(StringUtils.yamlString(viewModel.getMessage().getString("afk-broadcast-on"), data));
			}
			viewModel.switchAfk(player);
			
			return true;
		}
		
		sender.sendMessage(StringUtils.colorFixing("&4&LERROR&C: This command is executable only by &4&lPlayers&c!"));
		
		return true;
		
	}

}
