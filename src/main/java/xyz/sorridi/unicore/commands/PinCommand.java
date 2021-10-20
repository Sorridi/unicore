package xyz.sorridi.unicore.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.sorridi.unicore.UniCore;
import xyz.sorridi.unicore.objects.UniPlayer;
import xyz.sorridi.unicore.storage.Mapper;
import xyz.sorridi.unicore.utils.StringFormat;
import xyz.sorridi.unicore.utils.WorldUtils;
import xyz.sorridi.unicore.utils.enums.MessageType;

public class PinCommand implements CommandExecutor {

    private final Mapper mapper;

    public PinCommand(UniCore instance) {
        mapper = instance.getMapper();
        instance.getServer().getPluginCommand("pin").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (!(commandSender instanceof Player)) {
            return false;
        }

        Player player = (Player) commandSender;
        UniPlayer uniPlayer = mapper.getFromPlayer(player);
        int len = strings.length;

        switch (len) {
            case 1: {
                String pin = strings[0];

                if (pin.equalsIgnoreCase("set")) {
                    StringFormat.sendFormattedPrefix(player, "Imposta la password con: /pin set <password>", MessageType.ERROR);
                    break;
                }

                if (uniPlayer.isLogged()) {
                    StringFormat.sendFormattedPrefix(player, "Hai gia' effettuato l'autenticazione!", MessageType.WARNING);
                    break;
                }

                if (!pin.equals(uniPlayer.getPin())) {
                    StringFormat.sendFormattedPrefix(player, "La password inserita e' errata!", MessageType.ERROR);
                    break;
                }

                uniPlayer.setLastIP(WorldUtils.getPlayerCurrentIP(player));
                uniPlayer.setLogged(true);
                StringFormat.sendFormattedPrefix(player, "Autenticazione effettuata!", MessageType.OK);
                break;
            }
            case 2: {
                String action = strings[0], pin = strings[1];

                if (!action.equals("set")) {
                    StringFormat.sendFormattedPrefix(player,
                            "Comandi disponibili:" +
                            "\n&8&l* &fEffettua l'autenticazione &7» &6/pin <password>" +
                            "\n&8&l* &fCambia la password &7» &6/pin set <password>",
                            MessageType.WARNING);
                    break;
                }

                if (pin.isBlank()) {
                    StringFormat.sendFormattedPrefix(player, "Devi inserire una password: /pin set <password>", MessageType.ERROR);
                    break;
                }

                if (pin.equalsIgnoreCase("NONE")) {
                    StringFormat.sendFormattedPrefix(player, "Mi spiace, la keyword NONE e' riservata :(", MessageType.ERROR);
                    break;
                }

                if (pin.equals(uniPlayer.getPin())) {
                    StringFormat.sendFormattedPrefix(player, "La nuova password inserita e' uguale a quella vecchia...", MessageType.WARNING);
                    break;
                }

                if (uniPlayer.getPin().isBlank() || uniPlayer.isLogged()) {
                    uniPlayer.setPin(pin);
                    uniPlayer.setLastIP(WorldUtils.getPlayerCurrentIP(player));
                    StringFormat.sendFormattedPrefix(player, "La nuova password impostata e': &a" + pin, MessageType.OK);
                    break;
                }

                StringFormat.sendFormattedPrefix(player, "Devi essere autenticato per cambiare la password, usa: /pin <password>", MessageType.ERROR);
                break;
            }
            default: {
                StringFormat.sendFormattedPrefix(player,
                        "Comandi disponibili:" +
                        "\n&8&l* &fEffettua l'autenticazione &7» &6/pin <password>" +
                        "\n&8&l* &fCambia la password &7» &6/pin set <password>",
                        MessageType.WARNING);
                break;
            }
        }

        return false;
    }

}
