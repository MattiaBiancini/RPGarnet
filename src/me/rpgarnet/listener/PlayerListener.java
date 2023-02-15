package me.rpgarnet.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.rpgarnet.PluginViewModel;
import me.rpgarnet.RPGarnet;
import me.rpgarnet.utils.StringUtils;

public class PlayerListener implements Listener {
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) {
		
		Player player = e.getPlayer();
		
		RPGarnet.instance.getServer().broadcastMessage(StringUtils.colorFixing("&8[&a+&8] &7" + player.getName()));
		
		PluginViewModel viewModel = RPGarnet.instance.getViewModel();
		
		if(!viewModel.isPlayerRegistered(player)) {
			
			viewModel.registerNewPlayer(player);
			
		}
		
	}

}
