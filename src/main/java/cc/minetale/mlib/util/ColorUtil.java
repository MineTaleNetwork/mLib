package cc.minetale.mlib.util;

import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.color.DyeColor;
import net.minestom.server.item.Material;

import java.util.Map;

public class ColorUtil {

    private static final Map<NamedTextColor, DyeColor> CHAT_DYE_COLOR_MAP = Map.ofEntries(
            Map.entry(NamedTextColor.AQUA, DyeColor.LIGHT_BLUE),
            Map.entry(NamedTextColor.BLACK, DyeColor.BLACK),
            Map.entry(NamedTextColor.BLUE, DyeColor.LIGHT_BLUE),
            Map.entry(NamedTextColor.DARK_AQUA, DyeColor.CYAN),
            Map.entry(NamedTextColor.DARK_BLUE, DyeColor.BLUE),
            Map.entry(NamedTextColor.DARK_GRAY, DyeColor.GRAY),
            Map.entry(NamedTextColor.DARK_GREEN, DyeColor.GREEN),
            Map.entry(NamedTextColor.DARK_PURPLE, DyeColor.PURPLE),
            Map.entry(NamedTextColor.DARK_RED, DyeColor.RED),
            Map.entry(NamedTextColor.GOLD, DyeColor.ORANGE),
            Map.entry(NamedTextColor.GRAY, DyeColor.LIGHT_GRAY),
            Map.entry(NamedTextColor.GREEN, DyeColor.LIME),
            Map.entry(NamedTextColor.LIGHT_PURPLE, DyeColor.MAGENTA),
            Map.entry(NamedTextColor.RED, DyeColor.RED),
            Map.entry(NamedTextColor.WHITE, DyeColor.WHITE),
            Map.entry(NamedTextColor.YELLOW, DyeColor.YELLOW)
    );

    private static final Map<NamedTextColor, Material> CHAT_CONCRETE_COLOR_MAP = Map.ofEntries(
            Map.entry(NamedTextColor.AQUA, Material.LIGHT_BLUE_CONCRETE),
            Map.entry(NamedTextColor.BLACK, Material.BLACK_CONCRETE),
            Map.entry(NamedTextColor.BLUE, Material.LIGHT_BLUE_CONCRETE),
            Map.entry(NamedTextColor.DARK_AQUA, Material.CYAN_CONCRETE),
            Map.entry(NamedTextColor.DARK_BLUE, Material.BLUE_CONCRETE),
            Map.entry(NamedTextColor.DARK_GRAY, Material.GRAY_CONCRETE),
            Map.entry(NamedTextColor.DARK_GREEN, Material.GREEN_CONCRETE),
            Map.entry(NamedTextColor.DARK_PURPLE, Material.PURPLE_CONCRETE),
            Map.entry(NamedTextColor.DARK_RED, Material.RED_CONCRETE),
            Map.entry(NamedTextColor.GOLD, Material.ORANGE_CONCRETE),
            Map.entry(NamedTextColor.GRAY, Material.LIGHT_GRAY_CONCRETE),
            Map.entry(NamedTextColor.GREEN, Material.LIME_CONCRETE),
            Map.entry(NamedTextColor.LIGHT_PURPLE, Material.MAGENTA_CONCRETE),
            Map.entry(NamedTextColor.RED, Material.RED_CONCRETE),
            Map.entry(NamedTextColor.WHITE, Material.WHITE_CONCRETE),
            Map.entry(NamedTextColor.YELLOW, Material.YELLOW_CONCRETE)
    );

    public static DyeColor toDyeColor(NamedTextColor color) {
        return CHAT_DYE_COLOR_MAP.get(color);
    }

    public static Material toConcrete(NamedTextColor color) {
        return CHAT_CONCRETE_COLOR_MAP.get(color);
    }

}
