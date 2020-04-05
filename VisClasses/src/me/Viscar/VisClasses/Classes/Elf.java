package me.Viscar.VisClasses.Classes;

import java.util.ArrayList;
import java.util.Collection;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Elf extends Race {

	private ArrayList<PotionEffect> potionEffects = new ArrayList<>();
	public Elf() {
		potionEffects.add(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, true, false));
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
		return "CHAINMAIL";
	}
	
	public String toString() {
		return ChatColor.GOLD + "Elf" + ChatColor.WHITE;
	}
	public String abilitiesToString() {
		return super.abilitiesToString() 
				+ ChatColor.GRAY +"["+ ChatColor.AQUA+ "Active Ability:" + ChatColor.GRAY +"]\n" 
				+ ChatColor.GOLD + "Grappling Arrow:" + ChatColor.WHITE + " Left click with a bow to shoot out a "
				+ "stringed arrow to propel yourself to new heights\n"
				+ ChatColor.GRAY +"["+ ChatColor.AQUA+ "Passive Abilities:" + ChatColor.GRAY +"]\n" + ChatColor.GOLD
				+ "Expert Marksmenship:" + ChatColor.WHITE+ " Inflict more damage with a bow\n"
				+ ChatColor.GOLD + "Swift Footed:" + ChatColor.WHITE + " Permanent Speed II effect applied";
	}
	public boolean armorCheck(Player player) {
		PlayerInventory inv = player.getInventory();
			if((inv.getHelmet() == null || inv.getHelmet().getType() == Material.CHAINMAIL_HELMET) 
			   && (inv.getChestplate() == null || inv.getChestplate().getType() == Material.CHAINMAIL_CHESTPLATE)
			   && (inv.getLeggings() == null || inv.getLeggings().getType() == Material.CHAINMAIL_LEGGINGS) 
			   && (inv.getBoots() == null || inv.getBoots().getType() == Material.CHAINMAIL_BOOTS)) 
				return true;

		return false;
	}
}
