package info.u_team.extreme_cobble_generator.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import info.u_team.extreme_cobble_generator.ExtremeCobbleGeneratorMod;
import info.u_team.u_team_core.tileentity.UTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

public class SetTileEntityNBT extends LootFunction {
	
	private SetTileEntityNBT(ILootCondition[] conditions) {
		super(conditions);
	}
	
	public ItemStack doApply(ItemStack stack, LootContext context) {
		if (context.has(LootParameters.BLOCK_ENTITY)) {
			final CompoundNBT compound = new CompoundNBT();
			final TileEntity tileEntity = context.get(LootParameters.BLOCK_ENTITY);
			if (tileEntity instanceof UTileEntity) {
				((UTileEntity) tileEntity).writeNBT(compound);
			} else {
				tileEntity.write(compound);
			}
			if (!compound.isEmpty()) {
				stack.setTagInfo("BlockEntityTag", compound);
			}
		}
		return stack;
	}
	
	public static LootFunction.Builder<?> builder() {
		return builder((conditions) -> new SetTileEntityNBT(conditions));
	}
	
	public static class Serializer extends LootFunction.Serializer<SetTileEntityNBT> {
		
		public Serializer() {
			super(new ResourceLocation(ExtremeCobbleGeneratorMod.MODID, "set_tileentity_nbt"), SetTileEntityNBT.class);
		}
		
		@Override
		public SetTileEntityNBT deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditions) {
			return new SetTileEntityNBT(conditions);
		}
	}
}