package com.yopal.easygifts;

import com.yopal.easygifts.commands.GiftCommand;
import com.yopal.easygifts.listeners.GUICloseListener;
import com.yopal.easygifts.listeners.main.MainInteractionListener;
import com.yopal.easygifts.listeners.GUIRestrictionListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class EasyGifts extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        // commands
        getCommand("ezg").setExecutor(new GiftCommand(this));

        // listeners
        Bukkit.getPluginManager().registerEvents(new GUIRestrictionListener(), this);
        Bukkit.getPluginManager().registerEvents(new GUICloseListener(), this);
        Bukkit.getPluginManager().registerEvents(new MainInteractionListener(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
