package me.Viscar.VisClasses.Classes;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Human extends Race {

	private ArrayList<PotionEffect> potionEffects = new ArrayList<>();
	public Human() {
		potionEffects.add(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0, true, false));
		potionEffects.add(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0, true, false));
	}
	
	public Collection<PotionEffect> getEffects(){
		return potionEffects;
	}
	
	public void applyEffects(Player player, Race oldRace) {
		for (PotionEffect effect : oldRace.getEffects())
	        player.removePotionEffect(effect.getType());
		player.addPotionEffects(this.getEffects());
	}
	
	public String armorToString() {
		return "LEATHER";
	}
	
	public String toString() {
		return ChatColor.GREEN + "Human" + ChatColor.WHITE;
	}
	public String abilitiesToString() {
		return super.abilitiesToString() 
				+ ChatColor.GRAY +"["+ ChatColor.AQUA+ "Active Ability:" + ChatColor.GRAY +"]\n" 
				+ ChatColor.WHITE + "None\n" 
				+ ChatColor.GRAY +"["+ ChatColor.AQUA+ "Passive Abilities:" + ChatColor.GRAY +"]\n" + ChatColor.GREEN
				+ "Experienced Farming:" + ChatColor.WHITE + " Gain experience when farming full grown crops\n" + ChatColor.GREEN
				+ "Resilient Nature:" + ChatColor.WHITE + " Indefinite Regen and Resistance is applied";
	}
	
	public boolean armorCheck(Player player) {
		PlayerInventory inv = player.getInventory();
			if((inv.getHelmet() == null || inv.getHelmet().getType() == Material.LEATHER_HELMET) 
			   && (inv.getChestplate() == null || inv.getChestplate().getType() == Material.LEATHER_CHESTPLATE)
			   && (inv.getLeggings() == null || inv.getLeggings().getType() == Material.LEATHER_LEGGINGS) 
			   && (inv.getBoots() == null || inv.getBoots().getType() == Material.LEATHER_BOOTS)) 
				return true;

		return false;
	}
}
