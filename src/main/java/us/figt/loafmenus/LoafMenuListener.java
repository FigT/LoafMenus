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

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

/**
 * @author FigT
 */
public class LoafMenuListener implements Listener {

    private LoafMenuRegistrar loafMenuRegistrar;

    public LoafMenuListener(LoafMenuRegistrar loafMenuRegistrar) {
        this.loafMenuRegistrar = loafMenuRegistrar;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        LoafMenu openMenu = this.loafMenuRegistrar.getOpenedLoafMenus().get(player.getUniqueId());

        // make sure the menu is not null and the clicked inventory is not the player's inventory
        if (openMenu == null || (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.PLAYER)) {
            return;
        }

        LoafMenuItem[] items = openMenu.getInternalItems();

        for (int i = 0; i < items.length; i++) {
            LoafMenuItem current = items[i];

            // check the slot
            // maybe in the future, check display name and/or material?
            if (event.getSlot() == i) {
                // no click handler? cancel click
                if (current.getClickHandler() == null) {
                    event.setCancelled(true);
                    return;
                }

                // call the onClick & get if we should cancel
                boolean cancel = current.getClickHandler().onClick(player, new ClickHandler.ClickInformation(event.getClick(), event.getSlot()));

                if (cancel) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onInventoryClose(InventoryCloseEvent event) {
        // getPlayer() returns HumanEntity, make sure it's a player
        if (!(event.getPlayer() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getPlayer();


        LoafMenu openMenu = this.loafMenuRegistrar.getOpenedLoafMenus().get(player.getUniqueId());

        // if the player does have an open LoafMenu
        if (openMenu != null) {
            // if CloseHandler is not null, deal with that
            if (openMenu.getCloseHandler() != null) {
                openMenu.getCloseHandler().accept(player);
            }

            // remove from map, cleanup menu object
            this.loafMenuRegistrar.getOpenedLoafMenus().remove(player.getUniqueId());
            openMenu.cleanup();
        }
    }
}
