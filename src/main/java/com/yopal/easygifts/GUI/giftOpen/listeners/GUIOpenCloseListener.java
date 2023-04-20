package com.yopal.easygifts.GUI.giftOpen.listeners;

import com.yopal.easygifts.GUI.giftOpen.instances.GUIOpen;
import com.yopal.easygifts.GUI.giftOpen.managers.GUIOpenManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GUIOpenCloseListener implements Listener {

    @EventHandler
    public void onCloseInv(InventoryCloseEvent e) {

        Player player = (Player) e.getPlayer();
        GUIOpen gui = GUIOpenManager.getGUI((player));
        if (e.getInventory() == null || gui == null) {
            return;
        }

        if (GUIOpenManager.correlateGUI(e.getInventory()) == null) {
            return;
        }

        if (GUIOpenManager.correlateGUI(e.getInventory()).getType() != gui.getType()) {
            return;
        }

        GUIOpenManager.removeGUI(gui);
    }

}
