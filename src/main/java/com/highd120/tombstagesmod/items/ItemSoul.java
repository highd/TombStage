package com.highd120.tombstagesmod.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemSoul extends ItemBase {
    public static final String TAG = "point";
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    	NBTTagCompound tag = stack.getTagCompound();
    	if (tag == null) {
    		return;
    	}
    	int x = tag.getInteger("X");
    	int y = tag.getInteger("Y");
    	int z = tag.getInteger("Z");
    	tooltip.add("(" + x + "," + y + "," + z + ")");
    }
}
