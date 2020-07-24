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
import org.bukkit.inventory.ItemStack;

/**
 * @author FigT
 */
@Getter
public class LoafMenuItem {

    private ItemStack itemStack;
    private ClickHandler clickHandler;


    /**
     * Creates a standard LoafMenuItem.
     *
     * @param itemStack    the ItemStack displayed in the menu
     * @param clickHandler the ClickHandler
     */
    public LoafMenuItem(ItemStack itemStack, ClickHandler clickHandler) {
        this.itemStack = itemStack;
        this.clickHandler = clickHandler;
    }

    /**
     * Creates a LoafMenuItem with no ClickHandler.
     *
     * @param itemStack the ItemStack displayed in the menu
     */
    public LoafMenuItem(ItemStack itemStack) {
        this(itemStack, null);
    }
}
