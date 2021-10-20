package xyz.sorridi.unicore.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UniPlayer {

    private final String playerName;

    @Setter
    private boolean treeCapitatorMode, veinMinerMode, logged;

    private String pin, lastIP;

}
