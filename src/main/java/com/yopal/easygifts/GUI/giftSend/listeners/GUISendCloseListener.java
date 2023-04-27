package com.yopal.easygifts.GUI.giftSend.listeners;

import com.yopal.easygifts.GUI.enums.GUITypes;
import com.yopal.easygifts.GUI.giftSend.managers.GUISendManager;
import com.yopal.easygifts.GUI.giftSend.instances.GUISend;
import com.yopal.easygifts.utils.PageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class GUISendCloseListener implements Listener {

    @EventHandler
    public void onCloseInv(InventoryCloseEvent e) {

        Player player = (Player) e.getPlayer();
        GUISend gui = GUISendManager.getGUI((player));
        if (e.getInventory() == null || gui == null) {
            return;
        }

        if (GUISendManager.correlateGUI(e.getInventory()) == null) {
            return;
        }

        if (GUISendManager.correlateGUI(e.getInventory()).getType() != gui.getType()) {
            return;
        }

        if (gui.getType().equals(GUITypes.CONVERSATION)) {
            return;
        }

        GUISendManager.removeGUI(gui);
        PageUtil.returnItems(gui, e.getInventory(), player);
    }
}
