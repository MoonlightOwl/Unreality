package totoro.unreality.common.entity;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;


@SuppressWarnings("WeakerAccess")
public abstract class EntityProjectile extends Entity implements IEntityAdditionalSpawnData {
    private int xTile = -1;
    private int yTile = -1;
    private int zTile = -1;
    private Block inTile;
    private boolean inGround;
    private int ticksAlive;
    private int ticksInAir;
    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;
    private int color = 0xFFFFFF;

    public EntityProjectile(World worldIn) {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
    }

    protected void entityInit() {}

    /**
     * Checks if the entity is in range to render.
     */
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0D;

        if (Double.isNaN(d0)) {
            d0 = 4.0D;
        }

        d0 = d0 * 64.0D;
        return distance < d0 * d0;
    }

    public EntityProjectile(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
        super(worldIn);
        this.setSize(1.0F, 1.0F);
        this.setPosition(x, y, z);
        double d0 = (double) MathHelper.sqrt_double(accelX * accelX + accelY * accelY + accelZ * accelZ);
        this.accelerationX = accelX / d0 * 0.2D;
        this.accelerationY = accelY / d0 * 0.2D;
        this.accelerationZ = accelZ / d0 * 0.2D;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate() {
        if (this.worldObj.isRemote || this.worldObj.isBlockLoaded(new BlockPos(this))) {
            super.onUpdate();

            if (this.inGround) {
                if (this.worldObj.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile) {
                    ++this.ticksAlive;

                    if (this.ticksAlive == 600) {
                        this.setDead();
                    }

                    return;
                }

                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
                this.ticksAlive = 0;
                this.ticksInAir = 0;
            }
            else {
                ++this.ticksInAir;
            }

            @SuppressWarnings("ConstantConditions")
            RayTraceResult raytraceresult =
                    ProjectileHelper.forwardsRaycast(this, true,
                            this.ticksInAir >= 25, null);
            //noinspection ConstantConditions
            if (raytraceresult != null) {
                this.onImpact(raytraceresult);
            }

            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            float f = this.getMotionFactor();

            if (this.isInWater()) {
                for (int i = 0; i < 4; ++i) {
                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE,
                            this.posX - this.motionX * 0.25D,
                            this.posY - this.motionY * 0.25D,
                            this.posZ - this.motionZ * 0.25D,
                            this.motionX, this.motionY, this.motionZ);
                }

                f = 0.8F;
            }

            this.motionX += this.accelerationX;
            this.motionY += this.accelerationY;
            this.motionZ += this.accelerationZ;
            this.motionX *= (double)f;
            this.motionY *= (double)f;
            this.motionZ *= (double)f;
            this.worldObj.spawnParticle(this.getParticleType(),
                    this.posX, this.posY - 0.2D, this.posZ,
                    0.0D, 0.0D, 0.0D);
            this.setPosition(this.posX, this.posY, this.posZ);
        }
        else {
            this.setDead();
        }
    }

    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.PORTAL;
    }

    /**
     * Return the motion factor for this projectile. The factor is multiplied by the original motion.
     */
    protected float getMotionFactor() {
        return 0.95F;
    }

    /**
     * Called when this EntityFireball hits a block or entity.
     */
    protected abstract void onImpact(RayTraceResult result);

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    @SuppressWarnings("ConstantConditions")
    public void writeEntityToNBT(@Nonnull NBTTagCompound compound) {
        compound.setInteger("xTile", this.xTile);
        compound.setInteger("yTile", this.yTile);
        compound.setInteger("zTile", this.zTile);
        ResourceLocation resourcelocation = Block.REGISTRY.getNameForObject(this.inTile);
        compound.setString("inTile", resourcelocation == null ? "" : resourcelocation.toString());
        compound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        compound.setTag("direction", this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
        compound.setTag("power", this.newDoubleNBTList(this.accelerationX, this.accelerationY, this.accelerationZ));
        compound.setInteger("life", this.ticksAlive);
        compound.setInteger("unreality:color", this.color);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(@Nonnull NBTTagCompound compound) {
        this.xTile = compound.getInteger("xTile");
        this.yTile = compound.getInteger("yTile");
        this.zTile = compound.getInteger("zTile");

        if (compound.hasKey("inTile", 8)) {
            this.inTile = Block.getBlockFromName(compound.getString("inTile"));
        }
        else {
            this.inTile = Block.getBlockById(compound.getByte("inTile") & 255);
        }

        this.inGround = compound.getByte("inGround") == 1;

        if (compound.hasKey("power", 9)) {
            NBTTagList nbttaglist = compound.getTagList("power", 6);

            if (nbttaglist.tagCount() == 3) {
                this.accelerationX = nbttaglist.getDoubleAt(0);
                this.accelerationY = nbttaglist.getDoubleAt(1);
                this.accelerationZ = nbttaglist.getDoubleAt(2);
            }
        }

        this.ticksAlive = compound.getInteger("life");

        if (compound.hasKey("direction", 9) && compound.getTagList("direction", 6).tagCount() == 3) {
            NBTTagList nbttaglist1 = compound.getTagList("direction", 6);
            this.motionX = nbttaglist1.getDoubleAt(0);
            this.motionY = nbttaglist1.getDoubleAt(1);
            this.motionZ = nbttaglist1.getDoubleAt(2);
        }
        else {
            this.setDead();
        }

        this.color = compound.getInteger("unreality:color");
    }

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith() {
        return false;
    }

    public float getCollisionBorderSize() {
        return 1.0F;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
        return false;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness(float partialTicks) {
        return 2.0F;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float partialTicks) {
        return 15728880;
    }

    public void setColor(int color) { this.color = color; }
    public int getColor() { return color; }

    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeInt(getColor());
    }

    public void readSpawnData(ByteBuf additionalData) {
        setColor(additionalData.readInt());
    }
}