package com.yopal.easygifts.listeners;

import com.yopal.easygifts.enums.GUITypes;
import com.yopal.easygifts.managers.GUIManager;
import com.yopal.easygifts.utils.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class GUICloseListener implements Listener {

    @EventHandler
    public void onCloseInv(InventoryCloseEvent e) {

        Player player = (Player) e.getPlayer();
        GUI gui = GUIManager.getGUI((player));
        if (e.getInventory() == null || gui == null) {
            return;
        }

        if (GUIManager.correlateGUI(e.getInventory()) == null) {
            return;
        }

        if (GUIManager.correlateGUI(e.getInventory()).getType() != gui.getType()) {
            return;
        }

        GUIManager.removeGUI(gui);

        // adding contents to player's inv if they had items in chest page
        if (gui.getChestInv() == null) {
            return;
        }

        if (gui.getType().equals(GUITypes.CHEST)) {
            for (int i = 0; i < 45; i++) {
                ItemStack itemStack = e.getInventory().getItem(i);
                if (itemStack != null) {
                    player.getInventory().addItem(itemStack);
                }
            }
        } else {
            for (int i = 0; i < 45; i++) {
                ItemStack itemStack = gui.getChestInv().getItem(i);
                if (itemStack != null) {
                    player.getInventory().addItem(itemStack);
                }
            }
        }
    }
}
