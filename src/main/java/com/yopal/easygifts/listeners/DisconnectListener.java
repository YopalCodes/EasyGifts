package com.yopal.easygifts.listeners;

import com.yopal.easygifts.managers.RequestManager;
import com.yopal.easygifts.utils.Request;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class DisconnectListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        if (RequestManager.getRequests().isEmpty()) {
            return;
        }

        for (Request request : RequestManager.getRequests()) {
            if (request.getReceiver() == e.getPlayer().getUniqueId()) {
                RequestManager.removeRequest(request);
            }
        }
    }

}
