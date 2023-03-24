package com.yopal.easygifts.listeners;

import com.yopal.easygifts.enums.GUITypes;
import com.yopal.easygifts.managers.GUIManager;
import com.yopal.easygifts.utils.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GUICloseListener implements Listener {

    @EventHandler
    public void onCloseInv(InventoryCloseEvent e) {

        GUI gui = GUIManager.getGUI((Player) e.getPlayer());
        if (e.getInventory() == null || gui == null) {
            return;
        }

        if (GUIManager.correlateGUI(e.getInventory()).getType() != gui.getType()) {
            return;
        }

        GUIManager.removeGUI(GUIManager.getGUI((Player) e.getPlayer()));

    }
}
