package xyz.sorridi.unicore.events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.sorridi.unicore.UniCore;
import xyz.sorridi.unicore.objects.UniPlayer;
import xyz.sorridi.unicore.storage.Mapper;
import xyz.sorridi.unicore.utils.StringFormat;
import xyz.sorridi.unicore.utils.WorldUtils;
import xyz.sorridi.unicore.utils.enums.MessageType;

import java.util.ArrayList;
import java.util.List;

public class PlayerEvents implements Listener {

    private final Mapper mapper;

    public PlayerEvents(UniCore instance) {
        mapper = instance.getMapper();
        instance.getPluginManager().registerEvents(this, instance);
    }

    @EventHandler
    public void on(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UniPlayer uniPlayer = mapper.getFromPlayer(player);

        if (uniPlayer == null) {
            uniPlayer = new UniPlayer(player.getName(), false, false, false, "", "");
            mapper.addUniPlayer(uniPlayer);
        }

        StringFormat.sendFormattedPrefix(player, "Tree-Capitator " + (uniPlayer.isTreeCapitatorMode() ? "&aattivo" : "&cdisattivo"), MessageType.WARNING);
        StringFormat.sendFormattedPrefix(player, "Vein-Miner " + (uniPlayer.isVeinMinerMode() ? "&aattivo" : "&cdisattivo"), MessageType.WARNING);
        player.sendMessage(" ");

        boolean isSameSession = uniPlayer.getLastIP().equals(WorldUtils.getPlayerCurrentIP(player));
        uniPlayer.setLogged(isSameSession || uniPlayer.getPin().isBlank());

        if (isSameSession)
            StringFormat.sendFormattedPrefix(player, "La tua sessione e' stata recuperata, autenticazione effettuata.", MessageType.OK);
        else
            if (uniPlayer.getPin().isBlank())
                StringFormat.sendFormattedPrefix(player, "Per proteggere il tuo account, imposta una password con il comando: /pin set <password>", MessageType.WARNING);
            else {
                player.showTitle(Title.title(Component.empty(), Component.text("Autenticazione richiesta, usa: /pin <password>").color(NamedTextColor.RED)));
                StringFormat.sendFormattedPrefix(player, "La tua sessione e' cambiata, e' necessario effettuare l'autenticazione, usa: /pin <password>", MessageType.WARNING);
            }

        player.sendMessage(" ");
    }

    @EventHandler
    public void on(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UniPlayer uniPlayer = mapper.getFromPlayer(player);

        if (!uniPlayer.isLogged()) {
            event.setCancelled(true);
            StringFormat.sendActionPinError(player);
            return;
        }

        if (!event.getAction().equals(Action.RIGHT_CLICK_AIR))
            return;

        PlayerInventory inventory = player.getInventory();
        ItemStack itemInMainHand = inventory.getItemInMainHand();

        boolean isPickaxe = WorldUtils.isPickaxe(itemInMainHand), isAxe = WorldUtils.isAxe(itemInMainHand);

        if (!player.isSneaking() || (!isAxe && !isPickaxe))
            return;

        if (isAxe) {
            uniPlayer.setTreeCapitatorMode(!uniPlayer.isTreeCapitatorMode());
            StringFormat.sendFormattedPrefix(player, "Tree-Capitator " + (uniPlayer.isTreeCapitatorMode() ? "&aattivo" : "&cdisattivo"), MessageType.WARNING);
        }

        if (isPickaxe) {
            uniPlayer.setVeinMinerMode(!uniPlayer.isVeinMinerMode());
            StringFormat.sendFormattedPrefix(player, "Vein-Miner " + (uniPlayer.isVeinMinerMode() ? "&aattivo" : "&cdisattivo"), MessageType.WARNING);
        }
    }

    @EventHandler
    public void on(BlockBreakEvent event) {
        Player player = event.getPlayer();
        UniPlayer uniPlayer = mapper.getFromPlayer(player);

        if (!uniPlayer.isLogged()) {
            event.setCancelled(true);
            StringFormat.sendActionPinError(player);
            return;
        }

        Block block = event.getBlock();
        Material material = event.getBlock().getType();

        boolean miningOres = WorldUtils.isOre(material), miningWood = WorldUtils.isWood(material);

        if (!miningOres && !miningWood)
            return;

        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        if (miningOres && (!WorldUtils.isPickaxe(itemInMainHand) || !uniPlayer.isVeinMinerMode()))
            return;

        if (miningWood && (!WorldUtils.isAxe(itemInMainHand) || !uniPlayer.isTreeCapitatorMode()))
            return;

        List<Block> blockList = new ArrayList<>();
        WorldUtils.breakBlocks(blockList, block, miningOres ? WorldUtils::isOre : WorldUtils::isWood);

        ItemMeta itemMeta = itemInMainHand.getItemMeta();

        Damageable damageable = (Damageable) itemMeta;
        int damage = damageable.getDamage();
        int num = blockList.size();
        int maxActions = itemInMainHand.getType().getMaxDurability() - damage, i = 0, k = 0, broken = num - k;

        damageable.setDamage(Math.max(damage + num, 0));
        itemInMainHand.setItemMeta(damageable);

        for (Block b : blockList) {
            if (maxActions < i) {
                k++;
                continue;
            }

            b.breakNaturally(itemInMainHand);
            i++;
        }

        if (k > 0)
            StringFormat.sendFormattedPrefix(player, miningOres ? "&cIl piccone" : "&cL'ascia" + " in uso non ha piu' durabilita'!", MessageType.WARNING);

        StringFormat.sendFormattedPrefix(player,
                (broken == 1 ? "E' stato scavato" : "Sono stati scavati")
                + " &b" + broken + " &fblocc" + (broken == 1 ? "o" : "hi") + " di "
                + (miningOres ? WorldUtils.getColor(material) : "&6") + material, MessageType.OK);
    }

    @EventHandler
    public void on(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        UniPlayer uniPlayer = mapper.getFromPlayer(player);

        if (uniPlayer.isLogged())
            return;

        event.setCancelled(true);
        StringFormat.sendActionPinError(player);
    }

    @EventHandler
    public void on(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if (!(entity instanceof Player)) {
            return;
        }

        Player player = (Player) entity;
        UniPlayer uniPlayer = mapper.getFromPlayer(player);

        if (uniPlayer.isLogged())
            return;

        event.setCancelled(true);
    }

}
