package com.yopal.easygifts.managers.YML;

import com.yopal.easygifts.EasyGifts;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    private static FileConfiguration config;

    public static void setupConfig(EasyGifts easyGifts) {
        ConfigManager.config = easyGifts.getConfig();
        easyGifts.saveDefaultConfig();
    }

    public static void reloadConfig(EasyGifts easyGifts) { easyGifts.reloadConfig(); config = easyGifts.getConfig();}

    public static String checkStorage() {
        return config.getString("storage");
    }
}
