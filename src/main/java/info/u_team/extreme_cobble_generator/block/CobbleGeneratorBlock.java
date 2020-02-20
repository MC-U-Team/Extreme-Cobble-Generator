package info.u_team.extreme_cobble_generator.block;

import javax.annotation.Nonnull;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorItemGroups;
import info.u_team.extreme_cobble_generator.render.tileentity.TileEntityRendererCobbleGenerator;
import info.u_team.extreme_cobble_generator.tileentity.TileEntityCobbleGenerator;
import info.u_team.u_team_core.block.UTileEntityBlock;
import info.u_team.u_team_core.util.registry.ClientRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.state.*;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class CobbleGeneratorBlock extends UTileEntityBlock {
	
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	
	public CobbleGeneratorBlock(String name) {
		super(name, Material.IRON, ExtremeCobbleGeneratorItemGroups.GROUP);
		this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
		return openContainer(world, pos, player, true);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	
	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.toRotation(state.get(FACING)));
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}
}
