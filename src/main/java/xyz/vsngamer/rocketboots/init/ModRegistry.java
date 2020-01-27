package xyz.vsngamer.rocketboots.init;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import xyz.vsngamer.rocketboots.RocketBoots;
import xyz.vsngamer.rocketboots.enchantment.HoverTimeEnchantment;
import xyz.vsngamer.rocketboots.item.HoverItem;

@Mod.EventBusSubscriber(modid = RocketBoots.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModRegistry {

    public static final HoverItem HOVER_ITEM = new HoverItem();

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> e) {
        e.getRegistry().register(HOVER_ITEM);
    }

    @SubscribeEvent
    public static void registerSounds(RegistryEvent.Register<SoundEvent> e) {
        e.getRegistry().register(ModSounds.HOVER_SOUND);
    }

    @SubscribeEvent
    public static void registerEnchantments(RegistryEvent.Register<Enchantment> e){
        e.getRegistry().register(HoverTimeEnchantment.HOVER_TIME_ENCHANTMENT);
    }


//    public static void onInit() {
//        Registry.register(Registry.ITEM, HOVER_ITEM.getIdentifier(), HOVER_ITEM);
//
//        NetworkManager.init();
//        ModSounds.init();
//
//        HoverTimeEnchantment.register();
//    }

//    public static void onInitClient() {
//        HoverHandler.init();
//        HoveringSoundInstance.initEvents();
//        NetworkManager.initClient();
//    }
}
