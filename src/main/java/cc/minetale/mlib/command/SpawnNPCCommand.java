package cc.minetale.mlib.command;

import cc.minetale.commonlib.util.MC;
import cc.minetale.mlib.hologram.component.HologramLine;
import cc.minetale.mlib.npc.NPC;
import net.kyori.adventure.text.Component;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;
import net.minestom.server.entity.PlayerSkin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadLocalRandom;

public class SpawnNPCCommand extends Command {

    public SpawnNPCCommand() {
        super("spawnnpc");
        addSyntax(this::execute);
    }

    private void execute(@NotNull CommandSender sender, @NotNull CommandContext context) {
        if (sender.isPlayer()) {
            Player player = sender.asPlayer();

            NPC entity = new NPC(
                    player.getInstance(),
                    player.getPosition(),
                    PlayerSkin.fromUuid(player.getUuid().toString()),
                    (event) -> event.getPlayer().sendMessage(Component.text("Working Interaction Event - " + event.getNpc().getUsername())),
                    new HologramLine(Component.text("Random Number: " + ThreadLocalRandom.current().nextInt(100))),
                    new HologramLine(Component.text("Random Color :) ", MC.CC.values()[ThreadLocalRandom.current().nextInt(15)].getTextColor()))

            );

            entity.setInstance(player.getInstance(), player.getPosition());
        }
    }
}