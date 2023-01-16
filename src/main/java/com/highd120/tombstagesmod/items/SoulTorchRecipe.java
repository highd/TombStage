package com.highd120.tombstagesmod.items;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class SoulTorchRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
	@Override
	public boolean isDynamic() {
		return true;
	}
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		return false;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		// TODO Auto-generated method stub
		return null;
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
