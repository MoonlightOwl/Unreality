package totoro.unreality.common.entity;

import li.cil.oc.api.internal.Robot;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class EntityPlasmaBolt extends Entity {
    private static DamageSource plasma = new DamageSource("plasma");
    static { plasma.setProjectile(); }

    private static final DataParameter<Float> YAW = EntityDataManager.createKey(EntityPlasmaBolt.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> PITCH = EntityDataManager.createKey(EntityPlasmaBolt.class, DataSerializers.FLOAT);
    private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(EntityPlasmaBolt.class, DataSerializers.FLOAT);

    private int life = 600;
    private float yaw = 0.0F;
    private float pitch = 0.0F;
    private float damage = 0.0F;

    public EntityPlasmaBolt(World world) {
        super(world);
        setSize(0.5F, 0.5F);
    }

    @Override
    protected void entityInit() {
        this.dataManager.register(YAW, 0.0F);
        this.dataManager.register(PITCH, 0.0F);
    }

    public void setHeading(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
        this.motionX = Math.sin(yaw) * Math.cos(pitch);
        this.motionY = Math.sin(pitch);
        this.motionZ = Math.cos(yaw) * Math.cos(pitch);
        this.dataManager.set(YAW, yaw);
        this.dataManager.set(PITCH, pitch);
    }

    public void setDamage(float damage) {
        this.damage = damage;
        this.dataManager.set(DAMAGE, damage);
    }


    protected void readEntityFromNBT(NBTTagCompound tag)
    {
        this.yaw = tag.getFloat("yaw");
        this.pitch = tag.getFloat("pitch");
    }

    protected void writeEntityToNBT(NBTTagCompound tag)
    {
        tag.setFloat("yaw", this.yaw);
        tag.setFloat("pitch", this.pitch);
    }


    protected boolean canTriggerWalking() { return false; }

    @SideOnly(Side.CLIENT)
    protected float getShadowSize() {
        return 0.0F;
    }


    public void onUpdate() {
        if (life < 0) this.isDead = true;

        // TODO: check actual robot block class name
        if ((!this.worldObj.isAirBlock(getPosition()))
                && (!(this.worldObj.getBlockState(getPosition()).getBlock() instanceof Robot))) {
            this.isDead = true;
        }

        this.yaw = this.dataManager.get(YAW);
        this.pitch = this.dataManager.get(PITCH);
        this.damage = this.dataManager.get(DAMAGE);

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        setPosition(this.posX, this.posY, this.posZ);

        super.onUpdate();
    }

    public float getYaw() { return yaw; }
    public float getPitch() { return pitch; }
}
