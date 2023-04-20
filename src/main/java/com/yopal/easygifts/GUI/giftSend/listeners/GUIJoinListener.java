package com.yopal.easygifts.GUI.giftSend.listeners;

import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.GUI.enums.GUITypes;
import com.yopal.easygifts.GUI.giftSend.managers.GUISendManager;
import com.yopal.easygifts.GUI.giftSend.instances.GUISend;
import com.yopal.easygifts.utils.PageUtil;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GUIJoinListener implements Listener {

    private EasyGifts easyGifts;

    public GUIJoinListener(EasyGifts easyGifts) {
        this.easyGifts = easyGifts;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        for (GUISend gui : GUISendManager.getGUIList()) {
            OfflinePlayer receiver = gui.getReceiver();

            if (gui.getType() == GUITypes.MAIN && receiver.getUniqueId().equals(e.getPlayer().getUniqueId())) {
                PageUtil.updateStatus(easyGifts, gui.getPlayer().getOpenInventory().getTopInventory(), gui.getReceiver(), 13);
                break;
            }
        }
    }

}
