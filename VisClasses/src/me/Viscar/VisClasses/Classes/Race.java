package me.Viscar.VisClasses.Classes;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class Race {

	
	public Race() {

	}

	/*
	 * @return toString representation of the class
	 */
	public String toString() {
		return "None";
	}

	/**
	 * @return List of effects to apply upon choosing class
	 */
	public Collection<PotionEffect> getEffects(){
		return new ArrayList<PotionEffect>();
	}
	
	/**
	 * @return string representation of armor for this class
	 */
	public String armorToString() {
		return "";
	}
	/**
	 * @return String representation of the class's abilities
	 */
	public String abilitiesToString() {
		return ChatColor.GRAY + "-----------={" + ChatColor.AQUA + "Abilities" + ChatColor.GRAY + "}=-----------\n" + ChatColor.WHITE;
	}

	/**
	 * Applies class effects to player
	 * 
	 * @param player
	 */
	public void applyEffects(Player player, Race oldClass) {
		for (PotionEffect effect : oldClass.getEffects())
	        player.removePotionEffect(effect.getType());
	}

	/**
	 * @param player
	 * @return true if correct class armor equipped, false otherwise
	 */
	public boolean armorCheck(Player player) {
		return true;
	}
}
