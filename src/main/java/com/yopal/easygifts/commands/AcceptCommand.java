package com.yopal.easygifts.commands;

import com.sun.tools.javac.util.Names;
import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.enums.GiftTypes;
import com.yopal.easygifts.managers.RequestManager;
import com.yopal.easygifts.utils.GiftPaper;
import com.yopal.easygifts.utils.GiftTransport;
import com.yopal.easygifts.utils.PlayerInteract;
import com.yopal.easygifts.utils.Request;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;
import java.util.UUID;

public class AcceptCommand implements CommandExecutor {

    private EasyGifts gifts;

    public AcceptCommand(EasyGifts gifts) {
        this.gifts = gifts;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        if (args.length != 1) {
            return false;
        }

        // player who uses this command is the receiver, as they accept the request from the sender
        Player receiver = (Player) sender;
        OfflinePlayer gifter = Bukkit.getOfflinePlayer(args[0]);
        UUID receiverUUID = receiver.getUniqueId();
        UUID gifterUUID = gifter.getUniqueId();

        if (!gifter.hasPlayedBefore()) {
            PlayerInteract.sendMessage(receiverUUID, ChatColor.RED + "That player hasn't joined the server!");
            return false;
        }

        if (!RequestManager.checkRequest(gifterUUID, receiverUUID)) {
            return false;
        }

        if (!receiver.isOnGround()) {
            PlayerInteract.sendMessage(receiverUUID, ChatColor.RED + "Please be on the ground!");
            return false;
        }

        if (!checkLocation(receiver.getLocation())) {
            PlayerInteract.sendMessage(receiverUUID, ChatColor.RED + "Please be between the height limit and the void! (15-256)");
            return false;
        }

        if (!checkBlockAbovePlayer(receiver.getEyeLocation())) {
            PlayerInteract.sendMessage(receiverUUID, ChatColor.RED + "There's a block above you!");
            return false;
        }

        // remove the request once a request is accepted
        Request request = RequestManager.getRequest(gifterUUID, receiverUUID);
        RequestManager.removeRequest(request);

        // accept logic
        GiftPaper giftPaper = new GiftPaper(gifts, gifterUUID, receiverUUID, args);
        giftPaper.givePaper(GiftTypes.REQUEST);

        return false;
    }

    public boolean checkLocation(Location loc) {
        if (loc.getY() < 256 && loc.getY() > 15) {
            return true;
        }
        return false;
    }

    public boolean checkBlockAbovePlayer(Location loc) {
        while (loc.getY() < 256) {
            if (loc.getBlock().getType() != Material.AIR) {
                return false;
            }

            loc.add(0,1,0);
        }

        return true;
    }
 }
