package pixlepix.auracascade.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import pixlepix.auracascade.registry.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pixlepix on 12/21/14.
 */
public class ItemAngelsteelPickaxe extends ItemPickaxe implements ITTinkererItem {
    public ItemAngelsteelPickaxe(Integer i) {
        super(AngelsteelToolHelper.materials[i.intValue()]);
        this.degree = i.intValue();
    }

    public ItemAngelsteelPickaxe() {
        this(0);
    }

    @Override
    public ArrayList<Object> getSpecialParameters() {
        return AngelsteelToolHelper.getDegreeList(true);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean p_77624_4_) {
        super.addInformation(stack, player, list, p_77624_4_);
        if(AngelsteelToolHelper.hasValidBuffs(stack)) {
            int[] buffs = AngelsteelToolHelper.readFromNBT(stack.stackTagCompound);
            list.add("Efficiency: " + buffs[0]);
            list.add("Fortune: " + buffs[1]);
            list.add("Shatter: " + buffs[2]);
            list.add("Disintegrate: " + buffs[3]);
        }
    }

    public int degree = 0;

    public static final String name = "angelsteelPickaxe";

    @Override
    public String getItemName() {
        return name + degree;
    }

    @Override
    public boolean shouldRegister() {
        return true;
    }

    @Override
    public boolean shouldDisplayInTab() {
        return degree == 0 || degree == AngelsteelToolHelper.MAX_DEGREE;
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        if(!world.isRemote){
            stack.stackTagCompound = AngelsteelToolHelper.getRandomBuffCompound(degree);
        }
    }

    @Override
    public ThaumicTinkererRecipe getRecipeItem() {
        return new CraftingBenchRecipe(new ItemStack(this, 1, degree), "AAA", " S ", " S ", 'A', new ItemStack(BlockRegistry.getFirstItemFromClass(ItemAngelsteelIngot.class), 1, degree), 'S', new ItemStack(Items.stick));
    }
}
