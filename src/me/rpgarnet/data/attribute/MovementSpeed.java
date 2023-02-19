package me.rpgarnet.data.attribute;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class MovementSpeed extends Statistic {
	
	private static final int BASE_EXP_LEVEL_UP = 500;
	private static final int MAX_LEVEL = 18;
	private static final double RATE = 1.5;
	private static final double ADDITIVE = 0.005;
	private static final double BASE = 0.075;
	
	public MovementSpeed(Player player, Attribute attribute) {
		this(player, attribute, 0, 0, BASE_EXP_LEVEL_UP, BASE);
	}
	
	public MovementSpeed(Player player, Attribute attribute, int experience, int level) {
		this.player = player;
		this.attribute = attribute;
		
		this.baseExpLevelUp = BASE_EXP_LEVEL_UP;
		this.maxLevel = MAX_LEVEL;
		this.rate = RATE;
		this.additivePerLevel = ADDITIVE;
		this.baseAttribute = BASE;
		
		this.experience = experience;
		this.level = level;
		this.attributeValue = Statistic.calculateAttributeLevel(level, BASE, ADDITIVE);
		
		this.expToLevel = expToLevel(level);
	}

	public MovementSpeed(Player player, Attribute attribute, int experience, int level, int expToLevel, double value) {
		this.player = player;
		this.attribute = attribute;
		
		this.experience = experience;
		this.level = level;
		this.expToLevel = expToLevel;
		this.attributeValue = value;
		
		this.baseExpLevelUp = BASE_EXP_LEVEL_UP;
		this.maxLevel = MAX_LEVEL;
		this.rate = RATE;
		this.additivePerLevel = ADDITIVE;
		this.baseAttribute = BASE;
	}

}
