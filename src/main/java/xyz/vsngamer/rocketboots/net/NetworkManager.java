package xyz.vsngamer.rocketboots.net;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkManager {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("mymodid", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public static void init(){
        byte i = 0;
        INSTANCE.registerMessage(i++, HoverPacket.class, HoverPacket::encode, HoverPacket::decode, HoverPacket::handle);
    }

//    public static void init() {
//        HoverPacket.init();
//    }
//
//    public static void initClient() {
//        SoundEnablePacket.init();
//    }
}
