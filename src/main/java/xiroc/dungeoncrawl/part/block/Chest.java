package xiroc.dungeoncrawl.part.block;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.storage.loot.LootTables;
import xiroc.dungeoncrawl.DungeonCrawl;
import xiroc.dungeoncrawl.util.IBlockPlacementHandler;
import xiroc.dungeoncrawl.util.Loot;

public class Chest implements IBlockPlacementHandler {

	@Override
	public void setupBlock(IWorld world, BlockState state, BlockPos pos, Random rand, int lootLevel) {
		world.setBlockState(pos, state, 2);
		LockableLootTileEntity.setLootTable(world, world.getRandom(), pos, getLootTable(lootLevel));
	}

	public static ResourceLocation getLootTable(int lootLevel) {
		switch (lootLevel) {
		case 0:
			return Loot.STAGE_1;
		case 1:
			return Loot.STAGE_2;
		case 2:
			return Loot.STAGE_3;
		case 3:
			return LootTables.CHESTS_STRONGHOLD_CORRIDOR;
		default:
			DungeonCrawl.LOGGER.warn("Unknown Loot Level: " + lootLevel);
			return null;
		}
	}

}
