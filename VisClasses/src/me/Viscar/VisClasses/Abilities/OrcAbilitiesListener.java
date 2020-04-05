package me.Viscar.VisClasses.Abilities;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

import me.Viscar.VisClasses.Main;
import me.Viscar.VisClasses.Classes.Race;

public class OrcAbilitiesListener implements Listener {

	private Race orc = Main.ORC;
//	private Plugin plugin;
	public OrcAbilitiesListener(Plugin plugin) {
//		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void berserk(PlayerDeathEvent e) {
		if(e.getEntity().getKiller() instanceof Player) {
			Player player = e.getEntity().getKiller();
			if(Main.getPlayerClass(player) == orc)
				player.setHealth(player.getMaxHealth());
		}
	}
}
