package xyz.vsngamer.rocketboots.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.inventory.EquipmentSlotType;
import xyz.vsngamer.rocketboots.item.HoverItem;

public class HoverTimeEnchantment extends Enchantment {

    public static final EnchantmentType TYPE = EnchantmentType.create("rocket_boots", item -> item instanceof HoverItem);
    public static HoverTimeEnchantment HOVER_TIME_ENCHANTMENT = new HoverTimeEnchantment();

    private HoverTimeEnchantment() {
        super(Rarity.RARE, TYPE, new EquipmentSlotType[]{EquipmentSlotType.FEET});
        setRegistryName("hover_time");
    }

//    public static void register() {
//        HOVER_DURATION_ENCHANTMENT = Registry.register(Registry.ENCHANTMENT, new Identifier(RocketBoots.ID, "hover_time"), new HoverTimeEnchantment());
//    }

//    @Override
//    public boolean isAcceptableItem(ItemStack stack) {
//        return super.isAcceptableItem(stack) && stack.getItem() == ModRegistry.HOVER_ITEM;
//    }


    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 10 + 20 * (enchantmentLevel - 1);
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return getMinEnchantability(enchantmentLevel) + 50;
    }

    @Override
    public int getMaxLevel() {
        return 5;
    }
}
