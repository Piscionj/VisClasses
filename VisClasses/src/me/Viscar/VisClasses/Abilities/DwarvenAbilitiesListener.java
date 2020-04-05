package me.Viscar.VisClasses.Abilities;

import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.Viscar.VisClasses.Main;
import me.Viscar.VisClasses.Classes.Race;

public class DwarvenAbilitiesListener implements Listener {

	private HashMap<Player, Integer> cooldownTime = new HashMap<>(); // Holds the time left for a player on cool-down
	private HashMap<Player, BukkitRunnable> cooldownTask = new HashMap<>();
	private Race dwarf = Main.DWARF;
	private Plugin plugin;
	public DwarvenAbilitiesListener(Plugin plugin) {
		this.plugin = plugin;
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void groundPound(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Race playerClass = Main.getPlayerClass(player);

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && playerClass == dwarf && e.getHand() == EquipmentSlot.HAND
				&& player.getItemInHand().getType().name().endsWith("PICKAXE")) {
			if (cooldownTime.containsKey(player)) {
				player.sendMessage(ChatColor.GRAY + "Dwarven Might on cooldown! Time remaining: " + ChatColor.RED
						+ ChatColor.BOLD + cooldownTime.get(player));
				return;
			} else {
				int cooldown = plugin.getConfig().getInt("Dwarf.dwarvenMightCooldown");
				cooldownTime.put(player, cooldown);
				cooldownTask.put(player, new BukkitRunnable() {
					public void run() {
						cooldownTime.put(player, cooldownTime.get(player) - 1);
						if (cooldownTime.get(player) == 0) {
							cooldownTime.remove(player);
							cooldownTask.remove(player);
							cancel();
						}
					}
				});
				cooldownTask.get(player).runTaskTimer(plugin, 20, 20);
			Vector vector = new Vector(0,1,0);
			player.sendMessage("poundin");
			Location locOfHit = e.getClickedBlock().getLocation();
			List<Entity> nearbyEntities = (List<Entity>) locOfHit.getWorld().getNearbyEntities(locOfHit, 8, 3, 8);
			nearbyEntities.remove(player);
			for(Entity entity : nearbyEntities) {
				entity.setVelocity(vector);
			}	
			}
		}
	}
}
