package com.yopal.easygifts.GUI.giftOpen.managers;

import com.yopal.easygifts.GUI.giftOpen.instances.GUIOpen;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class GUIOpenManager {

    private static List<GUIOpen> GUIList = new ArrayList<>();

    public static void addGUI(GUIOpen gui) {
        GUIList.add(gui);
    }

    public static void removeGUI(GUIOpen gui) {
        GUIList.remove(gui);
    }


    public static GUIOpen getGUI(Player player) {
        for (GUIOpen gui : GUIList) {
            if (gui.getPlayer() == player) {
                return gui;
            }
        }

        return null;
    }


    public static GUIOpen correlateGUI(Inventory inv) {
        for (GUIOpen gui : GUIList) {
            if (gui.getCurrentInv() == inv) {
                return gui;
            }
        }
        return null;
    }



    public static List<GUIOpen> getGUIList() {
        return GUIList;
    }


}
