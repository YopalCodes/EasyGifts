package com.yopal.easygifts;

import com.yopal.easygifts.commands.GiftCommand;
import com.yopal.easygifts.listeners.GUICloseListener;
import com.yopal.easygifts.listeners.GUIJoinListener;
import com.yopal.easygifts.listeners.GUIQuitListener;
import com.yopal.easygifts.listeners.chest.ChestClickInteractionListener;
import com.yopal.easygifts.listeners.chest.ChestRestrictionListener;
import com.yopal.easygifts.listeners.main.MainClickListener;
import com.yopal.easygifts.listeners.GUIRestrictionListener;
import com.yopal.easygifts.listeners.particle.ParticleClickInteractionListener;
import com.yopal.easygifts.listeners.player.PlayerPickupListener;
import com.yopal.easygifts.listeners.time.TimeClickInteractionListener;
import com.yopal.easygifts.listeners.time.TimeDropInteractionListener;
import com.yopal.easygifts.listeners.time.TimeLeftInteractionListener;
import com.yopal.easygifts.listeners.time.TimeRightInteractionListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class EasyGifts extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        // commands
        getCommand("ezg").setExecutor(new GiftCommand(this));

        // listeners
        Bukkit.getPluginManager().registerEvents(new PlayerPickupListener(), this);

        Bukkit.getPluginManager().registerEvents(new GUIRestrictionListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUICloseListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUIJoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GUIQuitListener(this), this);

        Bukkit.getPluginManager().registerEvents(new MainClickListener(), this);

        Bukkit.getPluginManager().registerEvents(new TimeDropInteractionListener(), this);
        Bukkit.getPluginManager().registerEvents(new TimeLeftInteractionListener(), this);
        Bukkit.getPluginManager().registerEvents(new TimeRightInteractionListener(), this);
        Bukkit.getPluginManager().registerEvents(new TimeClickInteractionListener(), this);

        Bukkit.getPluginManager().registerEvents(new ParticleClickInteractionListener(), this);

        Bukkit.getPluginManager().registerEvents(new ChestClickInteractionListener(), this);
        Bukkit.getPluginManager().registerEvents(new ChestRestrictionListener(), this);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
