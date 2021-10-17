package xyz.sorridi.unicore.storage;

import lombok.Getter;
import org.bukkit.entity.Player;
import xyz.sorridi.unicore.UniCore;
import xyz.sorridi.unicore.objects.UniPlayer;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Mapper {

    private final List<UniPlayer> uniPlayerList;

    public Mapper(UniCore instance) {
        uniPlayerList = new ArrayList<>();
        List<String> configMapper = instance.getConfig().getStringList("mapper");

        System.out.println(configMapper);
        for (String s : configMapper) {
            String[] stringSplit = s.split(" ");
            uniPlayerList.add(new UniPlayer(stringSplit[0], Boolean.parseBoolean(stringSplit[1]), Boolean.parseBoolean(stringSplit[2])));
        }

    }

    public UniPlayer getFromPlayer(Player player) {
        for (UniPlayer uniPlayer : uniPlayerList) {
            if (uniPlayer.getPlayerName().equals(player.getName())) {
                return uniPlayer;
            }
        }
        return null;
    }

    public void addUniPlayer(UniPlayer uniPlayer) {
        uniPlayerList.add(uniPlayer);
    }

}
