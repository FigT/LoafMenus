package us.figt.loafmenus.example;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import us.figt.loafmenus.LoafMenu;
import us.figt.loafmenus.LoafMenuItem;
import us.figt.loafmenus.MenuRowSize;
import us.figt.loafmenus.utils.MicroItemBuilder;

import java.util.Arrays;

/**
 * @author FigT
 */
public class ExampleMenu extends LoafMenu {

    public ExampleMenu() {
        super("&a&lExample Menu", MenuRowSize.THREE);
    }

    @Override
    public LoafMenuItem[] getMenuItems(Player player) {
        // newArray() is a shortcut method in LoafMenu to create a new LoafMenuItem array with the bounds of the menu size
        LoafMenuItem[] array = newArray();

        LoafMenuItem test = new LoafMenuItem(new MicroItemBuilder(Material.WOOL).durability(5).lore(Arrays.asList("&7...", "&a...")).name("&a&lTest").build(), (clicker, clickInformation) -> {
            clicker.sendMessage(ChatColor.GREEN + "You " + clickInformation.getType().name() + " clicked slot " + clickInformation.getSlot());
            clicker.setLevel(clicker.getLevel() + 5);
            return true;
        });

        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(player.getName());
        skullMeta.setDisplayName(ChatColor.AQUA + player.getName() + "'s Head");
        skull.setItemMeta(skullMeta);


        LoafMenuItem clickAllowed = new LoafMenuItem(new ItemStack(Material.APPLE), (clicker, clickInformation) -> {
            clicker.sendMessage(ChatColor.LIGHT_PURPLE + "Boop");
            return false;
        });


        array[4] = clickAllowed;
        array[13] = test; // slot 13 is the center
        array[22] = new LoafMenuItem(skull);

        array = replaceAll(array, Material.AIR, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 9));

        return array;
    }
}
