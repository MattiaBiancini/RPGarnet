package me.rpgarnet.event.time;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

import me.rpgarnet.RPGarnet;

public class BloodMoonEvent implements Listener {
	
	@EventHandler
	public void onMobSpawn(CreatureSpawnEvent e) {
		
		if(RPGarnet.instance.getViewModel().getTimeSchedule().isBloodMoon()) {
			
			Entity entity = e.getEntity();
			if(entity instanceof Monster) {
				
				Monster monster = (Monster) entity;
				monster.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(monster.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue() * 5);
				monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(monster.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue() * 2);
				monster.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(20);
				monster.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).setBaseValue(monster.getAttribute(Attribute.GENERIC_FOLLOW_RANGE).getBaseValue() * 10);
				monster.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(0.6);
				monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(monster.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() * 3);
				
				if(entity instanceof Zombie) {
					monster.getAttribute(Attribute.ZOMBIE_SPAWN_REINFORCEMENTS).setBaseValue(0.3);
				}
				
			}
			
		}
		
	}

}
