package com.yopal.easygifts.GUI.giftOpen.listeners;

import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.GUI.enums.GUITypes;
import com.yopal.easygifts.GUI.giftOpen.instances.GUIOpen;
import com.yopal.easygifts.GUI.giftOpen.managers.GUIOpenManager;
import com.yopal.easygifts.YML.instances.PlayerData;
import com.yopal.easygifts.YML.managers.GiftDataManager;
import com.yopal.easygifts.utils.PlayerInteract;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class GUIOpenGiftInteractionListener implements Listener {

    private EasyGifts easyGifts;

    public GUIOpenGiftInteractionListener(EasyGifts easyGifts) {
        this.easyGifts = easyGifts;
    }

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
        } else {
            player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        }

        switch (e.getSlot()) {
            case 45:
                gui.openMainPage();
                break;
            case 53:
                if (!gui.checkHasSpace()) {
                    break;
                }

                for (int i = 0; i < 45; i++) {
                    ItemStack itemStack = gui.getCurrentInv().getItem(i);
                    if (itemStack == null) {
                        continue;
                    }

                    player.getInventory().addItem(itemStack);
                }

                // sending sender message if they accept it and the sender is online
                OfflinePlayer sender = Bukkit.getOfflinePlayer(playerData.getSenderUUID(gui.getGiftID()));
                if (sender.isOnline()) {
                    PlayerInteract.sendMessage(sender.getPlayer(), ChatColor.GREEN + "Your gift to " + player.getName() +  " has been accepted!");
                }

                // sending player personalized message
                String personalizedMsg = playerData.getPersonalizedMessage(gui.getGiftID());
                if (personalizedMsg != null) {
                    player.sendMessage(ChatColor.GOLD + ChatColor.BOLD.toString() + "From " + sender.getName() + ": " + ChatColor.GRAY + personalizedMsg);
                }

                player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
                playerData.removeData(easyGifts, gui.getGiftID());
                gui.openMainPage();


                break;
        }


    }

}
