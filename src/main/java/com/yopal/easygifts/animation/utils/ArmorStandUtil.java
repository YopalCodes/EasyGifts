package com.yopal.easygifts.animation.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ArmorStandUtil {

    public static ArmorStand createArmorStand(Player player, OfflinePlayer offlinePlayer) {
        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);

        // properties
        armorStand.setGravity(false);
        armorStand.setInvulnerable(true);
        armorStand.setVisible(false);

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwningPlayer(offlinePlayer);
        skull.setItemMeta(skullMeta);

        armorStand.getEquipment().setHelmet(skull);

        // locking equipment

        return armorStand;

    }

}
