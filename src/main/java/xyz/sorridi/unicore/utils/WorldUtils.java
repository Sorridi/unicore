package xyz.sorridi.unicore.utils;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import xyz.sorridi.unicore.utils.enums.Ores;
import xyz.sorridi.unicore.utils.enums.Woods;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class WorldUtils {

    public static boolean isWood(Material material) {
        return Arrays.stream(Woods.values()).anyMatch(m -> material == m.getMaterial());
    }

    public static final Material[] axes = new Material[]
            {
                    Material.WOODEN_AXE, Material.STONE_AXE, Material.IRON_AXE,
                    Material.GOLDEN_AXE, Material.DIAMOND_AXE, Material.NETHERITE_AXE
            };

    public static boolean isAxe(ItemStack itemStack) {
        return Arrays.stream(axes).anyMatch(m -> itemStack.getType() == m);
    }

    public static final Material[] pickaxes = new Material[]
            {
                    Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE,
                    Material.GOLDEN_PICKAXE, Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE
            };

    public static boolean isPickaxe(ItemStack itemStack) {
        return Arrays.stream(pickaxes).anyMatch(m -> itemStack.getType() == m);
    }

    public static boolean isOre(Material material) {
        return Arrays.stream(Ores.values()).anyMatch(m -> material == m.getMaterial());
    }

    public static String getColor(Material material) {
        for (Ores ore : Ores.values()) {
            if (material == ore.getMaterial())
                return ore.getColor();
        }
        return "";
    }

    public static void breakBlocks(List<Block> blockList, Block block, Function<Material, Boolean> function) {
        World world = block.getWorld();
        int x = block.getX(), y = block.getY(), z = block.getZ();
        blockList.add(block);

        for (int j = 0; j < 3; j++)
            for (int i = -1; i <= 1; i += 2) {
                Block scannedBlock = world.getBlockAt(x + (j == 0 ? i : 0), y + (j == 1 ? i : 0), z + (j == 2 ? i : 0));
                Material scannedMaterial = scannedBlock.getType();

                if (function.apply(scannedMaterial) && scannedMaterial == block.getType() && !blockList.contains(scannedBlock))
                    breakBlocks(blockList, scannedBlock, function);
            }
    }

}
