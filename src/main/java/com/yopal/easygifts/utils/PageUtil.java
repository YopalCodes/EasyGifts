package com.yopal.easygifts.utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PageUtil {

    /**
     * Create a framing for the GUI - only the top and the bottom row
     * @param inv
     * @param frameItem
     * @param topLeftSlot
     * @param bottomRightSlot
     */
    public static void createFrames(Inventory inv, ItemStack frameItem, int topLeftSlot, int bottomRightSlot) {

        // top row
        for (int i = topLeftSlot; i < topLeftSlot + 8; i++) {
            inv.setItem(i, frameItem);
        }

        // bottom row
        for (int i = bottomRightSlot; i < bottomRightSlot - 8; i--) {
            inv.setItem(i, frameItem);
        }

    }

    /**
     * Set a specific ItemStack at a slot in the GUI.
     * @param inv
     * @param itemName
     * @param material
     * @param itemLore
     * @param slot
     */
    public static void setItem(Inventory inv, String itemName, Material material, List<String> itemLore, int slot) {
        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(itemName);
        itemMeta.setLore(itemLore);

        item.setItemMeta(itemMeta);

        inv.setItem(slot, item);
    }

}
