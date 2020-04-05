package me.Viscar.VisClasses;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.Viscar.VisClasses.Classes.Race;

public class RaceSelector implements Listener {
	private Race human = Main.HUMAN;
	private Race elf = Main.ELF;
	private Race orc = Main.ORC;
	private Race dwarf = Main.DWARF;
	
	static ItemStack chooseElf = new ItemStack(Material.BOW, 1);
	static {
	ItemMeta im = chooseElf.getItemMeta();
	im.setDisplayName(ChatColor.GOLD + "Elf");
	List<String> loreList = new ArrayList<String>();
	loreList.add(ChatColor.AQUA + "Click to select the Elf class!");
	im.setLore(loreList);
	chooseElf.setItemMeta(im);
	}
	
	static ItemStack chooseDwarf = new ItemStack(Material.GOLDEN_PICKAXE, 1);
	static {
	ItemMeta im = chooseDwarf.getItemMeta();
	im.setDisplayName(ChatColor.DARK_GRAY + "Dwarf");
	List<String> loreList = new ArrayList<String>();
	loreList.add(ChatColor.AQUA + "Click to select the Dwarf class!");
	im.setLore(loreList);
	chooseDwarf.setItemMeta(im);
	}
	
	static ItemStack chooseOrc = new ItemStack(Material.IRON_AXE, 1);
	static {
	ItemMeta im = chooseOrc.getItemMeta();
	im.setDisplayName(ChatColor.RED + "Orc");
	List<String> loreList = new ArrayList<String>();
	loreList.add(ChatColor.AQUA + "Click to select the Orc class!");
	im.setLore(loreList);
	chooseOrc.setItemMeta(im);
	}

	static ItemStack chooseHuman = new ItemStack(Material.STONE_HOE, 1);
	static {
	ItemMeta im = chooseHuman.getItemMeta();
	im.setDisplayName(ChatColor.GREEN+ "Human");
	List<String> loreList = new ArrayList<String>();
	loreList.add(ChatColor.AQUA + "Click to select the Human class!");
	im.setLore(loreList);
	chooseHuman.setItemMeta(im);
	}
	
	static ItemStack selectorBorder = new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1);
	static {
	ItemMeta im = selectorBorder.getItemMeta();
	im.setDisplayName(ChatColor.GRAY + "Class Selector");
	List<String> loreList = new ArrayList<String>();
	loreList.add(ChatColor.AQUA + "Click on a class to select it!");
	im.setLore(loreList);
	selectorBorder.setItemMeta(im);
	}
	
	static Inventory inv = Bukkit.getServer().createInventory(null, 27, ChatColor.AQUA + "Class Selector");
	
	static {
		for(int i = 0; i <= 8; i++)
			inv.setItem(i, selectorBorder);
		inv.setItem(13, chooseElf);
		inv.setItem(11, chooseHuman);
		inv.setItem(15, chooseOrc);
		inv.setItem(17, chooseDwarf);
		for(int i = 18; i <= 26; i++)
			inv.setItem(i, selectorBorder);
	}

	public RaceSelector() {
		// TODO Auto-generated constructor stub
	}
	
	public static void openRaceSelector(Player player) {
		player.openInventory(inv);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked(); 
		ItemStack clicked = event.getCurrentItem(); 
		if(clicked == null) //Handle if no item is clicked
			return;
		if(clicked.equals(chooseElf)) {
			if(Main.updatePlayerClass(player, elf)) {
				player.closeInventory();
			}
			event.setCancelled(true);
		}else if(clicked.equals(chooseHuman)) {
			if(Main.updatePlayerClass(player, human)) {
				player.closeInventory();
			}
			event.setCancelled(true);
		}else if(clicked.equals(chooseOrc)) {
			if(Main.updatePlayerClass(player, orc)) {
				player.closeInventory();
			}
			event.setCancelled(true);
		}else if(clicked.equals(chooseDwarf)) {
			if(Main.updatePlayerClass(player, dwarf)) {
				player.closeInventory();
			}
			event.setCancelled(true);
		}else if(clicked.equals(selectorBorder))
			event.setCancelled(true);
		
		}
}
