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
package us.figt.loafmenus.example;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import us.figt.loafmenus.LoafMenu;
import us.figt.loafmenus.LoafMenuItem;
import us.figt.loafmenus.MenuRowSize;
import us.figt.loafmenus.utils.MicroItemBuilder;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author FigT
 */
public class ExampleMenu extends LoafMenu {

    public ExampleMenu(Player player) {
        super(ExampleMenuPlugin.getInstance().getLoafMenuRegistrar(), "&a&lExample Menu", MenuRowSize.FIVE, player);

        setCloseHandler(closer -> closer.sendMessage(ChatColor.RED + "You closed the example menu"));
    }

    @Override
    public LoafMenuItem[] getMenuItems() {
        // newLoafMenuItemArray() is a small shortcut method
        LoafMenuItem[] array = newLoafMenuItemArray();

        LoafMenuItem test = new LoafMenuItem(new MicroItemBuilder(Material.DIAMOND_SWORD).name("&b&lTest Item").lore(Collections.singletonList("&7Click me!")).build(), (clicker, clickInformation) -> {
            clicker.sendMessage(ChatColor.GREEN + "You " + clickInformation.getType().name() + " clicked slot " + clickInformation.getSlot());

            if (clicker.isOp()) {
                clicker.sendMessage("Did not cancel event because you're OP.");
                // returning false will not cancel the click event
                return false;
            }

            // returning true will cancel the click event
            return true;
        });

        ItemStack playerInfoItem = new ItemStack(Material.NAME_TAG);
        ItemMeta playerInfoItemMeta = playerInfoItem.getItemMeta();
        playerInfoItemMeta.setDisplayName(ChatColor.GOLD + getPlayer().getName());
        playerInfoItemMeta.setLore(Arrays.asList("EXP: " + getPlayer().getExp(), "Is Flying: " + getPlayer().isFlying()));
        playerInfoItem.setItemMeta(playerInfoItemMeta);

        // set the items to the respective slots
        array[21] = test;
        array[23] = new LoafMenuItem(playerInfoItem);

        // replace all air with white stained glass pane
        replaceAll(array, Material.AIR, new ItemStack(Material.STAINED_GLASS_PANE));

        return array;
    }
}
