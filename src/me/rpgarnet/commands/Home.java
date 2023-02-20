package me.rpgarnet.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.rpgarnet.PluginViewModel;
import me.rpgarnet.RPGarnet;
import me.rpgarnet.utils.StringUtils;

public class Home implements CommandExecutor {
	
	private static final int COOLDOWN = 5;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player)) {
			sender.sendMessage(StringUtils.colorFixing("&4&LERROR&C: This command is executable only by &4&lPlayers&c!"));
			return true;
		}
		
		Player player = (Player) sender;
		
		PluginViewModel viewModel = RPGarnet.instance.getViewModel();
		if(viewModel.isAfk(player)) {
			player.sendMessage(StringUtils.yamlString(viewModel.getMessage().getString("afk-cancel")));
			return true;
		}
		
		if(viewModel.getHome(player) == null) {
			player.sendMessage(StringUtils.yamlString(viewModel.getMessage().getString("home.not-found")));
			return true;
		}
		startTeleport(player, viewModel.getHome(player), viewModel);
		
		return true;
		
	}
	
	private void startTeleport(Player player, Location location, PluginViewModel viewModel) {
		
		player.sendMessage(StringUtils.yamlString(viewModel.getMessage().getString("home.delay")));
		
		new BukkitRunnable() {

			@Override
			public void run() {
				player.sendMessage(StringUtils.yamlString(viewModel.getMessage().getString("home.teleport")));
				player.teleport(viewModel.getHome(player));	
			}
			
		}.runTaskLater(RPGarnet.instance, COOLDOWN * 20);
		
	}

}
