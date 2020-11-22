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
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import us.figt.loafmenus.utils.StringUtil;

import java.util.function.Consumer;


/**
 * @author FigT
 */
@Getter
public abstract class LoafMenu {

    private LoafMenuRegistrar loafMenuRegistrar;

    @Setter
    private String name;
    private MenuRowSize rowSize;
    private Player player;
    private Inventory inventory;
    @Setter
    private Consumer<Player> closeHandler;

    private LoafMenuItem[] internalItems;

    /**
     * Creates a LoafMenu with the specified name and size.
     *
     * @param name the name/title of the inventory (color codes '&' are supported)
     * @param size the size of the inventory
     */
    public LoafMenu(@NonNull LoafMenuRegistrar loafMenuRegistrar, @NonNull String name, @NonNull MenuRowSize size, @NonNull Player player) {
        this.loafMenuRegistrar = loafMenuRegistrar;
        this.name = name;
        this.rowSize = size;
        this.player = player;
        this.inventory = this.rowSize.toInventory(null, StringUtil.translateColors(this.name));
        this.internalItems = new LoafMenuItem[this.rowSize.getSize()];
    }


    public abstract LoafMenuItem[] getMenuItems();

    /**
     * Creates inventory, sets items, adds to map, and opens inventory.
     */
    public void open() {
        // if the title has changed, create new inventory with that title
        if (!StringUtil.equalsTranslateColors(this.inventory.getName(), this.name)) {
            this.inventory = this.rowSize.toInventory(null, StringUtil.translateColors(this.name));
        }


        // store the abstract items in the internal items array
        this.internalItems = getMenuItems();

        // set items
        for (int i = 0; i < this.internalItems.length; i++) {
            if (this.internalItems[i] == null) {
                this.internalItems[i] = new LoafMenuItem(new ItemStack(Material.AIR));
            }

            this.inventory.setItem(i, this.internalItems[i].getItemStack());
        }

        // open inventory
        this.player.openInventory(this.inventory);

        // if contains, replace, else put
        if (this.loafMenuRegistrar.getOpenedLoafMenus().containsKey(this.player.getUniqueId())) {
            this.loafMenuRegistrar.getOpenedLoafMenus().replace(this.player.getUniqueId(), this);
        } else {
            this.loafMenuRegistrar.getOpenedLoafMenus().put(this.player.getUniqueId(), this);
        }
    }

    /**
     * Removes from map, closes inventory, and cleans up the LoafMenu's fields.
     * NOTE: Call this method when closing a menu, do NOT use {@link org.bukkit.entity.Player#closeInventory()}!
     */
    public void close() {
        // remove from map
        this.loafMenuRegistrar.getOpenedLoafMenus().remove(this.player.getUniqueId());

        // close inventory & clear inventory
        this.player.closeInventory();
        cleanup();
    }

    /**
     * Cleans up this LoafMenu's fields (only used internally).
     */
    void cleanup() {
        this.inventory.clear();

        this.name = null;
        this.rowSize = null;
        this.player = null;
        this.inventory = null;
        this.internalItems = null;
    }

    /**
     * Creates a new LoafMenuItem array with the bounds of the size of the menu.
     *
     * @return new LoafMenuItem array
     */
    public LoafMenuItem[] newLoafMenuItemArray() {
        return new LoafMenuItem[this.rowSize.getSize()];
    }

    /**
     * Replaces all of a Material with a LoafMenuItem.
     *
     * @param array        an input LoafMenuItem array
     * @param material     the Material to replace
     * @param loafMenuItem the LoafMenuItem to replace the Material with
     * @return the new (output) array with the Materials replaced
     */
    public LoafMenuItem[] replaceAll(LoafMenuItem[] array, Material material, LoafMenuItem loafMenuItem) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == null) {
                array[i] = new LoafMenuItem(new ItemStack(Material.AIR));
            }

            if (array[i].getItemStack().getType() == material) {
                array[i] = loafMenuItem;
            }
        }

        return array;
    }

    /**
     * Replaces all of a Material with an ItemStack.
     *
     * @param array     an input LoafMenuItem array
     * @param material  the Material to replace
     * @param itemStack the ItemStack to replace the Material with
     * @return the new (output) array with the Materials replaced
     */
    public LoafMenuItem[] replaceAll(LoafMenuItem[] array, Material material, ItemStack itemStack) {
        return replaceAll(array, material, new LoafMenuItem(itemStack));
    }
}
