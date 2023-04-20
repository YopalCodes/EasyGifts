package com.yopal.easygifts.animation.instances;

import com.yopal.easygifts.animation.enums.AnimationTypes;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class NoneAnimation {

    private Player player;
    private OfflinePlayer sender;

    public NoneAnimation(Player player, OfflinePlayer sender) {
        this.player = player;
        this.sender = sender;
    }

    public void animate() {

    }

}
