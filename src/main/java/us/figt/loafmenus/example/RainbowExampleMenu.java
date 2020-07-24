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
import org.bukkit.scheduler.BukkitRunnable;
import us.figt.loafmenus.LoafMenu;
import us.figt.loafmenus.LoafMenuItem;
import us.figt.loafmenus.MenuRowSize;
import us.figt.loafmenus.utils.MicroItemBuilder;

import java.util.Random;

/**
 * @author FigT
 */
public class RainbowExampleMenu extends LoafMenu {

    private static final Random RANDOM = new Random();

    private BukkitRunnable rainbowRunnable;


    public RainbowExampleMenu(Player player) {
        super(ExampleMenuPlugin.getInstance().getLoafMenuRegistrar(), "&b&lRainbow Menu", MenuRowSize.SIX, player);

        // this example isn't performant
        rainbowRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                for (int i = 0; i < getInventory().getContents().length; i++) {
                    ItemStack current = getInventory().getItem(i);

                    if (current.getType() == Material.STAINED_GLASS_PANE) {
                        getInventory().setItem(i, new MicroItemBuilder(Material.STAINED_GLASS_PANE).durability(RANDOM.nextInt(13) + 1).build());
                    }
                }
            }
        };

        rainbowRunnable.runTaskTimer(ExampleMenuPlugin.getInstance(), 1L, 10L);

        setCloseHandler(closer -> {
            if (rainbowRunnable != null) {
                rainbowRunnable.cancel();
            }

            closer.sendMessage(ChatColor.LIGHT_PURPLE + "You closed the rainbow menu");
        });
    }

    @Override
    protected LoafMenuItem[] getMenuItems() {
        LoafMenuItem[] array = newLoafMenuItemArray();

        // replace all air with plain stained glass pane, with a custom ClickHandler
        // this will apply to all rainbow items set in runnable
        replaceAll(array, Material.AIR, new LoafMenuItem(new ItemStack(Material.STAINED_GLASS_PANE), (clicker, clickInformation) -> {
            clicker.sendMessage("You clicked a rainbow menu item in slot " + clickInformation.getSlot());
            return true;
        }));

        return array;
    }
}
