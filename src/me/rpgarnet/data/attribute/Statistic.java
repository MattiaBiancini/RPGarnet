package me.rpgarnet.data.attribute;

import org.bukkit.attribute.Attribute;

public abstract class Statistic extends CustomStatistic {

	protected Attribute attribute;
	
	@Override
	public boolean levelUp() {
		
		if(super.levelUp()) {
			player.getAttribute(attribute).setBaseValue(attributeValue);
			return true;
		}
		
		return false;
		
	}

	public Attribute getAttribute() {
		return attribute;
	}

}
