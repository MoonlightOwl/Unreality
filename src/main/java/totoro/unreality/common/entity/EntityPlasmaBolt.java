package totoro.unreality.common.entity;

import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import totoro.unreality.Config;
import totoro.unreality.common.PlasmaExplosion;
import totoro.unreality.util.Helper;

import javax.annotation.Nonnull;


public class EntityPlasmaBolt extends EntityProjectile {

    public EntityPlasmaBolt(World worldIn) {
        super(worldIn);
        this.setSize(0.3125F, 0.3125F);
    }

    public EntityPlasmaBolt(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(worldIn, x, y, z, accelX, accelY, accelZ);
        this.setSize(0.3125F, 0.3125F);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    protected void onImpact(@Nonnull RayTraceResult result) {
        BlockPos collision = result.getBlockPos();
        if (collision != null) {
            String blockname = this.worldObj.getBlockState(collision).getBlock()
                    .getRegistryName().toString();
            if (!Helper.contains(Config.PLASMA_PERMEABLE_BLOCKS, blockname)) {
                if (Helper.contains(Config.PLASMA_EXPLOSIVE_BLOCKS, blockname)) {
                    this.worldObj.destroyBlock(result.getBlockPos(), false);
                    PlasmaExplosion.bigExplode(this.worldObj,
                            this.posX, this.posY, this.posZ, Config.PLASMA_EXPLOSION_RADIUS);
                } else if (Helper.contains(Config.PLASMA_DESTRUCTIBLE_BLOCKS, blockname)) {
                    this.worldObj.destroyBlock(result.getBlockPos(), false);
                    PlasmaExplosion.smallExplode(this.worldObj,
                            this.posX, this.posY, this.posZ, Config.PLASMA_EXPLOSION_RADIUS / 2);
                } else {
                    PlasmaExplosion.miss(this.worldObj, this.posX, this.posY, this.posZ);
                }
                this.setDead();
            }
        }
    }

    protected float getMotionFactor() {
        return 1.1f;
    }

    @Nonnull
    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.PORTAL;
    }
}
