package com.yopal.easygifts.GUI.giftSend.listeners.chest;

import com.yopal.easygifts.GUI.enums.GUITypes;
import com.yopal.easygifts.GUI.giftSend.managers.GUISendManager;
import com.yopal.easygifts.GUI.giftSend.instances.GUISend;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class ChestRestrictionListener implements Listener {


    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        GUISend gui = GUISendManager.getGUI(player);

        if (e.getInventory() == null || e.getCurrentItem() == null || gui  == null) {
            return;
        }


        // during the chest page, the only items that should be restricted is the gray stained-glass panes and the barrier
        if (!gui.getType().equals(GUITypes.CHEST)) {
            return;
        }

        for (int i = 45; i < 54; i++) {
            ItemStack itemStack = gui.getChestInv().getItem(i);

            if (e.getCurrentItem().isSimilar(itemStack)) {
                e.setCancelled(true);
                return;
            }
        }
    }
}
