package us.bemrose.mc.pitweaks;

public class FastFurnaceTweak extends Tweak {

    @Override
    public void init(net.minecraftforge.fml.common.event.FMLInitializationEvent event) {
        // Register block place event
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(this);
        // Register new tile entity
        net.minecraftforge.fml.common.registry.GameRegistry.registerTileEntity(TileEntityFastFurnace.class, "PiTweaksFastFurnace");
    }

    // BlockEvent
    @net.minecraftforge.fml.common.eventhandler.SubscribeEvent
    public void onBlockPlaced(net.minecraftforge.event.world.BlockEvent.PlaceEvent event) {

        // When a furnace is placed, replace its tile entity with our own
        if (TweakConfig.furnace.fastFurnace) {
            if (event.getPlacedBlock().getBlock() == net.minecraft.init.Blocks.FURNACE) {
                event.getWorld().setTileEntity(event.getBlockSnapshot().getPos(), new TileEntityFastFurnace());
            }
        }
    }
}
