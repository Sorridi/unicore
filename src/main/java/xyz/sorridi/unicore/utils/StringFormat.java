package xyz.sorridi.unicore.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import xyz.sorridi.unicore.utils.enums.MessageType;

public class StringFormat {

    public static String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void sendFormatted(Player player, String string) {
        player.sendMessage(translate(string));
    }

    public static void sendFormattedPrefix(Player player, String string, MessageType messageType) {
        sendFormatted(player, "&8(" + messageType.getChatColor() + "unicore&8) &7» &f" + string);
    }

    public static void sendActionPinError(Player player) {
        sendFormattedPrefix(player, "Devi effettuare l'autenticazione, usa: &c/pin <password>", MessageType.ERROR);
    }

}
