package com.yopal.easygifts.listeners.main;

import com.yopal.easygifts.enums.GUITypes;
import com.yopal.easygifts.managers.GUIManager;
import com.yopal.easygifts.utils.GUI;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIInteractionListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        GUI gui = GUIManager.getGUI(player);
        if (e.getInventory() == null || e.getCurrentItem() == null || gui  == null) {
            return;
        }

        if (!gui.getType().equals(GUITypes.MAIN)) {
            return;
        }

        if (e.getRawSlot() == 29 || e.getRawSlot() == 31 || e.getRawSlot() == 33) {
            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, 2);
        }

        // if not one of the interactive slots, restrict click
        switch (e.getRawSlot()) {
            case 29:
                gui.openTimePage();
                break;
            case 31:
                // open particle page
                break;
            case 33:
                // open chest page
                break;
        }


    }
}
