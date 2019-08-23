package xiroc.dungeoncrawl.dungeon.segment;

/*
 * DungeonCrawl (C) 2019 XYROC (XIROC1337), All Rights Reserved 
 */

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import xiroc.dungeoncrawl.DungeonCrawl;
import xiroc.dungeoncrawl.theme.Theme;
import xiroc.dungeoncrawl.util.IBlockPlacementHandler;

public class DungeonSegmentModel {

    public Integer id;
    public int width, height, length;
    public DungeonSegmentModelBlock[][][] model;

    public DungeonSegmentModel() {
	this.model = new DungeonSegmentModelBlock[8][8][8];
	this.width = this.height = this.length = 8;
    }

    public DungeonSegmentModel(DungeonSegmentModelBlock[][][] model) {
	this.model = model;
	this.width = model.length;
	this.height = model[0].length;
	this.length = model[0][0].length;
    }

    public DungeonSegmentModel setId(int id) {
	DungeonSegmentModelRegistry.MAP.put(id, this);
	this.id = id;
	return this;
    }
    /*
     * Test functions
     */

    public static void build(DungeonSegmentModel model, IWorld world, BlockPos pos, Theme theme, int lootLevel) {
	for (int x = 0; x < model.width; x++) {
	    for (int y = 0; y < model.height; y++) {
		for (int z = 0; z < model.length; z++) {
		    BlockState state;
		    if (model.model[x][y][z] == null)
			state = Blocks.AIR.getDefaultState();
		    else
			state = DungeonSegmentModelBlock.getBlockState(model.model[x][y][z], theme, world.getRandom());
		    if (state == null)
			continue;
		    world.setBlockState(new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z), state, 2);
		}
	    }
	}
    }

    public static void buildRotated(DungeonSegmentModel model, IWorld world, BlockPos pos, Theme theme, int lootLevel,
	    Rotation rotation) {
	switch (rotation) {
	case CLOCKWISE_90:
	    for (int x = 0; x < model.width; x++) {
		for (int y = 0; y < model.height; y++) {
		    for (int z = 0; z < model.length; z++) {
			BlockState state;
			if (model.model[x][y][z] == null)
			    state = Blocks.AIR.getDefaultState();
			else
			    state = DungeonSegmentModelBlock.getBlockState(model.model[x][y][z], theme,
				    world.getRandom(), Rotation.CLOCKWISE_90);
			if (state == null)
			    continue;
			world.setBlockState(new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z), state, 2);
		    }
		}
	    }
	    break;
	case COUNTERCLOCKWISE_90:
	    for (int x = 0; x < model.width; x++) {
		for (int y = 0; y < model.height; y++) {
		    for (int z = 0; z < model.length; z++) {
			BlockState state;
			if (model.model[x][y][z] == null)
			    state = Blocks.AIR.getDefaultState();
			else
			    state = DungeonSegmentModelBlock.getBlockState(model.model[x][y][z], theme,
				    world.getRandom(), Rotation.COUNTERCLOCKWISE_90);
			if (state == null)
			    continue;
			world.setBlockState(new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z), state, 2);
		    }
		}
	    }
	    break;
	case CLOCKWISE_180:
	    for (int x = 0; x < model.width; x++) {
		for (int y = 0; y < model.height; y++) {
		    for (int z = 0; z < model.length; z++) {
			BlockState state;
			if (model.model[x][y][z] == null)
			    state = Blocks.AIR.getDefaultState();
			else
			    state = DungeonSegmentModelBlock.getBlockState(model.model[x][y][z], theme,
				    world.getRandom(), Rotation.CLOCKWISE_180);
			if (state == null)
			    continue;
			world.setBlockState(new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z), state, 2);
		    }
		}
	    }
	    break;
	case NONE:
	    build(model, world, pos, theme, lootLevel);
	    break;
	default:
	    DungeonCrawl.LOGGER.warn("Failed to build a rotated dungeon segment: Unknown rotation " + rotation);
	    break;
	}
    }

    public static void setupBlockState(BlockState state, World world, BlockPos pos) {
	if (state == null)
	    return;
	IBlockPlacementHandler.getHandler(state.getBlock()).setupBlock(world, state, pos, world.getRandom(), 0); // lootLevel
    }

    public BlockState[][][] transform() {
	return null;
    }

}
