package xyz.vsngamer.rocketboots.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import xyz.vsngamer.rocketboots.RocketBoots;

public class ModSounds {
    private static final ResourceLocation HOVER_SOUND_ID = new ResourceLocation(RocketBoots.ID, "hover");
    public static final SoundEvent HOVER_SOUND = new SoundEvent(HOVER_SOUND_ID);

    static {
        HOVER_SOUND.setRegistryName(HOVER_SOUND.getName());
    }
}
