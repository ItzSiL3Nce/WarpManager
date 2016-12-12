package it.ItzSiL3Nce.warpmanager.events;

import it.ItzSiL3Nce.warpmanager.Warp;

import org.bukkit.event.Cancellable;

public class WarpDeleteEvent extends WarpEvent implements Cancellable {

	private final String deleter;
	private boolean cancelled = false;
	
	public WarpDeleteEvent(Warp warp, String deleter){
		super(warp);
		this.deleter = deleter;
	}
	
	@Override
	public boolean isCancelled() {
		return cancelled;
	}
	
	public String getDeleter() {
		return deleter;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}
}
