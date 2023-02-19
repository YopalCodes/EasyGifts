package com.yopal.easygifts.commands;

import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.enums.GiftTypes;
import com.yopal.easygifts.managers.RequestManager;
import com.yopal.easygifts.utils.GiftPaper;
import com.yopal.easygifts.utils.GiftTransport;
import com.yopal.easygifts.utils.PlayerInteract;
import com.yopal.easygifts.utils.Request;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.UUID;

public class GiftCommand implements CommandExecutor {

    private EasyGifts gifts;

    public GiftCommand(EasyGifts gifts) {
        this.gifts = gifts;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        UUID receiverUUID = getReceiverUUID(player.getUniqueId(), args);
        GiftPaper giftPaper = new GiftPaper(gifts, player.getUniqueId(), receiverUUID, args);
        OfflinePlayer receiver = Bukkit.getPlayer(receiverUUID);


        if (receiverUUID == null) {
            return false;
        }

        if (receiverUUID.equals(player.getUniqueId())) {
            PlayerInteract.sendMessage(player.getUniqueId(), ChatColor.RED + "Send a gift to someone besides yourself!");
            return false;
        }

        if (args.length == 1) {
            giftPaper.givePaper(GiftTypes.NONE);
            return false;
        }

        if (args.length > 1) {

            if (receiver == null) {
                PlayerInteract.sendMessage(player.getUniqueId(), ChatColor.RED + "That player isn't online.");
                return false;
            }

            boolean commandSuccess = false;

            for (String arg : args) {
                if (arg.contains("r:")) {
                    String requestInput = arg.replace("r:", "");
                    if (!requestInput.equalsIgnoreCase("true") && !requestInput.equalsIgnoreCase("t")) {
                        return false;
                    }

                    // deny if there's already a request
                    if (!RequestManager.getRequests().isEmpty()) {
                        if (RequestManager.checkRequest(player.getUniqueId(), receiverUUID)) {
                            PlayerInteract.sendMessage(player.getUniqueId(), ChatColor.RED + "You've already made a request with this player!");
                            return false;
                        }
                    }

                    // making a request with the player and the receiver
                    new Request(player.getUniqueId(), receiverUUID);
                    commandSuccess = true;

                }

            }

            if (!commandSuccess) {
                PlayerInteract.sendMessage(player.getUniqueId(), ChatColor.RED+  "Invalid usage!");
                return false;
            }

        }

        return false;
    }

    public UUID getReceiverUUID(UUID playerUUID, String[] args) {
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getName().equalsIgnoreCase(args[0])) {
                return Bukkit.getPlayer(args[0]).getUniqueId();
            }
        }

        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers()) {
            if (offlinePlayer.getName().equalsIgnoreCase(args[0])) {
                return offlinePlayer.getUniqueId();
            }
        }

        PlayerInteract.sendMessage(playerUUID, ChatColor.RED + "That player hasn't joined this server!");
        return null;
    }

}
