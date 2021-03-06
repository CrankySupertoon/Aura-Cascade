package pixlepix.auracascade.block.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import pixlepix.auracascade.block.entity.EntityMinerExplosion;
import pixlepix.auracascade.data.OreDropManager;

/**
 * Created by localmacaccount on 6/4/15.
 */
public class MinerTile extends ConsumerTile {
    public EntityMinerExplosion explosion;

    public boolean hasBeenPulsed = false;

    @Override
    public void readCustomNBT(NBTTagCompound nbt) {
        super.readCustomNBT(nbt);
        if (nbt.hasKey("explosion")) {
            explosion = (EntityMinerExplosion) world.getEntityByID(nbt.getInteger("explosion"));
            nbt.setBoolean("hasBeenPulsed", hasBeenPulsed);
        }
    }

    @Override
    public void writeCustomNBT(NBTTagCompound nbt) {
        super.writeCustomNBT(nbt);
        if (explosion != null) {
            nbt.setInteger("explosion", explosion.getEntityId());
            nbt.setBoolean("hasBeenPulsed", hasBeenPulsed);
        }
    }

    @Override
    public int getMaxProgress() {
        return 1;
    }

    @Override
    public int getPowerPerProgress() {
        return 2500;
    }

    @Override
    public boolean validItemsNearby() {
        return true;
    }

    @Override
    public void update() {
        if (world.isBlockIndirectlyGettingPowered(getPos()) > 0) {
            hasBeenPulsed = true;
        }
        super.update();
    }

    @Override
    public void onUsePower() {
       // AuraCascade.analytics.eventDesign("consumerMiner", AuraUtil.formatLocation(this));

        if (!hasBeenPulsed) {
            if (explosion != null && !explosion.isDead) {
                explosion.charge++;
                explosion.lastCharged = world.getTotalWorldTime();
            } else {
                explosion = new EntityMinerExplosion(world);
                explosion.setPosition(pos.getX() + .5, pos.getY() - 1.5, pos.getZ() + .5);
                explosion.charge = 1;
                explosion.lastCharged = world.getTotalWorldTime();
                explosion.bounce();
                world.spawnEntity(explosion);

            }

        } else {
            if (explosion != null && !explosion.isDead) {
                explosion.setDead();

                if (world.isRemote) {
                    this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, explosion.posX, explosion.posY, explosion.posZ, 0.0D, 0.0D, 0.0D);
                } else if (explosion.charge > 20) {
                    int oresSpawned = (int) ((Math.pow(explosion.charge, 1.5)) / 50);
                    for (int i = 0; i < oresSpawned; i++) {
                        ItemStack stack = OreDropManager.getOreToPut();
                        EntityItem item = new EntityItem(world, pos.getX() + .5, pos.getY() + 1.5, pos.getZ() + .5, stack);
                        world.spawnEntity(item);
                    }
                //    AuraCascade.analytics.eventDesign("consumerMinerLoot", AuraUtil.formatLocation(this), explosion.charge);
                }
            }
            hasBeenPulsed = false;
        }
    }
}
