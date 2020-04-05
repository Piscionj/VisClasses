package me.Viscar.VisClasses;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.Viscar.VisClasses.Abilities.DwarvenAbilitiesListener;
import me.Viscar.VisClasses.Abilities.ElvenAbilitiesListener;
import me.Viscar.VisClasses.Abilities.HumanAbilitiesListener;
import me.Viscar.VisClasses.Abilities.OrcAbilitiesListener;
import me.Viscar.VisClasses.ArmorTracker.ArmorListener;
import me.Viscar.VisClasses.ArmorTracker.ClassArmorListener;
import me.Viscar.VisClasses.ArmorTracker.DispenserArmorListener;
import me.Viscar.VisClasses.Classes.Race;
import me.Viscar.VisClasses.Classes.RaceListener;
import me.Viscar.VisClasses.Classes.Dwarf;
import me.Viscar.VisClasses.Classes.Elf;
import me.Viscar.VisClasses.Classes.Human;
import me.Viscar.VisClasses.Classes.Orc;

public class Main extends JavaPlugin implements Listener {
	public static final Race DWARF = new Dwarf();
	public static final Race HUMAN = new Human();
	public static final Race DEFAULT_CLASS = new Race();
	public static final Race ELF = new Elf();
	public static final Race ORC = new Orc();
	static HashMap<UUID, Race> playerClasses = new HashMap<>(); // Keeps track of each players' current class
	
	Logger pluginLogger = Bukkit.getLogger();

	static File playerData = null;
	static FileConfiguration customConfig = null;

	public void onEnable() {
		pluginLogger.info("[VisClasses] Booting up");
		saveDefaultConfig();
		loadConfiguration();
		registerClassEvents();
		this.getCommand("vc").setExecutor((CommandExecutor) new Commands());
	}

	public void onDisable() {
		pluginLogger.info("[VisClasses] Recording online player's class selections");
		for(Player p : Bukkit.getOnlinePlayers())
			saveRace(p);
		pluginLogger.info("[VisClasses] Saving player data file");
		saveCustomYml(customConfig, playerData);
	}

	/**
	 * Loads file for player class data
	 */
	public void loadConfiguration() {
		playerData = new File(this.getDataFolder() + "/PlayerData.yml");
		customConfig = YamlConfiguration.loadConfiguration(playerData);
	}

	/**
	 * Saves ymlConfig to ymlFile
	 * 
	 * @param ymlConfig
	 * @param ymlFile
	 */
	public static void saveCustomYml(FileConfiguration ymlConfig, File ymlFile) {
		try {
			ymlConfig.save(ymlFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Registers all class ability listeners
	 */
	public void registerClassEvents() {
		PluginManager plugin = this.getServer().getPluginManager();
		plugin.registerEvents(new PlayerData(playerData, customConfig), this);
		plugin.registerEvents(new HumanAbilitiesListener(this), this);
		plugin.registerEvents(new ElvenAbilitiesListener(this), this);
		plugin.registerEvents(new OrcAbilitiesListener(this), this);
		plugin.registerEvents(new DwarvenAbilitiesListener(this), this);
		plugin.registerEvents(new RaceSelector(), this);
		plugin.registerEvents(new RaceListener(this), this);
		plugin.registerEvents(new ArmorListener(getConfig().getStringList("blocked")), this);
		try{
			//Better way to check for this? Only in 1.13.1+?
			Class.forName("org.bukkit.event.block.BlockDispenseArmorEvent");
			plugin.registerEvents(new DispenserArmorListener(), this);
		}catch(Exception ignored){}
		//example();
		plugin.registerEvents(new ClassArmorListener(), this);
	}

	/**
	 * Updates a given players class
	 * 
	 * @param player
	 * @param c
	 * @return true if updated
	 */
	public static boolean updatePlayerClass(Player player, Race c) {
		UUID id = player.getUniqueId();
		//If the player does not already have a stored class
		if (!playerClasses.containsKey(id)) {
			if(c.armorCheck(player)) {
			playerClasses.put(id, c);
			updateDisplayName(player, c);
			c.applyEffects(player, DEFAULT_CLASS);
			return true;
			}else {
				armorError(player, c);
				return false;
			}
		}
		//If the player does already have a stored class
		else {
			Race oldClass = getPlayerClass(player);
			if (c == playerClasses.get(id)) {
				player.sendMessage(c.toString() + " class already chosen");
				return false;
			}
			if(c.armorCheck(player)) {
				playerClasses.replace(id, c);
				updateDisplayName(player, c);
				c.applyEffects(player, oldClass);
				return true;
			}
			else {
				armorError(player,c);
				return false;
			}
		}
	}
	
	/**
	 * Sends player an error message if they are not wearing the correct armor for
	 * the given class
	 * 
	 * @param player
	 * @param c
	 */
	public static void armorError(Player player, Race c) {
		player.sendMessage(ChatColor.RED + "Can't change to " + c.toString() + ChatColor.RED
				+ " with the currently equipped armor!");
	}

	/**
	 * Updates player's display name to reflect class
	 * 
	 * @param player
	 * @param c
	 */
	public static void updateDisplayName(Player player, Race c) {
		player.setDisplayName(
				player.getName() + ChatColor.GRAY + " [" + c.toString() + ChatColor.GRAY + "]" + ChatColor.WHITE);
		player.setPlayerListName(
				player.getName() + ChatColor.GRAY + " [" + c.toString() + ChatColor.GRAY + "]" + ChatColor.WHITE);
		player.sendMessage("Updated Class: " + ChatColor.GRAY + "[" + c.toString() + ChatColor.GRAY + "]");
	}

	/**
	 * @param player
	 * @return Given player's class
	 */
	public static Race getPlayerClass(Player player) {
		UUID id = player.getUniqueId();
		if (!playerClasses.containsKey(id)) {
			playerClasses.put(id, DEFAULT_CLASS);
		}
		return playerClasses.get(player.getUniqueId());
	}

	/**
	 * Removes given player from playerClasses map
	 * 
	 * @param player
	 */
	public static void removePlayer(Player player) {
		playerClasses.remove(player.getUniqueId());

	}
	
	/**
	 * Writes the given player's race to the yaml file
	 * 
	 * @param player
	 */
	public static void saveRace(Player player) {	
		UUID id = player.getUniqueId();
		String idString = id.toString();
		Race c = Main.getPlayerClass(player);
		if (c == HUMAN)
			customConfig.set(idString, "Human");
		else if (c == ELF)
			customConfig.set(idString, "Elf");
		else if (c == ORC)
			customConfig.set(idString, "Orc");
		else if (c == DWARF)
			customConfig.set(idString, "Dwarf");
		else
			customConfig.set(idString, "Default");
		Main.removePlayer(player);
	}

}
