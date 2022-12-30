package com.highd120.tombstagesmod;

import com.highd120.tombstagesmod.blocks.TileTomb;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = TombStagesStatus.MOD_ID, name = TombStagesStatus.NAME, version = TombStagesStatus.VERSION)
public class TombStagesMod{
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		GameRegistry.registerTileEntity(TileTomb.class, new ResourceLocation(TombStagesStatus.MOD_ID, "tomb"));
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(PlayerDropEvent.class);
    }
}
