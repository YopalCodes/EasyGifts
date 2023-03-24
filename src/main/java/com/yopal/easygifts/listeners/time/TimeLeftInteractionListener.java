package com.yopal.easygifts.listeners.time;

import com.yopal.easygifts.enums.GUITypes;
import com.yopal.easygifts.managers.GUIManager;
import com.yopal.easygifts.utils.GUI;
import com.yopal.easygifts.utils.PageUtil;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TimeLeftInteractionListener implements Listener {



    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        GUI gui = GUIManager.getGUI(player);
        if (e.getInventory() == null || e.getCurrentItem() == null || gui  == null) {
            return;
        }

        if (!gui.getType().equals(GUITypes.TIME)) {
            return;
        }

        if (!e.getClick().equals(ClickType.LEFT)) {
            return;
        }

        if (e.getRawSlot() == 11 || e.getRawSlot() == 13 || e.getRawSlot() == 15) {
            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, 1.75f);
        }

        // if not one of the interactive slots
        switch (e.getRawSlot()) {
            case 11:
                gui.setMonths(gui.getMonths() + 1);
                PageUtil.updateDisplayName(e.getInventory(), e.getRawSlot(), ChatColor.BOLD + "MONTHS " + ChatColor.GRAY + ChatColor.BOLD + "-> " + ChatColor.GOLD + ChatColor.BOLD + gui.getMonths());

                break;
            case 13:
                gui.setDays(gui.getDays() + 1);
                PageUtil.updateDisplayName(e.getInventory(), e.getRawSlot(), ChatColor.BOLD + "DAYS " + ChatColor.GRAY + ChatColor.BOLD + "-> " + ChatColor.GOLD + ChatColor.BOLD + gui.getDays());

                break;
            case 15:
                gui.setSeconds(gui.getSeconds() + 1);
                PageUtil.updateDisplayName(e.getInventory(), e.getRawSlot(), ChatColor.BOLD + "SECONDS " + ChatColor.GRAY + ChatColor.BOLD + "-> " + ChatColor.GOLD + ChatColor.BOLD + gui.getSeconds());

                break;
        }


    }
}
