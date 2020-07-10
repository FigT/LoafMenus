package us.figt.loafmenus.utils;

import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author FigT
 */
public final class StringUtil {

    private StringUtil() {
        throw new AssertionError();
    }

    private static final char COLOR_CODE_CHAR = '&';

    public static String translateColors(String input) {
        return ChatColor.translateAlternateColorCodes(COLOR_CODE_CHAR, input);
    }

    public static List<String> translateColors(List<String> input) {
        return input.stream().map(StringUtil::translateColors).collect(Collectors.toList());
    }
}
