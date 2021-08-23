package cc.minetale.mlib.commands;

import cc.minetale.mlib.mLib;
import cc.minetale.mlib.npc.NPC;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnNPCCommand extends Command {

    public SpawnNPCCommand() {
        super("spawnnpc");
        addSyntax(this::execute);
    }

    private void execute(@NotNull CommandSender sender, @NotNull CommandContext context) {
        if (sender.isPlayer()) {
            System.out.println("NPC spawn command ran");
            Player player = sender.asPlayer();
            NPC npc = new NPC(player.getInstance(), player.getPosition(), player.getSkin());
            mLib.getMLib().getNpcList().add(npc);
        }
    }
}
