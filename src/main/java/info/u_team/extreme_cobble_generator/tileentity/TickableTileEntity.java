package info.u_team.extreme_cobble_generator.tileentity;

import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.tileentity.*;

// TODO move to uteamcore
public abstract class TickableTileEntity extends UTileEntity implements ITickableTileEntity {
	
	private boolean first;
	
	public TickableTileEntity(TileEntityType<?> type) {
		super(type);
	}
	
	@Override
	public void tick() {
		if (!first) {
			first = true;
			firstTick();
		}
		if (world.isRemote()) {
			tickClient();
		} else {
			tickServer();
		}
	}
	
	protected void firstTick() {
	}
	
	protected void tickServer() {
	}
	
	protected void tickClient() {
	}
	
}
