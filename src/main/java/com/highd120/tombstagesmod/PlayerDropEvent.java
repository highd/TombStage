package com.highd120.tombstagesmod;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import com.highd120.tombstagesmod.blocks.ModBlocks;
import com.highd120.tombstagesmod.blocks.TileTomb;

import net.darkhax.gamestages.GameStageHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PlayerDropEvent {
	private static String POINT_TAGNAME = "tombstagesmod_point";
	private static String MSG_TOMB_CREATE_FAIL_KEY = "msg.tombcreatefail";
	
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void onPlayerDrops(PlayerDropsEvent event) {
		EntityPlayerMP player = (EntityPlayerMP) event.getEntityPlayer();
		if (!GameStageHelper.hasStage(player, TombStageConfig.tombStageName)) {
			String msg = I18n.format("msg.tomblock", TombStageConfig.tombStageName);
			player.sendMessage(new TextComponentString(msg));
			return;
		}
		if (player instanceof  FakePlayer) {
			return;
		}
		List<EntityItem> itemEntityList = event.getDrops();
		WorldServer world = player.getServerWorld();
		Optional<BlockPos> spawnPos = getPlayerTombPoint(world, player);
		spawnPos.ifPresent(pos -> createTomb(pos, world, player, itemEntityList));
		if (!spawnPos.isPresent()) {
			String msg = I18n.format(MSG_TOMB_CREATE_FAIL_KEY);
			player.sendMessage(new TextComponentString(msg));
		}
	}
	
	public static void createTomb(BlockPos pos, World world, EntityPlayer player, List<EntityItem> itemEntityList) {
		IBlockState blockState = ModBlocks.tomb.getDefaultState();
		if (!world.isAirBlock(pos)) {
			String msg = I18n.format(MSG_TOMB_CREATE_FAIL_KEY);
			player.sendMessage(new TextComponentString(msg));
			return;
		}
		boolean isTombCreated = world.setBlockState(pos, blockState, 3);
		if (!isTombCreated) {
			String msg = I18n.format(MSG_TOMB_CREATE_FAIL_KEY);
			player.sendMessage(new TextComponentString(msg));
			return;
		}
		TileTomb tile = (TileTomb) world.getTileEntity(pos);
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
	
	public static Optional<BlockPos> getPlayerTombPoint(World world, EntityPlayer player) {
		NBTTagCompound data = player.getEntityData();
		if (!data.hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
			return searchCreateTomb(world, new BlockPos(player));			
		}
		NBTTagCompound persist = data.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		if (!persist.hasKey(POINT_TAGNAME)) {
			return searchCreateTomb(world, new BlockPos(player));	
		}
		NBTTagCompound point = persist.getCompoundTag(POINT_TAGNAME);
		int x = point.getInteger("X");
		int y = point.getInteger("Y");
		int z = point.getInteger("Z");
		return Optional.of(new BlockPos(x, y, z));
	}
	
	public static Optional<BlockPos> searchCreateTomb(World world, BlockPos init) {
		int searchRange = TombStageConfig.tombSearchRange;
		BlockPos start = init.add(-searchRange, -searchRange, -searchRange);
		BlockPos end = init.add(searchRange, searchRange, searchRange);
		Iterable<BlockPos> it = BlockPos.getAllInBox(start,end);
		return StreamSupport.stream(BlockPos.getAllInBox(start,end).spliterator(), false)
				.sorted((pos0, pos1) -> {
					double distance1 = pos0.distanceSq(init.getX(), init.getY(), init.getZ());
					double distance2 = pos1.distanceSq(init.getX(), init.getY(), init.getZ());
					return Double.compare(distance1, distance2);
				})
				.filter(pos -> world.isAirBlock(pos))
				.findAny();
	}
	
	@SubscribeEvent
	public static void onPlayerUpdate(LivingUpdateEvent event) {
		if (!(event.getEntityLiving() instanceof EntityPlayer)
				|| event.getEntityLiving().getEntityWorld().isRemote) {
			return;
		}
		EntityPlayer player = (EntityPlayer) event.getEntityLiving();
		NBTTagCompound data = player.getEntityData();
		if (!data.hasKey(EntityPlayer.PERSISTED_NBT_TAG)) {
			data.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());
		}
		World world = player.getEntityWorld();
		NBTTagCompound persist = data.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		if (!persist.hasKey(POINT_TAGNAME)) {
			persist.setTag(POINT_TAGNAME, new NBTTagCompound());
		}
		NBTTagCompound point = persist.getCompoundTag(POINT_TAGNAME);
		BlockPos playerPos = new BlockPos(player);
		if (world.isAirBlock(playerPos.down())) {
			return;
		}
		searchCreateTomb(world, playerPos).ifPresent(pos -> {
			point.setInteger("X", pos.getX());
			point.setInteger("Y", pos.getY());
			point.setInteger("Z", pos.getZ());
		});
	}
}
