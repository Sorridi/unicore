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
    NETHER_GOLD(Material.NETHER_GOLD_ORE, "&e"),
    DEEPSLATE_COAL(Material.DEEPSLATE_COAL_ORE, "&8"),
    DEEPSLATE_IRON(Material.DEEPSLATE_IRON_ORE, "&7"),
    DEEPSLATE_GOLD(Material.DEEPSLATE_GOLD_ORE, "&e"),
    DEEPSLATE_DIAMOND(Material.DEEPSLATE_DIAMOND_ORE, "&b"),
    DEEPSLATE_EMERALD(Material.DEEPSLATE_EMERALD_ORE, "&a"),
    DEEPSLATE_REDSTONE(Material.DEEPSLATE_REDSTONE_ORE, "&c"),
    DEEPSLATE_LAPIS(Material.DEEPSLATE_LAPIS_ORE, "&9"),
    DEEPSLATE_COPPER(Material.DEEPSLATE_COPPER_ORE, "&6");

    private final Material material;
    private final String color;

}
