package me.rpgarnet.commands;

import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AttributeCMD implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		Player player = (Player) sender;
		
		if(args.length < 1)
			return true;
		
		if(args.length == 1) {
			player.sendMessage("Valore di " + args[0] + ": " + player.getAttribute(Attribute.valueOf(args[0])).getBaseValue());
			return true;
		}
		
		player.getAttribute(Attribute.valueOf(args[0])).setBaseValue(Double.parseDouble(args[1]));
		return true;
		
	}

}
