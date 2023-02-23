package com.yopal.easygifts.listeners;

import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.commands.AcceptCommand;
import com.yopal.easygifts.enums.GiftTypes;
import com.yopal.easygifts.utils.GiftTransport;
import com.yopal.easygifts.utils.PlayerInteract;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.*;
import org.bukkit.block.Chest;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.regex.qual.Regex;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GiftCreateListener implements Listener {
    private EasyGifts gifts;

    private NamespacedKey receiverKey;

    private NamespacedKey giftTypeKey;
    private NamespacedKey receiverLocationKey;

    private NamespacedKey playerKey;
    public GiftCreateListener(EasyGifts gifts) {
        this.gifts = gifts;
        this.giftTypeKey = new NamespacedKey(gifts, "giftType");
        this.playerKey = new NamespacedKey(gifts, "playerUUID");
        this.receiverKey = new NamespacedKey(gifts, "receiverUUID");
        this.receiverLocationKey = new NamespacedKey(gifts, "receiverLoc");

    }


    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType().equals(Material.CHEST)) {
            if (!(e.getBlock().getState() instanceof Chest)) {
                return;
            }

            Chest chest = (Chest) e.getBlock().getState();
            PersistentDataContainer blockPDC = chest.getPersistentDataContainer();
            blockPDC.set(playerKey, PersistentDataType.STRING,  e.getPlayer().getUniqueId().toString());
            chest.update();
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {

        // conditions

        Player player = e.getPlayer();
        UUID playerUUID = player.getUniqueId();
        ItemStack itemStack = e.getItem();

        if (e.getClickedBlock() == null) {
            return;
        }

        if (!e.getClickedBlock().getType().equals(Material.CHEST)) {
            return;
        }

        if (itemStack == null) {
            return;
        }

        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer itemPDC = itemMeta.getPersistentDataContainer();

        if (!itemPDC.has(giftTypeKey, PersistentDataType.STRING)) {
            return;
        }

        NamespacedKey receiverKey = new NamespacedKey(gifts, "receiverUUID");

        GiftTypes giftType = GiftTypes.valueOf(itemPDC.get(giftTypeKey, PersistentDataType.STRING));
        Chest chest = (Chest) e.getClickedBlock().getState();
        PersistentDataContainer blockPDC = chest.getPersistentDataContainer();

        if (!player.isSneaking()) {
            return;
        }

        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        if (!itemPDC.has(receiverKey, PersistentDataType.STRING)) {
            return;
        }

        if (blockPDC.has(receiverKey, PersistentDataType.STRING)) {
            return;
        }

        if (!checkBlockAboveChest(chest.getLocation().add(0,1,0))) {
            PlayerInteract.sendMessage(playerUUID, ChatColor.RED + "There's a block above the chest!");
            return;
        }

        if (blockPDC.has(playerKey, PersistentDataType.STRING) && !blockPDC.isEmpty()) {
            if (!blockPDC.get(playerKey, PersistentDataType.STRING).equalsIgnoreCase(playerUUID.toString())) {
                return;
            }
        } else {
            // if there's a receiver, but no playerkey set, a player key will be set to the player that created the gift

            blockPDC.set(playerKey, PersistentDataType.STRING, playerUUID.toString());
        }


        // logic

        OfflinePlayer receiver = Bukkit.getOfflinePlayer(UUID.fromString(itemPDC.get(receiverKey, PersistentDataType.STRING)));
        UUID receiverUUID = receiver.getUniqueId();

        // request type gift
        if (itemPDC.has(receiverLocationKey, PersistentDataType.STRING)) {
            Location location = parseLocation(itemPDC.get(receiverLocationKey, PersistentDataType.STRING));
            if (giftType.equals(GiftTypes.REQUEST) && location != null) {
                new GiftTransport(gifts, chest, centerLocation(chest.getLocation()), centerLocation(location), playerUUID, receiverUUID, giftType);
            } else {
                PlayerInteract.sendMessage(playerUUID, ChatColor.RED + "Request failed! Unknown world!");
            }
        }

        // summon a fire work at the chest if it's not a request gift type
        if (!giftType.equals(GiftTypes.REQUEST)) {
            spawnFirework(playerUUID, centerLocation(chest.getLocation()));
            PlayerInteract.sendMessage(playerUUID, "You have created a gift for " + receiver.getName());
        }

        // set a hologram at chest if it's not a request gift type
        if (!giftType.equals(GiftTypes.REQUEST)) {
            createHologram(chest.getWorld().getUID(), centerLocation(chest.getBlock().getLocation()), receiver.getName(), player.getName(), giftType);
        }

        // set chest to have a receiver
        blockPDC.set(receiverKey, PersistentDataType.STRING, receiverUUID.toString());

        itemStack.setAmount(itemStack.getAmount() - 1);
        chest.update();
    }

    public void spawnFirework(UUID playerUUID, Location chestLoc) {
        Player player = Bukkit.getPlayer(playerUUID);
        Firework firework = player.getWorld().spawn(chestLoc, Firework.class);
        FireworkMeta fireworkMeta = firework.getFireworkMeta();
        fireworkMeta.addEffect(FireworkEffect.builder().withColor(Color.RED).withColor(Color.NAVY).withColor(Color.GREEN).withColor(Color.YELLOW).withColor(Color.ORANGE).withColor(Color.BLUE).withColor(Color.PURPLE).with(FireworkEffect.Type.BALL_LARGE).build());
        fireworkMeta.setPower(1);
        firework.setFireworkMeta(fireworkMeta);
    }

    public void createHologram(UUID worldUUID, Location loc, String receiverName, String playerName, GiftTypes giftType) {
        ArmorStand armorStand = (ArmorStand) Bukkit.getWorld(worldUUID).spawnEntity(loc, EntityType.ARMOR_STAND);
        Player player = Bukkit.getPlayer(playerName);
        OfflinePlayer receiver = Bukkit.getOfflinePlayer(receiverName);

        // setting PDC of hologram to player UUID && receiver UUID
        armorStand.getPersistentDataContainer().set(receiverKey, PersistentDataType.STRING, receiver.getUniqueId().toString());
        armorStand.getPersistentDataContainer().set(playerKey, PersistentDataType.STRING, player.getUniqueId().toString());

        armorStand.setInvisible(true);
        armorStand.setSmall(true);
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);
        armorStand.setCustomName(ChatColor.GREEN + "To: " + receiverName + " | From: " + playerName + " | GiftType: " + giftType.toString());
        armorStand.setCustomNameVisible(true);

        // adding lock
        armorStand.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING_OR_CHANGING);
    }

    public Location centerLocation(Location loc) {
        return loc.add(0.5, 0, 0.5);
    }

    public Location parseLocation(String string) {
        String[] stringList = string.split("=");
        String worldName = stringList[2].replace("},x", "");
        double x = Double.parseDouble(stringList[3].replace(",y", ""));
        double y = Double.parseDouble(stringList[4].replace(",z", ""));
        double z = Double.parseDouble(stringList[5].replace(",pitch", ""));
        if (Bukkit.getWorld(worldName) == null) {
            gifts.getLogger().log(Level.SEVERE, "Request failed! World " + worldName + " is not found!");
            return null;
        }

        return new Location(Bukkit.getWorld(worldName), x, y, z);

    }

    public boolean checkBlockAboveChest(Location loc) {
        while (loc.getY() < 256) {
            if (loc.getBlock().getType() != Material.AIR) {
                return false;
            }

            loc.add(0,1,0);
        }

        return true;
    }

}
