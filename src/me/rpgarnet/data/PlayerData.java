package me.rpgarnet.data;

import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;

public class PlayerData {

	private double armor;
	private double damage;
	private double knockback;
	private double attackSpeed;
	private double knockbackResistance;
	private double health;
	private double movementSpeed;
	private double luck;

	private Player player;

	public PlayerData(double armor, double damage, double knockback, double attackSpeed, double knockbackResistance,
			double health, double movementSpeed, double luck, Player player) {
		this.player = player;
		setArmor(armor);
		setDamage(damage);
		setKnockback(knockback);
		setAttackSpeed(attackSpeed);
		setKnockbackResistance(knockbackResistance);
		setHealth(health);
		setMovementSpeed(movementSpeed);
		setLuck(luck);
	}

	public PlayerData(Player player) {
		this(player.getAttribute(Attribute.GENERIC_ARMOR).getBaseValue(),
				player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).getBaseValue(),
				player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getBaseValue(),
				player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).getBaseValue(),
				player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).getBaseValue(),
				player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue(),
				player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).getBaseValue(),
				player.getAttribute(Attribute.GENERIC_LUCK).getBaseValue(),
				player);
	}

	public void setArmor(double armor) {
		if(armor <= 0.0)
			this.armor = 0.0;
		else if(armor >= 30.0)
			this.armor = 30.0;
		else
			this.armor = armor;

		player.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(this.armor);
	}

	public void setDamage(double damage) {
		if(damage <= 0.0)
			this.damage = 0.0;
		else if(damage >= 2048.0)
			this.damage = 2048.0;
		else
			this.damage = damage;

		player.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);
	}

	public void setKnockback(double knockback) {
		if(knockback <= 0.0)
			this.knockback = 0.0;
		else if(knockback >= 5.0)
			this.knockback = 5.0;
		else
			this.knockback = knockback;

		player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(knockback);
	}

	public void setAttackSpeed(double attackSpeed) {
		if(attackSpeed <= 0.0)
			this.attackSpeed = 0.0;
		else if(attackSpeed >= 1024.0)
			this.attackSpeed = 1024.0;
		else
			this.attackSpeed = attackSpeed;
		
		player.getAttribute(Attribute.GENERIC_ATTACK_SPEED).setBaseValue(attackSpeed);
	}

	public void setKnockbackResistance(double knockbackResistance) {
		if(knockbackResistance <= 0.0)
			this.knockbackResistance = 0.0;
		else if(knockbackResistance >= 1.0)
			this.knockbackResistance = 1.0;
		else
			this.knockbackResistance = knockbackResistance;

		player.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE).setBaseValue(knockbackResistance);
	}

	public void setHealth(double health) {
		if(health <= 0.5)
			this.health = 0.5;
		else if(health >= 1024.0)
			this.health = 1024.0;
		else
			this.health = health;

		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
	}

	public void setMovementSpeed(double movementSpeed) {
		if(movementSpeed <= 0.0)
			this.movementSpeed = 0.0;
		else if(movementSpeed >= 1024.0)
			this.movementSpeed = 1024.0;
		else
			this.movementSpeed = movementSpeed;

		player.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(movementSpeed);
	}

	public void setLuck(double luck) {
		if(movementSpeed <= -1024.0)
			this.movementSpeed = -1024.0;
		else if(movementSpeed >= 1024.0)
			this.movementSpeed = 1024.0;
		else
			this.luck = luck;
		
		player.getAttribute(Attribute.GENERIC_LUCK).setBaseValue(luck);
	}



	public double getArmor() {
		return armor;
	}
	public double getDamage() {
		return damage;
	}
	public double getKnockback() {
		return knockback;
	}
	public double getAttackSpeed() {
		return attackSpeed;
	}
	public double getKnockbackResistance() {
		return knockbackResistance;
	}
	public double getMovementSpeed() {
		return movementSpeed;
	}
	public double getHealth() {
		return health;
	}
	public double getLuck() {
		return luck;
	}
	public Player getPlayer() {
		return player;
	}
}
