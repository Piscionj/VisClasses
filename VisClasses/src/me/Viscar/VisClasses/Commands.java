package me.Viscar.VisClasses;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.Viscar.VisClasses.Classes.Race;

public class Commands implements CommandExecutor {

	private Race human = Main.HUMAN;
	private Race defaultClass = Main.DEFAULT_CLASS;
	private Race elf = Main.ELF;
	private Race orc = Main.ORC;
	private Race dwarf = Main.DWARF;
	
	
	/* 
	 * Handles all plug-in related commands
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("vc")) {
				if (args.length == 0 || args.length > 1)
					classHelp(player);
				else if (args[0].equalsIgnoreCase("list")) 
					player.sendMessage(ChatColor.GRAY + "=====[" + ChatColor.AQUA + "All Classes" + ChatColor.GRAY
							+ "]=====\n" + human.toString() + "\n" + elf.toString() + "\n" + orc.toString());
				 else if (args[0].equalsIgnoreCase("choose") || args[0].equalsIgnoreCase("select")) 
					RaceSelector.openRaceSelector(player);
				 else if (args[0].equalsIgnoreCase("human")) 
					Main.updatePlayerClass(player, human);
				 else if (args[0].equalsIgnoreCase("dwarf")) 
						Main.updatePlayerClass(player, dwarf);
				 else if (args[0].equalsIgnoreCase("default")) 
					Main.updatePlayerClass(player, defaultClass);
				 else if (args[0].equalsIgnoreCase("elf")) 
					Main.updatePlayerClass(player, elf);
				 else if (args[0].equalsIgnoreCase("help"))
					 classHelp(player);
				 else if (args[0].equalsIgnoreCase("show")) {
					Race c = Main.getPlayerClass(player);
					player.sendMessage(ChatColor.GRAY + "-----------={" + ChatColor.AQUA + "Current Class" + ChatColor.GRAY
							+ "}=-----------\n" + c.toString());
					player.sendMessage(c.abilitiesToString());
				}
			}
		}
		return true;
	}

	
	/**
	 * Sends basic help text to given player
	 * 
	 * @param player
	 */
	public void classHelp(Player player) {
		player.sendMessage(ChatColor.GRAY + "-----------={" + ChatColor.AQUA + "VisClasses" + ChatColor.GRAY + "}=-----------\n"
				+ ChatColor.AQUA + "/vc choose, select" + ChatColor.GRAY + " : Select a class to use\n"
				+ ChatColor.AQUA + "/vc list" + ChatColor.GRAY + " : List all available classes to choose from\n"
				+ ChatColor.AQUA + "/vc show" + ChatColor.GRAY + " : Show info for the currently chosen class\n");
	}
	
}
