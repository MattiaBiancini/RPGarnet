package me.rpgarnet.data.attribute;

import org.bukkit.Sound;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

import me.rpgarnet.PluginViewModel;
import me.rpgarnet.RPGarnet;
import me.rpgarnet.utils.StringUtils;

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
	
	public void addExperience(int experience) {
		this.experience = this.experience + experience;
		if(levelUp()) {
			PluginViewModel viewModel = RPGarnet.instance.getViewModel();
			player.sendMessage(StringUtils.yamlString(
					viewModel.getMessage().getString("levelup"), 
					viewModel.getPlayerData(player), 
					getStatsFromClass(this)));
			player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 5, 0);
		}
	}
	
	public void removeExperience(int experience) {
		this.experience = this.experience - experience;
		if(this.experience < 0) {
			level--;
			this.experience = expToLevel(level) + experience;
			
			PluginViewModel viewModel = RPGarnet.instance.getViewModel();
			player.sendMessage(StringUtils.yamlString(
					viewModel.getMessage().getString("leveldown"), 
					viewModel.getPlayerData(player), 
					getStatsFromClass(this)));
			player.playSound(player, Sound.ENTITY_GHAST_SHOOT, 5, 0);
		}
		
	}
	
	public void deathReset() {
		
		if(experience > 0) {
			experience = 0;
			return;
		}
		
		if(level == 0)
			return;
		
		level--;
		
	}
	
	public double getPercentage() {
		return (experience * 1.0)/expToLevel();
	}
	
	private static Stats getStatsFromClass(Statistic statistic) {
		if(statistic instanceof Armor)
			return Stats.ARMOR;
		if(statistic instanceof AttackSpeed)
			return Stats.ATTACK_SPEED;
		if(statistic instanceof Damage)
			return Stats.DAMAGE;
		if(statistic instanceof Health)
			return Stats.HEALTH;
		if(statistic instanceof KnockbackResistance)
			return Stats.KNOCKBACK_RESISTANCE;
		if(statistic instanceof Luck)
			return Stats.LUCK;
		if(statistic instanceof MovementSpeed)
			return Stats.MOVEMENT_SPEED;
		return null;
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
