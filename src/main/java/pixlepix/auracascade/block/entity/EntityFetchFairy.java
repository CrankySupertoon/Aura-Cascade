package pixlepix.auracascade.block.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import pixlepix.auracascade.main.AuraUtil;

import java.util.List;

/**
 * Created by pixlepix on 12/15/14.
 */
public class EntityFetchFairy extends EntityFairy {
    public EntityFetchFairy(World p_i1582_1_) {
        super(p_i1582_1_);
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        if (!world.isRemote) {
            List<EntityItem> nearbyEntities = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(posX - 4, posY - 4, posZ - 4, posX + 4, posY + 4, posZ + 4));
            for (EntityItem entity : nearbyEntities) {
                if (AuraUtil.getItemDelay(entity) <= 0) {
                    entity.setPositionAndRotation(player.posX, player.posY, player.posZ, 0, 0);
                }
            }
        }
    }
}
