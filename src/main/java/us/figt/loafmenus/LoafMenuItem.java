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
