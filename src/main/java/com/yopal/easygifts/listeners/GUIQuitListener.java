package com.yopal.easygifts.listeners;

import com.yopal.easygifts.enums.GUITypes;
import com.yopal.easygifts.managers.GUIManager;
import com.yopal.easygifts.utils.GUI;
import com.yopal.easygifts.utils.PageUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GUIQuitListener implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        for (GUI gui : GUIManager.getGUIList()) {
            Player receiver = gui.getReceiver().getPlayer();
            if (receiver == null) {
                break;
            }

            if (receiver != e.getPlayer()) {
                break;
            }

            if (gui.getType() != GUITypes.MAIN) {
                break;
            }

            PageUtil.updateStatus(gui.getPlayer().getInventory(), gui.getReceiver(), 13);
        }
    }

}
