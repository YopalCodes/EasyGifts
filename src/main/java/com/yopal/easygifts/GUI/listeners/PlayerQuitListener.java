package com.yopal.easygifts.GUI.listeners;

import com.yopal.easygifts.GUI.giftOpen.instances.GUIOpen;
import com.yopal.easygifts.GUI.giftOpen.managers.GUIOpenManager;
import com.yopal.easygifts.GUI.giftSend.instances.GUISend;
import com.yopal.easygifts.GUI.giftSend.managers.GUISendManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        GUIOpen guiOpen = GUIOpenManager.getGUI(e.getPlayer());
        GUISend guiSend = GUISendManager.getGUI(e.getPlayer());

        if (guiOpen != null) {
            GUIOpenManager.removeGUI(guiOpen);
        }

        if (guiSend != null) {
            GUISendManager.removeGUI(guiSend);
        }

    }
}
