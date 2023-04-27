package com.yopal.easygifts.GUI.giftOpen.listeners;

import com.yopal.easygifts.GUI.enums.GUITypes;
import com.yopal.easygifts.GUI.giftOpen.instances.GUIOpen;
import com.yopal.easygifts.GUI.giftOpen.managers.GUIOpenManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIOpenRestrictionListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        GUIOpen gui = GUIOpenManager.getGUI(player);

        if (e.getInventory() == null || e.getCurrentItem() == null || gui  == null) {
            return;
        }

        if (!gui.getType().equals(GUITypes.CHANGEPAGE) && !gui.getType().equals(GUITypes.GIFTPAGE)) {
            return;
        }

        e.setCancelled(true);

    }

}
