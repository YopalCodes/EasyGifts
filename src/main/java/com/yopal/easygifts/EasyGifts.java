package com.yopal.easygifts;

import com.yopal.easygifts.GUI.giftOpen.listeners.*;
import com.yopal.easygifts.YML.listeners.PlayerConnectListener;
import com.yopal.easygifts.commands.GiftCommand;
import com.yopal.easygifts.GUI.giftSend.listeners.GUISendCloseListener;
import com.yopal.easygifts.GUI.giftSend.listeners.GUIJoinListener;
import com.yopal.easygifts.GUI.giftSend.listeners.GUIQuitListener;
import com.yopal.easygifts.GUI.giftSend.listeners.chest.ChestClickInteractionListener;
import com.yopal.easygifts.GUI.giftSend.listeners.chest.ChestRestrictionListener;
import com.yopal.easygifts.GUI.giftSend.listeners.main.MainClickListener;
import com.yopal.easygifts.GUI.giftSend.listeners.GUISendRestrictionListener;
import com.yopal.easygifts.GUI.giftSend.listeners.main.MainLeftInteractionListener;
import com.yopal.easygifts.GUI.giftSend.listeners.player.PlayerPickupListener;
import com.yopal.easygifts.GUI.giftSend.listeners.time.TimeClickInteractionListener;
import com.yopal.easygifts.GUI.giftSend.listeners.time.TimeDropInteractionListener;
import com.yopal.easygifts.GUI.giftSend.listeners.time.TimeLeftInteractionListener;
import com.yopal.easygifts.GUI.giftSend.listeners.time.TimeRightInteractionListener;
import com.yopal.easygifts.YML.managers.GiftDataManager;
import com.yopal.easygifts.GUI.listeners.PlayerQuitListener;
import com.yopal.easygifts.commands.GiftCommandTabCompleter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class EasyGifts extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        // commands
        getCommand("ezg").setExecutor(new GiftCommand(this));
        getCommand("ezg").setTabCompleter(new GiftCommandTabCompleter());

        // YML file
        GiftDataManager.setupFiles(this);

        // listeners
        Bukkit.getPluginManager().registerEvents(new PlayerPickupListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerConnectListener(this), this);

        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);

        Bukkit.getPluginManager().registerEvents(new GUISendRestrictionListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUISendCloseListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GUIQuitListener(this), this);

        Bukkit.getPluginManager().registerEvents(new MainClickListener(this), this);
        Bukkit.getPluginManager().registerEvents(new MainLeftInteractionListener(this), this);

        Bukkit.getPluginManager().registerEvents(new TimeDropInteractionListener(), this);
        Bukkit.getPluginManager().registerEvents(new TimeLeftInteractionListener(), this);
        Bukkit.getPluginManager().registerEvents(new TimeRightInteractionListener(), this);
        Bukkit.getPluginManager().registerEvents(new TimeClickInteractionListener(), this);

        Bukkit.getPluginManager().registerEvents(new ChestClickInteractionListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChestRestrictionListener(), this);

        Bukkit.getPluginManager().registerEvents(new GUIOpenRestrictionListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIOpenCloseListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIOpenPageListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIOpenHeadInteractionListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIOpenGiftInteractionListener(this), this);

    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
