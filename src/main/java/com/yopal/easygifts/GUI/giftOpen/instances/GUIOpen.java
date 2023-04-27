package com.yopal.easygifts.GUI.giftOpen.instances;

import com.yopal.easygifts.YML.instances.PlayerData;
import com.yopal.easygifts.YML.managers.GiftDataManager;
import com.yopal.easygifts.GUI.enums.GUITypes;
import com.yopal.easygifts.utils.PageUtil;
import com.yopal.easygifts.utils.PlayerInteract;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class GUIOpen {

    private Player player;

    private int page = 0;
    private GUITypes guiType;
    private boolean canGoToNextPage;
    private Inventory currentInv;

    public GUIOpen(Player player) {
        this.player = player;

        openMainPage();

    }

    public void openMainPage() {

        Inventory inv = Bukkit.createInventory(player, 45, PlayerInteract.returnPrefix() + "Main Page - Page " + (page + 1));
        PageUtil.createFrames(inv, new ItemStack(Material.GRAY_STAINED_GLASS_PANE), 0, 44);
        currentInv = inv;
        guiType = GUITypes.CHANGEPAGE;


        // left
        if (page != 0) {
            PageUtil.setItem(inv, ChatColor.GREEN + ChatColor.BOLD.toString() + "PAGE LEFT", Material.GREEN_STAINED_GLASS_PANE, Arrays.asList(
                    ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Click" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "To Move A Page Left!"
            ), 0);
        } else {
            PageUtil.setItem(inv, ChatColor.RED + ChatColor.BOLD.toString() + "PAGE LEFT", Material.RED_STAINED_GLASS_PANE, Arrays.asList(
                    ChatColor.RED + "Cannot move a page left!"
            ), 0);
        }

        // refresh
        PageUtil.setCustomSkull(inv, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDc1ZDNkYjAzZGMyMWU1NjNiMDM0MTk3ZGE0MzViNzllY2ZlZjRiOGUyZWNkYjczMGUzNzBjMzE2NjI5ZDM2ZiJ9fX0=", 4);
        PageUtil.updateDisplayName(inv, 4, ChatColor.WHITE + ChatColor.BOLD.toString() + "REFRESH THE PAGE");
        PageUtil.updateLore(inv, 4, Arrays.asList(
                ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Click" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "To Refresh the Page!"
        ));

        PlayerData playerData = GiftDataManager.getPlayerData(player.getUniqueId());
        List<ItemStack> itemStackList = playerData.getPlayerGifts();

        boolean nextPage = true;

        for (int i = page * 27; i < ((page + 1) * 27); i++) {
            try {
                inv.setItem(inv.firstEmpty(), itemStackList.get(i));
            } catch (IndexOutOfBoundsException e) {
                nextPage = false;
                break;
            }
        }

        // right
        if (nextPage) {
            PageUtil.setItem(inv, ChatColor.GREEN + ChatColor.BOLD.toString() + "PAGE RIGHT", Material.GREEN_STAINED_GLASS_PANE, Arrays.asList(
                    ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Click" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "To Move A Page Right!"
            ), 8);
        } else {
            PageUtil.setItem(inv, ChatColor.RED + ChatColor.BOLD.toString() + "PAGE RIGHT", Material.RED_STAINED_GLASS_PANE, Arrays.asList(
                    ChatColor.RED + "Cannot move a page right!"
            ), 8);
        }

        player.openInventory(inv);
    }

    public void openGiftPage(PlayerData playerData, int giftID) {
        Inventory inv = playerData.getGiftInventory(player.getUniqueId(), giftID);
        currentInv = inv;
        guiType = GUITypes.GIFTPAGE;

        // back
        PageUtil.setCustomSkull(inv, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTQ4ZDdkMWUwM2UxYWYxNDViMDEyNWFiODQxMjg1NjcyYjQyMTI2NWRhMmFiOTE1MDE1ZjkwNTg0MzhiYTJkOCJ9fX0=", 45);
        PageUtil.updateDisplayName(inv, 45, ChatColor.RED + ChatColor.BOLD.toString() + "GO BACK");
        PageUtil.updateLore(inv, 45, Arrays.asList(
                ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Click" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "To Go Back to your List of Gifts!"
        ));

        // collect
        PageUtil.setCustomSkull(inv, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmZlYzNkMjVhZTBkMTQ3YzM0MmM0NTM3MGUwZTQzMzAwYTRlNDhhNWI0M2Y5YmI4NThiYWJmZjc1NjE0NGRhYyJ9fX0=", 53);
        PageUtil.updateDisplayName(inv, 53, ChatColor.RED + ChatColor.BOLD.toString() + "COLLECT");
        PageUtil.updateLore(inv, 53, Arrays.asList(
                (checkHasSpace() ? ChatColor.DARK_GRAY + "[" + ChatColor.GREEN + "Click" + ChatColor.DARK_GRAY + "] " + ChatColor.GRAY + "To Collect your Items!" : ChatColor.RED + "You don't have enough space to collect these items!")
        ));

        String personalizedMsg = playerData.getPersonalizedMessage(player.getUniqueId(), giftID);
        if (personalizedMsg != null) {
            player.sendMessage(personalizedMsg);
        }

        player.openInventory(inv);

    }

    // setters

    public void addPage(Material material) {
        if (material.equals(Material.RED_STAINED_GLASS_PANE)) {
            return;
        }

        page ++;
    }

    public void subtractPage(Material material) {
        if (material.equals(Material.RED_STAINED_GLASS_PANE)) {
            return;
        }

        page --;
    }

    public boolean checkHasSpace() {
        if (player.getInventory().addItem(currentInv.getContents()).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    // getters
    public Player getPlayer() { return player; }
    public GUITypes getType() { return guiType; }


    public Inventory getCurrentInv() { return currentInv;
    }
}
