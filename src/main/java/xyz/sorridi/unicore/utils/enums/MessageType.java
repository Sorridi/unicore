package xyz.sorridi.unicore.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;

@Getter
@AllArgsConstructor
public enum MessageType {

    OK(ChatColor.GREEN),
    WARNING(ChatColor.YELLOW),
    ERROR(ChatColor.RED);

    private final ChatColor chatColor;

}
