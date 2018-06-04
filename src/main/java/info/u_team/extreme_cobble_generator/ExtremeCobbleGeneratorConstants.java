package info.u_team.extreme_cobble_generator;

import org.apache.logging.log4j.*;

public class ExtremeCobbleGeneratorConstants {
	
	public static final String MODID = "extremecobblegenerator";
	public static final String NAME = "Extreme Cobble Generator";
	public static final String VERSION = "@VERSION@";
	public static final String MCVERSION = "1.12.2";
	public static final String DEPENDENCIES = "required-after:uteamcore@[1.1.2,);";
	public static final String UPDATEURL = "https://api.u-team.info/update/extremecobblegenerator.json";
	
	public static final Logger LOGGER = LogManager.getLogger(NAME);
	
	public static final String COMMONPROXY = "info.u_team.extreme_cobble_generator.proxy.CommonProxy";
	public static final String CLIENTPROXY = "info.u_team.extreme_cobble_generator.proxy.ClientProxy";
	
	private ExtremeCobbleGeneratorConstants() {
	}
	
}
