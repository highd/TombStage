package com.highd120.tombstagesmod.items;

import com.highd120.tombstagesmod.TombStageConfig;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class SoulTorchAndSoulResetRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
	@Override
	public boolean isDynamic() {
		return true;
	}
	
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		boolean isFoundTorch = false;
		boolean isFoundSoulReset = false;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty()) {
				if (stack.getItem() == ModItems.soulTorch) {
					isFoundTorch = true;
				}
				if (stack.getItem() == ModItems.soulReset) {
					isFoundSoulReset = true;
				}
			}
		}
		return isFoundTorch && isFoundSoulReset;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack item = null;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (stack.getItem() == ModItems.soulTorch) {
				item = stack;
			}
		}
		if (item == null) {
			return ItemStack.EMPTY;
		}
		item = item.copy();
		NBTTagCompound tag = item.getTagCompound();
		
		if (tag == null) {
			return item;
		}
		
		tag.removeTag("X");
		tag.removeTag("Y");
		tag.removeTag("Z");
		item.setTagCompound(tag);
		
		return item;
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
