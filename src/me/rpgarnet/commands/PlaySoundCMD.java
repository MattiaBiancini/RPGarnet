package me.rpgarnet.commands;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlaySoundCMD implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if(!(sender instanceof Player))
			return true;
		
		if(args[0].equalsIgnoreCase("list")) {
			String msg = "";
			for(Sound sound : Sound.values())
				msg = msg + " - " + sound.toString();
			sender.sendMessage(msg);
			return true;
		}
		
		Player player = (Player) sender;
		
		if(args.length == 4) {
			player = Bukkit.getPlayer(args[3]);
		}
		player.playSound(player.getLocation(), Sound.valueOf(args[0]), Float.parseFloat(args[1]), Float.parseFloat(args[2]));
		
		return true;

	}

}
