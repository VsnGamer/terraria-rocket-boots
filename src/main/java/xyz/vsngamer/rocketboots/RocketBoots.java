package xyz.vsngamer.rocketboots;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import xyz.vsngamer.rocketboots.net.NetworkManager;

@Mod(RocketBoots.ID)
public class RocketBoots {

    public static final String ID = "rocketboots";
    public static final Logger LOGGER = LogManager.getLogger();

    public RocketBoots() {
        NetworkManager.init();
    }
}
