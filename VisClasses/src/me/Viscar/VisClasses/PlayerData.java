package me.Viscar.VisClasses;

import java.io.File;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.Viscar.VisClasses.Classes.Race;

/**
 * @author piscionj
 *
 * Keeps track of player race selections in the given yaml file upon joining and leaving the game
 */
public class PlayerData implements Listener {
	Race human = Main.HUMAN;
	Race elf = Main.ELF;
	Race orc = Main.ORC;
	Race defaultClass = Main.DEFAULT_CLASS;
	Race dwarf = Main.DWARF;

	File playerData = null;
	FileConfiguration customConfig = null;

	public PlayerData(File playerData, FileConfiguration customConfig) {
		this.playerData = playerData;
		this.customConfig = customConfig;
	}

	/**
	 * Loads player's class data on join
	 * 
	 * @param e
	 */
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		UUID id = player.getUniqueId();
		String idString = id.toString();
		if (!customConfig.contains(idString)) {
			customConfig.set(idString, "default");
		} else {
			String c = customConfig.getString(idString);
			if (c.equalsIgnoreCase("Human"))
				Main.updatePlayerClass(player, human);
			else if (c.equalsIgnoreCase("Elf"))
				Main.updatePlayerClass(player, elf);
			else if (c.equalsIgnoreCase("Orc"))
				Main.updatePlayerClass(player, orc);
			else if (c.equalsIgnoreCase("Dwarf"))
				Main.updatePlayerClass(player, dwarf);
			else
				Main.updatePlayerClass(player, defaultClass);
		}
	}

	/**
	 * Saves player's class data on exit
	 * 
	 * @param e
	 */
	@EventHandler
	public void onExit(PlayerQuitEvent e) {
		Main.saveRace(e.getPlayer());
	}
	
	

}
