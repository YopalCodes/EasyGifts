package com.yopal.easygifts.GUI.giftSend.listeners.time;

import com.yopal.easygifts.GUI.giftSend.instances.GUISend;
import com.yopal.easygifts.GUI.enums.GUITypes;
import com.yopal.easygifts.GUI.giftSend.managers.GUISendManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TimeClickInteractionListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        GUISend gui = GUISendManager.getGUI(player);
        if (e.getInventory() == null || e.getCurrentItem() == null || gui  == null) {
            return;
        }

        if (!gui.getType().equals(GUITypes.TIME)) {
            return;
        }

        if (e.getRawSlot() == 0) {
            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, 1);
            gui.openMainPage();
        }
    }

}
