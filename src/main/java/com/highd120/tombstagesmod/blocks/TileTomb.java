package com.highd120.tombstagesmod.blocks;

public class TileTomb extends TileHasInventory {

	@Override
	public void update() {
	}

	@Override
	public SimpleItemStackHandler createItemStackHandler() {
		return new SimpleItemStackHandler(this, 60);
	}

}
