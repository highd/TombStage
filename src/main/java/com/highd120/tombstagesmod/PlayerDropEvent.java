package com.highd120.tombstagesmod;

import java.util.List;

import com.highd120.tombstagesmod.blocks.ModBlocks;
import com.highd120.tombstagesmod.blocks.TileTomb;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerDropEvent {
	  @SubscribeEvent(priority = EventPriority.LOWEST)
	  public static void onPlayerDrops(PlayerDropsEvent event) {
		  EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
		  if (player instanceof  FakePlayer) {
			  return;
		  }
		  BlockPos tombPos = new BlockPos(player);
		  List<EntityItem> itemEntityList = event.getDrops();
		  WorldServer world = player.getServerWorld();
		  IBlockState blockState = ModBlocks.tomb.getDefaultState();
		  world.setBlockState(tombPos, blockState, 3);
		  TileTomb tile = (TileTomb) world.getTileEntity(tombPos);
		  int index = 0;
		  for (EntityItem itemEntity : itemEntityList) {
			  if (itemEntity != null && !itemEntity.getItem().isEmpty()) {
				  ItemStack itemStack = itemEntity.getItem();
				  tile.setInventory(index, itemStack);
				  index++;
			  }
		  }
		  itemEntityList.clear();
	  }
}
