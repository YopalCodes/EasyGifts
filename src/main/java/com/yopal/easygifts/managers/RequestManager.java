package com.yopal.easygifts.managers;

import com.yopal.easygifts.utils.PlayerInteract;
import com.yopal.easygifts.utils.Request;
import jdk.jfr.internal.RequestEngine;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RequestManager {
    private static List<Request> requests = new ArrayList<>();

    public static void addRequests(Request request) {
        requests.add(request);
    }

    public static Request getRequest(UUID senderUUID, UUID receiverUUID) {
        if (requests.isEmpty()) {
            return null;
        }

        for (Request request : requests) {
            if (request.getSender() == senderUUID && request.getReceiver() == receiverUUID) {
                return request;
            }
        }

        return null;
    }

    public static boolean checkRequest(UUID senderUUID, UUID receiverUUID) {

        if (requests.isEmpty()) {
            PlayerInteract.sendMessage(receiverUUID, ChatColor.RED + "There are no ongoing requests!");
            return false;
        }

        for (Request request : requests) {
            if (request.getSender() == senderUUID && request.getReceiver() == receiverUUID) {
                return true;
            }
        }

        PlayerInteract.sendMessage(receiverUUID, ChatColor.RED + "You don't have a request with this player!");
        return false;
    }

    public static void removeRequest(Request request) {
        if (requests.isEmpty()) {
            return;
        }

        requests.remove(request);
    }

    public static List<Request> getRequests() {
        return requests;
    }

}
