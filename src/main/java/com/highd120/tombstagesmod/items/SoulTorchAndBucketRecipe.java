package com.highd120.tombstagesmod.items;

import com.highd120.tombstagesmod.TombStageConfig;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class SoulTorchAndBucketRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {
	@Override
	public boolean isDynamic() {
		return true;
	}
	
	boolean isBucket(ItemStack stack) {
        UniversalBucket universalBucket = ForgeModContainer.getInstance().universalBucket;
        Item item = stack.getItem();
		return item == universalBucket ||
				item == Items.BUCKET ||
				item == Items.LAVA_BUCKET ||
				item == Items.MILK_BUCKET;
				
	}
	
	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
        UniversalBucket universalBucket = ForgeModContainer.getInstance().universalBucket;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty()) {
				if (stack.getItem() == ModItems.soulTorch) {
					// empty block
				} else if (isBucket(stack)) {
					FluidStack fluid = FluidUtil.getFluidContained(stack);
					if (fluid == null || !fluid.getFluid().getName().equals(TombStageConfig.soulTorchFuel)) {
						return false;
					}
				} else {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
        UniversalBucket universalBucket = ForgeModContainer.getInstance().universalBucket;
		ItemStack item = null;
		FluidStack fluid = null;
		for (int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack stack = inv.getStackInSlot(i);
			if (!stack.isEmpty()) {
				if (stack.getItem() == ModItems.soulTorch) {
					item = stack;
				}
				if (isBucket(stack)) {
					fluid = FluidUtil.getFluidContained(stack);
				}
			}
		}
		if (fluid == null || item == null) {
			return ItemStack.EMPTY;
		}
		item = item.copy();
		IFluidHandlerItem handler = FluidUtil.getFluidHandler(item);
		handler.fill(fluid, true);
		
		return item;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width > 1 || height > 1;
	}

	@Override
	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
		return IRecipe.super.getRemainingItems(inv);
	}
	
	@Override
	public ItemStack getRecipeOutput() {
		return ItemStack.EMPTY;
	}
	
}

