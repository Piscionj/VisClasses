package me.Viscar.VisClasses.Classes;

import org.bukkit.ChatColor;

public class Orc extends Race {

	public Orc() {
		
	}
	
	public String toString() {
		return ChatColor.RED + "Orc" + ChatColor.WHITE;
	}
	
	public String abilitiesToString() {
		return super.abilitiesToString() 
				+ ChatColor.GRAY +"["+ ChatColor.AQUA+ "Active Ability:" + ChatColor.GRAY +"]\n" 
				+ ChatColor.WHITE + "None\n" 
				+ ChatColor.GRAY +"["+ ChatColor.AQUA+ "Passive Abilities:" + ChatColor.GRAY +"]\n" + ChatColor.RED
				+ "Berserk:" + ChatColor.WHITE + " Regain full health upon killing another player";
	}
	
}
