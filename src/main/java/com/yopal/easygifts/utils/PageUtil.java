package com.yopal.easygifts.utils;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

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
        // making sure item doesn't have display name
        ItemMeta itemMeta = frameItem.getItemMeta();
        itemMeta.setDisplayName("");
        frameItem.setItemMeta(itemMeta);

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

    /**
     * Set the receiver's skull in the GUI
     * @param inv
     * @param player
     * @param slot
     */
    public static void setSkull(Inventory inv, OfflinePlayer player, int slot) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwningPlayer(player);
        itemStack.setItemMeta(skullMeta);

        inv.setItem(slot, itemStack);
    }

}
