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

public class TimeDropInteractionListener implements Listener {


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

        if (!e.getClick().equals(ClickType.DROP)) {
            return;
        }

        if (e.getRawSlot() == 11 || e.getRawSlot() == 13 || e.getRawSlot() == 15) {
            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, 0.5f);
        }

        // if not one of the interactive slots
        switch (e.getRawSlot()) {
            case 11:
                gui.setDays(0);
                PageUtil.updateDisplayName(e.getInventory(), e.getRawSlot(), ChatColor.BOLD + "DAYS " + ChatColor.GRAY + ChatColor.BOLD + "-> " + ChatColor.GOLD + ChatColor.BOLD + gui.getDays());

                break;
            case 13:
                gui.setHours(0);
                PageUtil.updateDisplayName(e.getInventory(), e.getRawSlot(), ChatColor.BOLD + "HOURS " + ChatColor.GRAY + ChatColor.BOLD + "-> " + ChatColor.GOLD + ChatColor.BOLD + gui.getHours());

                break;
            case 15:
                gui.setMinutes(0);
                PageUtil.updateDisplayName(e.getInventory(), e.getRawSlot(), ChatColor.BOLD + "MINUTES " + ChatColor.GRAY + ChatColor.BOLD + "-> " + ChatColor.GOLD + ChatColor.BOLD + gui.getMinutes());

                break;
        }


    }


}
