package me.rpgarnet.data.attribute;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public abstract class Statistic {

	protected int baseExpLevelUp;
	protected int maxLevel;
	protected double rate;
	protected double additivePerLevel;
	protected double baseAttribute;
	protected double attributeValue;
	
	protected int experience;
	protected int level;
	protected int expToLevel;
	
	protected Player player;
	protected Attribute attribute;
	
	
	public boolean levelUp() {
		
		if(!canLevelUp())
			return false;
		
		experience = experience - expToLevel;
		level++;
		expToLevel = expToLevel();
		attributeValue = attributeValue + additivePerLevel;
		player.getAttribute(attribute).setBaseValue(attributeValue);
		
		return true;
	}

	public boolean canLevelUp() {

		if(level == maxLevel)
			return false;

		if(experience >= expToLevel())
			return true;

		return false;

	}

	public int expToLevel() {

		if(level == 0)
			return baseExpLevelUp;

		return (int) (expToLevel * rate);

	}

	public int expToLevel(int level) {

		if(level == 0)
			return baseExpLevelUp;
		else
			return (int) (expToLevel(level - 1) * rate);

	}
	
	public static double calculateAttributeLevel(int level, double baseAttribute, double additivePerLevel) {
		if(level == 0)
			return baseAttribute;
		return additivePerLevel + calculateAttributeLevel(level -1, baseAttribute, additivePerLevel);
	}

	public int getBaseExpLevelUp() {
		return baseExpLevelUp;
	}

	public int getMaxLevel() {
		return maxLevel;
	}

	public int getExpToLevel() {
		return expToLevel;
	}

	public double getRate() {
		return rate;
	}

	public int getExperience() {
		return experience;
	}

	public int getLevel() {
		return level;
	}

	public void setExpToLevel(int expToLevel) {
		this.expToLevel = expToLevel;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public double getAdditivePerLevel() {
		return additivePerLevel;
	}

	public double getBaseAttribute() {
		return baseAttribute;
	}

	public Player getPlayer() {
		return player;
	}

	public Attribute getAttribute() {
		return attribute;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public double getAttributeValue() {
		return attributeValue;
	}
	
}
