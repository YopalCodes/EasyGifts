package com.yopal.easygifts.GUI.giftOpen.listeners;

import com.yopal.easygifts.GUI.enums.GUITypes;
import com.yopal.easygifts.GUI.giftOpen.instances.GUIOpen;
import com.yopal.easygifts.GUI.giftOpen.managers.GUIOpenManager;
import com.yopal.easygifts.YML.instances.PlayerData;
import com.yopal.easygifts.YML.managers.GiftDataManager;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class GUIOpenHeadInteractionListener implements Listener {

    @EventHandler
    public void onHeadClick(InventoryClickEvent e) throws ParseException {
        Player player = (Player) e.getWhoClicked();
        GUIOpen gui = GUIOpenManager.getGUI(player);

        if (e.getInventory() == null || e.getCurrentItem() == null || gui  == null) {
            return;
        }

        if (!e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
            return;
        }

        if (!gui.getType().equals(GUITypes.CHANGEPAGE)) {
            return;
        }

        if (e.getSlot() == 4) {
            return;
        }

        int giftID = Integer.parseInt(e.getCurrentItem().getItemMeta().getLore().get(1).replace("§7GIFT ID: ", ""));

        // check if current time
        PlayerData playerData = GiftDataManager.getPlayerData(player.getUniqueId());

        Date today = Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTime();

        Date futureDate = playerData.getFutureDate(giftID);

        if (!today.after(futureDate) && !today.equals(futureDate)) {
            return;
        }

        player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
        gui.openGiftPage(playerData, giftID);

    }

}
