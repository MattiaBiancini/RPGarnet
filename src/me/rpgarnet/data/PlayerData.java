package me.rpgarnet.data;

import org.bukkit.entity.Player;

import me.rpgarnet.data.attribute.*;

public class PlayerData {

	public static final int STATS_NUMBER = 8;
	
	private CustomStatistic[] stats;	
	private Player player;

	public PlayerData(Player player, CustomStatistic[] stats) {
		this.player = player;
		this.stats = stats;	
		setPlayerAttributes();
	}

	public PlayerData(Player player, Armor armor, AttackSpeed attackSpeed, Damage damage, Evasion evasion, Health health, 
			KnockbackResistance knockbackResistence, Luck luck, MovementSpeed movementSpeed) {
		
		this.player = player;
		
		this.stats = new Statistic[STATS_NUMBER];
		stats[0] = armor;
		stats[1] = attackSpeed;
		stats[2] = damage;
		stats[3] = evasion;
		stats[4] = health;
		stats[5] = knockbackResistence;
		stats[6] = luck;
		stats[7] = movementSpeed;
		
		setPlayerAttributes();
	}
	
	public PlayerData(Player player) {
		this.player = player;
		
		this.stats = new CustomStatistic[STATS_NUMBER];
		for(int i = 0; i < stats.length; i++)
			stats[i] = Stats.getStatistic(Stats.getStats(i), player);
		
		setPlayerAttributes();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerData other = (PlayerData) obj;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		return true;
	}
	
	public void setPlayerAttributes() {
		for(int i = 0; i < stats.length; i++)
			if(stats[i] instanceof Statistic)
				player.getAttribute(((Statistic)stats[i]).getAttribute()).setBaseValue(
						Statistic.calculateAttributeLevel(
								stats[i].getLevel(), stats[i].getBaseAttribute(), stats[i].getAdditivePerLevel()));
	}
	
	public Player getPlayer() {
		return player;
	}

	public CustomStatistic[] getStats() {
		return stats;
	}

	public void setStats(CustomStatistic[] stats) {
		this.stats = stats;
	}

}
