package me.Viscar.VisClasses.Classes;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.Viscar.VisClasses.Main;

/**
 * @author piscionj
 *
 * Listeners that apply for all races in general
 */
public class RaceListener implements Listener {

	private Plugin plugin;
	public RaceListener(Plugin plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * @param e
	 * 
	 * Makes sure players keep their race potion effects upon drinking milk
	 */
	@EventHandler
	public void milkDrink(PlayerItemConsumeEvent e) {
		if(e.getItem().getType().equals(Material.MILK_BUCKET)) {
			Player p = e.getPlayer();
			Race race = Main.getPlayerClass(p);
			new BukkitRunnable() {
                public void run () {
                	race.applyEffects(p, Main.DEFAULT_CLASS);
                }
            }.runTaskLater(plugin, 1);
			
			
		}
	}
}
