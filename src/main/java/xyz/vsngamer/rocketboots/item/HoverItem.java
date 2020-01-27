package xyz.vsngamer.rocketboots.item;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import xyz.vsngamer.rocketboots.ModItemGroup;
import xyz.vsngamer.rocketboots.RocketBoots;
import xyz.vsngamer.rocketboots.item.armor.ModArmor;

import javax.annotation.Nullable;
import java.util.List;

public class HoverItem extends ArmorItem {
//    private final ResourceLocation identifier = new ResourceLocation(RocketBoots.ID, "hover_item");

    public HoverItem() {
        super(ModArmor.HOVER, EquipmentSlotType.FEET, new Item.Properties().group(ModItemGroup.GROUP));
        setRegistryName("rocket_boots");
    }

//    public Identifier getIdentifier() {
//        return identifier;
//    }


    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTag() && stack.getTag().contains("hover_time"))
            tooltip.add(new StringTextComponent(String.valueOf(stack.getTag().getInt("hover_time"))).setStyle(new Style().setColor(TextFormatting.GRAY)));
    }

//    @Override
//    public void appendTooltip(ItemStack stack, World world, List<Text> texts, TooltipContext ctx) {
//        if (stack.hasTag() && stack.getTag().containsKey("hover_time"))
//            texts.add(new LiteralText(String.valueOf(stack.getTag().getInt("hover_time"))).formatted(Formatting.GRAY));
//    }


//    @Override
//    public Collection<ItemGroup> getCreativeTabs() {
//        return null;
//    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (this.isInGroup(group)) {
            ItemStack stack = new ItemStack(this);
            stack.getOrCreateTag().putInt("hover_time", 0);
            items.add(stack);
        }
    }

//    @Override
//    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> items) {
//        if (this.isIn(group)) {
//            ItemStack stack = new ItemStack(this);
//            stack.getOrCreateTag().putInt("hover_time", 0);
//            items.add(stack);
//        }
//    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (!(entity instanceof ServerPlayerEntity))
            return;

        ServerPlayerEntity player = (ServerPlayerEntity) entity;
        ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);

        if (!stack.hasTag() || !stack.getTag().contains("hover_time"))
            stack.getOrCreateTag().putInt("hover_time", 0);

        if (boots != stack)
            return;

        if (player.onGround)
            stack.getTag().putInt("hover_time", 0);
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }
}
