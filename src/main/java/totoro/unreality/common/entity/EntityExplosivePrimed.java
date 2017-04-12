package totoro.unreality.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

import javax.annotation.Nonnull;


@SuppressWarnings("WeakerAccess")
public class EntityExplosivePrimed extends Entity {
    private static final DataParameter<Integer> FUSE =
            EntityDataManager.createKey(EntityExplosivePrimed.class, DataSerializers.VARINT);
    private int fuse;

    public EntityExplosivePrimed(World worldIn) {
        super(worldIn);
        this.fuse = 80;
        this.preventEntitySpawning = true;
        this.setSize(0.98F, 0.98F);
    }

    public EntityExplosivePrimed(World worldIn, double x, double y, double z) {
        this(worldIn);
        this.setPosition(x, y, z);
        float f = (float)(Math.random() * (Math.PI * 2D));
        this.motionX = (double)(-((float)Math.sin((double)f)) * 0.02F);
        this.motionY = 0.20000000298023224D;
        this.motionZ = (double)(-((float)Math.cos((double)f)) * 0.02F);
        this.setFuse(80);
        this.prevPosX = x;
        this.prevPosY = y;
        this.prevPosZ = z;
    }

    protected void entityInit() {
        this.dataManager.register(FUSE, 80);
    }

    /**
     * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
     * prevent them from trampling crops
     */
    protected boolean canTriggerWalking() {
        return false;
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith() {
        return !this.isDead;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        this.setRotation(this.rotationYaw + 2f, 0);

        --this.fuse;

        if (this.fuse <= 0) {
            this.setDead();

            if (!this.worldObj.isRemote) {
                this.explode();
            }
        } else {
            this.handleWaterMovement();
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,
                    this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        }
    }

    private void explode() {
        this.worldObj.createExplosion(this,
                this.posX, this.posY + (double)(this.height / 16.0F), this.posZ, 4.0F, true);
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    protected void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
        compound.setShort("Fuse", (short)this.getFuse());
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    protected void readEntityFromNBT(@Nonnull NBTTagCompound compound) {
        this.setFuse(compound.getShort("Fuse"));
    }

    /**
     * You've got nice eyes, dear explosive :3
     */
    public float getEyeHeight() {
        return 0.0F;
    }

    public void setFuse(int fuseIn) {
        this.dataManager.set(FUSE, fuseIn);
        this.fuse = fuseIn;
    }

    public void notifyDataManagerChange(DataParameter<?> key) {
        if (FUSE.equals(key)) {
            this.fuse = this.getFuseDataManager();
        }
    }

    /**
     * Gets the fuse from the data manager
     */
    public int getFuseDataManager() {
        return this.dataManager.get(FUSE);
    }

    public int getFuse() {
        return this.fuse;
    }
}