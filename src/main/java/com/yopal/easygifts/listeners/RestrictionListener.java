package com.yopal.easygifts.listeners;

import com.yopal.easygifts.EasyGifts;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class RestrictionListener implements Listener {

    private NamespacedKey receiverKey;
    private NamespacedKey playerKey;
    public RestrictionListener(EasyGifts gifts) {
        this.receiverKey = new NamespacedKey(gifts, "receiverUUID");
        this.playerKey = new NamespacedKey(gifts, "playerUUID");
    }
    @EventHandler
    public void onEntityExplodeEvent(EntityExplodeEvent e) {
        // chest cannot be blown up

        for (Block block : e.blockList()) {
            if (block.getType().equals(Material.CHEST)) {
                Chest chest = (Chest) block.getState();
                PersistentDataContainer pdc = chest.getPersistentDataContainer();

                if (!pdc.isEmpty()) {
                    e.setCancelled(true);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        // chest cannot be broken by player except if they have permission

        if (!e.getBlock().getType().equals(Material.CHEST)) {
            return;
        }
        if (!(e.getBlock().getState() instanceof Chest)) {
            return;
        }

        Chest chest = (Chest) e.getBlock().getState();
        PersistentDataContainer pdc = chest.getPersistentDataContainer();
        UUID uuid = e.getPlayer().getUniqueId();

        String playerUUID = pdc.get(playerKey, PersistentDataType.STRING);
        String receiverUUID = pdc.get(receiverKey, PersistentDataType.STRING);

        if (e.getPlayer().hasPermission("ezg.breakChest")) {
            removeHologram(uuid, chest.getLocation());
            return;
        }

        if (playerUUID != null) {
            if (playerUUID.equals(uuid.toString())) {
                removeHologram(uuid, chest.getLocation());
                return;
            }
        }

        if (receiverUUID != null) {
            if (receiverUUID.equals(uuid.toString())) {
                removeHologram(uuid, chest.getLocation());
                return;
            }
        }

        if (receiverUUID == null && playerUUID == null) {
            return;
        }

        e.setCancelled(true);

    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        // gift paper cannot be dropped unless they have permission

        ItemStack itemStack = e.getItemDrop().getItemStack();
        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();

        if (e.getPlayer().hasPermission("ezg.admin")) {
            return;
        }

        if (pdc.has(receiverKey, PersistentDataType.STRING)) {
            e.getItemDrop().remove();
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        // gift cannot be clicked on unless they have permission

        ItemStack itemStack = e.getCurrentItem();
        if (itemStack == null) {
            return;
        }

        if (e.getWhoClicked().hasPermission("ezg.admin")) {
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return;
        }

        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();

        if (pdc.has(receiverKey, PersistentDataType.STRING)) {
            switch (e.getInventory().getType()) {
                case CHEST:
                    e.getCurrentItem().setAmount(0);
                    break;
                case ANVIL:
                    e.getCurrentItem().setAmount(0);
                    break;
                case SHULKER_BOX:
                    e.getCurrentItem().setAmount(0);
                    break;
            }
        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {
        if (e.getDrops().isEmpty()) {
            return;
        }

        for (ItemStack itemStack : e.getDrops()) {
            ItemMeta itemMeta = itemStack.getItemMeta();
            PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
            if (pdc.has(receiverKey, PersistentDataType.STRING)) {
                itemStack.setAmount(0);
            }
        }
    }

    @EventHandler
    public void onBlockExplodeEvent(BlockExplodeEvent e) {
        // chest cannot be blown up

        for (Block block : e.blockList()) {
            if (block.getType().equals(Material.CHEST)) {
                Chest chest = (Chest) block.getState();
                PersistentDataContainer pdc = chest.getPersistentDataContainer();

                if (!pdc.isEmpty()) {
                    e.setCancelled(true);
                    break;
                }
            }
        }
    }

    public void removeHologram(UUID uuid, Location chestLoc) {
        Player player = Bukkit.getPlayer(uuid);
        for (Entity entity : player.getNearbyEntities(10,10,10)) {
            PersistentDataContainer pdc = entity.getPersistentDataContainer();
            String receiverUUID = pdc.get(receiverKey, PersistentDataType.STRING);
            String playerUUID = pdc.get(playerKey, PersistentDataType.STRING);

            if (entity.getType().equals(EntityType.ARMOR_STAND)) {
                if (receiverUUID != null || playerUUID != null) {

                    if (receiverUUID.equals(uuid.toString()) || playerUUID.equals(uuid.toString())){

                        if (entity.getLocation().getBlock().equals(chestLoc.getBlock())) {

                            entity.remove();

                        }

                    }

                }

            }

        }

    }
}
