package xiroc.dungeoncrawl.dungeon.segment;

/*
 * DungeonCrawl (C) 2019 XYROC (XIROC1337), All Rights Reserved 
 */

import java.io.IOException;
import java.util.HashMap;

import net.minecraft.resources.IResourceManager;
import xiroc.dungeoncrawl.DungeonCrawl;
import xiroc.dungeoncrawl.util.DungeonSegmentModelReader;

public class DungeonSegmentModelRegistry {

	public static boolean LOADED = false;

	public static final HashMap<Integer, DungeonSegmentModel> MAP = new HashMap<Integer, DungeonSegmentModel>();

	public static final DungeonSegmentModelBlock NONE = new DungeonSegmentModelBlock(DungeonSegmentModelBlockType.NONE);

	public static final DungeonSegmentModelBlock WATER = new DungeonSegmentModelBlock(
			DungeonSegmentModelBlockType.NONE);
	public static final DungeonSegmentModelBlock LAVA = new DungeonSegmentModelBlock(DungeonSegmentModelBlockType.NONE);

	// public static final DungeonSegmentModelBlock TORCH_DARK_NORTH = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.TORCH_DARK);
	// public static final DungeonSegmentModelBlock TORCH_DARK_EAST = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.TORCH_DARK);
	// public static final DungeonSegmentModelBlock TORCH_DARK_SOUTH = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.TORCH_DARK);
	// public static final DungeonSegmentModelBlock TORCH_DARK_WEST = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.TORCH_DARK);
	//
	// public static final DungeonSegmentModelBlock CEILING = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.CEILING);
	// public static final DungeonSegmentModelBlock CEILING_STAIRS_NORTH = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.CEILING_STAIRS,
	// Direction.NORTH, false);
	// public static final DungeonSegmentModelBlock CEILING_STAIRS_EAST = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.CEILING_STAIRS,
	// Direction.EAST, false);
	// public static final DungeonSegmentModelBlock CEILING_STAIRS_SOUTH = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.CEILING_STAIRS,
	// Direction.SOUTH, false);
	// public static final DungeonSegmentModelBlock CEILING_STAIRS_WEST = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.CEILING_STAIRS,
	// Direction.WEST, false);
	// public static final DungeonSegmentModelBlock CEILING_STAIRS_NORTH_UD =
	// new DungeonSegmentModelBlock(DungeonSegmentModelBlockType.CEILING_STAIRS,
	// Direction.NORTH, true);
	// public static final DungeonSegmentModelBlock CEILING_STAIRS_EAST_UD = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.CEILING_STAIRS,
	// Direction.EAST, true);
	// public static final DungeonSegmentModelBlock CEILING_STAIRS_SOUTH_UD =
	// new DungeonSegmentModelBlock(DungeonSegmentModelBlockType.CEILING_STAIRS,
	// Direction.SOUTH, true);
	// public static final DungeonSegmentModelBlock CEILING_STAIRS_WEST_UD = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.CEILING_STAIRS,
	// Direction.WEST, true);
	//
	// public static final DungeonSegmentModelBlock WALL = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.WALL);
	// public static final DungeonSegmentModelBlock WALL_LOG = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.WALL_LOG,
	// Direction.UP, false);
	//
	// public static final DungeonSegmentModelBlock FLOOR = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.FLOOR);
	// public static final DungeonSegmentModelBlock FLOOR_STAIRS_NORTH = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.FLOOR_STAIRS,
	// Direction.NORTH, false);
	// public static final DungeonSegmentModelBlock FLOOR_STAIRS_EAST = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.FLOOR_STAIRS,
	// Direction.EAST, false);
	// public static final DungeonSegmentModelBlock FLOOR_STAIRS_SOUTH = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.FLOOR_STAIRS,
	// Direction.SOUTH, false);
	// public static final DungeonSegmentModelBlock FLOOR_STAIRS_WEST = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.FLOOR_STAIRS,
	// Direction.WEST, false);
	// public static final DungeonSegmentModelBlock FLOOR_STAIRS_NORTH_UD = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.FLOOR_STAIRS,
	// Direction.NORTH, true);
	// public static final DungeonSegmentModelBlock FLOOR_STAIRS_EAST_UD = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.FLOOR_STAIRS,
	// Direction.EAST, true);
	// public static final DungeonSegmentModelBlock FLOOR_STAIRS_SOUTH_UD = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.FLOOR_STAIRS,
	// Direction.SOUTH, true);
	// public static final DungeonSegmentModelBlock FLOOR_STAIRS_WEST_UD = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.FLOOR_STAIRS,
	// Direction.WEST, true);
	//
	// public static final DungeonSegmentModelBlock RND_WALL_SPAWNER = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.RAND_WALL_SPAWNER);
	// public static final DungeonSegmentModelBlock CHEST_COMMON_NORTH = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.CHEST_COMMON,
	// Direction.NORTH, false);
	// public static final DungeonSegmentModelBlock CHEST_COMMON_EAST = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.CHEST_COMMON,
	// Direction.EAST, false);
	// public static final DungeonSegmentModelBlock CHEST_COMMON_SOUTH = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.CHEST_COMMON,
	// Direction.SOUTH, false);
	// public static final DungeonSegmentModelBlock CHEST_COMMON_WEST = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.CHEST_COMMON,
	// Direction.WEST, false);
	// public static final DungeonSegmentModelBlock RND_CC_FLOOR_SPWN_NORTH =
	// new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.RAND_FLOOR_CHESTCOMMON_SPAWNER,
	// Direction.NORTH, false);
	// public static final DungeonSegmentModelBlock RND_CC_FLOOR_SPWN_EAST = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.RAND_FLOOR_CHESTCOMMON_SPAWNER,
	// Direction.EAST, false);
	// public static final DungeonSegmentModelBlock RND_CC_FLOOR_SPWN_SOUTH =
	// new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.RAND_FLOOR_CHESTCOMMON_SPAWNER,
	// Direction.SOUTH, false);
	// public static final DungeonSegmentModelBlock RND_CC_FLOOR_SPWN_WEST = new
	// DungeonSegmentModelBlock(DungeonSegmentModelBlockType.RAND_FLOOR_CHESTCOMMON_SPAWNER,
	// Direction.WEST, false);
	//
	// public static final DungeonSegmentModelTrapDoorBlock TRAPDOOR_NORTH = new
	// DungeonSegmentModelTrapDoorBlock(DungeonSegmentModelBlockType.TRAPDOOR,
	// Direction.NORTH, true, Half.BOTTOM, false);
	// public static final DungeonSegmentModelTrapDoorBlock TRAPDOOR_EAST = new
	// DungeonSegmentModelTrapDoorBlock(DungeonSegmentModelBlockType.TRAPDOOR,
	// Direction.EAST, true, Half.BOTTOM, false);
	// public static final DungeonSegmentModelTrapDoorBlock TRAPDOOR_SOUTH = new
	// DungeonSegmentModelTrapDoorBlock(DungeonSegmentModelBlockType.TRAPDOOR,
	// Direction.SOUTH, true, Half.BOTTOM, false);
	// public static final DungeonSegmentModelTrapDoorBlock TRAPDOOR_WEST = new
	// DungeonSegmentModelTrapDoorBlock(DungeonSegmentModelBlockType.TRAPDOOR,
	// Direction.WEST, true, Half.BOTTOM, false);
	//
	// public static final DungeonSegmentModelTrapDoorBlock
	// TRAPDOOR_CLOSED_NORTH = new
	// DungeonSegmentModelTrapDoorBlock(DungeonSegmentModelBlockType.TRAPDOOR,
	// Direction.NORTH, false, Half.BOTTOM, false);
	// public static final DungeonSegmentModelTrapDoorBlock TRAPDOOR_CLOSED_EAST
	// = new
	// DungeonSegmentModelTrapDoorBlock(DungeonSegmentModelBlockType.TRAPDOOR,
	// Direction.EAST, false, Half.BOTTOM, false);
	// public static final DungeonSegmentModelTrapDoorBlock
	// TRAPDOOR_CLOSED_SOUTH = new
	// DungeonSegmentModelTrapDoorBlock(DungeonSegmentModelBlockType.TRAPDOOR,
	// Direction.SOUTH, false, Half.BOTTOM, false);
	// public static final DungeonSegmentModelTrapDoorBlock TRAPDOOR_CLOSED_WEST
	// = new
	// DungeonSegmentModelTrapDoorBlock(DungeonSegmentModelBlockType.TRAPDOOR,
	// Direction.WEST, false, Half.BOTTOM, false);
	//
	// public static final DungeonSegmentModelFourWayBlock IRON_BARS_NORTH = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// true, false, false, false, false);
	// public static final DungeonSegmentModelFourWayBlock IRON_BARS_NORTH_EAST
	// = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// true, true, false, false, false);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_NORTH_EAST_SOUTH = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// true, true, true, false, false);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_NORTH_EAST_SOUTH_WEST = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// true, true, true, true, false);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_NORTH_EAST_WEST = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// true, true, false, true, false);
	// public static final DungeonSegmentModelFourWayBlock IRON_BARS_NORTH_SOUTH
	// = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// true, false, true, false, false);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_NORTH_SOUTH_WEST = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// true, false, true, true, false);
	// public static final DungeonSegmentModelFourWayBlock IRON_BARS_NORTH_WEST
	// = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// true, false, false, true, false);
	// public static final DungeonSegmentModelFourWayBlock IRON_BARS_EAST = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// false, true, false, false, false);
	// public static final DungeonSegmentModelFourWayBlock IRON_BARS_EAST_SOUTH
	// = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// false, true, true, false, false);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_EAST_SOUTH_WEST = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// false, true, true, true, false);
	// public static final DungeonSegmentModelFourWayBlock IRON_BARS_EAST_WEST =
	// new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// false, true, false, true, false);
	// public static final DungeonSegmentModelFourWayBlock IRON_BARS_SOUTH = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// false, false, true, false, false);
	// public static final DungeonSegmentModelFourWayBlock IRON_BARS_SOUTH_WEST
	// = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// false, false, true, true, false);
	// public static final DungeonSegmentModelFourWayBlock IRON_BARS_WEST = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// false, false, false, true, false);
	//
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_NORTH_WATERLOGGED = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// true, false, false, false, true);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_NORTH_EAST_WATERLOGGED = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// true, true, false, false, true);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_NORTH_EAST_SOUTH_WATERLOGGED = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// true, true, true, false, true);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_NORTH_EAST_SOUTH_WEST_WATERLOGGED = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// true, true, true, true, true);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_NORTH_EAST_WEST_WATERLOGGED = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// true, true, false, true, true);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_NORTH_SOUTH_WATERLOGGED = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// true, false, true, false, true);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_NORTH_SOUTH_WEST_WATERLOGGED = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// true, false, true, true, true);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_NORTH_WEST_WATERLOGGED = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// true, false, false, true, true);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_EAST_WATERLOGGED = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// false, true, false, false, true);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_EAST_SOUTH_WATERLOGGED = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// false, true, true, false, true);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_EAST_SOUTH_WEST_WATERLOGGED = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// false, true, true, true, true);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_EAST_WEST_WATERLOGGED = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// false, true, false, true, true);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_SOUTH_WATERLOGGED = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// false, false, true, false, true);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_SOUTH_WEST_WATERLOGGED = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// false, false, true, true, true);
	// public static final DungeonSegmentModelFourWayBlock
	// IRON_BARS_WEST_WATERLOGGED = new
	// DungeonSegmentModelFourWayBlock(DungeonSegmentModelBlockType.IRON_BARS,
	// false, false, false, true, true);

	public static DungeonSegmentModel BRIDGE;
	public static DungeonSegmentModel BRIDGE_TURN;
	public static DungeonSegmentModel BRIDGE_SIDE;
	public static DungeonSegmentModel BRIDGE_ALL_SIDES;

	public static DungeonSegmentModel CORRIDOR;
	public static DungeonSegmentModel CORRIDOR_TURN;
	public static DungeonSegmentModel CORRIDOR_OPEN;
	public static DungeonSegmentModel CORRIDOR_ALL_OPEN;

	public static DungeonSegmentModel CORRIDOR_2;
	public static DungeonSegmentModel CORRIDOR_2_TURN;
	public static DungeonSegmentModel CORRIDOR_2_OPEN;
	public static DungeonSegmentModel CORRIDOR_2_ALL_OPEN;

	public static DungeonSegmentModel CORRIDOR_3;
	public static DungeonSegmentModel CORRIDOR_3_TURN;
	public static DungeonSegmentModel CORRIDOR_3_OPEN;
	public static DungeonSegmentModel CORRIDOR_3_ALL_OPEN;

	public static DungeonSegmentModel CORRIDOR_ROOM;
	public static DungeonSegmentModel CORRIDOR_TRAP;

	public static DungeonSegmentModel HOLE;
	public static DungeonSegmentModel HOLE_LAVA;
	public static DungeonSegmentModel HOLE_TRAP;

	public static DungeonSegmentModel STAIRS;
	public static DungeonSegmentModel STAIRS_TOP;
	public static DungeonSegmentModel STAIRS_BOTTOM;

	public static DungeonSegmentModel ROOM;

	public static DungeonSegmentModel LARGE_ROOM;

	public static DungeonSegmentModel ENTRANCE;
	
	public static DungeonSegmentModel KITCHEN;
	
	public static DungeonSegmentModel LOOT_ROOM;

	public static void load(IResourceManager resourceManager) {
		if (LOADED)
			return;
		DungeonCrawl.LOGGER.info("Loading dungeon segment models");
		CORRIDOR = loadFromFile("models/dungeon/corridor.json", resourceManager).setId(0);
		CORRIDOR_TURN = loadFromFile("models/dungeon/corridor_turn.json", resourceManager).setId(1);
		CORRIDOR_OPEN = loadFromFile("models/dungeon/corridor_open.json", resourceManager).setId(2);
		CORRIDOR_ALL_OPEN = loadFromFile("models/dungeon/corridor_all_open.json", resourceManager).setId(3);
		CORRIDOR_2 = loadFromFile("models/dungeon/corridor_2.json", resourceManager).setId(4);
		CORRIDOR_2_TURN = loadFromFile("models/dungeon/corridor_2_turn.json", resourceManager).setId(5);
		CORRIDOR_2_OPEN = loadFromFile("models/dungeon/corridor_2_open.json", resourceManager).setId(6);
		CORRIDOR_2_ALL_OPEN = loadFromFile("models/dungeon/corridor_2_all_open.json", resourceManager).setId(7);
		CORRIDOR_3 = loadFromFile("models/dungeon/corridor_3.json", resourceManager).setId(8);
		CORRIDOR_3_TURN = loadFromFile("models/dungeon/corridor_3_turn.json", resourceManager).setId(9);
		CORRIDOR_3_OPEN = loadFromFile("models/dungeon/corridor_3_open.json", resourceManager).setId(10);
		CORRIDOR_3_ALL_OPEN = loadFromFile("models/dungeon/corridor_3_all_open.json", resourceManager).setId(11);

		CORRIDOR_ROOM = loadFromFile("models/dungeon/corridor_room.json", resourceManager).setId(12);
		CORRIDOR_TRAP = loadFromFile("models/dungeon/corridor_trap.json", resourceManager).setId(13);

		HOLE = loadFromFile("models/dungeon/hole.json", resourceManager).setId(14);
		HOLE_LAVA = loadFromFile("models/dungeon/hole_lava.json", resourceManager).setId(15);

		STAIRS = loadFromFile("models/dungeon/stairs.json", resourceManager).setId(16);
		STAIRS_TOP = loadFromFile("models/dungeon/stairs_top.json", resourceManager).setId(17);
		STAIRS_BOTTOM = loadFromFile("models/dungeon/stairs_bottom.json", resourceManager).setId(18);
		ROOM = loadFromFile("models/dungeon/room.json", resourceManager).setId(19);
		ENTRANCE = loadFromFile("models/dungeon/entrance.json", resourceManager).setId(20);

		BRIDGE = loadFromFile("models/dungeon/bridge.json", resourceManager).setId(21);
		BRIDGE_TURN = loadFromFile("models/dungeon/bridge_turn.json", resourceManager).setId(22);
		BRIDGE_SIDE = loadFromFile("models/dungeon/bridge_side.json", resourceManager).setId(23);
		BRIDGE_ALL_SIDES = loadFromFile("models/dungeon/bridge_all_sides.json", resourceManager).setId(24);

		LARGE_ROOM = loadFromFile("models/dungeon/large_room.json", resourceManager).setId(25);

		HOLE_TRAP = loadFromFile("models/dungeon/hole_trap.json", resourceManager).setId(26);
		
		KITCHEN = loadFromFile("models/dungeon/kitchen.json", resourceManager).setId(27);
		
		LOOT_ROOM = loadFromFile("models/dungeon/loot_room.json", resourceManager).setId(28);

		LOADED = true;
	}

	public static DungeonSegmentModel loadFromFile(String path, IResourceManager resourceManager) {
		DungeonCrawl.LOGGER.debug("Loading model {}", path);
		try {
			return DungeonSegmentModelReader
					.readModelFromInputStream(resourceManager.getResource(DungeonCrawl.locate(path)).getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
