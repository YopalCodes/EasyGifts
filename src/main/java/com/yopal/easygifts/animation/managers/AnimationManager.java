package com.yopal.easygifts.animation.managers;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AnimationManager {

    private static List<UUID> playerUUIDList = new ArrayList<>();

    public void removePlayerUUID(Player player) {
        playerUUIDList.remove(player.getUniqueId());
    }

    public void addPlayerUUID(Player player) {
        playerUUIDList.add(player.getUniqueId());
    }


}
