package xyz.vsngamer.rocketboots.client.sound;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import xyz.vsngamer.rocketboots.init.ModSounds;

import java.util.HashMap;
import java.util.Map;

public class HoveringSoundInstance extends TickableSound {
    private static final Map<Integer, HoveringSoundInstance> instances = new HashMap<>();

    private final AbstractClientPlayerEntity player;
    private boolean willPlay;

    public HoveringSoundInstance(AbstractClientPlayerEntity player) {
        super(ModSounds.HOVER_SOUND, SoundCategory.NEUTRAL);
        repeat = true;
        volume = 0;
        this.player = player;

        instances.put(player.getEntityId(), this);
    }

    @Override
    public void tick() {
        if (!player.isAlive()) {
            donePlaying = true;
            instances.remove(player.getEntityId());
        } else {
            volume = 0;
            x = (float) player.getPosX();
            y = (float) player.getPosY();
            z = (float) player.getPosZ();

            if (willPlay) {
                if (player instanceof RemoteClientPlayerEntity) {
                    float dist = Minecraft.getInstance().player.getDistance(player);
                    volume = MathHelper.lerp(dist / 48, 1F, 0F);
                } else {
                    volume = 1;
                }
                willPlay = false;
            }
        }
    }

    @Override
    public boolean canBeSilent() {
        return true;
    }

//    private static boolean notPlaying(int id) {
//        return !instances.containsKey(id) || instances.get(id) == null;
//    }

    // TODO rewrite
    public static HoveringSoundInstance getInstance(int id) {
        if (instances.get(id) == null) {
            if (Minecraft.getInstance().world != null) {
                Minecraft.getInstance().getSoundHandler().play(new HoveringSoundInstance((AbstractClientPlayerEntity) Minecraft.getInstance().world.getEntityByID(id)));
            }
        }
        return instances.get(id);
    }

    public void enableForTick() {
        willPlay = true;
    }

//    public static void initEvents() {
//        MinecraftClient minecraftClient = MinecraftClient.getInstance();
//
//        PlayerSpawnCallback.EVENT.register(pkt -> {
//            OtherClientPlayerEntity otherPlayer = (OtherClientPlayerEntity) minecraftClient.world.getEntityById(pkt.getId());
//            if (otherPlayer != null && notPlaying(otherPlayer.getEntityId())) {
//                minecraftClient.getSoundManager().play(new HoveringSoundInstance(otherPlayer));
//            }
//        });
//
//        ClientPlayerJoinCallback.EVENT.register(() -> {
//            if (notPlaying(minecraftClient.player.getEntityId()))
//                minecraftClient.getSoundManager().play(new HoveringSoundInstance(minecraftClient.player));
//        });
//
//        ClientPlayerRespawnCallback.EVENT.register(() -> {
//            if (notPlaying(minecraftClient.player.getEntityId()))
//                minecraftClient.getSoundManager().play(new HoveringSoundInstance(minecraftClient.player));
//        });
//    }
}
