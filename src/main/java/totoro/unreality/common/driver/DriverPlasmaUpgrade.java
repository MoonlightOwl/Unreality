package totoro.unreality.common.driver;

import li.cil.oc.api.Network;
import li.cil.oc.api.driver.DeviceInfo;
import li.cil.oc.api.driver.item.HostAware;
import li.cil.oc.api.driver.item.Slot;
import li.cil.oc.api.internal.Robot;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ComponentConnector;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.ManagedEnvironment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;
import totoro.unreality.Config;
import totoro.unreality.common.Tier;
import totoro.unreality.common.entity.EntityPlasmaBolt;
import totoro.unreality.common.item.ItemPlasmaUpgrade;

import java.util.HashMap;
import java.util.Map;


public class DriverPlasmaUpgrade extends ManagedEnvironment implements DeviceInfo, HostAware {
    private static final int CALL_LIMIT = 15;

    private int color = 0xff004d;
    private float yaw = 0, pitch = 0;
    private boolean needsUpdate = false;

    private ComponentConnector node;
    private EnvironmentHost host;


    public DriverPlasmaUpgrade(EnvironmentHost host) {
        this.setNode(Network.newNode(this, Visibility.Network)
                .withConnector().withComponent("plasma", Visibility.Neighbors).create());
        node = (ComponentConnector) this.node();
        this.host = host;
    }


    @Callback(doc = "function(): string -- " +
            "Will return test string", direct = true, limit = CALL_LIMIT)
    public Object[] test(Context context, Arguments arguments) {
        return new Object[] { "Hello UT!" };
    }

    @Callback(doc = "function(color: number): boolean -- " +
            "Sets the color of the plasma-core. Returns true on success, " +
            "false and an error message otherwise", direct = true)
    public Object[] setColor(Context context, Arguments args) {
        int color = args.checkInteger(0);
        if(color >= 0 && color <= 0xFFFFFF) {
            if(node.tryChangeBuffer(-Config.PLASMA_UPGRADE_COLOR_CHANGE_COST)) {
                setColor(color);
                return new Object[] { true };
            }
            return new Object[] { false, "not enough energy" };
        }
        return new Object[] { false, "number must be between 0 and 16777215" };
    }

    @Callback(doc = "function(): boolean, [string] -- " +
            "Sets the color of the plasma-core. Returns true on success, " +
            "false and an error message otherwise", limit = CALL_LIMIT)
    public Object[] fire(Context context, Arguments args) {
        if(node.tryChangeBuffer(-Config.PLASMA_UPGRADE_FIRE_COST)) {
            EnumFacing facing = ((Robot) host).facing();
            float yaw = this.yaw;
            float mountX = 0, mountY = 0.2f, mountZ = 0;
            switch (facing) {
                case SOUTH: mountX = -0.3f; mountZ = 0; break;
                case NORTH: mountX = 0.3f; mountZ = 0; yaw += 180; break;
                case EAST: mountX = 0; mountZ = 0.3f; yaw += 90; break;
                case WEST: mountX = 0; mountZ = -0.3f; yaw += 270; break;
            }
            float accelX = (float) (Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
            float accelY = (float) (Math.sin(Math.toRadians(pitch)));
            float accelZ = (float) (Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch)));
            EntityPlasmaBolt bolt = new EntityPlasmaBolt(host.world(),
                    host.xPosition() + accelX + mountX,
                    host.yPosition() + accelY + mountY,
                    host.zPosition() + accelZ + mountZ,
                    accelX, accelY, accelZ);
            bolt.rotationYaw = yaw;
            bolt.rotationPitch = pitch;
            bolt.setColor(this.color);
            host.world().spawnEntityInWorld(bolt);
            return new Object[] { true };
        }
        return new Object[] { false, "not enough energy" };
    }

    @Callback(doc = "function(yaw: number, pitch: number): boolean, [string] -- " +
            "Change weapon angle (yaw in [-20, 20], pitch in [-90, 90] range)",
            direct = true, limit = CALL_LIMIT)
    public Object[] turn(Context context, Arguments arguments) {
        if(node.tryChangeBuffer(-Config.PLASMA_UPGRADE_ROTATION_COST)) {
            float yaw = (float) arguments.checkDouble(0);
            float pitch = (float) arguments.checkDouble(1);
            if (yaw < Config.PLASMA_UPGRADE_MIN_YAW || yaw > Config.PLASMA_UPGRADE_MAX_YAW) {
                throw new IllegalArgumentException("yaw value must be in [" +
                        Config.PLASMA_UPGRADE_MIN_YAW + ", " +
                        Config.PLASMA_UPGRADE_MAX_YAW + "] range");
            }
            if (pitch < Config.PLASMA_UPGRADE_MIN_PITCH || pitch > Config.PLASMA_UPGRADE_MAX_PITCH) {
                throw new IllegalArgumentException("pitch value must be in [" +
                        Config.PLASMA_UPGRADE_MIN_PITCH + ", " +
                        Config.PLASMA_UPGRADE_MAX_PITCH + "] range");
            }
            this.setYaw(yaw);
            this.setPitch(pitch);
            return new Object[] { true };
        }
        return new Object[] { false, "not enough energy" };
    }


    public int getColor() { return color; }
    public void setColor(int color) {
        if(this.color != color) {
            this.color = color;
            needsUpdate = true;
        }
    }

    public float getYaw() { return yaw; }
    public void setYaw(float yaw) {
        if (this.yaw != yaw) {
            this.yaw = yaw;
            needsUpdate = true;
        }
    }

    public float getPitch() { return pitch; }
    public void setPitch(float pitch) {
        if (this.pitch != pitch) {
            this.pitch = pitch;
            needsUpdate = true;
        }
    }


    protected void updateClient() {
        try {
            if(host instanceof Robot) {
                Robot robot = (Robot) host;
                robot.synchronizeSlot(robot.componentSlot(node.address()));
            }
        } catch(NullPointerException e) {
            // NO-OP
        }
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public void update() {
        if(needsUpdate) {
            updateClient();
            needsUpdate = false;
        }
    }

    @Override
    public void load(NBTTagCompound nbt) {
        super.load(nbt);
        if (nbt.hasKey("unreality:color")) { setColor(nbt.getInteger("unreality:color")); }
        if (nbt.hasKey("unreality:yaw")) { setYaw(nbt.getFloat("unreality:yaw")); }
        if (nbt.hasKey("unreality:pitch")) { setPitch(nbt.getFloat("unreality:pitch")); }
        this.needsUpdate = true;
    }

    @Override
    public void save(NBTTagCompound nbt) {
        super.save(nbt);
        nbt.setInteger("unreality:color", this.getColor());
        nbt.setFloat("unreality:yaw", this.getYaw());
        nbt.setFloat("unreality:pitch", this.getPitch());
    }


    private Map<String, String> deviceInfo;

    @Override
    public Map<String, String> getDeviceInfo() {
        if(deviceInfo == null) {
            deviceInfo = new HashMap<String, String>() {{
                put(DeviceAttribute.Class, DeviceClass.Generic);
                put(DeviceAttribute.Description, "Weapon upgrade for UT2: Deathmatch event");
                put(DeviceAttribute.Vendor, "Totoro Corp.");
            }};
        }
        return deviceInfo;
    }

    public boolean worksWith(ItemStack stack, Class<? extends EnvironmentHost> host) {
        return worksWith(stack) && Robot.class.isAssignableFrom(host);
    }

    @Override
    public boolean worksWith(ItemStack stack) {
        return stack.getItem() instanceof ItemPlasmaUpgrade;
    }

    @Override
    public li.cil.oc.api.network.ManagedEnvironment createEnvironment(ItemStack stack, EnvironmentHost host) {
        return new DriverPlasmaUpgrade(host);
    }

    @Override
    public String slot(ItemStack stack) {
        return Slot.Upgrade;
    }

    @Override
    public int tier(ItemStack stack) {
        return Tier.One;
    }

    @Override
    public NBTTagCompound dataTag(ItemStack stack) {
        return null;
    }
}
