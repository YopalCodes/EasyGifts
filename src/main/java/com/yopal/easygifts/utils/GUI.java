package com.yopal.easygifts.utils;

import com.yopal.easygifts.enums.ParticleTypes;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class GUI {


    private int month = 0;

    private int day = 0;

    private int seconds = 0;

    private ParticleTypes pType = ParticleTypes.DEFAULT;
    private UUID playerUUID;

    private UUID receiverUUID;

    public GUI(UUID playerUUID, UUID receiverUUID) {
        this.playerUUID = playerUUID;
        this.receiverUUID = receiverUUID;
        openMainPage();
    }

    public void openMainPage() {
        Player player = Bukkit.getPlayer(playerUUID);
        Inventory inv = Bukkit.createInventory(player, 53, PlayerInteract.returnPrefix());



    }

}
