package com.highd120.tombstagesmod.blocks;

import com.highd120.tombstagesmod.TombStagesStatus;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = TombStagesStatus.MOD_ID)
public class ModBlocks {
	private static <T extends Block> T regist(IForgeRegistry<Block> register, T block, String name) {
        block.setRegistryName(new ResourceLocation(TombStagesStatus.MOD_ID, name));
        block.setUnlocalizedName(TombStagesStatus.MOD_ID + "." + name);
        register.register(block);
        return block;
	}
	
	public static BlockTomb tomb;
	
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> evt) {
		IForgeRegistry<Block> register = evt.getRegistry();
		tomb = regist(register, new BlockTomb(), "tomb");
	}
}
