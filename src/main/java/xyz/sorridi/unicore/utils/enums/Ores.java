package xyz.sorridi.unicore.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
public enum Ores {

    COAL(Material.COAL_ORE, "&8"),
    IRON(Material.IRON_ORE, "&7"),
    GOLD(Material.GOLD_ORE, "&e"),
    DIAMOND(Material.DIAMOND_ORE, "&b"),
    EMERALD(Material.EMERALD_ORE, "&a"),
    REDSTONE(Material.REDSTONE_ORE, "&c"),
    LAPIS(Material.LAPIS_ORE, "&9"),
    COPPER(Material.COPPER_ORE, "&6"),
    QUARTZ(Material.NETHER_QUARTZ_ORE, "&f"),
    QUARTZ_GOLD(Material.NETHER_GOLD_ORE, "&g");

    private final Material material;
    private final String color;

}
