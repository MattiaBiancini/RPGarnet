package me.rpgarnet.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.rpgarnet.data.attribute.Stats;

public class ChatEmoji implements Listener {
	
	@EventHandler
	public void chatEvent(AsyncPlayerChatEvent e) {

		String msg = e.getMessage();

		if(msg.indexOf("<3") != -1)
			msg = msg.replaceAll("<3", "❤");
		if(msg.indexOf(":smile:") != -1)
			msg = msg.replaceAll(":smile:", "☺");
		if(msg.indexOf(":sad:") != -1)
			msg = msg.replaceAll(":sad:", "☹");
		if(msg.indexOf(":star:") != -1)
			msg = msg.replaceAll(":star:", "★");
		if(msg.indexOf(":sun:") != -1)
			msg = msg.replaceAll(":sun:", "☀");
		if(msg.indexOf(":armor:") != -1)
			msg = msg.replaceAll(":armor:", Stats.ARMOR.getIcon());
		if(msg.indexOf(":atkspd:") != -1)
			msg = msg.replaceAll(":sun:", Stats.ATTACK_SPEED.getIcon());
		if(msg.indexOf(":damage:") != -1)
			msg = msg.replaceAll(":sun:", Stats.DAMAGE.getIcon());
		if(msg.indexOf(":health:") != -1)
			msg = msg.replaceAll(":sun:", Stats.HEALTH.getIcon());
		if(msg.indexOf(":knock:") != -1)
			msg = msg.replaceAll(":sun:", Stats.KNOCKBACK_RESISTANCE.getIcon());
		if(msg.indexOf(":luck:") != -1)
			msg = msg.replaceAll(":sun:", Stats.LUCK.getIcon());
		if(msg.indexOf(":speed:") != -1)
			msg = msg.replaceAll(":sun:", Stats.MOVEMENT_SPEED.getIcon());

		e.setMessage(msg);

	}

}
