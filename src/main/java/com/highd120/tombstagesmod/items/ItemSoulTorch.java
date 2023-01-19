package com.highd120.tombstagesmod.items;

import java.util.List;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemSoulTorch extends ItemBase {
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack= playerIn.getHeldItem(handIn);
    	if (playerIn.getHeldItemMainhand() != stack) {
    		return super.onItemRightClick(worldIn, playerIn, handIn);
    	}
    	
    	if (stack.isEmpty() || stack.getItem() != ModItems.soulTorch) {
    		return super.onItemRightClick(worldIn, playerIn, handIn);
    	}
    	NBTTagCompound tag = stack.getTagCompound();
    	if (tag == null) {
    		return super.onItemRightClick(worldIn, playerIn, handIn);
    	}
    	
    	if (!worldIn.isRemote) {
    		return super.onItemRightClick(worldIn, playerIn, handIn);
    	}
    	
		double x = tag.getInteger("X");
		double y = tag.getInteger("Y");
		double z = tag.getInteger("Z");
		
		Vec3d playerPos = playerIn.getPositionVector().addVector(0, 1.8, 0);
		Vec3d targetPos = new Vec3d(x, y, z);
		
		Vec3d targetVec = targetPos.subtract(playerPos).normalize();
		for (int i = 0; i < 9 ; i++) {
			Vec3d speed = targetVec
					.rotatePitch(i / 3 * 0.02f)
					.rotateYaw(i % 3 * 0.02f)
					.scale(0.6);
			worldIn.spawnParticle(EnumParticleTypes.FLAME, 
					playerPos.x, playerPos.y, playerPos.z, 
					speed.x, speed.y, speed.z);
		}
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, stack);
	}
    
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
