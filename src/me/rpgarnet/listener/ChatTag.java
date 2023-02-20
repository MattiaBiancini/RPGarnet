package me.rpgarnet.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.rpgarnet.commands.IgnoreCMD;

public class ChatTag implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void chatEvent(AsyncPlayerChatEvent e) {

		if(e.isCancelled())
			return;

		Player player = e.getPlayer();
		String msg = e.getMessage();
		msg = ChatColor.stripColor(msg);
		ChatColor color = whatColor(player);

		for(Player p : Bukkit.getOnlinePlayers()) {

			int index = msg.indexOf(p.getName());
			if(index != -1 && player.canSee(p)) {
				if(msg.equals(p.getName()) || (index == 0 && msg.indexOf(p.getName() + " ") != -1) || (index + p.getName().length() == msg.length() && msg.indexOf(" " + p.getName()) != -1) || msg.indexOf(" " + p.getName() + " ") != -1)
					if(!IgnoreCMD.ingoreAll.contains(p.getName()) && (!IgnoreCMD.ingorePlayer.containsKey(p) || !IgnoreCMD.ingorePlayer.get(p).contains(e.getPlayer().getName()))) {
						p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 100, 10);
						msg = getMessage(msg, false, p, color);
					} else
						msg = getMessage(msg, true, p, color);
			}
		}

		if(msg.indexOf("&b@") == -1 && msg.indexOf("&e@") == -1)
			return;
		e.setMessage(ChatColor.translateAlternateColorCodes('&', msg));

	}

	public ChatColor whatColor(Player player) {

		return ChatColor.GRAY;
	}

	public String getMessage(String msg, boolean silent, Player p, ChatColor color) {

		String before = "";
		String after = "";
		String name = "";

		if(msg.indexOf(p.getName()) != 0) {
			before = msg.substring(0, msg.indexOf(p.getName()));
		}

		if(msg.indexOf(p.getName()) + p.getName().length() < msg.length()) {
			after = msg.substring(msg.indexOf(p.getName()) + 1 + p.getName().length());
		}

		if(msg.indexOf(p.getName()) + p.getName().length() < msg.length()) {
			name = msg.substring(msg.indexOf(p.getName()), msg.indexOf(p.getName()) + 1 + p.getName().length());
		}
		else {
			name = msg.substring(msg.indexOf(p.getName()));
		}


		if(silent)
			name = name.replaceAll(p.getName(), "&b@" + p.getName() + color);
		else
			name = name.replaceAll(p.getName(), "&e@" + p.getName() + color);

		return color + before + name + after;
	}

}
