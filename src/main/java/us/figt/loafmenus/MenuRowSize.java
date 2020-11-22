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
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Arrays;

/**
 * @author FigT
 */
@Getter
public enum MenuRowSize {

    // CHEST SIZES
    ONE(9),
    TWO(18),
    THREE(27),
    FOUR(36),
    FIVE(45),
    SIX(54),
    // OTHER TYPES
    BOX(9, InventoryType.DISPENSER),
    HOPPER(5, InventoryType.HOPPER);

    private int size;
    private InventoryType inventoryType;

    MenuRowSize(int size) {
        this.size = size;
    }

    MenuRowSize(int size, InventoryType inventoryType) {
        this(size);
        this.inventoryType = inventoryType;
    }

    public boolean isSpecialType() {
        return this.inventoryType != null;
    }

    /**
     * Creates an inventory based on the type of MenuRowSize
     *
     * @param holder the owner of the inventory
     * @param title  the title to be displayed
     * @return the newly created inventory
     */
    public Inventory toInventory(InventoryHolder holder, String title) {
        switch (this) {
            case BOX:
            case HOPPER:
                return Bukkit.createInventory(holder, inventoryType, title);
            default:
                return Bukkit.createInventory(holder, size, title);
        }
    }

    /**
     * Parses a MenuRowSize from an integer
     * (doesn't return special types)
     *
     * @param integer the integer to parse
     * @return the found MenuRowSize, or if the integer couldn't be parsed, MenuRowSize.THREE (normal chest size)
     */
    public static MenuRowSize fromInteger(int integer) {
        return Arrays.stream(values()).filter(value -> !value.isSpecialType()).filter(value -> value.size == integer).findFirst().orElse(THREE);
    }
}
