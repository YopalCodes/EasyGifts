package com.yopal.easygifts.GUI.giftSend.listeners.main;

import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.GUI.giftSend.instances.GUISend;
import com.yopal.easygifts.GUI.giftSend.managers.ConversationManager;
import com.yopal.easygifts.GUI.giftSend.managers.GUISendManager;
import com.yopal.easygifts.GUI.enums.GUITypes;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MainClickListener implements Listener {

    private EasyGifts easyGifts;

    public MainClickListener(EasyGifts easyGifts) {
        this.easyGifts = easyGifts;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        GUISend gui = GUISendManager.getGUI(player);
        if (e.getInventory() == null || e.getCurrentItem() == null || gui  == null) {
            return;
        }

        if (!gui.getType().equals(GUITypes.MAIN)) {
            return;
        }

        if (e.getRawSlot() == 20 || e.getRawSlot() == 22 || e.getRawSlot() == 24 || e.getRawSlot() == 30) {
            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, 1);
        }

        // if not one of the interactive slots
        switch (e.getRawSlot()) {
            case 20:
                gui.openChestPage();
                break;
            case 22:
                gui.openTimePage();
                break;
            case 24:
                gui.setType(GUITypes.CONVERSATION);
                player.closeInventory();
                ConversationManager.converseTitle(easyGifts, player);
                break;
            case 30:
                gui.setType(GUITypes.CONVERSATION);
                player.closeInventory();
                ConversationManager.converseMessage(easyGifts, player);
                break;
        }


    }
}
