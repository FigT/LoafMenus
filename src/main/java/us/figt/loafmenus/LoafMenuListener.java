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

    private LoafMenuHandler loafMenuHandler;

    public LoafMenuListener(LoafMenuHandler loafMenuHandler) {
        this.loafMenuHandler = loafMenuHandler;
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getWhoClicked();

        LoafMenu openMenu = this.loafMenuHandler.getOpenLoafMenuFromPlayer(player);

        // make sure the menu is not null and the clicked inventory is not the player's inventory
        if (openMenu == null || (event.getClickedInventory() != null && event.getClickedInventory().getType() == InventoryType.PLAYER)) {
            return;
        }

        // TODO: possible optimization
        LoafMenuItem[] items = openMenu.getMenuItems(player);

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

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (!(event.getPlayer() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getPlayer();

        LoafMenu openMenu = this.loafMenuHandler.getOpenLoafMenuFromPlayer(player);

        if (openMenu != null) {
            openMenu.getCurrentlyOpenedInventories().remove(player.getUniqueId());
        }
    }
}
