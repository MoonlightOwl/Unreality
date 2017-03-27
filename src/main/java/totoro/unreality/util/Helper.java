package totoro.unreality.util;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;


public class Helper {
    public static NBTTagCompound dataTag(final ItemStack stack) {
        if(!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }
        final NBTTagCompound nbt = stack.getTagCompound();
        // This is the suggested key under which to store item component data.
        // You are free to change this as you please.
        if (nbt != null) {
            if (!nbt.hasKey("oc:data")) {
                nbt.setTag("oc:data", new NBTTagCompound());
            }
            return nbt.getCompoundTag("oc:data");
        } else return null;
    }

    public static <T> boolean contains(T[] array, T item) {
        for (T x : array) if (item.equals(x)) return true;
        return false;
    }
}
