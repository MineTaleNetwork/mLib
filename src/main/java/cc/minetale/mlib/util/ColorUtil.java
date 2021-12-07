package cc.minetale.mlib.util;

import cc.minetale.commonlib.api.Punishment;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.color.DyeColor;
import net.minestom.server.item.Material;

import java.util.Map;

public class ColorUtil {

    private static final ImmutableMap<NamedTextColor, DyeColor> CHAT_DYE_COLOR_MAP = Maps.immutableEnumMap((Map) ImmutableMap.builder()
            .put(NamedTextColor.AQUA, DyeColor.LIGHT_BLUE)
            .put(NamedTextColor.BLACK, DyeColor.BLACK)
            .put(NamedTextColor.BLUE, DyeColor.LIGHT_BLUE)
            .put(NamedTextColor.DARK_AQUA, DyeColor.CYAN)
            .put(NamedTextColor.DARK_BLUE, DyeColor.BLUE)
            .put(NamedTextColor.DARK_GRAY, DyeColor.GRAY)
            .put(NamedTextColor.DARK_GREEN, DyeColor.GREEN)
            .put(NamedTextColor.DARK_PURPLE, DyeColor.PURPLE)
            .put(NamedTextColor.DARK_RED, DyeColor.RED)
            .put(NamedTextColor.GOLD, DyeColor.ORANGE)
            .put(NamedTextColor.GRAY, DyeColor.LIGHT_GRAY)
            .put(NamedTextColor.GREEN, DyeColor.LIME)
            .put(NamedTextColor.LIGHT_PURPLE, DyeColor.MAGENTA)
            .put(NamedTextColor.RED, DyeColor.RED)
            .put(NamedTextColor.WHITE, DyeColor.WHITE)
            .put(NamedTextColor.YELLOW, DyeColor.YELLOW)
            .build()
    );
    private static final ImmutableMap<NamedTextColor, Material> CHAT_CONCRETE_COLOR_MAP = Maps.immutableEnumMap((Map) ImmutableMap.builder()
            .put(NamedTextColor.AQUA, Material.LIGHT_BLUE_CONCRETE)
            .put(NamedTextColor.BLACK, Material.BLACK_CONCRETE)
            .put(NamedTextColor.BLUE, Material.LIGHT_BLUE_CONCRETE)
            .put(NamedTextColor.DARK_AQUA, Material.CYAN_CONCRETE)
            .put(NamedTextColor.DARK_BLUE, Material.BLUE_CONCRETE)
            .put(NamedTextColor.DARK_GRAY, Material.GRAY_CONCRETE)
            .put(NamedTextColor.DARK_GREEN, Material.GREEN_CONCRETE)
            .put(NamedTextColor.DARK_PURPLE, Material.PURPLE_CONCRETE)
            .put(NamedTextColor.DARK_RED, Material.RED_CONCRETE)
            .put(NamedTextColor.GOLD, Material.ORANGE_CONCRETE)
            .put(NamedTextColor.GRAY, Material.LIGHT_GRAY_CONCRETE)
            .put(NamedTextColor.GREEN, Material.LIME_CONCRETE)
            .put(NamedTextColor.LIGHT_PURPLE, Material.MAGENTA_CONCRETE)
            .put(NamedTextColor.RED, Material.RED_CONCRETE)
            .put(NamedTextColor.WHITE, Material.WHITE_CONCRETE)
            .put(NamedTextColor.YELLOW, Material.YELLOW_CONCRETE)
            .build()
    );

    public static DyeColor toDyeColor(NamedTextColor color) {
        return CHAT_DYE_COLOR_MAP.get(color);
    }

    public static Material toConcrete(NamedTextColor color) {
        return CHAT_CONCRETE_COLOR_MAP.get(color);
    }

    public static NamedTextColor getPunishmentColor(Punishment punishment) {
        switch (punishment.getType()) {
            case BLACKLIST -> { return NamedTextColor.RED; }
            case BAN -> { return NamedTextColor.GOLD; }
            case MUTE -> { return NamedTextColor.GREEN; }
            case WARN -> { return NamedTextColor.BLUE; }
            default -> { return NamedTextColor.WHITE; }
        }
    }

}
