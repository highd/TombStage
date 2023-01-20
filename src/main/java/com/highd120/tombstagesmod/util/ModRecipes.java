package com.highd120.tombstagesmod.util;

import com.highd120.tombstagesmod.TombStagesStatus;
import com.highd120.tombstagesmod.items.SoulTorchAndBucketRecipe;
import com.highd120.tombstagesmod.items.SoulTorchAndSoulRecipe;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid = TombStagesStatus.MOD_ID)
public class ModRecipes {
	@SubscribeEvent
	public static void registerRecipes(RegistryEvent.Register<IRecipe> evt) {
		IForgeRegistry<IRecipe> register = evt.getRegistry();

		register.register(new SoulTorchAndSoulRecipe()
				.setRegistryName(new ResourceLocation(TombStagesStatus.MOD_ID, "soul_torch_recipe")));
		register.register(new SoulTorchAndBucketRecipe()
				.setRegistryName(new ResourceLocation(TombStagesStatus.MOD_ID, "soul_torch_bucket_recipe")));
	}
}
