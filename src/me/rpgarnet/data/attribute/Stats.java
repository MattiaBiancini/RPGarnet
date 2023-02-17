package me.rpgarnet.data.attribute;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import me.rpgarnet.utils.StringUtils;

public enum Stats {
	ARMOR,
	ATTACK_SPEED,
	DAMAGE,
	HEALTH,
	KNOCKBACK,
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
			case HEALTH:
				return 3;
			case KNOCKBACK:
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
	            return Stats.HEALTH;
	        case 4:
	            return Stats.KNOCKBACK;
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
	        case "ATTACKSPEED":
	            return Stats.ATTACK_SPEED;
	        case "DAMAGE":
	            return Stats.DAMAGE;
	        case "HEALTH":
	            return Stats.HEALTH;
	        case "KNOCKBACK":
	            return Stats.KNOCKBACK;
	        case "KNOCKBACKRESISTANCE":
	            return Stats.KNOCKBACK_RESISTANCE;
	        case "LUCK":
	            return Stats.LUCK;
	        case "MOVEMENTSPEED":
	            return Stats.MOVEMENT_SPEED;
	        default:
	        	return null;
		}
		
	}
	
	public static Statistic getStatistic(Stats stat, Player player) {
		switch(stat) {
			case ARMOR:
				return new Armor(player, Attribute.GENERIC_ARMOR);
			case ATTACK_SPEED:
				return new AttackSpeed(player, Attribute.GENERIC_ATTACK_SPEED);
			case DAMAGE:
				return new Damage(player, Attribute.GENERIC_ATTACK_DAMAGE);
			case HEALTH:
				return new Health(player, Attribute.GENERIC_MAX_HEALTH);
			case KNOCKBACK:
				return new Knockback(player, Attribute.GENERIC_ATTACK_KNOCKBACK);
			case KNOCKBACK_RESISTANCE:
				return new KnockbackResistance(player, Attribute.GENERIC_KNOCKBACK_RESISTANCE);
			case LUCK:
				return new Luck(player, Attribute.GENERIC_LUCK);
			case MOVEMENT_SPEED:
				return new MovementSpeed(player, Attribute.GENERIC_MOVEMENT_SPEED);
		}
		return null;
	}
	
	public static Statistic getStatistic(Stats stat, Player player, int experience, int level) {
		switch(stat) {
			case ARMOR:
				return new Armor(player, Attribute.GENERIC_ARMOR, experience, level);
			case ATTACK_SPEED:
				return new AttackSpeed(player, Attribute.GENERIC_ATTACK_SPEED, experience, level);
			case DAMAGE:
				return new Damage(player, Attribute.GENERIC_ATTACK_DAMAGE, experience, level);
			case HEALTH:
				return new Health(player, Attribute.GENERIC_MAX_HEALTH, experience, level);
			case KNOCKBACK:
				return new Knockback(player, Attribute.GENERIC_ATTACK_KNOCKBACK, experience, level);
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
		return StringUtils.capital(super.toString().replaceAll("_", " "));
	}

	public String getIcon() {
		switch(this) {
			case ARMOR:
				return "✚";
			case ATTACK_SPEED:
				return "✦";
			case DAMAGE:
				return "¤";
			case HEALTH:
				return "♡";
			case KNOCKBACK:
				return "☄";
			case KNOCKBACK_RESISTANCE:
				return "✖";
			case LUCK:
				return "★";
			case MOVEMENT_SPEED:
				return "✤";
	}
	return "";
	}
	
}
