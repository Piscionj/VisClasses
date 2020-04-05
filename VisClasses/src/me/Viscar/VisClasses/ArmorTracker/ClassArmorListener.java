package me.Viscar.VisClasses.ArmorTracker;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.Viscar.VisClasses.Main;
import me.Viscar.VisClasses.Classes.Race;


public class ClassArmorListener implements Listener {
	
	public ClassArmorListener() {
		
	}

	@EventHandler
	public void armorChange(ArmorEquipEvent e) {
		if(e.getNewArmorPiece() == null) //All classes can wear no armor
			return;
		String type = e.getNewArmorPiece().getType().name();
		Player player = e.getPlayer();
		Race playerClass = Main.getPlayerClass(player);
		if(!type.startsWith(playerClass.armorToString())) {
			e.setCancelled(true);
			player.sendMessage(playerClass.toString() + ChatColor.RED + " can't equip that type of armor!");
		}
	}
}
