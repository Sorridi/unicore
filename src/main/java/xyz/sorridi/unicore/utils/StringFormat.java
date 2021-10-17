package xyz.sorridi.unicore.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;

public class StringFormat {

    public static String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void sendFormatted(Player player, String string) {
        player.sendMessage(translate(string));
    }

    public static void sendFormattedPrefix(Player player, String string) {
        sendFormatted(player, "&8(&cunicore&8) &7» &f" + string);
    }

}
