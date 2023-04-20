package com.yopal.easygifts.GUI.giftSend.listeners.chest;

import com.yopal.easygifts.GUI.enums.GUITypes;
import com.yopal.easygifts.GUI.giftSend.managers.GUISendManager;
import com.yopal.easygifts.GUI.giftSend.instances.GUISend;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ChestClickInteractionListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        GUISend gui = GUISendManager.getGUI(player);
        if (e.getInventory() == null || e.getCurrentItem() == null || gui  == null) {
            return;
        }

        if (!gui.getType().equals(GUITypes.CHEST)) {
            return;
        }

        if (e.getRawSlot() == 45) {
            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, 1);
            gui.openMainPage();

            gui.setChestInv(e.getInventory());
        }

    }

}
