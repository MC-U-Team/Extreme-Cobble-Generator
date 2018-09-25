package info.u_team.extreme_cobble_generator.network;

import info.u_team.extreme_cobble_generator.network.message.MessageCobbleGeneratorUpdateAmount;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class ExtremeCobbleGeneratorNetworkHandler {
	
	public static final SimpleNetworkWrapper network = NetworkRegistry.INSTANCE.newSimpleChannel("excbbgen"); // This is so annoying that there is no documentation about, that you can use
	// only a limited string size. Took me 1h to find. Thx forge :c
	
	public static void init() {
		network.registerMessage(MessageCobbleGeneratorUpdateAmount.Handler.class, MessageCobbleGeneratorUpdateAmount.class, 0, Side.SERVER);
	}
	
}
