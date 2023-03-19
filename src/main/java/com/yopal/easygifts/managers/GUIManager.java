package com.yopal.easygifts.managers;

import com.yopal.easygifts.utils.GUI;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.List;

public class GUIManager {
    private static List<GUI> GUIList;

    public static void addGUI(GUI gui) {
        GUIList.add(gui);
    }

    public static void removeGUI(GUI gui) {
        GUIList.remove(gui);
    }

    public static GUI getGUI(Player player, OfflinePlayer receiver) {
        for (GUI gui : GUIList) {
            if (gui.getPlayer() == player && gui.getReceiver() == receiver) {
                return gui;
            }
        }

        return null;
    }
}
