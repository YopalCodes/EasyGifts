package com.yopal.easygifts.utils;

import com.yopal.easygifts.enums.ParticleTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GUI {

    private int months = 0;
    private int days = 0;
    private int seconds = 0;
    private ParticleTypes pType = ParticleTypes.DEFAULT;
    private Player player;
    private OfflinePlayer receiver;


    public GUI(Player player, OfflinePlayer receiver) {
        this.player = player;
        this.receiver = receiver;
        openMainPage();
    }

    public void openMainPage() {
        Inventory inv = Bukkit.createInventory(player, 53, PlayerInteract.returnPrefix());

        PageUtil.createFrames(inv, new ItemStack(Material.GRAY_STAINED_GLASS_PANE), 0, 44);
        PageUtil.setSkull(inv, receiver, 13);

        // time section
        List<String> monthsDaysSeconds = Arrays.asList(
                ChatColor.GRAY + "Months: " + months,
                ChatColor.GRAY + "Days: " + days,
                ChatColor.GRAY + "Seconds: " + seconds
        );

        PageUtil.setItem(inv, ChatColor.DARK_GRAY + "Time", Material.CLOCK, (months == 0 && days == 0 && seconds == 0 ? monthsDaysSeconds : Arrays.asList(ChatColor.GRAY + "Immediately send to the player!")), 29);

        // particle section
        PageUtil.setItem(inv, ChatColor.DARK_GRAY + "Particle Type", Material.BLAZE_ROD, Arrays.asList(ChatColor.GRAY + "Particle Animation that Plays When GUI is Opened"), 31);

        // chest section
        PageUtil.setItem(inv, ChatColor.DARK_GRAY + "Chest", Material.CHEST, Arrays.asList(ChatColor.GRAY + "Store the Items for the Gift"), 33);
    }

    // getters
    public Player getPlayer() { return player; };
    public OfflinePlayer getReceiver() { return receiver; }

}
