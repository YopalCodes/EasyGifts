package com.yopal.easygifts.utils;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class EZGHelp {

    public static void sendHelp(Player player) {
        PlayerInteract.sendMessage(player, "Here are the following commands and their descriptions for the EasyGifts plugin:");

        // send out all of the spigot messages
        HashMap<String, String> commandAndDesc = new HashMap<>();
        commandAndDesc.put("/ezg send <user>", "Send a gift to the player.");
        commandAndDesc.put("/ezg open", "See the gifts you've received.");
        commandAndDesc.put("/ezg toggle", "Toggle whether to receive gifts or not");
        commandAndDesc.put("/ezg help", "Open this help page");

        for (String str : commandAndDesc.keySet()) {
            TextComponent command = new TextComponent(ChatColor.GOLD + ChatColor.BOLD.toString() + str + ": " + ChatColor.GRAY + commandAndDesc.get(str));
            command.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§aClick me in order to auto-fill the command!")));
            command.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, str));

            PlayerInteract.sendSpigotMessageWithoutPrefix(player, new TextComponent[]{command});
        }

    }
}
