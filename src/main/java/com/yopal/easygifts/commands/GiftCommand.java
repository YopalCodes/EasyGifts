package com.yopal.easygifts.commands;

import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.GUI.giftOpen.instances.GUIOpen;
import com.yopal.easygifts.GUI.giftSend.managers.GUISendManager;
import com.yopal.easygifts.GUI.giftOpen.managers.GUIOpenManager;
import com.yopal.easygifts.GUI.giftSend.instances.GUISend;
import com.yopal.easygifts.utils.PlayerInteract;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

        if (args.length < 1) {
            PlayerInteract.sendInvalidUsage(player, "Not a command!");
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "open":
                GUIOpenManager.addGUI(new GUIOpen(player));
                break;

            case "send":

                if (args.length != 2) {
                    PlayerInteract.sendInvalidUsage(player, "Please specify a player!");
                    return false;
                }

                OfflinePlayer receiver = Bukkit.getOfflinePlayer(args[1]);

                if (receiver == null) {
                    PlayerInteract.sendInvalidUsage(player, "That's not a player");
                    return false;
                }

                if (!receiver.hasPlayedBefore()) {
                    PlayerInteract.sendInvalidUsage(player, "Player has to play this server!");
                    return false;
                }

                if (receiver.getUniqueId().equals(player.getUniqueId())) {
                    PlayerInteract.sendInvalidUsage(player, "You cannot put your own name!");
                    return false;
                }

                GUISendManager.addGUI(new GUISend(easyGifts, player, receiver));
                break;

            default:
                PlayerInteract.sendInvalidUsage(player, "Not a command!");
                break;
        }






        return false;
    }

}
