package xyz.vsngamer.rocketboots;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import xyz.vsngamer.rocketboots.client.sound.HoveringSoundInstance;
import xyz.vsngamer.rocketboots.net.HoverPacket;
import xyz.vsngamer.rocketboots.net.NetworkManager;

@Mod.EventBusSubscriber(modid = RocketBoots.ID, value = Dist.CLIENT)
public class HoverHandler {

    private static boolean lastJumping;
    private static boolean hoverMode = false;

    @SubscribeEvent
    public static void playerTick(TickEvent.PlayerTickEvent e) {
        if (e.side == LogicalSide.SERVER)
            return;

//        RocketBoots.LOGGER.debug(e.side);

        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null || !player.isAlive() || !HoverPacket.getHoverItem(player).isPresent()) {
            return;
        }

        checkHoverMode(player);

        ItemStack item = HoverPacket.getHoverItem(player).get();

        if (hoverMode && HoverPacket.hasTime(item) && player.movementInput.jump) {
//            ClientSidePacketRegistry.INSTANCE.sendToServer(HoverPacket.ID, new PacketByteBuf(Unpooled.buffer()));
            NetworkManager.INSTANCE.sendToServer(new HoverPacket());

            HoverPacket.hover(player);
            HoveringSoundInstance.getOrAddInstance(player).enableForTick();
        }

        lastJumping = player.movementInput.jump;
    }

//    public static void init() {
//        PlayerTickCallback.EVENT.register(p -> {
//            if (!validPlayer(p) || !HoverPacket.getHoverItem(p).isPresent()) {
//                return;
//            }
//
//            checkHoverMode(p);
//
//            ItemStack item = HoverPacket.getHoverItem(p).get();
//
//            if (hoverMode && HoverPacket.hasTime(item) && p.input.jumping) {
//                ClientSidePacketRegistry.INSTANCE.sendToServer(HoverPacket.ID, new PacketByteBuf(Unpooled.buffer()));
//                HoverPacket.hover(p);
//                HoveringSoundInstance.getInstance(p.getEntityId()).enableForTick();
//            }
//
//            lastJumping = p.input.jumping;
//        });
//    }

    private static void checkHoverMode(ClientPlayerEntity p) {
        if (p.onGround || p.isSpectator() || p.getRidingEntity() != null) {
            hoverMode = false;
            return;
        }

        if (!lastJumping && p.movementInput.jump && p.getMotion().y < 0) {
            hoverMode = true;
        }
    }

    @SubscribeEvent
    public static void joinStartSound(EntityJoinWorldEvent e) {
//        if (Minecraft.getInstance().player != null && e.getEntity() == Minecraft.getInstance().player) {
//            Minecraft.getInstance().getSoundHandler().play(new HoveringSoundInstance(Minecraft.getInstance().player));
//        }
    }
}
