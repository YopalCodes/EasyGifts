package com.yopal.easygifts.utils;

import com.yopal.easygifts.EasyGifts;
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

    private int days = 0;
    private int hours = 0;
    private int minutes = 0;
    private GUITypes guiType = GUITypes.MAIN;
    private Inventory currentInv;
    private ParticleTypes pType = ParticleTypes.DEFAULT;
    private Inventory chestInv;
    private Player player;
    private OfflinePlayer receiver;
    private EasyGifts easyGifts;

    public GUI(EasyGifts easyGifts, Player player, OfflinePlayer receiver) {
        this.easyGifts = easyGifts;
        this.player = player;
        this.receiver = receiver;
        openMainPage();
    }

    public void openMainPage() {
        guiType = GUITypes.MAIN;
        Inventory inv = Bukkit.createInventory(player, 45, PlayerInteract.returnPrefix() + "Main Page");
        currentInv = inv;

        PageUtil.createFrames(inv, new ItemStack(Material.GRAY_STAINED_GLASS_PANE), 0, 44);
        PageUtil.setPlayerSkull(inv, receiver, 13);
        PageUtil.updateStatus(easyGifts, inv, receiver, 13);

        // time section
        List<String> dayshoursminutes = Arrays.asList(
                TimeConvert.getFutureDate(days, hours, minutes),
                ChatColor.GRAY + "Days: " + days,
                ChatColor.GRAY + "Hours: " + hours,
                ChatColor.GRAY + "Minutes: " + minutes
        );

        PageUtil.setItem(inv, ChatColor.DARK_GRAY + "Time", Material.CLOCK, (days == 0 && hours == 0 && minutes == 0 ? Arrays.asList(ChatColor.GRAY + "Immediately send the gift to the player!") : dayshoursminutes), 29);

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
        currentInv = inv;

        // frames
        PageUtil.createFrames(inv, new ItemStack(Material.GRAY_STAINED_GLASS_PANE), 0, 26);

        // barrier
        PageUtil.setItem(inv, ChatColor.RED + ChatColor.BOLD.toString() + "GO BACK", Material.BARRIER, Arrays.asList(
                ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Click" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "To Go Back!"
        ), 0);

        // strings
        String leftClick = ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Left-Click" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
        String rightClick = ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Right-Click" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;
        String reset = ChatColor.DARK_GRAY + "[" + ChatColor.RED + "Drop" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY;

        // days
        PageUtil.setCustomSkull(inv, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzhkZWM0NjY2YjRjNjdkODc1OTcxNGM4NTcxNGJlNmVhNGUzOWZmOTYyODg0OWY5OGI1MTRlZGYxYzNlNDY4MCJ9fX0=", 11);

        PageUtil.updateDisplayName(inv, 11, ChatColor.BOLD + "DAYS " + ChatColor.GRAY + ChatColor.BOLD + "-> " + ChatColor.GOLD + ChatColor.BOLD + days);

        PageUtil.updateLore(inv, 11, Arrays.asList(
                leftClick + ChatColor.GRAY + "To INCREASE the Days!",
                rightClick + ChatColor.GRAY + "To DECREASE the Days!",
                reset + "To RESET the Days!"
        ));


        // hours
        PageUtil.setCustomSkull(inv, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmE2NjE0MTlkZTQ5ZmY0YTJjOTdiMjdmODY4MDE0ZmJkYWViOGRkN2Y0MzkyNzc3ODMwYjI3MTRjYWFmZDFmIn19fQ==", 13);

        PageUtil.updateDisplayName(inv, 13, ChatColor.BOLD + "HOURS " + ChatColor.GRAY + ChatColor.BOLD + "-> " + ChatColor.GOLD + ChatColor.BOLD + hours);

        PageUtil.updateLore(inv, 13, Arrays.asList(
                leftClick + "To INCREASE the Hours!",
                rightClick + "To DECREASE the Hours!",
                reset + "To RESET the Hours!"
        ));


        // minutes
        PageUtil.setCustomSkull(inv, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDcxMDEzODQxNjUyODg4OTgxNTU0OGI0NjIzZDI4ZDg2YmJiYWU1NjE5ZDY5Y2Q5ZGJjNWFkNmI0Mzc0NCJ9fX0=", 15);

        PageUtil.updateDisplayName(inv, 15, ChatColor.BOLD + "MINUTES " + ChatColor.GRAY + ChatColor.BOLD + "-> " + ChatColor.GOLD + ChatColor.BOLD + minutes);

        PageUtil.updateLore(inv, 15, Arrays.asList(
                leftClick + "To INCREASE the Minutes!",
                rightClick + "To DECREASE the Minutes!",
                reset + ChatColor.GRAY + "To RESET the Minutes!"
        ));

        player.openInventory(inv);
    }

    public void openParticlePage() {
        guiType = GUITypes.PARTICLE;
        Inventory inv = Bukkit.createInventory(player, 27, PlayerInteract.returnPrefix() +  "Particle Page");
        currentInv = inv;

        // frames
        PageUtil.createFrames(inv, new ItemStack(Material.GRAY_STAINED_GLASS_PANE), 0, 26);

        // barrier
        PageUtil.setItem(inv, ChatColor.RED + ChatColor.BOLD.toString() + "GO BACK", Material.BARRIER, Arrays.asList(
                ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Click" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "To Go Back!"
        ), 0);

        // particle items
        updateParticlePage(inv);

        player.openInventory(inv);
    }

    public void updateParticlePage(Inventory inv) {
        String selected = ChatColor.GREEN + ChatColor.BOLD.toString() + "SELECTED";
        String notSelected = ChatColor.RED + ChatColor.BOLD.toString() + "NOT SELECTED";
        String click = ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Click" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "to Select the Particle Type!";
        String italicsBold = ChatColor.YELLOW.toString() + ChatColor.BOLD + ChatColor.ITALIC;

        PageUtil.setItem(
                inv,
                italicsBold + "DEFAULT " + ChatColor.GRAY + ChatColor.BOLD + "-> " + (pType == ParticleTypes.DEFAULT ? selected : notSelected),
                Material.CHEST,
                Arrays.asList(click),
                9
        );

        PageUtil.setItem(
                inv,
                italicsBold + "FIRE " + ChatColor.GRAY + ChatColor.BOLD + "-> " + (pType == ParticleTypes.FIRE ? selected : notSelected),
                Material.FLINT_AND_STEEL,
                Arrays.asList(click),
                11
        );

        PageUtil.setItem(
                inv,
                italicsBold + "ENDER " + ChatColor.GRAY + ChatColor.BOLD + "-> " + (pType == ParticleTypes.ENDER ? selected : notSelected),
                Material.ENDER_PEARL,
                Arrays.asList(click),
                13
        );

        PageUtil.setItem(
                inv,
                italicsBold + "LOVE " + ChatColor.GRAY + ChatColor.BOLD + "-> " + (pType == ParticleTypes.LOVE ? selected : notSelected),
                Material.ARROW,
                Arrays.asList(click),
                15
        );

        PageUtil.setItem(
                inv,
                ChatColor.BOLD + ChatColor.YELLOW.toString() + "POOP " + ChatColor.GRAY + ChatColor.BOLD + "-> " + (pType == ParticleTypes.POOP ? selected : notSelected),
                Material.BROWN_DYE,
                Arrays.asList(click),
                17
        );
    }

    public void openChestPage() {
        guiType = GUITypes.CHEST;
        Inventory inv = Bukkit.createInventory(player, 54, PlayerInteract.returnPrefix() +  "Chest Page");

        if (chestInv != null) {
            inv.setContents(chestInv.getContents());
        } else {

            // frames
            PageUtil.createFrames(inv, new ItemStack(Material.GRAY_STAINED_GLASS_PANE), 45, 53);

            // barrier
            PageUtil.setItem(inv, ChatColor.RED + ChatColor.BOLD.toString() + "GO BACK", Material.BARRIER, Arrays.asList(
                    ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Click" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "To Go Back!"
            ), 45);
        }

        currentInv = inv;
        player.openInventory(inv);
        chestInv = inv;

    }

    // getters
    public Player getPlayer() { return player; };
    public OfflinePlayer getReceiver() { return receiver; }
    public GUITypes getType() { return guiType; }
    public int getDays() { return days; }
    public int getHours() { return hours; }
    public int getMinutes() { return minutes; }
    public Inventory getCurrentInv() { return currentInv; }
    public Inventory getChestInv() { return chestInv; }

    // setters
    public void setDays(int time) { days = time; }
    public void setHours(int time) { hours = time; }
    public void setMinutes(int time) { minutes = time; }
    public void setpType(ParticleTypes type) { pType = type; }
    public void setChestInv(Inventory inv) { chestInv = inv; }


}
