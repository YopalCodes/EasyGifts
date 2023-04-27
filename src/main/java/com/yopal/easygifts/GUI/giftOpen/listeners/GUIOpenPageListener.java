package com.yopal.easygifts.GUI.giftOpen.listeners;

import com.yopal.easygifts.GUI.giftOpen.managers.GUIOpenManager;
import com.yopal.easygifts.GUI.enums.GUITypes;
import com.yopal.easygifts.GUI.giftOpen.instances.GUIOpen;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIOpenPageListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        GUIOpen gui = GUIOpenManager.getGUI(player);

        if (e.getInventory() == null || e.getCurrentItem() == null || gui  == null) {
            return;
        }

        if (e.getRawSlot() == 0 || e.getRawSlot() == 4 || e.getRawSlot() == 8) {
            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, 1);
        }

        if (!gui.getType().equals(GUITypes.CHANGEPAGE)) {
            return;
        }

        // if not one of the interactive slots
        switch (e.getRawSlot()) {
            case 0:
                gui.subtractPage(e.getCurrentItem().getType());
                gui.openMainPage();
                break;
            case 4:
                gui.openMainPage();
                break;
            case 8:
                gui.addPage(e.getCurrentItem().getType());
                gui.openMainPage();
                break;
        }


    }

}
