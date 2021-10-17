package xyz.sorridi.unicore.utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;

@Getter
@AllArgsConstructor
public enum Woods {

    ACACIA(Material.ACACIA_LOG),
    SPRUCE(Material.SPRUCE_LOG),
    BIRCH(Material.BIRCH_LOG),
    JUNGLE(Material.JUNGLE_LOG),
    OAK(Material.OAK_LOG),
    DARK_OAK(Material.DARK_OAK_LOG);

    private final Material material;

}
