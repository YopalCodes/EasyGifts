package com.yopal.easygifts.GUI.giftSend.listeners.time;

import com.yopal.easygifts.GUI.giftSend.instances.GUISend;
import com.yopal.easygifts.GUI.enums.GUITypes;
import com.yopal.easygifts.GUI.giftSend.managers.GUISendManager;
import com.yopal.easygifts.utils.PageUtil;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TimeRightInteractionListener implements Listener {

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

        if (!e.getClick().equals(ClickType.RIGHT)) {
            return;
        }

        if (e.getRawSlot() == 11 || e.getRawSlot() == 13 || e.getRawSlot() == 15) {
            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, 1.75f);
        }

        switch (e.getRawSlot()) {
            case 11:
                if (gui.getDays() == 0) { return; }

                gui.setDays(gui.getDays() - 1);
                PageUtil.updateDisplayName(e.getInventory(), e.getRawSlot(), ChatColor.BOLD + "DAYS " + ChatColor.GRAY + ChatColor.BOLD + "-> " + ChatColor.GOLD + ChatColor.BOLD + gui.getDays());

                break;
            case 13:
                if (gui.getHours() == 0) { return; }

                gui.setHours(gui.getHours() - 1);
                PageUtil.updateDisplayName(e.getInventory(), e.getRawSlot(), ChatColor.BOLD + "HOURS " + ChatColor.GRAY + ChatColor.BOLD + "-> " + ChatColor.GOLD + ChatColor.BOLD + gui.getHours());

                break;
            case 15:
                if (gui.getMinutes() == 0) { return; }

                gui.setMinutes(gui.getMinutes() - 1);
                PageUtil.updateDisplayName(e.getInventory(), e.getRawSlot(), ChatColor.BOLD + "MINUTES " + ChatColor.GRAY + ChatColor.BOLD + "-> " + ChatColor.GOLD + ChatColor.BOLD + gui.getMinutes());

                break;
        }


    }

}
