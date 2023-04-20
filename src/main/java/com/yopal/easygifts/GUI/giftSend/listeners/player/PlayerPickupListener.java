package com.yopal.easygifts.GUI.giftSend.listeners.player;

import com.yopal.easygifts.GUI.giftSend.managers.GUISendManager;
import com.yopal.easygifts.GUI.giftSend.instances.GUISend;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupListener implements Listener {
    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        for (GUISend gui : GUISendManager.getGUIList()) {
            if (gui.getPlayer() != e.getPlayer()) {
                break;
            }

            e.setCancelled(true);
        }


    }
}
