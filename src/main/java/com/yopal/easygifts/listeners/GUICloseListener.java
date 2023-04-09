package com.yopal.easygifts.listeners;

import com.yopal.easygifts.enums.GUITypes;
import com.yopal.easygifts.managers.GUIManager;
import com.yopal.easygifts.utils.GUI;
import com.yopal.easygifts.utils.PageUtil;
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
        PageUtil.returnItems(gui, e.getInventory(), player);
    }
}
