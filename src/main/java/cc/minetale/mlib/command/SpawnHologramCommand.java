package cc.minetale.mlib.command;

import cc.minetale.mlib.hologram.Hologram;
import cc.minetale.mlib.hologram.component.HologramLine;
import net.kyori.adventure.text.Component;
import net.minestom.server.command.CommandSender;
import net.minestom.server.command.builder.Command;
import net.minestom.server.command.builder.CommandContext;
import net.minestom.server.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SpawnHologramCommand extends Command {

    public SpawnHologramCommand() {
        super("spawnhologram");
        addSyntax(this::execute);
    }

    private void execute(@NotNull CommandSender sender, @NotNull CommandContext context) {
        if (sender.isPlayer()) {
            Player player = sender.asPlayer();

            Hologram hologram = new Hologram(player.getInstance(), player.getPosition(), false)
                    .append(new HologramLine(Component.text("Test 1")))
                    .append(new HologramLine(Component.text("Test 2")))
                    .append(new HologramLine(Component.text("Test 3")))
                    .append(new HologramLine(Component.text("Test 4")))
                    .append(new HologramLine(Component.text("Test 5")));

            hologram.create();
        }
    }
}