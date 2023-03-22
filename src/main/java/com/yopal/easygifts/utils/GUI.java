package com.yopal.easygifts.utils;

import com.yopal.easygifts.enums.GUITypes;
import com.yopal.easygifts.enums.ParticleTypes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class GUI {

    private int months = 0;
    private int days = 0;
    private int seconds = 0;
    private GUITypes guiType = GUITypes.MAIN;
    private ParticleTypes pType = ParticleTypes.DEFAULT;
    private Player player;
    private OfflinePlayer receiver;


    public GUI(Player player, OfflinePlayer receiver) {
        this.player = player;
        this.receiver = receiver;
        openMainPage();
    }

    public void openMainPage() {
        guiType = GUITypes.MAIN;
        Inventory inv = Bukkit.createInventory(player, 45, PlayerInteract.returnPrefix() + "Main Page");

        PageUtil.createFrames(inv, new ItemStack(Material.GRAY_STAINED_GLASS_PANE), 0, 44);
        PageUtil.setPlayerSkull(inv, receiver, 13);

        // time section
        List<String> monthsDaysSeconds = Arrays.asList(
                ChatColor.GRAY + "Months: " + months,
                ChatColor.GRAY + "Days: " + days,
                ChatColor.GRAY + "Seconds: " + seconds
        );

        PageUtil.setItem(inv, ChatColor.DARK_GRAY + "Time", Material.CLOCK, (months != 0 && days != 0 && seconds != 0 ? monthsDaysSeconds : Arrays.asList(ChatColor.GRAY + "Immediately send the gift to the player!")), 29);

        // particle section
        PageUtil.setItem(inv, ChatColor.DARK_GRAY + "Particle Type", Material.BLAZE_ROD, Arrays.asList(
                ChatColor.GRAY + "Particle Type: " + pType.toString()
        ), 31);

        // chest section
        PageUtil.setItem(inv, ChatColor.DARK_GRAY + "Chest", Material.CHEST, Arrays.asList(
                ChatColor.GRAY + "Store the items for the gift!"
        ), 33);

        player.openInventory(inv);
    }

    public void openTimePage() {
        guiType = GUITypes.TIME;
        Inventory inv = Bukkit.createInventory(player, 27, PlayerInteract.returnPrefix() +  "Time Page");

        // frames
        PageUtil.createFrames(inv, new ItemStack(Material.GRAY_STAINED_GLASS_PANE), 0, 26);

        // barrier
        PageUtil.setItem(inv, ChatColor.RED + "GO BACK", Material.BARRIER, Arrays.asList(
                ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Click" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "To go back!"
        ), 0);

        // strings
        String leftClick = ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Left-Click" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
        String rightClick = ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Right-Click" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
        String reset = ChatColor.DARK_GRAY + "[" + ChatColor.RED + "Drop" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;

        // months
        PageUtil.setCustomSkull(inv, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzhkZWM0NjY2YjRjNjdkODc1OTcxNGM4NTcxNGJlNmVhNGUzOWZmOTYyODg0OWY5OGI1MTRlZGYxYzNlNDY4MCJ9fX0=", 11);

        PageUtil.updateDisplayName(inv, 11, ChatColor.BOLD + "MONTHS " + ChatColor.GRAY + ChatColor.BOLD + "-> " + ChatColor.GOLD + ChatColor.BOLD + months);

        PageUtil.updateLore(inv, 11, Arrays.asList(
                leftClick + ChatColor.GRAY + "To INCREASE the MONTHS!",
                rightClick + ChatColor.GRAY + "To DECREASE the MONTHS!",
                reset + "To RESET the MONTHS!"
        ));


        // days
        PageUtil.setCustomSkull(inv, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmE2NjE0MTlkZTQ5ZmY0YTJjOTdiMjdmODY4MDE0ZmJkYWViOGRkN2Y0MzkyNzc3ODMwYjI3MTRjYWFmZDFmIn19fQ==", 13);

        PageUtil.updateDisplayName(inv, 13, ChatColor.BOLD + "DAYS " + ChatColor.GRAY + ChatColor.BOLD + "-> " + ChatColor.GOLD + ChatColor.BOLD + days);

        PageUtil.updateLore(inv, 13, Arrays.asList(
                leftClick + "To INCREASE the DAYS!",
                rightClick + "To DECREASE the DAYS!",
                reset + "To RESET the DAYS!"
        ));


        // seconds
        PageUtil.setCustomSkull(inv, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDcxMDEzODQxNjUyODg4OTgxNTU0OGI0NjIzZDI4ZDg2YmJiYWU1NjE5ZDY5Y2Q5ZGJjNWFkNmI0Mzc0NCJ9fX0=", 15);

        PageUtil.updateDisplayName(inv, 15, ChatColor.BOLD + "SECONDS " + ChatColor.GRAY + ChatColor.BOLD + "-> " + ChatColor.GOLD + ChatColor.BOLD + seconds);

        PageUtil.updateLore(inv, 15, Arrays.asList(
                leftClick + "To INCREASE the SECONDS!",
                rightClick + "To DECREASE the SECONDS!",
                reset + ChatColor.GRAY + "To RESET the SECONDS!"
        ));

        player.openInventory(inv);
    }

    // getters
    public Player getPlayer() { return player; };
    public OfflinePlayer getReceiver() { return receiver; }
    public GUITypes getType() { return guiType; }
    public int getMonths() { return months; }
    public int getDays() { return days; }
    public int getSeconds() { return seconds; }

    // setters
    public void setMonths(int time) { months = time; }
    public void setDays(int time) { days = time; }
    public void setSeconds(int time) { seconds = time; }
    public void setpType(ParticleTypes type) { pType = type; }


}
