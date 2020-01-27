package xyz.vsngamer.rocketboots.net;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import xyz.vsngamer.rocketboots.enchantment.HoverTimeEnchantment;
import xyz.vsngamer.rocketboots.init.ModRegistry;

import java.util.Optional;
import java.util.function.Supplier;

public class HoverPacket {

//    public static final Identifier ID = new Identifier(RocketBoots.ID, "hover");

//    static void init() {
//        ServerSidePacketRegistry.INSTANCE.register(ID, (ctx, buf) -> ctx.getTaskQueue().execute(() -> {
//            ServerPlayerEntity player = (ServerPlayerEntity) ctx.getPlayer();
//            if (!getHoverItem(player).isPresent())
//                return;
//
//            ItemStack item = getHoverItem(player).get();
//
//            if (hasTime(item)) {
//                hover(player);
//                spawnParticles(player);
//                sendPackets(player);
//
//                int debug = addTime(item);
//                player.sendMessage(new LiteralText(String.valueOf(debug)));
//            }
//        }));
//    }

//    private static void sendPackets(ServerPlayerEntity player) {
//        for (PlayerEntity p : player.world.getPlayers()) {
//            if (p.dimension == player.dimension && p != player) {
//                double relX = player.x - p.x;
//                double relY = player.y - p.y;
//                double relZ = player.z - p.z;
//                if (relX * relX + relY * relY + relZ * relZ < 2304) {
//                    PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
//                    buf.writeInt(player.getEntityId());
//                    ServerSidePacketRegistry.INSTANCE.sendToPlayer(p, SoundEnablePacket.ID, buf);
//                }
//            }
//        }
//    }

    private static int addTime(ItemStack stack) {
        if (!stack.hasTag() || !stack.getTag().contains("hover_time"))
            return -1;

        int t = stack.getTag().getInt("hover_time") + 1;
        stack.getTag().putInt("hover_time", t);

        return t;
    }

    public static Optional<ItemStack> getHoverItem(PlayerEntity player) {
        ItemStack boots = player.getItemStackFromSlot(EquipmentSlotType.FEET);
        if (boots.getItem() == ModRegistry.HOVER_ITEM)
            return Optional.of(boots);

        return Optional.empty();
    }

    public static boolean hasTime(ItemStack stack) {
        if (!stack.hasTag() || !stack.getTag().contains("hover_time"))
            return false;

        int level = EnchantmentHelper.getEnchantmentLevel(HoverTimeEnchantment.HOVER_TIME_ENCHANTMENT, stack);
        return stack.getTag().getInt("hover_time") < (32 + (((double) level / 4) * 32)); // +25% per level
    }


    public static void hover(PlayerEntity player) {
        Vec3d vel = player.getMotion();
        vel = new Vec3d(vel.x, Math.min(vel.y + .08D, .6D), vel.z);

        if (player.isSprinting()) {
            double accel = player.rotationYaw * ((float)Math.PI / 180F);
            vel = vel.add(-MathHelper.sin((float) accel) * 0.06F, 0.0D, MathHelper.cos((float) accel) * 0.06F);
        }

        player.setMotion(vel);
        player.fallDistance = 0;
    }

    private static void spawnParticles(ServerPlayerEntity player) {
        ServerWorld world = player.getServerWorld();
        Supplier<Double> mot = () -> world.getRandom().nextGaussian() * .02D;

        world.spawnParticle(ParticleTypes.FLAME, player.getPosX(), player.getPosY(), player.getPosZ(), 1, mot.get(), mot.get(), mot.get(), .05D);
        world.spawnParticle(ParticleTypes.SMOKE, player.getPosX(), player.getPosY(), player.getPosZ(), 20, mot.get(), mot.get(), mot.get(), .05D);
    }

    public static void encode(HoverPacket msg, PacketBuffer buf) {

    }

    public static HoverPacket decode(PacketBuffer buf) {
        return new HoverPacket();
    }

    public static void handle(HoverPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayerEntity player = ctx.get().getSender();

            if (player == null)
                return;

            Optional<ItemStack> itemOpt = getHoverItem(player);
            if (!itemOpt.isPresent())
                return;

            ItemStack item = itemOpt.get();
            if (hasTime(item)) {
                hover(player);
                spawnParticles(player);
//                sendPackets(player);

                int debug = addTime(item);
                player.sendMessage(new StringTextComponent(String.valueOf(debug)));
            }
        });

        ctx.get().setPacketHandled(true);
    }
}
