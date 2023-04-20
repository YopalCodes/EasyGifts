package com.yopal.easygifts.GUI.giftSend.managers;

import com.yopal.easygifts.GUI.giftSend.instances.GUISend;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class GUISendManager {
    private static List<GUISend> GUIList = new ArrayList<>();

    public static void addGUI(GUISend gui) {
        GUIList.add(gui);
    }

    public static void removeGUI(GUISend gui) {
        GUIList.remove(gui);
    }

    public static GUISend getGUI(Player player) {
        for (GUISend gui : GUIList) {
            if (gui.getPlayer() == player) {
                return gui;
            }
        }

        return null;
    }

    public static GUISend correlateGUI(Inventory inv) {
        for (GUISend gui : GUIList) {
            if (gui.getCurrentInv() == inv) {
                return gui;
            }
        }
        return null;
    }

    public static List<GUISend> getGUIList() {
        return GUIList;
    }
}
