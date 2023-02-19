package com.yopal.easygifts.utils;

import com.yopal.easygifts.enums.UtilTypes;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerInteract {

    private static String prefix = ChatColor.GRAY + "[" + ChatColor.RED + "EasyGifts" + ChatColor.GRAY  + "] " + ChatColor.GRAY;
    private static TextComponent prefixComponent = new TextComponent("§7[§cEasyGifts§7] ");

    public static void sendMessage(UUID playerUUID, String message) {
        Player player = Bukkit.getPlayer(playerUUID);
        player.sendMessage(prefix + message);
        player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 2);
    }

    public static void sendSpigotMessage(UUID playerUUID, TextComponent[] textComponents) {
        Player player = Bukkit.getPlayer(playerUUID);

        TextComponent start = new TextComponent(prefixComponent);
        for (TextComponent textComponent : textComponents) {
            start.addExtra(textComponent);
        }

        player.spigot().sendMessage(start);
    }

    public static void sendTitle(Player player, String title) {
        player.sendTitle(title, "");
    }

    public static void sendTitle(Player player, String title, String subtitle) {
        player.sendTitle(title, subtitle);
    }

}
