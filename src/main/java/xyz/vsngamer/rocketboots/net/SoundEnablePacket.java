//package xyz.vsngamer.rocketboots.net;
//
//import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
//import net.minecraft.util.Identifier;
//import xyz.vsngamer.rocketboots.RocketBoots;
//import xyz.vsngamer.rocketboots.client.sound.HoveringSoundInstance;
//
//public class SoundEnablePacket {
//
//    public static final Identifier ID = new Identifier(RocketBoots.ID, "enable_sound");
//
//    static void init() {
//        ClientSidePacketRegistry.INSTANCE.register(ID, (ctx, buf) -> {
//            int playerId = buf.readInt();
//            ctx.getTaskQueue().execute(() -> HoveringSoundInstance.getInstance(playerId).enableForTick());
//        });
//    }
//}
