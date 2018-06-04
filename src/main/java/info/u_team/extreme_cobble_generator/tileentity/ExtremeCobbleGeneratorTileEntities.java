package info.u_team.extreme_cobble_generator.tileentity;

import info.u_team.u_team_core.tileentity.UTileEntityProvider;

public class ExtremeCobbleGeneratorTileEntities {
	
	public static UTileEntityProvider cobblegenerator;
	
	public ExtremeCobbleGeneratorTileEntities() {
		cobblegenerator = new UTileEntityProvider("cobblegenerator", TileEntityCobbleGenerator.class);
	}
	
}
