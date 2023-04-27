package com.yopal.easygifts.GUI.giftOpen.listeners;

import com.yopal.easygifts.GUI.enums.GUITypes;
import com.yopal.easygifts.GUI.giftOpen.instances.GUIOpen;
import com.yopal.easygifts.GUI.giftOpen.managers.GUIOpenManager;
import com.yopal.easygifts.YML.instances.PlayerData;
import com.yopal.easygifts.YML.managers.GiftDataManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class GUIOpenGiftInteractionListener implements Listener {

    @EventHandler
    public void onHeadClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        GUIOpen gui = GUIOpenManager.getGUI(player);
        PlayerData playerData = GiftDataManager.getPlayerData(player.getUniqueId());

        if (e.getInventory() == null || e.getCurrentItem() == null || gui  == null) {
            return;
        }

        if (!e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
            return;
        }

        if (!gui.getType().equals(GUITypes.GIFTPAGE)) {
            return;
        }

        if (e.getSlot() != 45 && e.getSlot() != 53) {
            return;
        }

        int giftID = Integer.parseInt(e.getCurrentItem().getItemMeta().getLore().get(1).replace("§7GIFT ID: ", ""));

        switch (e.getSlot()) {
            case 45:
                gui.openMainPage();
                break;
            case 53:
                if (gui.checkHasSpace()) {
                    player.getInventory().addItem(gui.getCurrentInv().getContents());
                }
                playerData.removeData(giftID);
                break;
        }


    }

}
