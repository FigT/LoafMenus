package us.figt.loafmenus.example;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import us.figt.loafmenus.LoafMenu;
import us.figt.loafmenus.LoafMenuItem;
import us.figt.loafmenus.MenuRowSize;
import us.figt.loafmenus.utils.MicroItemBuilder;

/**
 * @author FigT
 */
public class HopperExampleMenu extends LoafMenu {

    public HopperExampleMenu(Player player) {
        super(ExampleMenuPlugin.getInstance().getLoafMenuRegistrar(), "&6Hopper Menu", MenuRowSize.HOPPER, player);
    }

    @Override
    public LoafMenuItem[] getMenuItems() {
        LoafMenuItem[] items = newLoafMenuItemArray();


        // index 2 is the center
        items[2] = new LoafMenuItem(new MicroItemBuilder(Material.CAKE).name("&d&l&oCake").build(), (clicker, clickInformation) -> {
            clicker.sendMessage(ChatColor.LIGHT_PURPLE + "You clicked a cake in a HOPPER menu!");
            return true;
        });

        return items;
    }
}
