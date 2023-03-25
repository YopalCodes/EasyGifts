package com.yopal.easygifts.commands;

import com.sun.net.httpserver.Request;
import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.enums.GiftTypes;
import com.yopal.easygifts.managers.GUIManager;
import com.yopal.easygifts.utils.GUI;
import com.yopal.easygifts.utils.PlayerInteract;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class GiftCommand implements CommandExecutor {

    private EasyGifts easyGifts;

    public GiftCommand(EasyGifts easyGifts) {
        this.easyGifts = easyGifts;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        OfflinePlayer receiver = Bukkit.getOfflinePlayer(args[0]);

        if (receiver == null) {
            PlayerInteract.sendInvalidUsage(player, "That's not a player");
            return false;
        }

        if (!receiver.hasPlayedBefore()) {
            PlayerInteract.sendInvalidUsage(player, "Player has to play this server!");
            return false;
        }

        if (receiver.getUniqueId().equals(player.getUniqueId())) {
            PlayerInteract.sendInvalidUsage(player, "Send a gift to someone besides yourself!");
            return false;
        }

        if (args.length > 1) {
            PlayerInteract.sendInvalidUsage(player, "Too many arguments, please only include a player's name!");
            return false;
        }

        /*
        GUI MENU:
        Time: days, hours, minutes
        Gift Particles: none (default), fire, water, ender, air, firework
        Inventory: have an inventory to deposit the items for the gift
         */

        GUIManager.addGUI(new GUI(easyGifts, player, receiver));

        return false;
    }

}
