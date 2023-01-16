package com.highd120.tombstagesmod.items;

import com.highd120.tombstagesmod.blocks.ModBlocks;
import com.highd120.tombstagesmod.TombStagesStatus;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = TombStagesStatus.MOD_ID)
public class ModItems {
	private static ItemBlock registItemBlock(IForgeRegistry<Item> register, Block block) {
        ItemBlock item = new ItemBlock(block);
        item.setUnlocalizedName(block.getUnlocalizedName());
        item.setRegistryName(block.getRegistryName());
        register.register(item);
        ModelLoader.setCustomModelResourceLocation(item, 0,
                new ModelResourceLocation(item.getRegistryName(), "inventory"));
		return item;
	}
	private static <T extends ItemBase> T regist(IForgeRegistry<Item> register, T item, String name) {
        item.setRegistryName(new ResourceLocation(TombStagesStatus.MOD_ID, name));
        item.setUnlocalizedName(TombStagesStatus.MOD_ID + "." + name);
        register.register(item);
        item.registerModel();
		return item;
	}

	public static ItemSoulTorch soulTorch;
	public static ItemSoul soul;
	
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> register = evt.getRegistry();
		
		soulTorch = regist(register, new ItemSoulTorch(), "soul_torch");
		soul = regist(register, new ItemSoul(), "soul");
		
		registItemBlock(register, ModBlocks.tomb);
	}
}
