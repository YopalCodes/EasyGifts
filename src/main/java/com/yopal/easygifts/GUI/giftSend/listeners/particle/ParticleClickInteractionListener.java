package com.yopal.easygifts.GUI.giftSend.listeners.particle;

import com.yopal.easygifts.GUI.giftSend.instances.GUISend;
import com.yopal.easygifts.GUI.enums.GUITypes;
import com.yopal.easygifts.GUI.enums.ParticleTypes;
import com.yopal.easygifts.GUI.giftSend.managers.GUISendManager;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ParticleClickInteractionListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        GUISend gui = GUISendManager.getGUI(player);
        if (e.getInventory() == null || e.getCurrentItem() == null || gui  == null) {
            return;
        }

        if (!gui.getType().equals(GUITypes.PARTICLE)) {
            return;
        }

        if (e.getRawSlot() == 0) {
            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, 1);
            gui.openMainPage();
        }

        switch (e.getRawSlot()) {
            case 9:
                gui.setpType(ParticleTypes.NONE);
                break;
            case 11:
                gui.setpType(ParticleTypes.FIRE);
                break;
            case 13:
                gui.setpType(ParticleTypes.ENDER);
                break;
            case 15:
                gui.setpType(ParticleTypes.LOVE);
                break;
            case 17:
                gui.setpType(ParticleTypes.POOP);
                break;
        }

        if (e.getRawSlot() == 9 || e.getRawSlot() == 11 || e.getRawSlot() == 13 || e.getRawSlot() == 15 || e.getRawSlot() == 17) {
            player.playSound(player.getLocation(), Sound.BLOCK_DISPENSER_DISPENSE, 1, 1.75f);
            gui.updateParticlePage(e.getInventory());
        }

    }

}
