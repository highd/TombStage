package com.highd120.tombstagesmod.items;

import java.util.List;

import com.highd120.tombstagesmod.TombStageConfig;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
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
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;

public class ItemSoulTorch extends ItemBase {
	public ItemSoulTorch() {
		super();
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.MISC);
	}

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

		FluidStack fluidStack = FluidUtil.getFluidContained(stack);
		if (fluidStack == null || fluidStack.amount < TombStageConfig.soulTorchConsumption) {
    		return super.onItemRightClick(worldIn, playerIn, handIn);
		}
		IFluidHandlerItem handler = FluidUtil.getFluidHandler(stack);
		handler.drain(TombStageConfig.soulTorchConsumption, true);
    	
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
    	if (tag != null && tag.hasKey("X")) {
        	int x = tag.getInteger("X");
        	int y = tag.getInteger("Y");
        	int z = tag.getInteger("Z");
        	tooltip.add("(" + x + "," + y + "," + z + ")");
    	}
		FluidStack fluidStack = FluidUtil.getFluidContained(stack);
		if (fluidStack != null) {
			tooltip.add(fluidStack.getLocalizedName() + ": " + fluidStack.amount + "/" + TombStageConfig.soulTorchCapacity + "mB");
		}
    }

	@Override
	public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt) {
		return new FluidHandlerItemStack(stack, TombStageConfig.soulTorchCapacity) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return fluid.getFluid().getName().equals(TombStageConfig.soulTorchFuel);
			}
		};
	}
}
