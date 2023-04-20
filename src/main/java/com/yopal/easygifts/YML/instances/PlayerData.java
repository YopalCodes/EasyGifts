package com.yopal.easygifts.YML.instances;

import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.GUI.enums.ParticleTypes;
import com.yopal.easygifts.GUI.giftSend.instances.GUISend;
import com.yopal.easygifts.utils.TimeConvert;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.logging.Level;

public class PlayerData {
    
    private YamlConfiguration playerFile;
    private File file;
    
    public PlayerData(File file) {
        this.file = file;
        playerFile = YamlConfiguration.loadConfiguration(file);
    }

    public void addGift(EasyGifts easyGifts, GUISend gui) {
        String startPath = "gifts." + getNewMaxID();
        playerFile.set(startPath + ".senderUUID", gui.getPlayer().getUniqueId().toString());
        playerFile.set(startPath + ".chestTitle", gui.getChestTitle().toString());
        playerFile.set(startPath + ".personalizedMessage", gui.getPersonalizedMessage());
        playerFile.set(startPath + ".futureDate", gui.getFutureDate().replace("§7", "").toString());
        playerFile.set(startPath + ".particleType", gui.getpType().toString());

        for (int i = 0; i < 45; i++) {
            ItemStack itemStack = gui.getChestInv().getItem(i);
            playerFile.set(startPath + ".giftInventory." + i, itemStack);
        }

        saveFile(easyGifts);

    }

    public void saveFile(EasyGifts easyGifts) {

        try {
            playerFile.save(file);
        } catch (IOException e) {
            easyGifts.getLogger().log(Level.SEVERE, "giftData.yml couldn't be saved");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public List<ItemStack> getPlayerGifts() {

        List<ItemStack> playerGifts = new ArrayList<>();
        ConfigurationSection giftsSection = playerFile.getConfigurationSection("gifts.");

        if (giftsSection == null) {
            return playerGifts;
        }

        if (giftsSection.getKeys(false).isEmpty()) {
            return playerGifts;
        }

        for (String i : giftsSection.getKeys(false)) {
            ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

            UUID uuid = UUID.fromString(playerFile.getString("gifts." + i + ".senderUUID"));
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            skullMeta.setOwningPlayer(offlinePlayer);
            skullMeta.setDisplayName(ChatColor.GOLD + ChatColor.BOLD.toString() + "From: " + offlinePlayer.getName());

            skullMeta.setLore(Arrays.asList(
                ChatColor.DARK_GRAY + "====================================",
                ChatColor.GRAY + "GIFT ID: " + i,
                ChatColor.GRAY + "OPEN DURING: " + playerFile.get("gifts." + i + ".futureDate").toString(),
                ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Left-Click" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "To Open With the Animation!",
                    ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Right-Click" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "To Open Without the Animation!",
                ChatColor.DARK_GRAY + "===================================="
            ));

            itemStack.setItemMeta(skullMeta);

            playerGifts.add(itemStack);
        }

        return playerGifts;
    }

    private int getNewMaxID() {
        ConfigurationSection giftsSection = playerFile.getConfigurationSection("gifts.");

        if (giftsSection == null) {
            return 0;
        }

        if (giftsSection.getKeys(false).isEmpty()) {
            return 0;
        }

        List<Integer> intList = new ArrayList<>();

        for (String string : giftsSection.getKeys(false)) {
            intList.add(Integer.parseInt(string));
        }

        return Collections.max(intList) + 1;

    }
    
    
    // getters
    public UUID getPlayerUUID() {
        return UUID.fromString(file.getName().replace(".yml", ""));
    }

    public UUID getSenderUUID() {
        String string = playerFile.getString("gifts." + getNewMaxID() + ".senderUUID");
        return UUID.fromString(string);
    }

    public String getChestTitle() {
        return playerFile.getString("gifts." + getNewMaxID() + ".chestTitle");
    }

    public String getPersonalizedMessage(UUID playerUUID) {
        if (playerFile.get(playerUUID + ".personalizedMessage") != null) {
            return playerFile.get("gifts." + getNewMaxID() + ".personalizedMessage").toString();
        } else {
            return null;
        }
    }

    public Date getFutureDate(UUID playerUUID) throws ParseException {
        return TimeConvert.getDateFromFormat(playerFile.getString("gifts." + getNewMaxID() + ".futureDate"));
    }

    public ParticleTypes getParticleType(UUID playerUUID) {
        return ParticleTypes.valueOf(playerFile.getString("gifts." + getNewMaxID() + ".particleType"));
    }

    public Inventory getInventory(UUID playerUUID) {
        Inventory inv = Bukkit.createInventory(Bukkit.getPlayer(playerUUID), 45);

        for (int i = 0; i < 45; i++) {
            ItemStack itemStack = playerFile.getItemStack(playerUUID + ".giftInventory." + i);

            if (itemStack == null) {
                inv.addItem(new ItemStack(Material.AIR));
            }

            inv.addItem(itemStack);
        }

        return inv;
    }
}