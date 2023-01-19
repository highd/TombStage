package com.highd120.tombstagesmod;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Type;

@Config(modid = TombStagesStatus.MOD_ID, type = Type.INSTANCE, name = TombStagesStatus.MOD_ID)
public class TombStageConfig {
	public static String tombStageName = "tomb";
	public static int tombSearchRange = 3;
	public static String soulStageName = "soul";
	public static boolean isDropSoul = true;
	public static boolean isCreateTomb = true;
}
