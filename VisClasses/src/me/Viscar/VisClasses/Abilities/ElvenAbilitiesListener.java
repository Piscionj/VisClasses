package me.Viscar.VisClasses.Abilities;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import me.Viscar.VisClasses.Main;
import me.Viscar.VisClasses.Classes.Race;

public class ElvenAbilitiesListener implements Listener {

	private HashMap<Player, Integer> cooldownTime = new HashMap<>(); // Holds the time left for a player on cool-down
	private HashMap<Player, BukkitRunnable> cooldownTask = new HashMap<>();
	HashMap<Entity, Player> grappleToPlayer = new HashMap<>(); // Keeps track of the player who fired a specific grappling arrow

	Race elf = Main.ELF;
	Plugin plugin;

	public ElvenAbilitiesListener(Plugin plugin) {
		this.plugin = plugin;
	}
	
	/**
	 * Fires out a grappling arrow out when the player has a bow in hand and has the
	 * Elf class equipped with the proper armor
	 * 
	 * @param e
	 */
	@SuppressWarnings("deprecation")
	@EventHandler
	public void grapplingArrow(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Race playerClass = Main.getPlayerClass(player);
		if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
			if (player.getItemInHand().getType() == Material.BOW && playerClass == elf) {
				if (cooldownTime.containsKey(player)) {
					player.sendMessage(ChatColor.GRAY + "Grapple cooldown! Time remaining: " + ChatColor.RED
							+ ChatColor.BOLD + cooldownTime.get(player));
					return;
				} else {
					int cooldown = plugin.getConfig().getInt("Elf.grapplerCooldown");
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
					Vector playerDirection = player.getLocation().getDirection();
					Arrow arrow = player.launchProjectile(Arrow.class, playerDirection);
					arrow.setVelocity(arrow.getVelocity().multiply(1.8));
					arrow.setPickupStatus(Arrow.PickupStatus.DISALLOWED);
					grappleToPlayer.put(arrow, player);
				}
			}
		}
	}
	
	 /**
	  * Increases the damage done by a player with a bow if the player is an elf
	  * 
	 * @param event
	 */
	@EventHandler
	public void expertMarksmenship(EntityDamageByEntityEvent event) {
	        if (event.getDamager() instanceof Arrow){
	            Arrow arrow = (Arrow) event.getDamager();
	            if (arrow.getShooter() instanceof Player && Main.getPlayerClass((Player) arrow.getShooter()) == elf) {
	            	double damageMult = plugin.getConfig().getDouble("Elf.damageWithBowMult");
	                event.setDamage(event.getDamage() * damageMult);
	            }
	        }
	    }
	/**
	 * Applies vector to player when grappling arrow lands
	 * 
	 * @param event
	 */
	@EventHandler
	public void arrowHit(ProjectileHitEvent event) {
		if (event.getEntityType() == EntityType.ARROW && grappleToPlayer.keySet().contains(event.getEntity())) {
			Player player = grappleToPlayer.get(event.getEntity());
			Entity hitEntity = event.getHitEntity();
			Vector vector = null;
			if (hitEntity != null)
				vector = getVectorForPoints(player.getLocation(), hitEntity.getLocation());
			else
				vector = getVectorForPoints(player.getLocation(), event.getHitBlock().getLocation());
			player.setVelocity(vector);
			//player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 40, 0));
			grappleToPlayer.remove(event.getEntity());
		}
	}

	
	
	/**
	 * Returns a vector between the two points given
	 * 
	 * @param l1
	 * @param l2
	 * @return Vector
	 */
	public Vector getVectorForPoints(Location l1, Location l2) {
		double g = -0.08;
		double d = l2.distance(l1);
		double t = d;
		double vX = (1.0 + 0.07 * t) * (l2.getX() - l1.getX()) / t;
		double vY = (1.0 + 0.03 * t) * (l2.getY() - l1.getY()) / t - 0.5 * g * t;
		double vZ = (1.0 + 0.07 * t) * (l2.getZ() - l1.getZ()) / t;
		return new Vector(vX, vY, vZ);
	}

}
