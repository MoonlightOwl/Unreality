package totoro.unreality.common.driver;

import li.cil.oc.api.Network;
import li.cil.oc.api.driver.DeviceInfo;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.EnvironmentHost;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.ManagedEnvironment;
import net.minecraft.util.EnumFacing;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DriverPlasmaUpgrade extends ManagedEnvironment implements DeviceInfo {
    private static final int CALL_LIMIT = 15;

    private final EnvironmentHost entity;

    public DriverPlasmaUpgrade(EnvironmentHost entity) {
        this.entity = entity;
        this.setNode(Network.newNode(this, Visibility.Network)
                .withConnector().withComponent("plasma", Visibility.Neighbors).create());
    }

    @Callback(doc = "function(): string; Will return test string", direct = true, limit = CALL_LIMIT)
    public Object[] test(Context context, Arguments arguments) {
        return new Object[] { "Hello UT!" };
    }


    protected Map<String, String> deviceInfo;

    @Override
    public Map<String, String> getDeviceInfo() {
        if(deviceInfo == null) {
            return new HashMap<String, String>() {{
                put(DeviceAttribute.Class, DeviceClass.Generic);
            }};
        }
        return deviceInfo;
    }
}
