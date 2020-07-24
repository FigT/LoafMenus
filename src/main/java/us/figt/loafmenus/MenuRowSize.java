/*
 *    Copyright 2020 FigT
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
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
