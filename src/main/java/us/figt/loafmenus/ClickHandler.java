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
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

/**
 * @author FigT
 */
@FunctionalInterface
public interface ClickHandler {

    /**
     * Called when a LoafMenuItem gets clicked
     *
     * @param clicker          the player who triggered the InventoryClickEvent
     * @param clickInformation information about the triggered click
     * @return true if the InventoryClickEvent should be cancelled, false if it should not be cancelled
     */
    boolean onClick(Player clicker, ClickInformation clickInformation);


    @Getter
    class ClickInformation {
        private ClickType type;
        private int slot;

        public ClickInformation(ClickType type, int slot) {
            this.type = type;
            this.slot = slot;
        }
    }
}
