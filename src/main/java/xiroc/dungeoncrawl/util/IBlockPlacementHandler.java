package xiroc.dungeoncrawl.util;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import xiroc.dungeoncrawl.part.block.Barrel;
import xiroc.dungeoncrawl.part.block.Chest;
import xiroc.dungeoncrawl.part.block.Furnace;
import xiroc.dungeoncrawl.part.block.Spawner;

public interface IBlockPlacementHandler {

	public static final HashMap<Block, IBlockPlacementHandler> handlers = new HashMap<Block, IBlockPlacementHandler>();

	public static final IBlockPlacementHandler DEFAULT = (world, state, pos, rand, lootLevel) -> {
		world.setBlockState(pos, state, 2);
	};

	public static void load() {
		handlers.put(Blocks.BARREL, new Barrel());
		handlers.put(Blocks.CHEST, new Chest());
		handlers.put(Blocks.FURNACE, new Furnace());
		handlers.put(Blocks.SPAWNER, new Spawner());
	}

	public abstract void setupBlock(IWorld world, BlockState state, BlockPos pos, Random rand, int lootLevel);

	public static IBlockPlacementHandler getHandler(Block block) {
		IBlockPlacementHandler handler = handlers.get(block);
		if (handler == null)
			return DEFAULT;
		return handler;
	}

}