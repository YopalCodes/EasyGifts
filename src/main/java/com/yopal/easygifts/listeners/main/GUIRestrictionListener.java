package com.yopal.easygifts.listeners.main;

import com.yopal.easygifts.enums.GUITypes;
import com.yopal.easygifts.managers.GUIManager;
import com.yopal.easygifts.utils.GUI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIRestrictionListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        GUI gui = GUIManager.getGUI(player);

        e.setCancelled(true);

        if (e.getInventory() == null || e.getCurrentItem() == null || gui  == null) {
            return;
        }

        // if not one of the interactive slots, restrict click
        if (e.getRawSlot() == 29 || e.getRawSlot() == 31 || e.getRawSlot() == 33) {
            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, 2);
            return;
        }

    }
}
