package me.rpgarnet.data.attribute;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import me.rpgarnet.utils.StringUtils;

public enum Stats {
	ARMOR,
	ATTACK_SPEED,
	DAMAGE,
	EVASION,
	HEALTH,
	KNOCKBACK_RESISTANCE,
	LUCK,
	MOVEMENT_SPEED;
	
	public static int getIntValue(Stats stat) {
		switch(stat) {
			case ARMOR:
				return 0;
			case ATTACK_SPEED:
				return 1;
			case DAMAGE:
				return 2;
			case EVASION:
				return 3;
			case HEALTH:
				return 4;
			case KNOCKBACK_RESISTANCE:
				return 5;
			case LUCK:
				return 6;
			case MOVEMENT_SPEED:
				return 7;
		}
		return -1;
	}
	
	public int getIntValue() {
		switch(this) {
			case ARMOR:
				return 0;
			case ATTACK_SPEED:
				return 1;
			case DAMAGE:
				return 2;
			case EVASION:
				return 3;
			case HEALTH:
				return 4;
			case KNOCKBACK_RESISTANCE:
				return 5;
			case LUCK:
				return 6;
			case MOVEMENT_SPEED:
				return 7;
		}
		return -1;
	}
	
	public static Stats getStats(int value) {
	    switch(value) {
	        case 0:
	            return Stats.ARMOR;
	        case 1:
	            return Stats.ATTACK_SPEED;
	        case 2:
	            return Stats.DAMAGE;
	        case 3:
	        	return Stats.EVASION;
	        case 4:
	            return Stats.HEALTH;
	        case 5:
	            return Stats.KNOCKBACK_RESISTANCE;
	        case 6:
	            return Stats.LUCK;
	        case 7:
	            return Stats.MOVEMENT_SPEED;
	        default:
	            return null;
	    }
	}
	
	public static Stats getStats(String value) {
		
		switch(value.toUpperCase()) {
	        case "ARMOR":
	            return Stats.ARMOR;
	        case "ATKSPEED":
	        case "ATTACKSPEED":
	            return Stats.ATTACK_SPEED;
	        case "DAMAGE":
	            return Stats.DAMAGE;
	        case "EVASION":
	        	return Stats.EVASION;
	        case "HEALTH":
	            return Stats.HEALTH;
	        case "RESISTANCE":
	        case "KNOCKBACKRESISTANCE":
	            return Stats.KNOCKBACK_RESISTANCE;
	        case "LUCK":
	            return Stats.LUCK;
	        case "SPEED":
	        case "MOVEMENTSPEED":
	            return Stats.MOVEMENT_SPEED;
	        default:
	        	return null;
		}
		
	}
	
	public static CustomStatistic getStatistic(Stats stat, Player player) {
		switch(stat) {
			case ARMOR:
				return new Armor(player, Attribute.GENERIC_ARMOR);
			case ATTACK_SPEED:
				return new AttackSpeed(player, Attribute.GENERIC_ATTACK_SPEED);
			case DAMAGE:
				return new Damage(player, Attribute.GENERIC_ATTACK_DAMAGE);
			case EVASION:
				return new Evasion(player, "Evasion");
			case HEALTH:
				return new Health(player, Attribute.GENERIC_MAX_HEALTH);
			case KNOCKBACK_RESISTANCE:
				return new KnockbackResistance(player, Attribute.GENERIC_KNOCKBACK_RESISTANCE);
			case LUCK:
				return new Luck(player, Attribute.GENERIC_LUCK);
			case MOVEMENT_SPEED:
				return new MovementSpeed(player, Attribute.GENERIC_MOVEMENT_SPEED);
		}
		return null;
	}
	
	public static CustomStatistic getStatistic(Stats stat, Player player, int experience, int level) {
		switch(stat) {
			case ARMOR:
				return new Armor(player, Attribute.GENERIC_ARMOR, experience, level);
			case ATTACK_SPEED:
				return new AttackSpeed(player, Attribute.GENERIC_ATTACK_SPEED, experience, level);
			case DAMAGE:
				return new Damage(player, Attribute.GENERIC_ATTACK_DAMAGE, experience, level);
			case EVASION:
				return new Evasion(player, "Evasion", experience, level);
			case HEALTH:
				return new Health(player, Attribute.GENERIC_MAX_HEALTH, experience, level);
			case KNOCKBACK_RESISTANCE:
				return new KnockbackResistance(player, Attribute.GENERIC_KNOCKBACK_RESISTANCE, experience, level);
			case LUCK:
				return new Luck(player, Attribute.GENERIC_LUCK, experience, level);
			case MOVEMENT_SPEED:
				return new MovementSpeed(player, Attribute.GENERIC_MOVEMENT_SPEED, experience, level);
		}
		return null;
	}
	
	@Override
	public String toString() {
		return super.toString().replaceAll("_", "").toLowerCase();
	}

	public String getStatsName() {
		if(this == ATTACK_SPEED)
			return "AtkSpeed";
		if(this == KNOCKBACK_RESISTANCE)
			return "Resistance";
		if(this == MOVEMENT_SPEED)
			return "Speed";
		return StringUtils.capital(super.toString());
	}

	public String getIcon() {
		switch(this) {
			case ARMOR:
				return "✚";
			case ATTACK_SPEED:
				return "✦";
			case DAMAGE:
				return "☄";
			case EVASION:
				return "※";
			case HEALTH:
				return "❤";
			case KNOCKBACK_RESISTANCE:
				return "✖";
			case LUCK:
				return "✤";
			case MOVEMENT_SPEED:
				return "⌛";
		}
		return "";
	}
	
}
