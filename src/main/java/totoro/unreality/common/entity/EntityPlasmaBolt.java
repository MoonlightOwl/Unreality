package totoro.unreality.common.entity;

import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

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

    @Override
    protected void onImpact(@Nonnull RayTraceResult result) {
        if (!this.worldObj.isRemote) {
            EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.worldObj, this.posX, this.posY, this.posZ);
            entityareaeffectcloud.setParticle(EnumParticleTypes.EXPLOSION_NORMAL);
            entityareaeffectcloud.setRadius(1.0F);
            entityareaeffectcloud.setDuration(5);
            entityareaeffectcloud.setRadiusPerTick((2.0F - entityareaeffectcloud.getRadius()) / (float)entityareaeffectcloud.getDuration());

            this.worldObj.spawnEntityInWorld(entityareaeffectcloud);
            this.setDead();
        }
    }

    protected float getMotionFactor() {
        return 1.5f;
    }

    @Nonnull
    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.PORTAL;
    }
}
