package com.yopal.easygifts;

import com.yopal.easygifts.commands.AcceptCommand;
import com.yopal.easygifts.commands.DenyCommand;
import com.yopal.easygifts.commands.GiftCommand;
import com.yopal.easygifts.listeners.DisconnectListener;
import com.yopal.easygifts.listeners.GiftCreateListener;
import com.yopal.easygifts.listeners.RestrictionListener;
import com.yopal.easygifts.listeners.GiftUsedListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class EasyGifts extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new RestrictionListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GiftCreateListener(this), this);
        Bukkit.getPluginManager().registerEvents(new GiftUsedListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DisconnectListener(), this);
        getCommand("ezg").setExecutor(new GiftCommand(this));
        getCommand("accept").setExecutor(new AcceptCommand(this));
        getCommand("deny").setExecutor(new DenyCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
