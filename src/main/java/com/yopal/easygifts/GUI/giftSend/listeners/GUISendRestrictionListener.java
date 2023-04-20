package com.yopal.easygifts.GUI.giftSend.listeners;

import com.yopal.easygifts.GUI.enums.GUITypes;
import com.yopal.easygifts.GUI.giftSend.managers.GUISendManager;
import com.yopal.easygifts.GUI.giftSend.instances.GUISend;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUISendRestrictionListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        GUISend gui = GUISendManager.getGUI(player);

        if (e.getInventory() == null || e.getCurrentItem() == null || gui  == null) {
            return;
        }

        if (gui.getType().equals(GUITypes.CHEST)) {
            return;
        }

        e.setCancelled(true);

    }
}
