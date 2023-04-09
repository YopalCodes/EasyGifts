package com.yopal.easygifts.managers.YML;

import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.utils.GUI;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class GiftsManager {
    private static YamlConfiguration giftsYML;

    public static void setupFile(EasyGifts easyGifts) {
        File file = new File(easyGifts.getDataFolder(), "gifts.yml");

        if (!file.exists()) {
            easyGifts.saveResource("gifts.yml", false);
        }

        GiftsManager.giftsYML = YamlConfiguration.loadConfiguration(file);

    }

    public static void saveFile(EasyGifts easyGifts) {

        try {
            giftsYML.save(new File(easyGifts.getDataFolder(), "gifts.yml"));
            setupFile(easyGifts);
        } catch (IOException e) {
            easyGifts.getLogger().log(Level.SEVERE, "gifts.yml couldn't be saved");
            return;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void addGift(EasyGifts easyGifts, GUI gui) {

        giftsYML.set(gui.getPlayer().getUniqueId() + ".receiverUUID", gui.getReceiver().getUniqueId().toString());
        giftsYML.set(gui.getPlayer().getUniqueId() + ".chestTitle", gui.getChestTitle() + "");
        giftsYML.set(gui.getPlayer().getUniqueId() + ".personalizedMessage", gui.getPersonalizedMessage() + "");
        giftsYML.set(gui.getPlayer().getUniqueId() + ".futureDate", gui.getFutureDate().replace("§7", ""));
        giftsYML.set(gui.getPlayer().getUniqueId() + ".particleType", gui.getpType().toString());

        List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < 45; i++) {
            ItemStack itemStack = gui.getChestInv().getItem(i);
            if (itemStack == null) {
                continue;
            }

            items.add(itemStack);
        }

        giftsYML.set(gui.getPlayer().getUniqueId() + ".chestInventory", Arrays.asList(items));

        saveFile(easyGifts);

    }


}
