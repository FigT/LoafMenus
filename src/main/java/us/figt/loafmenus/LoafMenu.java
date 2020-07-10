package us.figt.loafmenus;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import us.figt.loafmenus.utils.StringUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * @author FigT
 */
@Getter
public abstract class LoafMenu {

    private String name;
    private MenuRowSize rowSize;

    private Map<UUID, Inventory> currentlyOpenedInventories;

    /**
     * Creates a LoafMenu with the specified name and size.
     *
     * @param name the name/title of the inventory (color codes '&' are supported)
     * @param size the size of the inventory
     */
    public LoafMenu(String name, MenuRowSize size) {
        this.name = name;
        this.rowSize = size;
        this.currentlyOpenedInventories = new HashMap<>();
    }

    /**
     * Creates a LoafMenu with the specified name and default chest inventory size.
     *
     * @param name the name/title of the inventory (color codes '&' are supported)
     */
    public LoafMenu(String name) {
        this(name, MenuRowSize.THREE);
    }


    public abstract LoafMenuItem[] getMenuItems(Player player);

    /**
     * Creates an inventory, sets items, adds to map, and opens inventory.
     *
     * @param player the player who wishes to open the inventory
     */
    public void open(Player player) {
        LoafMenuItem[] items = getMenuItems(player);

        // create inventory
        Inventory inventory = Bukkit.getServer().createInventory(null, this.rowSize.getSize(), StringUtil.translateColors(this.name));

        // set items
        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = new LoafMenuItem(new ItemStack(Material.AIR));
            }

            inventory.setItem(i, items[i].getItemStack());
        }

        // open inventory
        player.openInventory(inventory);

        // if contains, replace, else put
        if (this.currentlyOpenedInventories.containsKey(player.getUniqueId())) {
            this.currentlyOpenedInventories.replace(player.getUniqueId(), inventory);
        } else {
            this.currentlyOpenedInventories.put(player.getUniqueId(), inventory);
        }
    }

    /**
     * Creates a new LoafMenuItem array with the bounds of the size of the menu.
     *
     * @return new LoafMenuItem array
     */
    protected LoafMenuItem[] newArray() {
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
    protected LoafMenuItem[] replaceAll(LoafMenuItem[] array, Material material, LoafMenuItem loafMenuItem) {
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
    protected LoafMenuItem[] replaceAll(LoafMenuItem[] array, Material material, ItemStack itemStack) {
        return replaceAll(array, material, new LoafMenuItem(itemStack));
    }
}
