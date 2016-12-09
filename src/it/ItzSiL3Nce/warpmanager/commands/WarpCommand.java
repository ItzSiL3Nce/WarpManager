package it.ItzSiL3Nce.warpmanager.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class WarpCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
	
		if(cmd.getName().equalsIgnoreCase("warp") || cmd.getName().equalsIgnoreCase("ewarp")){
			String com = "warpmanager ";
			if(args.length == 0)
				com += "list";
			else com += "go " + args[0];
			Bukkit.getServer().dispatchCommand(sender, com);
		}
		return true;
	}

}
