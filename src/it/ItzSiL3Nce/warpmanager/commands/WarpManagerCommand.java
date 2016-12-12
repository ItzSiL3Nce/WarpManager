package it.ItzSiL3Nce.warpmanager.commands;

import it.ItzSiL3Nce.warpmanager.Warp;
import it.ItzSiL3Nce.warpmanager.WarpTeleport;
import it.ItzSiL3Nce.warpmanager.Warp.WarpInfo;
import it.ItzSiL3Nce.warpmanager.WarpManager;
import it.ItzSiL3Nce.warpmanager.commands.Commands.CommandsEnum;
import it.ItzSiL3Nce.warpmanager.configuration.Config.Messages;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpManagerCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (cmd.getName().equalsIgnoreCase("wm")
				|| cmd.getName().equalsIgnoreCase("warpmanager")) {
			if (args.length == 0 || args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
				if (sender.isOp() || sender.hasPermission("warpmanager.help"))
					Commands.showCommandsList(sender, label
							.equalsIgnoreCase("wm") ? CommandsEnum.WM
							: CommandsEnum.WARPMANAGER);
				else
					sender.sendMessage(Messages.getMessage("No permission"));
			} else {
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("list")
							|| args[0].equalsIgnoreCase("l")) {
						if (sender.isOp()
								|| sender.hasPermission("warpmanager.list")) {
							WarpManager.listWarps(sender);
						} else
							sender.sendMessage(Messages.getMessage("No permission"));
					} else if (args[0].equalsIgnoreCase("convert")) {
						if (sender.isOp()
								|| sender.hasPermission("warpmanager.convert")) {
							WarpManager.convertWarps(sender);
						}
					} else if (sender.isOp()
							|| sender.hasPermission("warpmanager.list"))
						WarpManager.listWarps(sender);
					else
						sender.sendMessage(Messages.getMessage("No permission"));
				} else {
					if (args[0].equalsIgnoreCase("delwarp") || args[0].equalsIgnoreCase("del")) {
						if (sender.isOp()
								|| sender.hasPermission("warpmanager.delwarp")) {
							if (Warp.delete(args[1]))
								sender.sendMessage(Messages.getMessage("Warp deleted", args[1]));
							else
								sender.sendMessage(Messages.getMessage("Cannot delete", args[1]));
						} else
							sender.sendMessage(Messages.getMessage("No permission"));
					} else if (args[0].equalsIgnoreCase("setwarp") || args[0].equalsIgnoreCase("set")) {
						if (sender.hasPermission("warpmanager.setwarp") || sender.isOp()) {
							if (sender instanceof Player) {
								Player p = (Player) sender;
								if (Warp.set(new WarpInfo(args[1], p.getLocation(), p.getName())))
									p.sendMessage(Messages.getMessage("Warp set", args[1]));
								else
									p.sendMessage(Messages.getMessage("Cannot set", args[1]));
							} else
								sender.sendMessage(Messages.getMessage("Only by player"));
						} else
							sender.sendMessage(Messages.getMessage("No permission"));
					} else if (args[0].equalsIgnoreCase("go")
							|| args[0].equalsIgnoreCase("warp")
							|| Warp.exists(args[0])) {
						if (sender instanceof Player) {
							Player p = (Player) sender;
							if (!WarpManager.permissionRequired()
									|| p.isOp()
									|| p.hasPermission("warpmanager.go.*")
									|| p.hasPermission("warpmanager.go."
											+ args[1].toLowerCase())
									|| p.hasPermission("essentials.warp."
											+ args[1].toLowerCase())) {
								if(args.length >= 2)
									WarpTeleport.to(p, Warp.get(args[1]), args[1]);
								else if(Warp.exists(args[0]))
									WarpTeleport.to(p, Warp.get(args[0]), args[0]);
								else p.sendMessage(Messages.getMessage("Bad Syntax"));
							} else
								p.sendMessage(Messages.getMessage("No permission"));
						} else
							sender.sendMessage(Messages.getMessage("Only by player"));
					} else {
						if (sender.hasPermission("warpmanager.help")
								|| sender.isOp()) {
							Commands.showCommandsList(sender, label
									.equalsIgnoreCase("wm") ? CommandsEnum.WM
									: CommandsEnum.WARPMANAGER);
						} else
							sender.sendMessage(Messages.getMessage("No permission"));
					}
				}
			}
		}
		return true;
	}

}
