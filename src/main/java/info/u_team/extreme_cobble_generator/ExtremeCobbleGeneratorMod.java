package info.u_team.extreme_cobble_generator;

import info.u_team.u_team_core.util.verify.JarSignVerifier;
import net.minecraftforge.fml.common.Mod;

@Mod(ExtremeCobbleGeneratorMod.MODID)
public class ExtremeCobbleGeneratorMod {
	
	public static final String MODID = "extremecobblegenerator";
	
	public ExtremeCobbleGeneratorMod() {
		JarSignVerifier.checkSigned(MODID);
	}
}
