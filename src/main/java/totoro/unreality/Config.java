package totoro.unreality;

import net.minecraftforge.common.config.Configuration;

import java.io.File;


@SuppressWarnings("WeakerAccess")
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
    // Physics
    public static String[] PLASMA_PERMEABLE_BLOCKS, PLASMA_EXPLOSIVE_BLOCKS, PLASMA_DESTRUCTIBLE_BLOCKS;
    public static double PLASMA_EXPLOSION_RADIUS = 1;
    // API
    public static long PLASMA_UPGRADE_FIRE_DELAY = 200;


    public static void load(File file) {
        Configuration config = new Configuration(file);
        PLASMA_UPGRADE_COLOR_CHANGE_COST = config.get("plasma",
                "colorChangeCost", 0.2,
                "How much energy will cost to change plasma color").getDouble();
        PLASMA_UPGRADE_FIRE_COST = config.get("plasma",
                "fireCost", 0.2,
                "How much energy will cost to make one shot").getDouble();
        PLASMA_UPGRADE_ROTATION_COST = config.get("plasma",
                "rotationCost", 0.2,
                "How much energy will cost to rotate the gun").getDouble();

        PLASMA_UPGRADE_MIN_YAW = config.getFloat("minYaw",
                "plasma", -20, -180, 180,
                "Minimal horizontal deflection of the gun");
        PLASMA_UPGRADE_MAX_YAW = config.getFloat("maxYaw",
                "plasma", 20, -180, 180,
                "Maximal horizontal deflection of the gun");
        PLASMA_UPGRADE_MIN_PITCH = config.getFloat("minPitch",
                "plasma", -90, -180, 180,
                "Minimal vertical deflection of the gun");
        PLASMA_UPGRADE_MAX_PITCH = config.getFloat("maxPitch",
                "plasma", 90, -180, 180,
                "Maximal vertical deflection of the gun");

        PLASMA_PERMEABLE_BLOCKS = config.getStringList("permeable", "plasma",
                new String[] {"minecraft:glass"},
                "Plasma will pass through these blocks, without collision.");
        PLASMA_EXPLOSIVE_BLOCKS = config.getStringList("explosive", "plasma",
                new String[] {"opencomputers:robot", "minecraft:sand"},
                "These block will explode after contact with plasma.");
        PLASMA_DESTRUCTIBLE_BLOCKS = config.getStringList("destructible", "plasma",
                new String[] {"minecraft:chest"},
                "These block will break after contact with plasma.");

        PLASMA_EXPLOSION_RADIUS = config.get("plasma",
                "explosionRadius", 1,
                "The size of plasma explosion.").getDouble();

        PLASMA_UPGRADE_FIRE_DELAY = config.getInt("fireDelay",
                "plasma", 200, 0, Integer.MAX_VALUE,
                "Cooldown after each shot (milliseconds).");

        if (config.hasChanged())
            config.save();
    }
}
