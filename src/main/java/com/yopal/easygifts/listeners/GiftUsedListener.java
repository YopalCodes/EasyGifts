package com.yopal.easygifts.listeners;

import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.utils.PlayerInteract;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.sound.midi.Receiver;
import java.util.UUID;

public class GiftUsedListener implements Listener {

    private NamespacedKey receiverKey;

    private NamespacedKey playerKey;

    public GiftUsedListener(EasyGifts gifts) {
        this.receiverKey = new NamespacedKey(gifts, "receiverUUID");
        this.playerKey = new NamespacedKey(gifts, "playerUUID");
    }

    @EventHandler
    public void onPlayerInteractWithGift(PlayerInteractEvent e) {
        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        if (!e.getClickedBlock().getType().equals(Material.CHEST)) {
            return;
        }

        Player player = e.getPlayer();
        Chest chest = (Chest) e.getClickedBlock().getState();
        PersistentDataContainer blockPDC = chest.getPersistentDataContainer();

        String receiverStringUUID = blockPDC.get(receiverKey, PersistentDataType.STRING);
        String playerStringUUID = blockPDC.get(playerKey, PersistentDataType.STRING);

        if (receiverStringUUID == null || playerStringUUID == null) {
            return;
        }

        // if opened by receiver
        if (receiverStringUUID.equals(player.getUniqueId().toString())) {
            Player sender = Bukkit.getPlayer(UUID.fromString(playerStringUUID));
            blockPDC.remove(receiverKey);
            blockPDC.remove(playerKey);
            chest.update();

            PlayerInteract.sendMessage(player.getUniqueId(), "Say thanks to " + sender.getName() + " as they have presented you with this gift!");

            // remove the hologram
            removeHologram(player.getUniqueId(), chest.getLocation());

            return;
        }

        // if opened by player
        if (playerStringUUID.equals(player.getUniqueId().toString())) {
            return;
        }

        // if player is admin then can be opened
        if (player.hasPermission("ezg.admin")) {
            return;
        }

        e.setCancelled(true);

    }

    public void removeHologram(UUID uuid, Location chestLoc) {
        Player player = Bukkit.getPlayer(uuid);
        for (Entity entity : player.getNearbyEntities(10,10,10)) {
            PersistentDataContainer pdc = entity.getPersistentDataContainer();
            String receiverUUID = pdc.get(receiverKey, PersistentDataType.STRING);

            if (entity.getType().equals(EntityType.ARMOR_STAND)) {
                if (receiverUUID != null) {

                    if (receiverUUID.equals(uuid.toString())){

                        if (entity.getLocation().getBlock().equals(chestLoc.getBlock())) {

                            entity.remove();

                        }

                    }

                }

            }

        }

    }



}
