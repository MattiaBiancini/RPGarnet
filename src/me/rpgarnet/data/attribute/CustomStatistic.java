package me.rpgarnet.data.attribute;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import me.rpgarnet.PluginViewModel;
import me.rpgarnet.RPGarnet;
import me.rpgarnet.utils.StringUtils;

public abstract class CustomStatistic {
	
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
	
	public boolean levelUp() {

		if(!canLevelUp())
			return false;

		experience = experience - expToLevel;
		level++;
		expToLevel = expToLevel();
		attributeValue = attributeValue + additivePerLevel;
		player.sendTitle(StringUtils.colorFixing("&6&l" + getStatsFromClass(this).getStatsName()), 
				StringUtils.placeholder("&7You reached level&e %LEVEL%&7!", RPGarnet.instance.getViewModel().getPlayerData(player), getStatsFromClass(this)), 
				10, 60, 20);

		return true;
	}

	public boolean canLevelUp() {

		if(level == maxLevel)
			return false;

		if(experience >= expToLevel)
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
		return additivePerLevel + calculateAttributeLevel(level -1, baseAttribute, additivePerLevel) ;
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
		else if(this.experience < 0 && level > 0) {
			level--;
			this.experience = expToLevel(level) + experience + this.experience;
			this.expToLevel = expToLevel();

			PluginViewModel viewModel = RPGarnet.instance.getViewModel();
			player.sendMessage(StringUtils.yamlString(
					viewModel.getMessage().getString("leveldown"), 
					viewModel.getPlayerData(player), 
					getStatsFromClass(this)));
			player.playSound(player, Sound.ENTITY_GHAST_SHOOT, 5, 0);
		}
	}

	public void removeExperience(int experience) {
		this.experience = this.experience - experience;
		if(this.experience < 0 && level > 0) {
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

	public static Stats getStatsFromClass(CustomStatistic statistic) {
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
		if(statistic instanceof Evasion)
			return Stats.EVASION;
		return null;
	}

	public static List<String> getDescription(CustomStatistic statistic) {
		List<String> desc = new ArrayList<>();
		if(statistic instanceof Armor) {
			desc.add(StringUtils.colorFixing("&7You get &e1 &7experience point for each &ehalf heart &7of true damage!"));
			desc.add(StringUtils.colorFixing("")); //&7This statistic increase you base &earmor
		}
		else if(statistic instanceof AttackSpeed) {
			desc.add(StringUtils.colorFixing("&7You get &e1 &7experience point for each &ehalf heart &7damage dealth!"));
			desc.add(StringUtils.colorFixing("&7This statistic increase you base &eattack speed"));
		}
		else if(statistic instanceof Damage) {
			desc.add(StringUtils.colorFixing("&7You get &e1 &7experience point for each &ehalf heart &7damage dealth!"));
			desc.add(StringUtils.colorFixing("&7This statistic increase you base &edamage"));
		}
		else if(statistic instanceof Health) {
			desc.add(StringUtils.colorFixing("&7You get &e1 &7experience point for each &ehalf heart &7of damage!"));
			desc.add(StringUtils.colorFixing("&7This statistic increase your &emax health"));
		}
		else if(statistic instanceof KnockbackResistance) {
			desc.add(StringUtils.colorFixing("&7You get &e1 &7experience point for each &ehalf heart &7of true damage!"));
			desc.add(StringUtils.colorFixing("&7This statistic increase your &eknockback resistence"));
		}
		else if(statistic instanceof Luck) {
			desc.add(StringUtils.colorFixing("&7You get &edifferent &7experience point depending by which ore you mine!"));
			desc.add(StringUtils.colorFixing("&7You can get experience even &eright clicking &7crops, &eshearing &7sheep or &efishing&7."));
			desc.add(StringUtils.colorFixing("&7This statistic increase you base &eluck"));
		}
		else if(statistic instanceof MovementSpeed) {
			desc.add(StringUtils.colorFixing("&7You get &edifferent &7experience point depending on which food you eat!"));
			desc.add(StringUtils.colorFixing("&7This statistic increase you base &emovement speed"));
		}
		else if(statistic instanceof Evasion) {
			desc.add(StringUtils.colorFixing("&7You get &edifferent &7experience point depending how much damage you avoid!"));
			desc.add(StringUtils.colorFixing("&7The &eprobability of evading damage &7is your value divided by the damage you would get"));
			desc.add(StringUtils.colorFixing("&7This statistic increase you base &eevasion"));
		}
		return desc;
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

	public void setPlayer(Player player) {
		this.player = player;
	}

	public double getAttributeValue() {
		return attributeValue;
	}

}
