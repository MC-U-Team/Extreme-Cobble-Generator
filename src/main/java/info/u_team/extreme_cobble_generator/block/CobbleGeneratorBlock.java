package info.u_team.extreme_cobble_generator.block;

import info.u_team.extreme_cobble_generator.blockentity.CobbleGeneratorBlockEntity;
import info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorCreativeTabs;
import info.u_team.extreme_cobble_generator.init.ExtremeCobbleGeneratorBlockEntityTypes;
import info.u_team.u_team_core.block.UEntityBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class CobbleGeneratorBlock extends UEntityBlock {
	
	public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
	
	public CobbleGeneratorBlock() {
		super(ExtremeCobbleGeneratorCreativeTabs.TAB, Properties.of(Material.METAL).noOcclusion().strength(4), ExtremeCobbleGeneratorBlockEntityTypes.GENERATOR);
		registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
		if ((type != tileEntityType.get()) || level.isClientSide()) {
			return null;
		}
		return (level_, pos, state_, instance) -> CobbleGeneratorBlockEntity.serverTick(level_, pos, state_, (CobbleGeneratorBlockEntity) instance);
	}
	
	// Trigger generator for production stop / start
	
	@Override
	public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
		if (level.isClientSide()) {
			return;
		}
		getBlockEntity(level, pos).map(CobbleGeneratorBlockEntity.class::cast).ifPresent(CobbleGeneratorBlockEntity::neighborChanged);
	}
	
	// Open gui
	
	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		return openMenu(level, pos, player, true);
	}
	
	// Facing stuff
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
	}
	
	@Override
	public BlockState rotate(BlockState state, Rotation rotation) {
		return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public BlockState mirror(BlockState state, Mirror mirror) {
		return state.rotate(mirror.getRotation(state.getValue(FACING)));
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
}
