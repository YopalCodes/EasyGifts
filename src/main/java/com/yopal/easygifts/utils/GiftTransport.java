package com.yopal.easygifts.utils;

import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.enums.GiftTypes;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.units.qual.A;

import java.util.Random;
import java.util.UUID;

public class GiftTransport {
    private Inventory chestInv;
    private EasyGifts gifts;
    private UUID playerUUID;
    private UUID receiverUUID;
    private Chest chest;
    private Location targetLoc;
    private Location ogLoc;
    private NamespacedKey playerKey;
    private NamespacedKey receiverKey;
    private BukkitTask checkLoc;
    private UUID launchStandUUID;
    private UUID landStandUUID;
    private GiftTypes giftType;
    public GiftTransport(EasyGifts gifts, Chest chest, Location ogLoc, Location targetLoc, UUID playerUUID, UUID receiverUUID, GiftTypes giftType) {
        this.gifts = gifts;
        this.chest = chest;
        this.ogLoc = ogLoc;
        this.targetLoc = targetLoc;
        this.playerUUID = playerUUID;
        this.receiverUUID = receiverUUID;
        this.chestInv = chest.getBlockInventory();
        this.playerKey = new NamespacedKey(gifts, "playerUUID");
        this.receiverKey = new NamespacedKey(gifts, "receiverUUID");
        this.giftType = giftType;

        startShaking();
    }

    public void changeArmorStand(UUID armorStandUUID) {
        ArmorStand armorStand = (ArmorStand) Bukkit.getEntity(armorStandUUID);

        if (armorStand == null) {
            return;
        }

        // locks
        armorStand.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.ADDING_OR_CHANGING);

        // head
        armorStand.setHelmet(new ItemStack(Material.CHEST));
        armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.REMOVING_OR_CHANGING);

        // other effects
        armorStand.setInvulnerable(true);
        armorStand.setVisible(false);
        armorStand.setSmall(true);
    }

    public void startShaking() {
        Directional chestDirection = (Directional) chest.getBlockData();
        BlockFace blockFace = chestDirection.getFacing();
        ogLoc.setYaw(returnYawFromFace(blockFace));

        ArmorStand armorStand = (ArmorStand) ogLoc.getWorld().spawnEntity(ogLoc, EntityType.ARMOR_STAND);
        changeArmorStand(armorStand.getUniqueId());
        this.launchStandUUID = armorStand.getUniqueId();

        Location centerLoc = armorStand.getLocation();
        chest.getBlock().setType(Material.AIR);

        BukkitTask shakeTask = Bukkit.getScheduler().runTaskTimer(gifts, () -> {
            armorStand.teleport(centerLoc);
            Random random = new Random();
            armorStand.teleport(armorStand.getLocation().add(random.nextFloat(0.25f), random.nextFloat(0.25f), random.nextFloat(0.25f)));
        }, 0, 3);

        Bukkit.getScheduler().runTaskLater(gifts, ()->{
            shakeTask.cancel();
            armorStand.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 50, true, false));
            startLanding();

        }, 100);

        Bukkit.getScheduler().runTaskLater(gifts, ()->{
            armorStand.remove();
        }, 200);

    }

    public void startLanding() {
        OfflinePlayer receiver = Bukkit.getOfflinePlayer(receiverUUID);
        if (receiver.isOnline()) {
            targetLoc.setYaw(receiver.getPlayer().getLocation().getYaw()-180);
        }

        ArmorStand landingStand = (ArmorStand) targetLoc.getWorld().spawnEntity(targetLoc.add(0,100,0), EntityType.ARMOR_STAND);
        changeArmorStand(landingStand.getUniqueId());
        this.landStandUUID = landingStand.getUniqueId();

        this.checkLoc = Bukkit.getScheduler().runTaskTimer(gifts, () -> {
            checkLocation(targetLoc);

            if (landingStand.getLocation().getY() - targetLoc.getY() < 20) {
                landingStand.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 1, 1, true, false));
            }

        },0,1);

    }

    public void cancelTransport() {
        ogLoc.getBlock().setType(Material.CHEST);
        Chest ogChest = (Chest) ogLoc.getBlock().getState();
        ogChest.getBlockInventory().setContents(chestInv.getContents());
        PlayerInteract.sendMessage(playerUUID, ChatColor.RED + "Something went wrong with the transport!");
        PlayerInteract.sendMessage(receiverUUID, ChatColor.RED + "Something went wrong with the transport!");

        ogChest.update();
    }

    public void finishTransport() {
        ArmorStand landingStand = (ArmorStand) Bukkit.getEntity(landStandUUID);
        if (landingStand != null) {
            landingStand.remove();
        }

        targetLoc.getBlock().setType(Material.CHEST);
        Chest landedChest = (Chest) targetLoc.getBlock().getState();
        landedChest.getBlockInventory().setContents(chestInv.getContents());

        PersistentDataContainer blockPDC = landedChest.getPersistentDataContainer();
        // set chest to have a player && receiver
        blockPDC.set(playerKey, PersistentDataType.STRING, playerUUID.toString());
        blockPDC.set(receiverKey, PersistentDataType.STRING, receiverUUID.toString());

        landedChest.update();

        // summon hologram
        createHologram(targetLoc.getWorld().getUID(), targetLoc);
    }

    public void checkLocation(Location targetLoc) {
        ArmorStand landingStand = (ArmorStand) Bukkit.getEntity(landStandUUID);
        Location landStandLoc = landingStand.getLocation();
        if (landingStand.isOnGround() && landStandLoc.getY() >= 256) {
            checkLoc.cancel();
            cancelTransport();
            return;
        }

        if (landingStand.isOnGround() && landStandLoc.getY() == targetLoc.getY()) {
            checkLoc.cancel();
            finishTransport();
            return;
        }

    }

    public int returnYawFromFace(BlockFace face) {
        switch (face) {
            case NORTH:
                return -180;
            case SOUTH:
                return 0;
            case EAST:
                return -90;
            case WEST:
                return 90;
        }
        return 0;
    }

    public void createHologram(UUID worldUUID, Location loc) {
        ArmorStand armorStand = (ArmorStand) Bukkit.getWorld(worldUUID).spawnEntity(loc, EntityType.ARMOR_STAND);
        Player player = Bukkit.getPlayer(playerUUID);
        OfflinePlayer receiver = Bukkit.getOfflinePlayer(receiverUUID);

        // setting PDC of hologram to player UUID && receiver UUID
        armorStand.getPersistentDataContainer().set(receiverKey, PersistentDataType.STRING, receiver.getUniqueId().toString());
        armorStand.getPersistentDataContainer().set(playerKey, PersistentDataType.STRING, player.getUniqueId().toString());

        armorStand.setInvisible(true);
        armorStand.setSmall(true);
        armorStand.setInvulnerable(true);
        armorStand.setGravity(false);
        armorStand.setCustomName(ChatColor.GREEN + "To: " + receiver.getName() + " | From: " + player.getName() + " | GiftType: " + giftType.toString());
        armorStand.setCustomNameVisible(true);

        // adding lock
        armorStand.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING_OR_CHANGING);
        armorStand.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING_OR_CHANGING);
    }
}
