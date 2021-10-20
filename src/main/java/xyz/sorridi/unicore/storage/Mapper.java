package xyz.sorridi.unicore.storage;

import lombok.Getter;
import org.bukkit.entity.Player;
import xyz.sorridi.unicore.UniCore;
import xyz.sorridi.unicore.objects.UniPlayer;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Mapper {

    private final UniCore instance;
    private final List<UniPlayer> uniPlayerList;

    public Mapper(UniCore instance) {
        this.instance = instance;
        uniPlayerList = new ArrayList<>();
        List<String> configMapper = instance.getConfig().getStringList("mapper");

        for (String s : configMapper) {
            String[] stringSplit = s.split(" ");
            uniPlayerList.add(
                    new UniPlayer(
                            stringSplit[0],
                            Boolean.parseBoolean(stringSplit[1]),
                            Boolean.parseBoolean(stringSplit[2]),
                            false,
                            stringSplit[3].equals("NONE") ? "" : stringSplit[3],
                            stringSplit[4].equals("NONE") ? "" : stringSplit[4]
                    )
            );
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
