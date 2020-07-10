package us.figt.loafmenus.utils;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * @author FigT
 */
public class MicroItemBuilder {

    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public MicroItemBuilder(@NonNull Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = this.itemStack.getItemMeta();
    }

    public MicroItemBuilder amount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public MicroItemBuilder durability(int durability) {
        this.itemStack.setDurability((short) durability);
        return this;
    }

    public MicroItemBuilder name(@NonNull String name) {
        this.itemMeta.setDisplayName(StringUtil.translateColors(name));
        return this;
    }

    public MicroItemBuilder lore(@NonNull List<String> lore) {
        this.itemMeta.setLore(StringUtil.translateColors(lore));
        return this;
    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.itemMeta);
        return this.itemStack;
    }
}
