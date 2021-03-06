package us.bemrose.mc.pitweaks;

import net.minecraft.entity.item.EntityBoat;

public class EntityBuoyantBoat extends EntityBoat {

    public EntityBuoyantBoat(net.minecraft.world.World worldIn) {
        super(worldIn);
    }
    
    public EntityBuoyantBoat(EntityBoat boat) {
        super(boat.world, boat.posX, boat.posY, boat.posZ);
        this.motionX = boat.motionX;
        this.motionY = boat.motionY;
        this.motionZ = boat.motionZ;
        this.rotationYaw = boat.rotationYaw;
        this.rotationPitch = boat.rotationPitch;
    }

    // Vanilla applies a Y momentum of -0.0007D to any underwater boat.  This value is added to that to make it float.
    static final double BUOYANCY = 0.14D;

    // EntityBoat.Status prevStatus = EntityBoat.Status.IN_AIR;
    
    // If boat is underwater, add Y momentum to make it float instead of sink
    @Override
    public void onUpdate()
    {
        // FRAGILE: This can break if MC gets recompiled...
        final int status_Field = 21;
        final int outOfControlTicks_Field = 7;

        // Let the boat calculate its motion
        super.onUpdate();

        // Now adjust buoyancy if boat is underwater
        try {
            java.lang.reflect.Field[] fs = getClass().getSuperclass().getDeclaredFields();

            // Boats know whether they're underwater, but of course that info is stored in a private field.  Thanks, Obama.
            fs[status_Field].setAccessible(true);
            EntityBoat.Status status = (EntityBoat.Status)fs[status_Field].get(this);

            // if (status != prevStatus) {
                // System.out.println("Boat status is now " + status);
                // prevStatus = status;
            // }

            if (status == EntityBoat.Status.UNDER_WATER || status == EntityBoat.Status.UNDER_FLOWING_WATER) {
                this.motionY += BUOYANCY;

                // Vanilla boats eject passengers after counting 60 ticks underwater.
                if (TweakConfig.boats.dontEjectPassengers) {
                    fs[outOfControlTicks_Field].setAccessible(true);
                    fs[outOfControlTicks_Field].setFloat(this, 0F);
                }
            }
        } catch(IllegalAccessException e) {
            // We'll never get this exception because we set the accessibility
            // but Java's checked exceptions want me to type this
            // boilerplate anyway, because I'm just the programmer,
            // what the hell do I know?
            System.out.println("This didn't happen.");
        }
    }

}