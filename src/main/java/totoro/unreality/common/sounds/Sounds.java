package totoro.unreality.common.sounds;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public class Sounds {
    public static SoundEvent Blast;

    private static int size = 0;

    public static void init() {
        size = SoundEvent.REGISTRY.getKeys().size();

        Blast = register("blast");
    }

    public static SoundEvent register(String name) {
        ResourceLocation loc = new ResourceLocation("unreality", name);
        SoundEvent e = new SoundEvent(loc);

        SoundEvent.REGISTRY.register(size, loc, e);
        size++;

        return e;
    }
}
