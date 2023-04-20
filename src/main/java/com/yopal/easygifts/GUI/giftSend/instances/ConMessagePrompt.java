package com.yopal.easygifts.GUI.giftSend.instances;

import com.yopal.easygifts.GUI.giftSend.managers.GUISendManager;
import com.yopal.easygifts.utils.PlayerInteract;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;
import org.bukkit.entity.Player;

public class ConMessagePrompt extends StringPrompt {
    @Override
    public String getPromptText(ConversationContext conversationContext) {
        return PlayerInteract.returnPrefix() + "Type a message to make a personal message! Use & for chat color. Type \"cancel\" to cancel making the title!";
    }

    @Override
    public Prompt acceptInput(ConversationContext conversationContext, String s) {

        GUISend gui = GUISendManager.getGUI((Player) conversationContext.getForWhom());

        if (gui == null) {
            return END_OF_CONVERSATION;
        }

        if (s.equalsIgnoreCase("cancel")) {
            gui.openMainPage();
            return END_OF_CONVERSATION;
        }

        gui.setPersonalizedMessage(s);
        gui.openMainPage();

        return END_OF_CONVERSATION;
    }
}
