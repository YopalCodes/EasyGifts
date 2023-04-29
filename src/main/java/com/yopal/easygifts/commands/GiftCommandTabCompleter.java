package com.yopal.easygifts.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GiftCommandTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }

        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("open", "send", "toggle", "help"), new ArrayList<>());
        }

        return null;
    }
}
