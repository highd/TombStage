package com.highd120.tombstagesmod.items;

import java.util.List;

import javax.annotation.Nonnull;

import com.highd120.tombstagesmod.TombStageConfig;
import com.highd120.tombstagesmod.TombStagesStatus;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSoulTorch extends ItemBase {
	String[] meteNameList = new String[] {
		"soul_less_torch",
		"soul_torch"
	};
	public ItemSoulTorch() {
		super();
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.MISC);
		setHasSubtypes(true);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack= playerIn.getHeldItem(handIn);
		if (stack.getMetadata() == 0) {
    		return super.onItemRightClick(worldIn, playerIn, handIn);
		}
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
		fluidStack = FluidUtil.getFluidContained(stack);
		if (fluidStack.amount <= TombStageConfig.soulTorchConsumption) {
			stack.setItemDamage(1);
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
	public int getMetadata(ItemStack stack) {
		FluidStack fluidStack = FluidUtil.getFluidContained(stack);
		if (fluidStack == null || fluidStack.amount <= TombStageConfig.soulTorchConsumption) {
			return 0;
		}
    	NBTTagCompound tag = stack.getTagCompound();
    	if (tag == null || !tag.hasKey("X") ) {
    		return 0;
    	}
		return 1;
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
	
	@Nonnull
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		return "item." + TombStagesStatus.MOD_ID + "." + meteNameList[Math.min(meteNameList.length - 1,
				par1ItemStack.getItemDamage())];
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerModel() {
		for (int i = 0; i < meteNameList.length; i++) {
			String location = TombStagesStatus.MOD_ID + ":" + meteNameList[i];
			ModelLoader.setCustomModelResourceLocation(this, i,
					new ModelResourceLocation(location, "inventory"));
		}
	}
}
