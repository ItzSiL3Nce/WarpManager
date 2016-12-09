package it.ItzSiL3Nce.warpmanager.listeners;

import it.ItzSiL3Nce.warpmanager.commands.Commands;
import it.ItzSiL3Nce.warpmanager.commands.Commands.CommandsEnum;
import it.ItzSiL3Nce.warpmanager.configuration.Config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PlayerCommandPreprocessListener implements Listener {

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e){
		String message = e.getMessage().toLowerCase();
		FileConfiguration fc = Config.get("config.yml");
		if(fc.contains("Disable essentials:(e)warp(s)") && fc.getBoolean("Disable essentials:(e)warp(s)")){
			if(message.startsWith("/essentials:warp") || message.toLowerCase().startsWith("/essentials:ewarp"))
			{
				Player p = e.getPlayer();
				if(!(p.isOp() || 
						(fc.contains("Bypass permission") ? p.hasPermission(fc.getString("Bypass permission")) : false)))
				{
					p.sendMessage("§6This command is disabled. Please use just §e/warp <name>§6!");
					e.setCancelled(true);
					return;
				}
			}
		}
		
		if(message.startsWith("/help warp") || message.startsWith("/help ewarp")
				|| message.startsWith("/? warp") || message.startsWith("/? ewarp")){
			Commands.showCommandsList(e.getPlayer(), CommandsEnum.WARP);
			e.setCancelled(true);
		}
	}
}
