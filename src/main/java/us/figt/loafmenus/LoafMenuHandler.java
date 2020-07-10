package us.figt.loafmenus;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

/**
 * @author FigT
 */
@Getter
public class LoafMenuHandler {

    private JavaPlugin plugin;
    private Map<Class, LoafMenu> loafMenus;


    private static boolean registered = false;

    /**
     * Instantiates a new LoafMenuHandler
     *
     * @param plugin the JavaPlugin this LoafMenuHandler belongs to
     * @throws InstantiationException if a LoafMenuHandler has already been registered
     */
    public LoafMenuHandler(JavaPlugin plugin) throws InstantiationException {
        if (registered) {
            throw new InstantiationException("LoafMenuHandler has already been registered");
        }

        this.plugin = plugin;
        this.loafMenus = new HashMap<>();
        registered = true;

        this.plugin.getServer().getPluginManager().registerEvents(new LoafMenuListener(this), this.plugin);
    }

    /**
     * Registers a class & instance of a LoafMenu.
     * NOTE: Call this method in onEnable
     *
     * @param clazz    the class of a LoafMenu to register
     * @param loafMenu an instance of a LoafMenu class to register
     * @return true if it has been registered, false if the class has already been registered
     */
    public boolean registerLoafMenu(Class<? extends LoafMenu> clazz, LoafMenu loafMenu) {
        if (this.loafMenus.containsKey(clazz)) {
            return false;
        }

        this.loafMenus.put(clazz, loafMenu);
        return true;
    }

    /**
     * Gets an instance of a LoafMenu from a class.
     *
     * @param clazz the class of a LoafMenu
     * @return the LoafMenu (if registered)
     * @throws IllegalArgumentException if the class has not been registered yet
     */
    public LoafMenu getLoafMenu(Class<? extends LoafMenu> clazz) {
        if (this.loafMenus.containsKey(clazz)) {
            return this.loafMenus.get(clazz);
        }

        throw new IllegalArgumentException(clazz.getSimpleName() + " has not been registered in the LoafMenuHandler");
    }

    /**
     * Gets an instance of a currently opened LoafMenu from a player.
     *
     * @param player the player who is viewing the inventory
     * @return the currently opened LoafMenu or null if not found
     */
    public LoafMenu getOpenLoafMenuFromPlayer(Player player) {
        for (LoafMenu loafMenu : loafMenus.values()) {
            if (loafMenu.getCurrentlyOpenedInventories().containsKey(player.getUniqueId())) {
                return loafMenu;
            }
        }
        return null;
    }


    /**
     * Cleans up objects, unregisters the handler, etc.
     * NOTE: Call this method in onDisable
     */
    public void unregister() {
        if (this.loafMenus != null) {

            this.loafMenus.forEach((clazz, loafMenu) -> {
                loafMenu.getCurrentlyOpenedInventories().forEach((uuid, inv) -> {
                    Player player = this.plugin.getServer().getPlayer(uuid);

                    if (player != null && player.getOpenInventory() != null) {
                        player.closeInventory();
                    }

                    inv.clear();
                });

                loafMenu.getCurrentlyOpenedInventories().clear();
            });


            this.loafMenus.clear();
            this.loafMenus = null;
        }

        if (this.plugin != null) this.plugin = null;


        registered = false;
    }
}
