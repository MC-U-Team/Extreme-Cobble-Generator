package info.u_team.extreme_cobble_generator.network;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorConstants;
import info.u_team.extreme_cobble_generator.network.message.MessageCobbleGeneratorUpdateAmount;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ExtremeCobbleGeneratorNetworkHandler {
	
	public static SimpleNetworkWrapper network;
	
	public ExtremeCobbleGeneratorNetworkHandler() {
		network = NetworkRegistry.INSTANCE.newSimpleChannel(ExtremeCobbleGeneratorConstants.MODID);
		message();
	}
	
	private void message() {
		network.registerMessage(MessageCobbleGeneratorUpdateAmount.Handler.class, MessageCobbleGeneratorUpdateAmount.class, 0, Side.SERVER);
	}
	
}
