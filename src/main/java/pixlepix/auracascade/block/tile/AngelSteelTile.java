package pixlepix.auracascade.block.tile;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import pixlepix.auracascade.item.ItemAngelsteelIngot;
import pixlepix.auracascade.registry.BlockRegistry;

/**
 * Created by pixlepix on 12/21/14.
 */
@SuppressWarnings("ALL")
public class AngelSteelTile extends ConsumerTile {

    public static int MAX_PROGRESS = 50;
    public static int POWER_PER_PROGRESS = 10000;

    @Override
    public int getMaxProgress() {
        return MAX_PROGRESS;
    }

    @Override
    public int getPowerPerProgress() {
        return POWER_PER_PROGRESS;
    }

    @Override
    public boolean validItemsNearby() {
        return true;
    }

    @Override
    public void onUsePower() {
      //  AuraCascade.analytics.eventDesign("cascaderAngel", AuraUtil.formatLocation(this));
        ItemStack lootStack = new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAngelsteelIngot.class));
        EntityItem entityItem = new EntityItem(world, pos.getX() + .5, pos.getY() + 1.5, pos.getZ() + .5, lootStack);
        entityItem.motionX = 0;
        entityItem.motionY = 0;
        entityItem.motionZ = 0;
        world.spawnEntity(entityItem);

    }
}
