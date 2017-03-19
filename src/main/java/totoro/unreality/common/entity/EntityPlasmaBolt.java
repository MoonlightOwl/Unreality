package totoro.unreality.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nonnull;


public class EntityPlasmaBolt extends EntityProjectile {
    private static final int explosionRadius = 2;

    public EntityPlasmaBolt(World worldIn) {
        super(worldIn);
        this.setSize(0.3125F, 0.3125F);
    }

    public EntityPlasmaBolt(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(worldIn, x, y, z, accelX, accelY, accelZ);
        this.setSize(0.3125F, 0.3125F);
    }

    private BlockPos scanForRobots(BlockPos center, int radius) {
        for (int x = center.getX() - radius; x < center.getX() + radius; x++) {
            for (int y = center.getY() - radius; y < center.getY() + radius; y++) {
                for (int z = center.getZ() - radius; z < center.getZ() + radius; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    Block target = this.worldObj.getBlockState(pos).getBlock();
                    if (target.getRegistryName().toString().equals("opencomputers:robot")) {
                        return pos;
                    }
                }
            }
        }
        return null;
    }

    @Override
    protected void onImpact(@Nonnull RayTraceResult result) {
        if (!this.worldObj.isRemote) {
            BlockPos collision = result.getBlockPos();
            if (collision != null && this.worldObj.getBlockState(collision).getBlock()
                    .getRegistryName().toString().equals("opencomputers:robot")) {
                this.worldObj.destroyBlock(result.getBlockPos(), false);
                this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ,
                        EntityPlasmaBolt.explosionRadius, true);
            } else {
                EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.worldObj, this.posX, this.posY, this.posZ);
                entityareaeffectcloud.setParticle(EnumParticleTypes.EXPLOSION_NORMAL);
                entityareaeffectcloud.setRadius(1.0F);
                entityareaeffectcloud.setDuration(5);
                entityareaeffectcloud.setRadiusPerTick((2.0F - entityareaeffectcloud.getRadius()) / (float)entityareaeffectcloud.getDuration());
                this.worldObj.spawnEntityInWorld(entityareaeffectcloud);
            }
            this.setDead();
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
