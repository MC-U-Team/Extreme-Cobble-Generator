package info.u_team.extreme_cobble_generator.block;

import info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorItemGroups;
import info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorTileEntityTypes;
import info.u_team.extreme_cobble_generator.tileentity.CobbleGeneratorTileEntity;
import info.u_team.u_team_core.block.UTileEntityBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class CobbleGeneratorBlock extends UTileEntityBlock {
	
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	
	public CobbleGeneratorBlock() {
		super(ExtremeCobbleGeneratorItemGroups.GROUP, Properties.create(Material.IRON).doesNotBlockMovement().hardnessAndResistance(2.0F), ExtremeCobbleGeneratorTileEntityTypes.GENERATOR);
		this.setDefaultState(getDefaultState().with(FACING, Direction.NORTH));
	}
	
	// Trigger generator for production stop / start
	
	@Override
	public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		if (world.isRemote) {
			return;
		}
		isTileEntityFromType(world, pos).map(CobbleGeneratorTileEntity.class::cast).ifPresent(CobbleGeneratorTileEntity::neighborChanged);
	}
	
	// Open gui
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
		return openContainer(world, pos, player, true);
	}
	
	// Facing stuff
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.with(FACING, rotation.rotate(state.get(FACING)));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.toRotation(state.get(FACING)));
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
