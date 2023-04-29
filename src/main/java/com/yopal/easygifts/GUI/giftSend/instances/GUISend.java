package com.yopal.easygifts.GUI.giftSend.instances;

import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.GUI.enums.GUITypes;
import com.yopal.easygifts.utils.PageUtil;
import com.yopal.easygifts.utils.PlayerInteract;
import com.yopal.easygifts.utils.TimeConvert;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class GUISend {

    private int days = 0;
    private int hours = 0;
    private int minutes = 0;
    private GUITypes guiType = GUITypes.MAIN;
    private Inventory currentInv;
    private String chestTitle;
    private String personalizedMessage;
    private Inventory chestInv;
    private Player player;
    private OfflinePlayer receiver;
    private EasyGifts easyGifts;

    public GUISend(EasyGifts easyGifts, Player player, OfflinePlayer receiver) {
        this.easyGifts = easyGifts;
        this.player = player;
        this.receiver = receiver;

        chestTitle = "Gift - " + player.getName();

        openMainPage();
    }

    public void openMainPage() {
        guiType = GUITypes.MAIN;
        Inventory inv = Bukkit.createInventory(player, 45, PlayerInteract.returnPrefix() + "Main Page");
        currentInv = inv;

        PageUtil.createFrames(inv, new ItemStack(Material.GRAY_STAINED_GLASS_PANE), 0, 44);
        PageUtil.setPlayerSkull(inv, receiver, 13);
        PageUtil.updateStatus(easyGifts, inv, receiver, 13);

        // chest section
        PageUtil.setItem(inv, ChatColor.DARK_GRAY + "Chest", Material.CHEST, Arrays.asList(
                ChatColor.GRAY + "Store the items for the gift!"
        ), 20);
        
        // time section
        List<String> dayshoursminutes = Arrays.asList(
                TimeConvert.getFutureDate(days, hours, minutes),
                ChatColor.GRAY + "Days: " + days,
                ChatColor.GRAY + "Hours: " + hours,
                ChatColor.GRAY + "Minutes: " + minutes
        );

        PageUtil.setItem(inv, ChatColor.DARK_GRAY + "Time", Material.CLOCK, (days == 0 && hours == 0 && minutes == 0 ? Arrays.asList(ChatColor.GRAY + "Immediately send the gift to the player!") : dayshoursminutes), 22);

        // chest title section
        PageUtil.setItem(inv, ChatColor.DARK_GRAY + "Chest Title", Material.OAK_SIGN, Arrays.asList(
                ChatColor.GRAY + "Title: " + chestTitle
        ), 24);

        // personalized message section
        PageUtil.setItem(inv, ChatColor.DARK_GRAY + "Personalized Message", Material.PAPER, Arrays.asList(
                ChatColor.GRAY + "Message: " + (personalizedMessage == null ? "NONE" : personalizedMessage)
        ), 30);

        // confirm
        PageUtil.setCustomSkull(inv, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTc5YTVjOTVlZTE3YWJmZWY0NWM4ZGMyMjQxODk5NjQ5NDRkNTYwZjE5YTQ0ZjE5ZjhhNDZhZWYzZmVlNDc1NiJ9fX0=", 32);
        PageUtil.updateDisplayName(inv, 32, ChatColor.GREEN + ChatColor.BOLD.toString() + "CONFIRM");
        PageUtil.updateLore(inv, 32, Arrays.asList(
                (checkChestInvEmpty() ? ChatColor.GRAY + "Please make put items into the gift first!" : ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Left-Click" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "To Send Out the Gift!")
        ));


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
        PageUtil.setCustomSkull(inv, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmE2NjE0MTlkZTQ5ZmY0YTJjOTdiMjdmODY4MDE0ZmJkYWViOGRkN2Y0MzkyNzc3ODMwYjI3MTRjYWFmZDFmIn19fQ==", 11);

        PageUtil.updateDisplayName(inv, 11, ChatColor.BOLD + "DAYS " + ChatColor.GRAY + ChatColor.BOLD + "-> " + ChatColor.GOLD + ChatColor.BOLD + days);

        PageUtil.updateLore(inv, 11, Arrays.asList(
                leftClick + ChatColor.GRAY + "To INCREASE the Days!",
                rightClick + ChatColor.GRAY + "To DECREASE the Days!",
                reset + "To RESET the Days!"
        ));


        // hours
        PageUtil.setCustomSkull(inv, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2JhOWMzM2E5NWZhMWU1MTlmODVhNDFjYTU2Nzk5Mzg0ZGI0MWZlN2UxZDdhNzkxNzUxZWNlOWJiYWU1ZDI3ZiJ9fX0=", 13);

        PageUtil.updateDisplayName(inv, 13, ChatColor.BOLD + "HOURS " + ChatColor.GRAY + ChatColor.BOLD + "-> " + ChatColor.GOLD + ChatColor.BOLD + hours);

        PageUtil.updateLore(inv, 13, Arrays.asList(
                leftClick + "To INCREASE the Hours!",
                rightClick + "To DECREASE the Hours!",
                reset + "To RESET the Hours!"
        ));


        // minutes
        PageUtil.setCustomSkull(inv, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzhkZWM0NjY2YjRjNjdkODc1OTcxNGM4NTcxNGJlNmVhNGUzOWZmOTYyODg0OWY5OGI1MTRlZGYxYzNlNDY4MCJ9fX0=", 15);

        PageUtil.updateDisplayName(inv, 15, ChatColor.BOLD + "MINUTES " + ChatColor.GRAY + ChatColor.BOLD + "-> " + ChatColor.GOLD + ChatColor.BOLD + minutes);

        PageUtil.updateLore(inv, 15, Arrays.asList(
                leftClick + "To INCREASE the Minutes!",
                rightClick + "To DECREASE the Minutes!",
                reset + ChatColor.GRAY + "To RESET the Minutes!"
        ));

        player.openInventory(inv);
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

    public boolean checkChestInvEmpty() {
        if (chestInv == null) {
            return true;
        }

        for (int i = 0; i < chestInv.getSize() - 9; i++) {
            if (chestInv.getItem(i) != null) {
                return false;
            }
        }

        return true;
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
    public String getChestTitle() { return chestTitle; }
    public String getPersonalizedMessage() { return personalizedMessage; }
    public String getFutureDate() { return TimeConvert.getFutureDate(days, hours, minutes); }

    // setters
    public void setDays(int time) { days = time; }
    public void setHours(int time) { hours = time; }
    public void setMinutes(int time) { minutes = time; }
    public void setChestInv(Inventory inv) { chestInv = inv; }
    public void setChestTitle(String string) { chestTitle = ChatColor.translateAlternateColorCodes('&', string); }
    public void setPersonalizedMessage(String string) { personalizedMessage = ChatColor.translateAlternateColorCodes('&', string); }
    public void setType(GUITypes guiType) { this.guiType = guiType; }

}
