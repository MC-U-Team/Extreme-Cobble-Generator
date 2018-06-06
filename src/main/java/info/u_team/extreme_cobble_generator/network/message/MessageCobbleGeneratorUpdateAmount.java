package info.u_team.extreme_cobble_generator.network.message;

import info.u_team.extreme_cobble_generator.tileentity.TileEntityCobbleGenerator;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import net.minecraftforge.fml.common.network.simpleimpl.*;

public class MessageCobbleGeneratorUpdateAmount implements IMessage {
	
	private BlockPos pos;
	private int newvalue;
	
	public MessageCobbleGeneratorUpdateAmount() {
	}
	
	public MessageCobbleGeneratorUpdateAmount(BlockPos pos, int newvalue) {
		this.pos = pos;
		this.newvalue = newvalue;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		int x = buf.readInt();
		int y = buf.readInt();
		int z = buf.readInt();
		pos = new BlockPos(x, y, z);
		newvalue = buf.readInt();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
		buf.writeInt(newvalue);
	}
	
	public static class Handler implements IMessageHandler<MessageCobbleGeneratorUpdateAmount, IMessage> {
		
		@Override
		public IMessage onMessage(MessageCobbleGeneratorUpdateAmount message, MessageContext context) {
			final EntityPlayerMP player = context.getServerHandler().player;
			final World world = player.world;
			final IThreadListener mainThread = (WorldServer) world;
			mainThread.addScheduledTask(new Runnable() {
				
				@Override
				public void run() {
					TileEntity tileentity = world.getTileEntity(message.pos);
					if (tileentity instanceof TileEntityCobbleGenerator) {
						TileEntityCobbleGenerator generator = (TileEntityCobbleGenerator) tileentity;
						int value = message.newvalue;
						value = Math.min(Math.max(value, 1), generator.getMaxAmount());
						generator.setAmount(value);
					}
				}
			});
			return null;
		}
	}
}