package com.yopal.easygifts.YML.listeners;

import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.YML.managers.GiftDataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerConnectListener implements Listener {

    private EasyGifts easyGifts;

    public PlayerConnectListener(EasyGifts easyGifts) {
        this.easyGifts = easyGifts;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        GiftDataManager.addFile(e.getPlayer().getUniqueId());
    }
}
