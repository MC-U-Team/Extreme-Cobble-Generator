package info.u_team.extreme_cobble_generator.block;

import javax.annotation.Nonnull;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.extreme_cobble_generator.tileentity.TileEntityCobbleGenerator;
import info.u_team.u_team_core.block.UBlockTileEntity;
import info.u_team.u_team_core.creativetab.UCreativeTab;
import info.u_team.u_team_core.tileentity.UTileEntityProvider;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.World;

public class BlockCobbleGenerator extends UBlockTileEntity {
	
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public BlockCobbleGenerator(String name, UCreativeTab tab, UTileEntityProvider provider) {
		super(name, Material.IRON, tab, provider);
		this.setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH));
	}
	
	// Facing
	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
		return super.getStateForPlacement(world, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex();
	}
	
	@Override
	public BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirror) {
		return state.withRotation(mirror.toRotation(state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rotation) {
		return state.withProperty(FACING, rotation.rotate(state.getValue(FACING)));
	}
	
	// Gui
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (world.isRemote) {
			return true;
		}
		
		if (player.isSneaking()) {
			return false;
		}
		
		TileEntity tileentity = world.getTileEntity(pos);
		
		if (tileentity instanceof TileEntityCobbleGenerator) {
			player.openGui(ExtremeCobbleGeneratorMod.getInstance(), 0, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
	
	// Rendering
	
	@Override
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}
	
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	// NBT
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		readItemStack(stack, world, pos);
	}
	
	@Override
	public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
		onBlockDestroyedByPlayer(world, pos, state);
		harvestBlock(world, player, pos, state, world.getTileEntity(pos), player.getHeldItemMainhand());
		world.setBlockToAir(pos);
		return false;
	}
	
	@Override
	public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune) {
		if (!world.isRemote && !world.restoringBlockSnapshots) {
			NonNullList<ItemStack> drops = NonNullList.create();
			getDrops(drops, world, pos, world.getBlockState(pos), 0);
			chance = net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(drops, world, pos, state, fortune, chance, false, harvesters.get());
			
			for (ItemStack stack : drops) {
				if (stack.getItem() == Item.getItemFromBlock(this)) {
					writeItemStack(stack, world, pos);
				}
				if (world.rand.nextFloat() <= chance) {
					spawnAsEntity(world, pos, stack);
				}
			}
		}
	}
	
	@Nonnull
	@Override
	public ItemStack getPickBlock(@Nonnull IBlockState state, RayTraceResult target, @Nonnull World world, @Nonnull BlockPos pos, EntityPlayer player) {
		NonNullList<ItemStack> drops = NonNullList.create();
		getDrops(drops, world, pos, world.getBlockState(pos), 0);
		if (drops.size() > 0) {
			ItemStack stack = drops.get(0);
			if (stack.getItem() == Item.getItemFromBlock(this)) {
				writeItemStack(stack, world, pos);
			}
			return stack;
		}
		return super.getPickBlock(state, target, world, pos, player);
	}
	
	// NBT Util methods
	
	private void readItemStack(ItemStack stack, World world, BlockPos pos) {
		if (!stack.hasTagCompound()) {
			return;
		}
		NBTTagCompound compound = stack.getTagCompound();
		if (compound == null || !compound.hasKey("TileEntityData", 10)) {
			return;
		}
		NBTTagCompound tiletag = compound.getCompoundTag("TileEntityData");
		
		TileEntity tileentity = world.getTileEntity(pos);
		if (!(tileentity instanceof TileEntityCobbleGenerator)) {
			return;
		}
		TileEntityCobbleGenerator generator = (TileEntityCobbleGenerator) tileentity;
		generator.readNBT(tiletag);
	}
	
	private void writeItemStack(ItemStack stack, World world, BlockPos pos) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		
		NBTTagCompound compound = stack.getTagCompound();
		NBTTagCompound tiletag = new NBTTagCompound();
		
		TileEntity tileentity = world.getTileEntity(pos);
		if (!(tileentity instanceof TileEntityCobbleGenerator)) {
			return;
		}
		TileEntityCobbleGenerator generator = (TileEntityCobbleGenerator) tileentity;
		generator.writeNBT(tiletag);
		compound.setTag("TileEntityData", tiletag);
	}
}
