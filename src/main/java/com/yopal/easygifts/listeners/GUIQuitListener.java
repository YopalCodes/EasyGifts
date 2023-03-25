package com.yopal.easygifts.listeners;

import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.enums.GUITypes;
import com.yopal.easygifts.managers.GUIManager;
import com.yopal.easygifts.utils.GUI;
import com.yopal.easygifts.utils.PageUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GUIQuitListener implements Listener {

    private EasyGifts easyGifts;

    public GUIQuitListener(EasyGifts easyGifts) {
        this.easyGifts = easyGifts;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        for (GUI gui : GUIManager.getGUIList()) {
            OfflinePlayer receiver = gui.getReceiver();

            if (gui.getType() == GUITypes.MAIN && receiver.getUniqueId().equals(e.getPlayer().getUniqueId())) {
                PageUtil.updateStatus(easyGifts, gui.getPlayer().getOpenInventory().getTopInventory(), gui.getReceiver(), 13);
                break;
            }

        }
    }

}
