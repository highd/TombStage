package com.highd120.tombstagesmod.blocks;

import com.highd120.tombstagesmod.TombStageConfig;

import net.minecraft.item.ItemStack;

public class TileTomb extends TileHasInventory {

	@Override
	public void update() {
	}

	@Override
	public SimpleItemStackHandler createItemStackHandler() {
		return new ItemStackHandler(this);
	}

	public static class ItemStackHandler extends SimpleItemStackHandler {
		public ItemStackHandler(TileHasInventory inv) {
			super(inv, TombStageConfig.tombSlotCount);
		}
		
		@Override
		public ItemStack extractItem(int slot, int amount, boolean simulate) {
			return ItemStack.EMPTY;
		}
		
		@Override
		public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
			return stack;
		}
	}
}
