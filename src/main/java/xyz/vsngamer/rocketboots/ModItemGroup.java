package xyz.vsngamer.rocketboots;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import xyz.vsngamer.rocketboots.enchantment.HoverTimeEnchantment;
import xyz.vsngamer.rocketboots.init.ModRegistry;

public class ModItemGroup extends ItemGroup {

    public static final ItemGroup GROUP = new ModItemGroup();

    public ModItemGroup() {
        super("rocketboots_tab");
        setRelevantEnchantmentTypes(HoverTimeEnchantment.TYPE);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModRegistry.HOVER_ITEM);
    }
}
