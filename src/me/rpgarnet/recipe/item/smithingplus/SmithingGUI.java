package me.rpgarnet.recipe.item.smithingplus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.rpgarnet.recipe.item.inventory.CornerBlack;
import me.rpgarnet.recipe.item.inventory.CornerGray;
import me.rpgarnet.recipe.item.inventory.PanesGUI;
import me.rpgarnet.recipe.item.inventory.Utility;

public class SmithingGUI {
	
	private Inventory gui;
	
	public SmithingGUI() {
		
		gui = Bukkit.createInventory(null, 45, "§4Enchanted Smithing Table");
		
		for(int i = 0; i < gui.getSize(); i++) {
			
			if(i <= 8 || i >= 36 || i%9 == 0 || (i + 1)%9 == 0)
				gui.setItem(i, CornerBlack.corner);
			else
				gui.setItem(i, CornerGray.corner);
			
		}
		
		ItemStack info = new ItemStack(Material.WRITABLE_BOOK);
		ItemMeta infoM = info.getItemMeta();
		infoM.setDisplayName("§6Information");
		
		List<String> lore = new ArrayList<>();
		lore.add("§7This Table allows you to §etransfer");
		lore.add("§eyour enchant §7from §ean item §7to");
		lore.add("§eanother item §7of the §esame category");
		lore.add("§7and §enext level§7.");
		lore.add("§7");
		lore.add("§7================================");
		lore.add("§7For more info, visit the §eGitHub Page");
		lore.add("§7================================");
		
		infoM.setLore(lore);
		info.setItemMeta(infoM);
		
		gui.setItem(4, info);
		gui.setItem(20, PanesGUI.ingredient);
		gui.setItem(22, PanesGUI.ingredient);
		gui.setItem(24, PanesGUI.result);
		gui.setItem(44, Utility.destroy);
		
	}
	
	public SmithingGUI(Inventory inv) {
		
		this.gui = inv;
		
	}
	
	public boolean isSetIngredient1() {
		return !gui.getItem(20).getType().equals(PanesGUI.ingredient.getType());
	}
	
	public boolean isSetIngredient2() {
		return !gui.getItem(22).getType().equals(PanesGUI.ingredient.getType());
	}
	
	public boolean isSetResult() {
		return !gui.getItem(24).getType().equals(PanesGUI.ingredient.getType());
	}
	
	public Inventory getInventory() {
		
		return gui;
		
	}
	
}
