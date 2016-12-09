package it.ItzSiL3Nce.warpmanager.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands {

	public final static CommandExecutor MANAGER = new WarpManagerCommand();
	public final static CommandExecutor WARP = new WarpCommand();
	
	private final static String prefix = "§e----------------------------";
	
	public static void showCommandsList(CommandSender sender, CommandsEnum ci){
		if(sender == null || ci == null) return;
		sender.sendMessage(prefix);
		String a = ci.name().toLowerCase();
		if(ci == CommandsEnum.WM || ci == CommandsEnum.WARPMANAGER){
			sender.sendMessage("§b" + a + " list  §aShows a list of available warps");
			sender.sendMessage("§b" + a + " delwarp <name>  §aDeletes the warp <name>");
			sender.sendMessage("§b" + a + " setwarp <name>  §aSets the warp <name> at your position");
			sender.sendMessage("§b" + a + " go <name>  §aWarps to <name>");
			sender.sendMessage("§b" + a + " convert  §aConverts essentials warps");
			sender.sendMessage("§b" + a + " help  §aShows this help");
		} else { 
			sender.sendMessage("§b/warp <name>  §aWarps to <name>");
			sender.sendMessage("§b/warps  §aShows a list of available warps");
		}
		sender.sendMessage(prefix);
	}
	
	public enum CommandsEnum {
		WM, WARPMANAGER, WARP
	}
}
