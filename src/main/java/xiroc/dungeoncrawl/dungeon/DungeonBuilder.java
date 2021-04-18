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

import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.server.ServerWorld;
import xiroc.dungeoncrawl.DungeonCrawl;
import xiroc.dungeoncrawl.config.Config;
import xiroc.dungeoncrawl.dungeon.generator.dungeon.DefaultDungeonGenerator;
import xiroc.dungeoncrawl.dungeon.generator.dungeon.DungeonGenerator;
import xiroc.dungeoncrawl.dungeon.generator.dungeon.DungeonGeneratorSettings;
import xiroc.dungeoncrawl.dungeon.model.ModelCategory;
import xiroc.dungeoncrawl.dungeon.piece.DungeonEntrance;
import xiroc.dungeoncrawl.dungeon.piece.DungeonPiece;
import xiroc.dungeoncrawl.theme.Theme;
import xiroc.dungeoncrawl.util.Position2D;

import java.util.List;
import java.util.Random;

public class DungeonBuilder {

    public static final DungeonGenerator DEFAULT_GENERATOR = new DefaultDungeonGenerator(DungeonGeneratorSettings.DEFAULT);

    public Random rand;
    public Position2D start;

    public DungeonLayer[] layers;
    public DungeonLayerMap[] maps;

    public DungeonStatTracker statTracker;

    public ChunkPos chunkPos;
    public BlockPos startPos;

    public ChunkGenerator<?> chunkGenerator;

    public Biome biome;

    public Theme entranceTheme, theme, lowerTheme, bottomTheme;
    public Theme.SubTheme entranceSubTheme, subTheme, lowerSubTheme, bottomSubTheme;

    /**
     * Instantiates a Dungeon Builder for usage during world gen.
     */
    public DungeonBuilder(ChunkGenerator<?> chunkGenerator, ChunkPos pos, Random rand) {
        this.chunkGenerator = chunkGenerator;
        this.rand = rand;

        this.chunkPos = pos;
        this.startPos = new BlockPos(pos.x * 16 - Dungeon.SIZE / 2 * 9, chunkGenerator.getSeaLevel() - 15,
                pos.z * 16 - Dungeon.SIZE / 2 * 9);


        DungeonCrawl.LOGGER.debug("Creating the layout for a dungeon at (" + startPos.getX() + " | " + startPos.getY() + " | "
                + startPos.getZ() + ").");
    }

    /**
     * Instantiates a Dungeon Builder for post world gen usage like a manual dungeon spawn by command.
     */
    public DungeonBuilder(ServerWorld world, BlockPos pos, Random rand) {
        this.chunkGenerator = world.getChunkProvider().getChunkGenerator();
        this.rand = rand;

        this.chunkPos = new ChunkPos(pos.getX() >> 4, pos.getZ() >> 4);
        this.startPos = new BlockPos(pos.getX() - Dungeon.SIZE / 2 * 9, world.getSeaLevel() - 15,
                pos.getZ() - Dungeon.SIZE / 2 * 9);

        DungeonCrawl.LOGGER.debug("Creating the layout for a dungeon at (" + startPos.getX() + " | " + startPos.getY() + " | "
                + startPos.getZ() + ").");
    }

    public static boolean isWorldEligible(ChunkGenerator<?> chunkGenerator) {
        return chunkGenerator.getSeaLevel() > 32;
    }

    public List<DungeonPiece> build() {
        generateLayout(DEFAULT_GENERATOR);

        List<DungeonPiece> pieces = Lists.newArrayList();
        DungeonPiece entrance = new DungeonEntrance();
        entrance.setWorldPosition(startPos.getX() + layers[0].start.x * 9, startPos.getY() + 9,
                startPos.getZ() + layers[0].start.z * 9);
        entrance.stage = 0;
        entrance.setupModel(this, null, pieces, rand);
        entrance.setupBoundingBox();

        this.biome = chunkGenerator.getBiomeProvider().getNoiseBiome(entrance.x >> 2, chunkGenerator.getSeaLevel() >> 2, entrance.z >> 2);

        determineThemes();

        entrance.theme = this.theme;
        entrance.subTheme = this.subTheme;

        pieces.add(entrance);

        postProcessDungeon(pieces, rand);

        return pieces;
    }

    private void generateLayout(DungeonGenerator generator) {
        generator.initializeDungeon(this, chunkPos, rand);

        int startCoordinate = generator.settings.gridSize.apply(0) / 2;

        this.start = new Position2D(startCoordinate, startCoordinate);

        int layerCount = generator.layerCount(rand, startPos.getY());

        this.layers = new DungeonLayer[layerCount];
        this.maps = new DungeonLayerMap[layerCount];

        for (int i = 0; i < layers.length; i++) {
            int size = generator.settings.gridSize.apply(i);
            this.maps[i] = new DungeonLayerMap(size, size);
            this.layers[i] = new DungeonLayer(size, size);
            this.layers[i].map = maps[i];
        }

        for (int i = 0; i < layers.length; i++) {
            generator.initializeLayer(this, rand, i);
            generator.generateLayer(this, layers[i], i, rand, (i == 0) ? this.start : layers[i - 1].end);
        }

        for (int i = 0; i < layers.length; i++) {
            processCorridors(layers[i], i);
        }
    }

    private void processCorridors(DungeonLayer layer, int lyr) {
        int stage = Math.min(lyr, 4);
        for (int x = 0; x < layer.width; x++) {
            for (int z = 0; z < layer.length; z++) {
                if (layer.grid[x][z] != null) {
                    if (!layer.grid[x][z].hasFlag(PlaceHolder.Flag.PLACEHOLDER)) {
                        layer.grid[x][z].reference.stage = stage;
                        if (layer.grid[x][z].reference.getType() == 0)
                            DungeonFeatures.processCorridor(this, layer, x, z, rand, lyr, stage, startPos);
                    }
                }
            }
        }
    }

    private void determineThemes() {
        ResourceLocation registryName = biome.getRegistryName();

        if (registryName != null) {
            if (this.entranceTheme == null) this.entranceTheme = Theme.randomTheme(registryName.toString(), rand);
            if (this.theme == null) this.theme = Theme.randomTheme(registryName.toString(), rand);
        } else {
            if (this.entranceTheme == null) this.entranceTheme = Theme.getDefaultTheme();
            if (this.theme == null) this.theme = Theme.getDefaultTheme();
        }

        if (subTheme == null) {
            if (theme.subTheme != null) {
                this.subTheme = theme.subTheme.roll(rand);
            } else {
                this.subTheme = registryName != null ? Theme.randomSubTheme(registryName.toString(), rand) : Theme.getDefaultSubTheme();
            }
        }

        if (entranceSubTheme == null) {
            if (entranceTheme.subTheme != null) {
                this.entranceSubTheme = entranceTheme.subTheme.roll(rand);
            } else {
                this.entranceSubTheme = registryName != null ? Theme.randomSubTheme(registryName.toString(), rand) : Theme.getDefaultSubTheme();
            }
        }

        if (this.lowerTheme == null)
            this.lowerTheme = Theme.getTheme("vanilla/catacombs/default");

        if (lowerSubTheme == null) {
            if (lowerTheme.subTheme != null) {
                this.lowerSubTheme = lowerTheme.subTheme.roll(rand);
            } else {
                this.lowerSubTheme = this.subTheme;
            }
        }

        if (this.bottomTheme == null)
            this.bottomTheme = Config.NO_NETHER_STUFF.get() ? Theme.getTheme("vanilla/hell/mossy_obsidian") : Theme.getTheme("vanilla/hell/hell");

        if (bottomTheme.subTheme != null && this.bottomSubTheme == null) {
            this.bottomSubTheme = bottomTheme.subTheme.roll(rand);
        } else {
            this.bottomSubTheme = this.subTheme;
        }
    }

    private void postProcessDungeon(List<DungeonPiece> pieces, Random rand) {
        boolean catacombs = layers.length > 3;

        for (int i = 0; i < layers.length; i++) {
            DungeonLayer layer = layers[i];
            ModelCategory layerCategory = DEFAULT_GENERATOR.getCategoryForLayer(i);
            for (int x = 0; x < layer.width; x++)
                for (int z = 0; z < layer.length; z++) {
                    if (layer.grid[x][z] != null && !layer.grid[x][z].hasFlag(PlaceHolder.Flag.PLACEHOLDER)) {

                        if (i == layers.length - 1) {
                            layer.grid[x][z].reference.theme = bottomTheme;
                            layer.grid[x][z].reference.subTheme = bottomSubTheme;
                        } else if (catacombs && layers.length - i < 4) {
                            layer.grid[x][z].reference.theme = lowerTheme;
                            layer.grid[x][z].reference.subTheme = lowerSubTheme;
                        } else {
                            layer.grid[x][z].reference.theme = theme;
                            layer.grid[x][z].reference.subTheme = subTheme;
                        }

                        if (!layer.grid[x][z].hasFlag(PlaceHolder.Flag.FIXED_MODEL)) {
                            layer.grid[x][z].reference.setupModel(this, layerCategory, pieces, rand);
                        }

                        if (!layer.grid[x][z].hasFlag(PlaceHolder.Flag.FIXED_POSITION)) {
                            layer.grid[x][z].reference.setWorldPosition(startPos.getX() + x * 9,
                                    startPos.getY() - i * 9, startPos.getZ() + z * 9);
                        }

                        layer.grid[x][z].reference.setupBoundingBox();

                        if (layer.grid[x][z].reference.hasChildPieces()) {
                            layer.grid[x][z].reference.addChildPieces(pieces, this, layerCategory, i, rand);
                        }

                        if (layer.grid[x][z].reference.getType() == 10) {
                            layer.rotateNode(layer.grid[x][z], rand);
                        }

                        layer.grid[x][z].reference.customSetup(rand);

                        pieces.add(layer.grid[x][z].reference);
                    }
                }
        }
    }

    /**
     * Checks if a piece can be placed at the given position.
     *
     * @return true if the piece can be placed, false if not
     */
    public static boolean canPlacePiece(DungeonLayer layer, int x, int z, int width, int length,
                                        boolean ignoreStartPosition) {
        if (x + width > Dungeon.SIZE || z + length > Dungeon.SIZE || x < 0 || z < 0)
            return false;

        for (int x0 = 0; x0 < width; x0++) {
            for (int z0 = 0; z0 < length; z0++) {
                if (!(ignoreStartPosition && x0 == 0 && z0 == 0)
                        && (layer.grid[x + x0][z + z0] != null || !layer.map.isPositionFree(x + x0, z + z0))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isBlockProtected(IWorld world, BlockPos pos, PlacementContext context) {
        BlockState state = world.getBlockState(pos);
        return state.getBlockHardness(world, pos) < 0 || context.protectedBlocks.contains(pos) || BlockTags.PORTALS.contains(state.getBlock());
    }

}
