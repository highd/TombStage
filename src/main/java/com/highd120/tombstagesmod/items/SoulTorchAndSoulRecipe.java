package com.highd120.tombstagesmod.items;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class SoulTorchAndSoulRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
	@Override
	public boolean isDynamic() {
		return true;
	}
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		boolean isFoundTorch = false;
		boolean isFoundSoul = false;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty()) {
				if (stack.getItem() == ModItems.soulTorch) {
					NBTTagCompound tag = stack.getTagCompound();
					isFoundTorch = tag == null || !tag.hasKey("X");
				}
				if (stack.getItem() == ModItems.soul) {
					isFoundSoul = true;
				}
			}
		}
		return isFoundTorch && isFoundSoul;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack item = null;
		NBTTagCompound point = null;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty()) {
				if (stack.getItem() == ModItems.soulTorch) {
					item = stack;
				}
				if (stack.getItem() == ModItems.soul) {
					point = stack.getTagCompound();
				}
			}
		}
		if (point == null || item == null) {
			return ItemStack.EMPTY;
		}
		item = item.copy();
		
		NBTTagCompound tag = item.getTagCompound();
		if (tag == null) {
			item.setTagCompound(point);
		} else {
			tag.setInteger("X", point.getInteger("X"));
			tag.setInteger("Y", point.getInteger("Y"));
			tag.setInteger("Z", point.getInteger("Z"));
		}
		return item;
	}
	
	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
        NonNullList<ItemStack> resultList = NonNullList.withSize(inv.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < resultList.size(); i++) {
        	ItemStack stack = inv.getStackInSlot(i);
        	ItemStack result = ForgeHooks.getContainerItem(stack);
        	if (stack.getItem() == ModItems.soul) {
        		resultList.set(i, stack.copy());
        	} else {
        		resultList.set(i, result);
        	}
        }
        return resultList;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width > 1 || height > 1;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}
	
}
