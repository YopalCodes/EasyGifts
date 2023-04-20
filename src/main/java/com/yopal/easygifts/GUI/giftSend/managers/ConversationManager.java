package com.yopal.easygifts.GUI.giftSend.managers;

import com.yopal.easygifts.EasyGifts;
import com.yopal.easygifts.GUI.giftSend.instances.ConMessagePrompt;
import com.yopal.easygifts.GUI.giftSend.instances.ConTitlePrompt;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.entity.Player;

public class ConversationManager {

    public static void converseTitle(EasyGifts easyGifts, Player player) {
        ConversationFactory conFactory = new ConversationFactory(easyGifts);
        Conversation con = conFactory.withFirstPrompt(new ConTitlePrompt()).withLocalEcho(false).buildConversation(player);
        con.begin();

    }

    public static void converseMessage(EasyGifts easyGifts, Player player) {
        ConversationFactory conFactory = new ConversationFactory(easyGifts);
        Conversation con = conFactory.withFirstPrompt(new ConMessagePrompt()).withLocalEcho(false).buildConversation(player);
        con.begin();

    }


}
