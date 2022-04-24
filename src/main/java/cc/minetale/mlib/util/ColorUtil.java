package cc.minetale.mlib.util;

import net.kyori.adventure.text.format.NamedTextColor;
import net.minestom.server.color.DyeColor;
import net.minestom.server.item.Material;

import java.util.HashMap;
import java.util.Map;

public class ColorUtil {

    private static final Map<NamedTextColor, DyeColor> CHAT_DYE_COLOR_MAP = new HashMap<>();
    private static final Map<NamedTextColor, Material> CHAT_CONCRETE_COLOR_MAP = new HashMap<>();

    static {
        CHAT_DYE_COLOR_MAP.put(NamedTextColor.AQUA, DyeColor.LIGHT_BLUE);
        CHAT_DYE_COLOR_MAP.put(NamedTextColor.BLACK, DyeColor.BLACK);
        CHAT_DYE_COLOR_MAP.put(NamedTextColor.BLUE, DyeColor.LIGHT_BLUE);
        CHAT_DYE_COLOR_MAP.put(NamedTextColor.DARK_AQUA, DyeColor.CYAN);
        CHAT_DYE_COLOR_MAP.put(NamedTextColor.DARK_BLUE, DyeColor.BLUE);
        CHAT_DYE_COLOR_MAP.put(NamedTextColor.DARK_GRAY, DyeColor.GRAY);
        CHAT_DYE_COLOR_MAP.put(NamedTextColor.DARK_GREEN, DyeColor.GREEN);
        CHAT_DYE_COLOR_MAP.put(NamedTextColor.DARK_PURPLE, DyeColor.PURPLE);
        CHAT_DYE_COLOR_MAP.put(NamedTextColor.DARK_RED, DyeColor.RED);
        CHAT_DYE_COLOR_MAP.put(NamedTextColor.GOLD, DyeColor.ORANGE);
        CHAT_DYE_COLOR_MAP.put(NamedTextColor.GRAY, DyeColor.LIGHT_GRAY);
        CHAT_DYE_COLOR_MAP.put(NamedTextColor.GREEN, DyeColor.LIME);
        CHAT_DYE_COLOR_MAP.put(NamedTextColor.LIGHT_PURPLE, DyeColor.MAGENTA);
        CHAT_DYE_COLOR_MAP.put(NamedTextColor.RED, DyeColor.RED);
        CHAT_DYE_COLOR_MAP.put(NamedTextColor.WHITE, DyeColor.WHITE);
        CHAT_DYE_COLOR_MAP.put(NamedTextColor.YELLOW, DyeColor.YELLOW);

        CHAT_CONCRETE_COLOR_MAP.put(NamedTextColor.AQUA, Material.LIGHT_BLUE_CONCRETE);
        CHAT_CONCRETE_COLOR_MAP.put(NamedTextColor.BLACK, Material.BLACK_CONCRETE);
        CHAT_CONCRETE_COLOR_MAP.put(NamedTextColor.BLUE, Material.LIGHT_BLUE_CONCRETE);
        CHAT_CONCRETE_COLOR_MAP.put(NamedTextColor.DARK_AQUA, Material.CYAN_CONCRETE);
        CHAT_CONCRETE_COLOR_MAP.put(NamedTextColor.DARK_BLUE, Material.BLUE_CONCRETE);
        CHAT_CONCRETE_COLOR_MAP.put(NamedTextColor.DARK_GRAY, Material.GRAY_CONCRETE);
        CHAT_CONCRETE_COLOR_MAP.put(NamedTextColor.DARK_GREEN, Material.GREEN_CONCRETE);
        CHAT_CONCRETE_COLOR_MAP.put(NamedTextColor.DARK_PURPLE, Material.PURPLE_CONCRETE);
        CHAT_CONCRETE_COLOR_MAP.put(NamedTextColor.DARK_RED, Material.RED_CONCRETE);
        CHAT_CONCRETE_COLOR_MAP.put(NamedTextColor.GOLD, Material.ORANGE_CONCRETE);
        CHAT_CONCRETE_COLOR_MAP.put(NamedTextColor.GRAY, Material.LIGHT_GRAY_CONCRETE);
        CHAT_CONCRETE_COLOR_MAP.put(NamedTextColor.GREEN, Material.LIME_CONCRETE);
        CHAT_CONCRETE_COLOR_MAP.put(NamedTextColor.LIGHT_PURPLE, Material.MAGENTA_CONCRETE);
        CHAT_CONCRETE_COLOR_MAP.put(NamedTextColor.RED, Material.RED_CONCRETE);
        CHAT_CONCRETE_COLOR_MAP.put(NamedTextColor.WHITE, Material.WHITE_CONCRETE);
        CHAT_CONCRETE_COLOR_MAP.put(NamedTextColor.YELLOW, Material.YELLOW_CONCRETE);
    }

    public static DyeColor toDyeColor(NamedTextColor color) {
        return CHAT_DYE_COLOR_MAP.get(color);
    }

    public static Material toConcrete(NamedTextColor color) {
        return CHAT_CONCRETE_COLOR_MAP.get(color);
    }

}
