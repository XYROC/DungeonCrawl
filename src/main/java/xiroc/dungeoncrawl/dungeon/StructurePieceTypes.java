/*
        Dungeon Crawl, a procedural dungeon generator for Minecraft 1.14 and later.
        Copyright (C) 2020

        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program.  If not, see <https://www.gnu.org/licenses/>.
*/

package xiroc.dungeoncrawl.dungeon;

import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import xiroc.dungeoncrawl.DungeonCrawl;
import xiroc.dungeoncrawl.config.Config;
import xiroc.dungeoncrawl.dungeon.piece.DummyStructurePiece;
import xiroc.dungeoncrawl.dungeon.piece.DungeonCorridor;
import xiroc.dungeoncrawl.dungeon.piece.DungeonEntrance;
import xiroc.dungeoncrawl.dungeon.piece.DungeonMultipartModelPiece;
import xiroc.dungeoncrawl.dungeon.piece.DungeonNodeConnector;
import xiroc.dungeoncrawl.dungeon.piece.DungeonStairs;
import xiroc.dungeoncrawl.dungeon.piece.room.DungeonNodeRoom;
import xiroc.dungeoncrawl.dungeon.piece.room.DungeonRoom;
import xiroc.dungeoncrawl.dungeon.piece.room.DungeonSecretRoom;
import xiroc.dungeoncrawl.dungeon.piece.room.DungeonSideRoom;
import xiroc.dungeoncrawl.dungeon.piece.room.DungeonSpiderRoom;

public class StructurePieceTypes {

    public static IStructurePieceType ENTRANCE;
    public static IStructurePieceType ROOM;
    public static IStructurePieceType CORRIDOR;
    public static IStructurePieceType STAIRS;
    public static IStructurePieceType SIDE_ROOM;
    public static IStructurePieceType NODE_ROOM;
    public static IStructurePieceType NODE_CONNECTOR;
    public static IStructurePieceType SECRET_ROOM;
    public static IStructurePieceType SPIDER_ROOM;
    public static IStructurePieceType MULTIPART_MODEL_PIECE;

    public static IStructurePieceType DUMMY;

    public static void register() {
        DungeonCrawl.LOGGER.info("Registering Structure Piece Types");

        ENTRANCE = IStructurePieceType.register(DungeonEntrance::new, createKey("entrance"));
        ROOM = IStructurePieceType.register(DungeonRoom::new, createKey("room"));
        CORRIDOR = IStructurePieceType.register(DungeonCorridor::new, createKey("corridor"));
        STAIRS = IStructurePieceType.register(DungeonStairs::new, createKey("stairs"));
        SIDE_ROOM = IStructurePieceType.register(DungeonSideRoom::new, createKey("side_room"));
        NODE_ROOM = IStructurePieceType.register(DungeonNodeRoom::new, createKey("node_room"));
        NODE_CONNECTOR = IStructurePieceType.register(DungeonNodeConnector::new, createKey("node_connector"));
        SECRET_ROOM = IStructurePieceType.register(DungeonSecretRoom::new, createKey("secret_room"));
        SPIDER_ROOM = IStructurePieceType.register(DungeonSpiderRoom::new, createKey("spider_room"));
        MULTIPART_MODEL_PIECE = IStructurePieceType.register(DungeonMultipartModelPiece::new, createKey("multipart_model_piece"));

        if (Config.ENABLE_DUMMY_PIECES.get()) {
            DungeonCrawl.LOGGER.info("Registering Dummy Structure Pieces");
            DUMMY  = IStructurePieceType.register(DummyStructurePiece::new, createKey("dummy"));

            IStructurePieceType.register(DummyStructurePiece::new, "DUNGEON_ENTR_BLDR");
            IStructurePieceType.register(DummyStructurePiece::new, "DUNGEON_ROOM");
            IStructurePieceType.register(DummyStructurePiece::new, "DUNGEON_CRRDR");
            IStructurePieceType.register(DummyStructurePiece::new, "DUNGEON_STTP");
            IStructurePieceType.register(DummyStructurePiece::new, "DUNGEON_STRS");
            IStructurePieceType.register(DummyStructurePiece::new, "DUNGEON_STBT");
            IStructurePieceType.register(DummyStructurePiece::new, "DUNGEON_HOLE");
            IStructurePieceType.register(DummyStructurePiece::new, "DUNGEON_CRRDR_ROOM");
            IStructurePieceType.register(DummyStructurePiece::new, "DUNGEON_TRAP");
            IStructurePieceType.register(DummyStructurePiece::new, "DUNGEON_PART");
            IStructurePieceType.register(DummyStructurePiece::new, "DUNGEON_HOLE_TRAP");
            IStructurePieceType.register(DummyStructurePiece::new, "DUNGEON_SIDE_ROOM");
            IStructurePieceType.register(DummyStructurePiece::new, "DUNGEON_PART_WITH_ENTITY");
        }

    }

    private static String createKey(String path) {
        return "dungeoncrawl:" + path;
    }

}
