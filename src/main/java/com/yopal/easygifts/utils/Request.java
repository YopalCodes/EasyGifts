package com.yopal.easygifts.utils;

import com.yopal.easygifts.managers.RequestManager;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Request {

    private UUID sender;
    private UUID receiver;

    public Request(UUID sender, UUID receiver) {
        this.sender = sender;
        this.receiver = receiver;

        RequestManager.addRequests(this);
        sendOutRequest();
    }

    public UUID getSender() {
        return sender;
    }

    public UUID getReceiver() {
        return receiver;
    }

    public void sendOutRequest() {
        Player sender = Bukkit.getPlayer(this.sender);
        Player receiver = Bukkit.getPlayer(this.receiver);

        TextComponent playerRequest = new TextComponent(sender.getName() + " has requested to send a gift to you! Will you accept it? ");

        TextComponent yes = new TextComponent("§a§l[YES] ");
        yes.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§aClick to accept the request!")));
        yes.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/accept " + sender.getName()));

        TextComponent no = new TextComponent("§c§l[NO]");
        no.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text("§cClick to deny the request!")));
        no.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/deny " + sender.getName()));

        PlayerInteract.sendSpigotMessage(receiver.getUniqueId(), new TextComponent[]{playerRequest, yes, no});
    }
}
