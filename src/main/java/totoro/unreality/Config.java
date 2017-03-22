package totoro.unreality;


import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class Config {
    // Power
    public static double PLASMA_UPGRADE_COLOR_CHANGE_COST = 0.2;
    public static double PLASMA_UPGRADE_FIRE_COST = 0.2;
    public static double PLASMA_UPGRADE_ROTATION_COST = 0.2;
    // Rotation
    public static float PLASMA_UPGRADE_MIN_YAW = -20;
    public static float PLASMA_UPGRADE_MAX_YAW = 20;
    public static float PLASMA_UPGRADE_MIN_PITCH = -90;
    public static float PLASMA_UPGRADE_MAX_PITCH = 90;

    public static void load(File file) {
        Configuration config = new Configuration(file);
        PLASMA_UPGRADE_COLOR_CHANGE_COST = config.get("power",
                "plasmaUpgradeColorChangeCost", 0.2,
                "How much energy will cost to change plasma color").getDouble();
        PLASMA_UPGRADE_FIRE_COST = config.get("power",
                "plasmaUpgradeFireCost", 0.2,
                "How much energy will cost to make one shot").getDouble();
        PLASMA_UPGRADE_ROTATION_COST = config.get("power",
                "plasmaUpgradeRotationCost", 0.2, "" +
                        "How much energy will cost to rotate the gun").getDouble();
        PLASMA_UPGRADE_MIN_YAW = config.getFloat("plasmaUpgradeMinYaw",
                "rotation", -20, -180, 180,
                "Minimal horizontal deflection of the gun");
        PLASMA_UPGRADE_MAX_YAW = config.getFloat("plasmaUpgradeMaxYaw",
                "rotation", 20, -180, 180,
                "Maximal horizontal deflection of the gun");
        PLASMA_UPGRADE_MIN_PITCH = config.getFloat("plasmaUpgradeMinPitch",
                "rotation", -90, -180, 180,
                "Minimal vertical deflection of the gun");
        PLASMA_UPGRADE_MAX_PITCH = config.getFloat("plasmaUpgradeMaxPitch",
                "rotation", 90, -180, 180,
                "Maximal vertical deflection of the gun");
        if (config.hasChanged())
            config.save();
    }
}
