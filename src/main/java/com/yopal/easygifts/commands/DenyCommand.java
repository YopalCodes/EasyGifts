package com.yopal.easygifts.commands;

import com.yopal.easygifts.managers.RequestManager;
import com.yopal.easygifts.utils.PlayerInteract;
import com.yopal.easygifts.utils.Request;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DenyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        if (args.length != 1) {
            return false;
        }

        Player player = (Player) sender;
        OfflinePlayer gifter = Bukkit.getOfflinePlayer(args[0]);

        if (!gifter.hasPlayedBefore()) {
            PlayerInteract.sendMessage(player.getUniqueId(), ChatColor.RED + "That's not a player!");
            return false;
        }

        if (!RequestManager.checkRequest(gifter.getUniqueId(), player.getUniqueId())) {
            return false;
        }

        // remove the request once a request is denied
        Request request = RequestManager.getRequest(gifter.getUniqueId(), player.getUniqueId());
        RequestManager.removeRequest(request);

        PlayerInteract.sendMessage(player.getUniqueId(), ChatColor.RED + "You've denied the request by " + gifter.getName() + "!");

        if (gifter.isOnline()) {
            // receiver may deny the request of the sender even when they're not online!
            PlayerInteract.sendMessage(gifter.getUniqueId(), ChatColor.RED + "Your request has been denied by " + player.getName() + "!");
        }


        return false;
    }
}
