package totoro.unreality.common;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;


@SuppressWarnings("WeakerAccess")
public class PlasmaExplosion {

    public static void bigExplode(World world, double x, double y, double z, double rad) {
        explode(world, x, y, z, rad, SoundEvents.ENTITY_GENERIC_EXPLODE, EnumParticleTypes.LAVA);
    }
    public static void smallExplode(World world, double x, double y, double z, double rad) {
        explode(world, x, y, z, rad, SoundEvents.ENTITY_FIREWORK_BLAST_FAR, EnumParticleTypes.SPELL_INSTANT);
    }

    public static void explode(World world, double x, double y, double z, double rad,
                               SoundEvent sound, EnumParticleTypes particles) {
        world.playSound(null, x, y, z, sound, SoundCategory.BLOCKS, 4.0F,
                (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);

        if (world.isRemote) {
            ArrayList<BlockPos> affected = new ArrayList<>();


            for (double dx = x-rad; dx < x+rad; dx+=0.5) {
                for (double dy = y-rad; dy < y+rad; dy+=0.5) {
                    for (double dz = z-rad; dz < z+rad; dz+=0.5) {
                        if (world.rand.nextBoolean())
                            affected.add(new BlockPos(dx, dy, dz));
                    }
                }
            }

            world.spawnParticle(rad >= 2.0F ?
                            EnumParticleTypes.EXPLOSION_HUGE : EnumParticleTypes.EXPLOSION_LARGE,
                    x, y, z, 0.0D, 0.0D, 0.0D);

            for (BlockPos blockpos : affected) {
                double d0 = (double) ((float) blockpos.getX() + world.rand.nextFloat());
                double d1 = (double) ((float) blockpos.getY() + world.rand.nextFloat());
                double d2 = (double) ((float) blockpos.getZ() + world.rand.nextFloat());
                double d3 = d0 - x;
                double d4 = d1 - y;
                double d5 = d2 - z;
                double d6 = (double) MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
                d3 = d3 / d6;
                d4 = d4 / d6;
                d5 = d5 / d6;
                double d7 = 0.5D / (d6 / (double) rad + 0.1D);
                d7 = d7 * (double) (world.rand.nextFloat() * world.rand.nextFloat() + 0.3F);
                d3 = d3 * d7;
                d4 = d4 * d7;
                d5 = d5 * d7;
                //world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,
                //        (d0 + x) / 2.0D, (d1 + y) / 2.0D, (d2 + z) / 2.0D, d3, d4, d5);
                if (world.rand.nextBoolean())
                    world.spawnParticle(particles, d0, d1, d2, d3, d4, d5);
                else
                    world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5);
            }
        }
    }

    public static void miss(World world, double x, double y, double z) {
        world.playSound(null, x, y, z, SoundEvents.BLOCK_ANVIL_HIT, SoundCategory.BLOCKS, 1.0F,
                (1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2F) * 0.7F);

        if (world.isRemote) {
            for(int i = 0; i < 5; i++) {
                world.spawnParticle(EnumParticleTypes.CLOUD,
                        x + world.rand.nextFloat()-0.5f,
                        y + world.rand.nextFloat()-0.5f,
                        z + world.rand.nextFloat()-0.5f,
                        0, 0, 0);
            }
        }
    }
}
