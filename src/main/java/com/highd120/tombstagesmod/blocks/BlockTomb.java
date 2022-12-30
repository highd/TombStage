package com.highd120.tombstagesmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockTomb extends Block {
	public BlockTomb() {
		super(Material.IRON);
	}

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    
    @Override
    public boolean hasTileEntity(IBlockState state) {
    	return true;
    }
    
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
    	return new TileTomb();
    }
    
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    	TileTomb tile = (TileTomb)worldIn.getTileEntity(pos);
    	tile.breakEvent();
    	super.breakBlock(worldIn, pos, state);
    }
}
