package com.yopal.easygifts.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.enums.GUITypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class PageUtil {

    /**
     * Create a framing for the GUI - only the top and the bottom row
     *
     * @param inv
     * @param frameItem
     * @param topLeftSlot
     * @param bottomRightSlot
     */
    public static void createFrames(Inventory inv, ItemStack frameItem, int topLeftSlot, int bottomRightSlot) {
        // making sure item doesn't have display name
        ItemMeta itemMeta = frameItem.getItemMeta();
        itemMeta.setDisplayName(" ");
        frameItem.setItemMeta(itemMeta);

        // top row
        for (int i = topLeftSlot; i < topLeftSlot + 9; i++) {
            inv.setItem(i, frameItem);
        }

        // bottom row
        for (int i = bottomRightSlot; i > bottomRightSlot - 9; i--) {
            inv.setItem(i, frameItem);
        }

    }

    /**
     * Set a specific ItemStack at a slot in the GUI.
     *
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
     * Update the lore of an item in a GUI
     *
     * @param inv
     * @param rawSlot
     * @param newLore
     */
    public static void updateLore(Inventory inv, int rawSlot, List<String> newLore) {
        ItemStack itemStack = inv.getItem(rawSlot);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(newLore);
        itemStack.setItemMeta(itemMeta);
    }

    /**
     * Update the display name of an item in a GUI
     *
     * @param inv
     * @param rawSlot
     * @param name
     */
    public static void updateDisplayName(Inventory inv, int rawSlot, String name) {
        ItemStack itemStack = inv.getItem(rawSlot);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
    }


    /**
     * Set the receiver's skull in the GUI
     *
     * @param inv
     * @param player
     * @param slot
     */
    public static void setPlayerSkull(Inventory inv, OfflinePlayer player, int slot) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwningPlayer(player);
        skullMeta.setDisplayName(ChatColor.DARK_GRAY + player.getName());
        skullMeta.setLore(Arrays.asList(
                ChatColor.GRAY + "The Receiver of the Gift!",
                ChatColor.GRAY + "Current Status: Unknown"
        ));
        itemStack.setItemMeta(skullMeta);

        inv.setItem(slot, itemStack);
    }

    /**
     * Update the online or offline status of the player skull in the main GUI
     *
     * @param inv
     * @param player
     * @param slot
     */
    public static void updateStatus(EasyGifts easyGifts, Inventory inv, OfflinePlayer player, int slot) {
        ItemStack itemStack = inv.getItem(slot);
        ItemMeta itemMeta = itemStack.getItemMeta();

        Bukkit.getScheduler().runTaskLater(easyGifts, () -> {
            if (player.isOnline()) {
                itemMeta.setLore(Arrays.asList(
                        ChatColor.GRAY + "The Receiver of the Gift!",
                        ChatColor.GRAY + "Current Status: ONLINE"
                ));
            } else {
                itemMeta.setLore(Arrays.asList(
                        ChatColor.GRAY + "The Receiver of the Gift!",
                        ChatColor.GRAY + "Current Status: OFFLINE"
                ));
            }

            itemStack.setItemMeta(itemMeta);
        }, 20);

    }

    /**
     * Set a custom skull in the GUI
     *
     * @param inv
     * @param textureString
     * @param slot
     */
    public static void setCustomSkull(Inventory inv, String textureString, int slot) {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", textureString));
        Field field;
        try {
            field = skullMeta.getClass().getDeclaredField("profile");
            field.setAccessible(true);
            field.set(skullMeta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return;
        }
        itemStack.setItemMeta(skullMeta);

        inv.setItem(slot, itemStack);
    }

    /**
     * Return the chest item contents of the GUI back to the player
     *
     * @param gui
     * @param player
     */
    public static void returnItems(GUI gui, Inventory inventory, Player player) {
        if (gui.getChestInv() == null) {
            return;
        }

        if (gui.getType().equals(GUITypes.CHEST)) {
            for (int i = 0; i < 45; i++) {
                ItemStack itemStack = inventory.getItem(i);
                if (itemStack != null) {
                    player.getInventory().addItem(itemStack);
                }
            }
        } else {
            for (int i = 0; i < 45; i++) {
                ItemStack itemStack = gui.getChestInv().getItem(i);
                if (itemStack != null) {
                    player.getInventory().addItem(itemStack);
                }
            }
        }
    }
}
