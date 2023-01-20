package com.highd120.tombstagesmod;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = TombStagesStatus.MOD_ID, type = Type.INSTANCE, name = TombStagesStatus.MOD_ID)
public class TombStageConfig {
	@Comment({"Name of the stage that locks the generation of the tomb"})
	public static String tombStageName = "tomb";
	
	@Comment({"Search area of the place where the grave is to be generated"})
	public static int tombSearchRange = 3;
	
	@Comment({"Name of the stage that locks the soul drop"})
	public static String soulStageName = "soul";

	@Comment({"If true, souls are dropped upon death."})
	public static boolean isDropSoul = true;
	
	@Comment({"If true, the tomb is generated at death."})
	public static boolean isCreateTomb = true;

	@Comment({"Capacity of the \"soul wand\". Unit is mb."})
	public static int soulTorchCapacity = 10000;

	@Comment({"Fuel for the \"soul wand.\""})
	public static String soulTorchFuel = "lava";

	@Comment({"Fuel consumption of the \"soul wand.\""})
	public static int soulTorchConsumption = 50;
	
	@Comment({"Number of slots in the tomb"})
	public static int tombSlotCount = 60;
}
