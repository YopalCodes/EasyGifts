package com.yopal.easygifts.utils;

import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;
import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.enums.GiftTypes;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.common.returnsreceiver.qual.This;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GiftPaper {
    private UUID playerUUID;

    private NamespacedKey receiverKey;
    private NamespacedKey giftTypeKey;
    private NamespacedKey receiverLocationKey;
    private UUID receiverUUID;
    private ItemStack giftPaper;
    private ItemMeta paperMeta;
    private String[] args;

    public GiftPaper(EasyGifts gifts, UUID playerUUID, UUID receiverUUID, String[] args) {
        this.playerUUID = playerUUID;
        this.receiverUUID = receiverUUID;
        this.args = args;
        this.giftTypeKey= new NamespacedKey(gifts, "giftType");
        this.receiverKey = new NamespacedKey(gifts, "receiverUUID");
        this.receiverLocationKey = new NamespacedKey(gifts, "receiverLoc");
        this.giftPaper = new ItemStack(Material.PAPER, 1);
        this.paperMeta = giftPaper.getItemMeta();


    }

    public void givePaper(GiftTypes giftType) {

        Player player = Bukkit.getPlayer(playerUUID);
        OfflinePlayer offlinePlayer = Bukkit.getPlayer(receiverUUID);
        PersistentDataContainer paperPDC = paperMeta.getPersistentDataContainer();
        paperPDC.set(receiverKey, PersistentDataType.STRING, receiverUUID.toString());

        // setting gift type
        paperPDC.set(giftTypeKey, PersistentDataType.STRING, giftType.toString());

        // setting receiver location - more for request type gift
        Player receiver = offlinePlayer.getPlayer();
        if (receiver == null) {
            return;
        }
        Location targetLoc = receiver.getLocation().getBlock().getLocation();
        paperPDC.set(receiverLocationKey, PersistentDataType.STRING, targetLoc.toString());

        paperMeta.setDisplayName(ChatColor.BOLD + ChatColor.GRAY.toString() +  "For: " + ChatColor.GOLD + offlinePlayer.getName() + ChatColor.DARK_GRAY + " | " + ChatColor.GRAY + "GiftType: " + giftType);
        paperMeta.setLore(Arrays.asList(ChatColor.GRAY + "Shift Right-click a chest you've placed to make your gift!", ChatColor.GRAY + "This paper cannot be interacted with..."));
        giftPaper.setItemMeta(paperMeta);

        if (!player.getInventory().addItem(giftPaper).isEmpty()) {
            PlayerInteract.sendMessage(player.getUniqueId(), ChatColor.RED + "Couldn't give you the paper! Inventory full!");
            return;
        }
    }




}
