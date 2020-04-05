package me.Viscar.VisClasses.Abilities;

import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;

import me.Viscar.VisClasses.Main;
import me.Viscar.VisClasses.Classes.Race;

public class HumanAbilitiesListener implements Listener {

	Race human = Main.HUMAN;
	Plugin plugin;

	public HumanAbilitiesListener(Plugin plugin) {
		this.plugin = plugin;
	}

	/**
	 * Gives experience for full crop yielding if the player is a human and has proper
	 * armor equipped
	 * 
	 * @param e
	 */
	@EventHandler
	public void xpFullCropDrop(BlockBreakEvent e) {
		Player player = e.getPlayer();
		BlockData bdata = e.getBlock().getBlockData();
		if (bdata instanceof Ageable) {
			Ageable age = (Ageable) bdata;
			if (age.getAge() == age.getMaximumAge()) {
				Race playerClass = Main.getPlayerClass(player);
				if (playerClass == human) {
					int xp = plugin.getConfig().getInt("Human.xpPerCrop");
					player.giveExp(xp);
				}
			}
		}
	}
}
