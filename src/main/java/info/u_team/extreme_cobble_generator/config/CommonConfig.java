package info.u_team.extreme_cobble_generator.config;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;

public class CommonConfig {
	
	public static final ForgeConfigSpec CONFIG;
	private static final CommonConfig INSTANCE;
	
	static {
		final Pair<CommonConfig, ForgeConfigSpec> pair = new Builder().configure(CommonConfig::new);
		CONFIG = pair.getRight();
		INSTANCE = pair.getLeft();
	}
	
	public static CommonConfig getInstance() {
		return INSTANCE;
	}
	
	public final IntValue capacity;
	
	public final IntValue maxReceive;
	
	public final IntValue costPerCobble;
	
	public final IntValue maxGeneration;
	
	private CommonConfig(Builder builder) {
		builder.comment("Energy settings").push("energySettings");
		capacity = builder.comment("The FE capacity of the internal storage of the generator").defineInRange("capacity", 2_000_000, 10_000, 1_000_000_000);
		maxReceive = builder.comment("The maximum FE/Tick that the generator can receive").defineInRange("maxReceive", 1_001_000, 10_000, 1_000_000_000);
		builder.pop();
		
		builder.comment("Generation settings").push("generationSettings");
		costPerCobble = builder.comment("How much FE one cobble generation consume").defineInRange("costPerCobble", 10, 1, 10_000);
		maxGeneration = builder.comment("The maximum FE/Tick that the generator can receive").defineInRange("maxGeneration", 100000, 10, 1_000_000);
		builder.pop();
	}
	
}
