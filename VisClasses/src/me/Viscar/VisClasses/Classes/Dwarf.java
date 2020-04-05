package me.Viscar.VisClasses.Classes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class Dwarf extends Race {

	public Dwarf() {
		
	}
	
	public String toString() {
		return ChatColor.DARK_GRAY + "Dwarf" + ChatColor.WHITE;
	}
	
	public String abilitiesToString() {
		return super.abilitiesToString() 
				+ ChatColor.GRAY +"["+ ChatColor.AQUA+ "Active Ability:" + ChatColor.GRAY +"]\n" 
				+ ChatColor.DARK_GRAY + "Dwarven Might:" + ChatColor.WHITE + " Right click the ground with a pickaxe to create shockwaves"
						+ " sending nearby entities shooting up\n"
				+ ChatColor.GRAY +"["+ ChatColor.AQUA+ "Passive Abilities:" + ChatColor.GRAY +"]\n" + ChatColor.WHITE
				+ "None";
	}
	
	public String armorToString() {
		return "IRON";
	}
	public boolean armorCheck(Player player) {
		PlayerInventory inv = player.getInventory();
			if((inv.getHelmet() == null || inv.getHelmet().getType() == Material.IRON_HELMET) 
			   && (inv.getChestplate() == null || inv.getChestplate().getType() == Material.IRON_CHESTPLATE)
			   && (inv.getLeggings() == null || inv.getLeggings().getType() == Material.IRON_LEGGINGS) 
			   && (inv.getBoots() == null || inv.getBoots().getType() == Material.IRON_BOOTS)) 
				return true;

		return false;
	}
}
