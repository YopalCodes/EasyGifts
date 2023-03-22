package com.yopal.easygifts.listeners;

import com.yopal.easygifts.enums.GUITypes;
import com.yopal.easygifts.managers.GUIManager;
import com.yopal.easygifts.utils.GUI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIRestrictionListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        GUI gui = GUIManager.getGUI(player);

        if (e.getInventory() == null || e.getCurrentItem() == null || gui  == null) {
            return;
        }

        e.setCancelled(true);

    }
}
