package us.figt.loafmenus;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author FigT
 */
public enum MenuRowSize {

    ONE(9),
    TWO(18),
    THREE(27),
    FOUR(36),
    FIVE(45),
    SIX(54);

    @Getter
    private int size;

    MenuRowSize(int size) {
        this.size = size;
    }

    /**
     * Parses a MenuRowSize from an integer
     *
     * @param integer the integer to parse
     * @return the found MenuRowSize, or if the integer couldn't be parsed, MenuRowSize.THREE (normal chest size)
     */
    public static MenuRowSize fromInteger(int integer) {
        return Arrays.stream(values()).filter(value -> value.size == integer).findFirst().orElse(THREE);
    }
}
