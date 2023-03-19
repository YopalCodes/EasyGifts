package com.yopal.easygifts;

import com.yopal.easygifts.commands.GiftCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class EasyGifts extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        getCommand("ezg").setExecutor(new GiftCommand(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
