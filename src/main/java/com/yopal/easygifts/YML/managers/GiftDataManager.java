package com.yopal.easygifts.YML.managers;

import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.YML.instances.PlayerData;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GiftDataManager {

    private static File dataFolder;
    private static List<PlayerData> playerDataList = new ArrayList<>();

    public static void setupFiles(EasyGifts easyGifts) {
        GiftDataManager.dataFolder = new File(easyGifts.getDataFolder(), "playerData/");

        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        for (File file : dataFolder.listFiles()) {
            playerDataList.add(new PlayerData(file));
        }

    }

    public static void addFile(UUID playerUUID) {
        File playerFile = new File(dataFolder, playerUUID + ".yml");

        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        playerDataList.add(new PlayerData(playerFile));

    }


    public static PlayerData getPlayerData(UUID playerUUID) {
        for (PlayerData playerData : playerDataList) {
            if (playerData.getPlayerUUID().equals(playerUUID)) {
                return playerData;
            }
        }

        return null;
    }






}
