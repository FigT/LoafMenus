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
