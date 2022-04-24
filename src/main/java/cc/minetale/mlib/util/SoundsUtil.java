package cc.minetale.mlib.util;

import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.minestom.server.entity.Player;

public class SoundsUtil {

    public static void playClickSound(Player player) {
        player.playSound(Sound.sound(Key.key("block.wooden_button.click_on"), Sound.Source.MASTER, 0.75F, 1.0F));
    }

    public static void playErrorSound(Player player) {
        player.playSound(Sound.sound(Key.key("entity.enderman.teleport"), Sound.Source.MASTER, 0.75F, 0.5F));
    }

    public static void playMessageSound(Player player) {
        player.playSound(Sound.sound(Key.key("entity.experience_orb.pickup"), Sound.Source.MASTER, 0.75F, 0.5F));
    }

}
