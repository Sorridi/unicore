package xyz.sorridi.unicore;

import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.sorridi.unicore.commands.PinCommand;
import xyz.sorridi.unicore.events.PlayerEvents;
import xyz.sorridi.unicore.objects.UniPlayer;
import xyz.sorridi.unicore.storage.Mapper;

import java.util.ArrayList;
import java.util.List;

@Getter
public class UniCore extends JavaPlugin {

    private PluginManager pluginManager;
    private Mapper mapper;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        pluginManager = getServer().getPluginManager();
        mapper = new Mapper(this);

        new PlayerEvents(this);

        new PinCommand(this);
    }

    @Override
    public void onDisable() {
        List<String> toSave = new ArrayList<>();

        for (UniPlayer uniPlayer : mapper.getUniPlayerList()) {
            toSave.add(
                    uniPlayer.getPlayerName() + " " + uniPlayer.isTreeCapitatorMode() + " " + uniPlayer.isVeinMinerMode() + " " +
                    (uniPlayer.getPin().isBlank() ? "NONE" : uniPlayer.getPin()) + " " +
                    (uniPlayer.getLastIP().isBlank() ? "NONE" : uniPlayer.getLastIP())
            );
        }

        getConfig().set("mapper", toSave);
        saveConfig();
    }

}
