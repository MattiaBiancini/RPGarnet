package me.rpgarnet.data.attribute;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class Damage extends Statistic {
	
	private static final int BASE_EXP_LEVEL_UP = 50;
	private static final int MAX_LEVEL = 4094;
	private static final double RATE = 1.2;
	private static final double ADDITIVE = 0.5;
	private static final double BASE = 1.0;
	
	public Damage(Player player, Attribute attribute) {
		this(player, attribute, 0, 0, BASE_EXP_LEVEL_UP, BASE);
	}
	
	public Damage(Player player, Attribute attribute, int experience, int level) {
		this.player = player;
		this.attribute = attribute;
		
		this.experience = experience;
		this.level = level;
		this.expToLevel = expToLevel(level);
		this.attributeValue = Statistic.calculateAttributeLevel(level, BASE, ADDITIVE);
		
		this.baseExpLevelUp = BASE_EXP_LEVEL_UP;
		this.maxLevel = MAX_LEVEL;
		this.rate = RATE;
		this.additivePerLevel = ADDITIVE;
		this.baseAttribute = BASE;
	}

	public Damage(Player player, Attribute attribute, int experience, int level, int expToLevel, double value) {
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