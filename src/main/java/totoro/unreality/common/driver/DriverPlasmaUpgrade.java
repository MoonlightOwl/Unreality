package totoro.unreality.common.driver;

import li.cil.oc.api.Network;
import li.cil.oc.api.driver.DeviceInfo;
import li.cil.oc.api.driver.item.HostAware;
import li.cil.oc.api.driver.item.Slot;
import li.cil.oc.api.internal.Robot;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.ManagedEnvironment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import totoro.unreality.common.Tier;
import totoro.unreality.common.item.ItemPlasmaUpgrade;

import java.util.HashMap;
import java.util.Map;

public class DriverPlasmaUpgrade extends ManagedEnvironment implements DeviceInfo, HostAware {
    private static final int CALL_LIMIT = 15;

    public DriverPlasmaUpgrade() {
        this.setNode(Network.newNode(this, Visibility.Network)
                .withConnector().withComponent("plasma", Visibility.Neighbors).create());
    }

    @Callback(doc = "function(): string; Will return test string", direct = true, limit = CALL_LIMIT)
    public Object[] test(Context context, Arguments arguments) {
        return new Object[] { "Hello UT!" };
    }


    private Map<String, String> deviceInfo;

    @Override
    public Map<String, String> getDeviceInfo() {
        if(deviceInfo == null) {
            deviceInfo = new HashMap<String, String>() {{
                put(DeviceAttribute.Class, DeviceClass.Generic);
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
        return new DriverPlasmaUpgrade();
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
