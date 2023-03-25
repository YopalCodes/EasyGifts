package com.yopal.easygifts.listeners.player;

import com.yopal.easygifts.enums.GUITypes;
import com.yopal.easygifts.managers.GUIManager;
import com.yopal.easygifts.utils.GUI;
import com.yopal.easygifts.utils.PageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerPickupListener implements Listener {
    @EventHandler
    public void onPickup(PlayerPickupItemEvent e) {
        for (GUI gui : GUIManager.getGUIList()) {
            if (gui.getPlayer() != e.getPlayer()) {
                break;
            }

            e.setCancelled(true);
        }


    }
}
