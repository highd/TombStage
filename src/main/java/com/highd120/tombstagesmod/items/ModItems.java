package com.highd120.tombstagesmod.items;

import com.highd120.tombstagesmod.blocks.ModBlocks;
import com.highd120.tombstagesmod.TombStagesStatus;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> evt) {
		IForgeRegistry<Item> register = evt.getRegistry();
		registItemBlock(register, ModBlocks.tomb);
	}
}
