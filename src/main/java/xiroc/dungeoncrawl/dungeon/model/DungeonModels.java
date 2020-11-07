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

package xiroc.dungeoncrawl.dungeon.model;

import com.google.common.collect.Lists;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTSizeTracker;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3i;
import org.jline.utils.InputStreamReader;
import xiroc.dungeoncrawl.DungeonCrawl;
import xiroc.dungeoncrawl.config.Config;
import xiroc.dungeoncrawl.dungeon.model.DungeonModel.Metadata;
import xiroc.dungeoncrawl.util.WeightedIntegerEntry;
import xiroc.dungeoncrawl.util.WeightedRandomInteger;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class DungeonModels {

    public static final HashMap<Integer, DungeonModel> MODELS = new HashMap<>();
    public static final HashMap<String, DungeonModel> PATH_TO_MODEL = new HashMap<>();

    public static final HashMap<Integer, WeightedRandomInteger> WEIGHTED_MODELS = new HashMap<>();

    public static final HashMap<Integer, Vec3i> OFFSETS = new HashMap<>();

    /*
    A list of all MultipartModelData instances to update their "forward references" after all models have been loaded.
     */
    public static final ArrayList<MultipartModelData> REFERENCES_TO_UPDATE = new ArrayList<>();

    public static final Vec3i NO_OFFSET = new Vec3i(0, 0, 0);

    public static DungeonModel CORRIDOR, CORRIDOR_2, CORRIDOR_3, CORRIDOR_STONE, CORRIDOR_ROOM, CORRIDOR_SECRET_ROOM_ENTRANCE;

    public static DungeonModel ENTRANCE;
    public static DungeonModel FOOD_SIDE_ROOM;
    public static DungeonModel LARGE_CORRIDOR_START, LARGE_CORRIDOR_STRAIGHT, LARGE_CORRIDOR_OPEN, LARGE_CORRIDOR_TURN;
    public static DungeonModel LOOT_ROOM, OLD_LOOT_ROOM;
    public static DungeonModel PRISON_CELL;
    public static DungeonModel SECRET_ROOM;
    public static DungeonModel STAIRCASE, STAIRS_BOTTOM, STAIRS_BOTTOM_2, STAIRS_TOP;
    public static DungeonModel STARTER_ROOM;

    public static synchronized void load(IResourceManager resourceManager) {
        REFERENCES_TO_UPDATE.clear();
        MODELS.clear();
        PATH_TO_MODEL.clear();
        WEIGHTED_MODELS.clear();
        OFFSETS.clear();

        ModelCategory.clear();

        LARGE_CORRIDOR_START = loadModel("models/dungeon/corridor/large_corridor_start.nbt", resourceManager).setId(28);
        LARGE_CORRIDOR_STRAIGHT = loadModel("models/dungeon/corridor/large_corridor_straight.nbt", resourceManager).setId(29);
        LARGE_CORRIDOR_TURN = loadModel("models/dungeon/corridor/large_corridor_turn.nbt", resourceManager).setId(30);
        LARGE_CORRIDOR_OPEN = loadModel("models/dungeon/corridor/large_corridor_open.nbt", resourceManager).setId(31);

        OLD_LOOT_ROOM = loadModel("models/dungeon/old_loot_room.nbt", resourceManager).setId(35);

        CORRIDOR_SECRET_ROOM_ENTRANCE = loadModel("models/dungeon/corridor/corridor_secret_room_entrance.nbt", resourceManager).setId(7);

        PRISON_CELL = loadModel("models/dungeon/prison_cell.nbt", resourceManager).setId(64);

        STAIRCASE = loadModel("models/dungeon/staircase.nbt", resourceManager).setId(72);
        STAIRS_TOP = loadModel("models/dungeon/stairs_top.nbt", resourceManager).setId(73);
        STAIRS_BOTTOM = loadModel("models/dungeon/stairs_bottom.nbt", resourceManager).setId(74);
        STAIRS_BOTTOM_2 = loadModel("models/dungeon/stairs_bottom_2.nbt", resourceManager).setId(75);

        // Models with metadata
        FOOD_SIDE_ROOM = load("models/dungeon/room/", "food_side_room", resourceManager);
        ENTRANCE = load("models/dungeon/entrance/", "roguelike_entrance", resourceManager);
        LOOT_ROOM = load("models/dungeon/", "loot_room", resourceManager);
        SECRET_ROOM = load("models/dungeon/room/", "secret_room", resourceManager);
        STARTER_ROOM = load("models/dungeon/room/", "starter_room", resourceManager);

        load("models/dungeon/room/", "tnt_trap_side_room", resourceManager);

        load("models/dungeon/corridor/", "corridor", resourceManager);
        load("models/dungeon/corridor/", "corridor_2", resourceManager);
        load("models/dungeon/corridor/", "corridor_3", resourceManager);
        load("models/dungeon/corridor/", "old_corridor", resourceManager);
        load("models/dungeon/corridor/", "old_corridor_3", resourceManager);
        load("models/dungeon/corridor/", "stone_corridor", resourceManager);
        load("models/dungeon/corridor/", "corridor_room", resourceManager);
        load("models/dungeon/corridor/", "corridor_fire", resourceManager);
        load("models/dungeon/corridor/", "corridor_spawner", resourceManager);
        load("models/dungeon/corridor/", "corridor_light", resourceManager);

        load("models/dungeon/corridor/dark/", "corridor", resourceManager);
        load("models/dungeon/corridor/dark/", "corridor_2", resourceManager);
        load("models/dungeon/corridor/dark/", "corridor_3", resourceManager);
        load("models/dungeon/corridor/dark/", "corridor_4", resourceManager);
        load("models/dungeon/corridor/dark/", "corridor_spawner", resourceManager);

        load("models/dungeon/corridor/linker/", "cake_room", resourceManager);
        load("models/dungeon/corridor/linker/", "corridor_linker", resourceManager);
        load("models/dungeon/corridor/linker/", "corridor_linker_2", resourceManager);
        load("models/dungeon/corridor/linker/", "corridor_linker_3", resourceManager);
        load("models/dungeon/corridor/linker/", "corridor_linker_4", resourceManager);
        load("models/dungeon/corridor/linker/", "corridor_linker_5", resourceManager);

        load("models/dungeon/node/", "forge", resourceManager);
        load("models/dungeon/node/", "node", resourceManager);
        load("models/dungeon/node/", "node_jukebox", resourceManager);
        load("models/dungeon/node/", "node_fork", resourceManager);
        load("models/dungeon/node/", "node_catacomb_dead_end", resourceManager);
        load("models/dungeon/node/", "node_catacomb_turn", resourceManager);
        load("models/dungeon/node/", "node_turn", resourceManager);
        load("models/dungeon/node/", "node_water", resourceManager);
        load("models/dungeon/node/", "node_water_2", resourceManager);
        load("models/dungeon/node/", "node_water_dead_end", resourceManager);
        load("models/dungeon/node/", "node_water_fork", resourceManager);
        load("models/dungeon/node/", "node_water_straight", resourceManager);
        load("models/dungeon/node/", "node_prison_fork", resourceManager);
        load("models/dungeon/node/", "node_prison", resourceManager);
        load("models/dungeon/node/", "large_node", resourceManager);
        load("models/dungeon/node/", "large_node_library", resourceManager);

        load("models/dungeon/node/dark/", "node_lava", resourceManager);
        load("models/dungeon/node/dark/", "node_lava_2", resourceManager);

        load("models/dungeon/node/connector/", "node_connector", resourceManager);
        load("models/dungeon/node/connector/", "node_connector_2", resourceManager);
        load("models/dungeon/node/connector/", "node_connector_3", resourceManager);
        load("models/dungeon/node/connector/", "node_connector_4", resourceManager);
        load("models/dungeon/node/connector/", "node_connector_5", resourceManager);
        load("models/dungeon/node/connector/", "node_connector_catacomb", resourceManager);
        load("models/dungeon/node/connector/", "node_connector_trap", resourceManager);

        load("models/dungeon/room/", "spawner_room", resourceManager);
        load("models/dungeon/room/", "spawner_room_2", resourceManager);
        load("models/dungeon/room/", "spawner_room_material", resourceManager);
        load("models/dungeon/room/", "spawner_room_material_2", resourceManager);

        load("models/dungeon/room/dark/", "spawner_room", resourceManager);

        load("models/dungeon/corridor/multipart/", "corridor_default", resourceManager);
        load("models/dungeon/corridor/multipart/", "corridor_variant", resourceManager);
        load("models/dungeon/corridor/multipart/", "corridor_variant_2", resourceManager);
        load("models/dungeon/corridor/multipart/", "corridor_crops", resourceManager);
        load("models/dungeon/corridor/multipart/", "corridor_chest", resourceManager);
        load("models/dungeon/corridor/multipart/", "corridor_spawner", resourceManager);
        load("models/dungeon/corridor/multipart/", "corridor_light", resourceManager);
        load("models/dungeon/corridor/multipart/", "corridor_light_switch", resourceManager);
        load("models/dungeon/corridor/multipart/", "corridor_bookshelf", resourceManager);

        // -Additional Models- //

        //DungeonCrawl.LOGGER.info("Loading additional models");

        resourceManager.getAllResourceLocations(DungeonCrawl.locate("models/dungeon/additional").getPath(), (s) -> s.endsWith(".nbt")).forEach((resource) -> {
            DungeonCrawl.LOGGER.debug("Additional resource: {}", resource.toString());
            DungeonModel model = loadModel(resource, resourceManager);
            ResourceLocation metadata = new ResourceLocation(resource.getNamespace(),
                    resource.getPath().substring(0, resource.getPath().indexOf(".nbt")) + ".json");
            DungeonCrawl.LOGGER.debug("Looking for metadata file {}", metadata.toString());
            if (resourceManager.hasResource(metadata)) {
                DungeonCrawl.LOGGER.debug("Loading metadata for {}", resource.getPath());

                try {
                    Metadata data = DungeonCrawl.GSON.fromJson(
                            new InputStreamReader(resourceManager.getResource(metadata).getInputStream()), Metadata.class);
                    model.loadMetadata(data);
                } catch (Exception e) {
                    DungeonCrawl.LOGGER.error("Failed to load metadata for {}", resource.getPath());
                    e.printStackTrace();
                }
            } else {
                DungeonCrawl.LOGGER.warn("Missing metadata for {}", resource.getPath());
            }
        });

        // -End of Model loading- //

        DungeonCrawl.LOGGER.debug("Dumping all registered models below:");
        MODELS.forEach((key, value) -> {
            DungeonCrawl.LOGGER.debug("{}   {}", key, value.location);
        });

        REFERENCES_TO_UPDATE.forEach(MultipartModelData::updateReference);

        HashMap<Integer, DungeonModel[]> tempMap = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            ModelCategory stage = ModelCategory.STAGES[i];
            for (ModelCategory nodeType : ModelCategory.NODE_TYPES) {

                DungeonCrawl.LOGGER.debug("Node type: {}, {}", i, nodeType);

                // Nodes
                {
                    WeightedRandomInteger.Builder builder = new WeightedRandomInteger.Builder();
                    ModelCategory[] category = new ModelCategory[]{ModelCategory.NORMAL_NODE, stage, nodeType};

                    for (DungeonModel model : ModelCategory.getIntersection(tempMap, category)) {
                        DungeonCrawl.LOGGER.debug("adding primary node entry ({} {})", stage, nodeType);
                        builder.entries.add(new WeightedIntegerEntry(model.metadata.weights[i], model.id));
                    }

                    for (ModelCategory secondaryType : ModelCategory.getSecondaryNodeCategories(nodeType)) {
                        for (DungeonModel model : ModelCategory.getIntersection(tempMap, ModelCategory.NORMAL_NODE,
                                stage, secondaryType)) {
                            DungeonCrawl.LOGGER.debug("adding secondary node entry: {}, {}", stage, model.id);
                            builder.entries.add(new WeightedIntegerEntry(1, model.id));
                        }
                    }

                    WEIGHTED_MODELS.put(Arrays.hashCode(category), builder.build());
                }

                // Large Nodes
                {
                    WeightedRandomInteger.Builder builder = new WeightedRandomInteger.Builder();
                    ModelCategory[] category = new ModelCategory[]{ModelCategory.LARGE_NODE, stage, nodeType};

                    for (DungeonModel model : ModelCategory.getIntersection(tempMap, category)) {
                        DungeonCrawl.LOGGER.debug("adding primary node entry ({} {})", stage, nodeType);
                        builder.entries.add(
                                new WeightedIntegerEntry(model.metadata.weights[i], model.id));
                    }

                    for (ModelCategory secondaryType : ModelCategory.getSecondaryNodeCategories(nodeType)) {
                        for (DungeonModel model : ModelCategory.getIntersection(tempMap, ModelCategory.LARGE_NODE,
                                stage, secondaryType)) {
                            DungeonCrawl.LOGGER.debug("adding secondary node entry: {}, {}", stage, model.id);
                            builder.entries.add(new WeightedIntegerEntry(1, model.id));
                        }
                    }

                    WEIGHTED_MODELS.put(Arrays.hashCode(category), builder.build());
                }
            }
            createWeightedRandomIntegers(tempMap, ModelCategory.CORRIDOR, stage, i);
            createWeightedRandomIntegers(tempMap, ModelCategory.CORRIDOR_LINKER, stage, i);
            createWeightedRandomIntegers(tempMap, ModelCategory.NODE_CONNECTOR, stage, i);
            createWeightedRandomIntegers(tempMap, ModelCategory.SIDE_ROOM, stage, i);
            createWeightedRandomIntegers(tempMap, ModelCategory.ROOM, stage, i);
        }
    }

    public static DungeonModel load(String directory, String file, IResourceManager resourceManager) {
        DungeonModel model = loadModel(directory + file + ".nbt", resourceManager);

        ResourceLocation metadata = DungeonCrawl.locate(directory + file + ".json");

        if (resourceManager.hasResource(metadata)) {
            // DungeonCrawl.LOGGER.debug("Loading metadata for {}", file);

            try {
                Metadata data = DungeonCrawl.GSON.fromJson(
                        new InputStreamReader(resourceManager.getResource(metadata).getInputStream()), Metadata.class);
                model.loadMetadata(data);
                if (Config.ENABLE_TOOLS.get()) {
                    PATH_TO_MODEL.put(directory.substring("models/dungeon/".length()) + file, model);
                }
            } catch (Exception e) {
                DungeonCrawl.LOGGER.error("Failed to load metadata for {}", file);
                e.printStackTrace();
            }

        }

        return model;
    }

    public static DungeonModel loadModel(String path, IResourceManager resourceManager) {
        DungeonCrawl.LOGGER.debug("Loading {}", path);

        try {
            ResourceLocation resource = DungeonCrawl.locate(path);
            DataInputStream input = new DataInputStream(resourceManager.getResource(resource).getInputStream());
            CompoundNBT nbt = CompoundNBT.TYPE.func_225649_b_(input, 16, NBTSizeTracker.INFINITE);
            DungeonModel model = ModelHandler.getModelFromNBT(nbt);
            if (Config.ENABLE_TOOLS.get()) {
                String key = path.substring("models/dungeon/".length(), path.indexOf(".nbt"));
                PATH_TO_MODEL.put(key, model);
                // DungeonCrawl.LOGGER.debug("Model key: {}", key);
            }
            model.setLocation(resource);
            return model;
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Failed to load " + path);
    }

    public static DungeonModel loadModel(ResourceLocation resource, IResourceManager resourceManager) {
        DungeonCrawl.LOGGER.debug("Loading {}", resource.getPath());

        try {
            DataInputStream input = new DataInputStream(resourceManager.getResource(resource).getInputStream());
            CompoundNBT nbt = CompoundNBT.TYPE.func_225649_b_(input, 16, NBTSizeTracker.INFINITE);
            DungeonModel model = ModelHandler.getModelFromNBT(nbt);
            if (Config.ENABLE_TOOLS.get()) {
                String path = resource.getPath();
                String key = path.substring("models/dungeon/".length(), path.indexOf(".nbt"));
                PATH_TO_MODEL.put(key, model);
                // DungeonCrawl.LOGGER.debug("Model key (2): {}", key);
            }
            model.setLocation(resource);
            return model;
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Failed to load " + resource.getPath());
    }

    private static void createWeightedRandomIntegers(HashMap<Integer, DungeonModel[]> tempMap, ModelCategory baseCategory, ModelCategory stageCategory, int stage) {
        WeightedRandomInteger.Builder builder = new WeightedRandomInteger.Builder();
        ModelCategory[] categories = new ModelCategory[]{baseCategory, stageCategory};
        for (DungeonModel model : ModelCategory.getIntersection(tempMap, categories)) {
            builder.entries.add(new WeightedIntegerEntry(model.metadata.weights[stage], model.id));
        }
        WEIGHTED_MODELS.put(Arrays.hashCode(categories), builder.build());
    }

    public static Vec3i getOffset(int model) {
        return OFFSETS.getOrDefault(model, NO_OFFSET);
    }

    public enum ModelCategory {

        STAGE_1, STAGE_2, STAGE_3, STAGE_4, STAGE_5,
        NODE_DEAD_END, NODE_STRAIGHT, NODE_TURN, NODE_FORK, NODE_FULL,
        NORMAL_NODE, LARGE_NODE,
        CORRIDOR, CORRIDOR_LINKER, NODE_CONNECTOR,
        ENTRANCE,
        SIDE_ROOM, ROOM;

        public static final ModelCategory[] STAGES = {STAGE_1, STAGE_2, STAGE_3, STAGE_4,
                STAGE_5};

        public static final ModelCategory[] NODE_TYPES = {NODE_FULL, NODE_FORK, NODE_STRAIGHT,
                NODE_TURN, NODE_DEAD_END};

        public static final ModelCategory[] EMPTY = new ModelCategory[0];

        public final List<DungeonModel> members;

        ModelCategory() {
            members = Lists.newArrayList();
        }

        public static void clear() {
            for (ModelCategory category : values()) {
                category.members.clear();
            }
        }

        public static WeightedRandomInteger get(ModelCategory... categories) {
            int hash = Arrays.hashCode(categories);

            return WEIGHTED_MODELS.get(hash);
        }

        public static DungeonModel[] getIntersection(HashMap<Integer, DungeonModel[]> map,
                                                     ModelCategory... categories) {
            int hash = Arrays.hashCode(categories);

            if (map.containsKey(hash)) {
                return map.get(hash);
            }

            List<DungeonModel> intersection = Lists.newArrayList();

            mainLoop:
            for (DungeonModel model : categories[0].members) {
                for (int i = 1; i < categories.length; i++) {
                    if (!categories[i].members.contains(model)) {
                        continue mainLoop;
                    }
                }
                intersection.add(model);
            }

            DungeonModel[] array = intersection.toArray(new DungeonModel[0]);
            map.put(hash, array);

            DungeonCrawl.LOGGER.debug("Intersection of {} is {}", Arrays.toString(categories), Arrays.toString(array));

            return array;
        }

        public static ModelCategory[] getSecondaryNodeCategories(ModelCategory primeCategory) {
            switch (primeCategory) {
                case NODE_DEAD_END:
                    return new ModelCategory[]{NODE_TURN, NODE_STRAIGHT, NODE_FORK, NODE_FULL};
                case NODE_FORK:
                    return new ModelCategory[]{NODE_FULL};
                case NODE_STRAIGHT:
                case NODE_TURN:
                    return new ModelCategory[]{NODE_FULL, NODE_FORK};
                default:
                    return EMPTY;
            }
        }

        public static ModelCategory getCategoryForStage(int stage) {
            if (stage < 0)
                return STAGE_1;
            if (stage > 4)
                return STAGE_5;
            return STAGES[stage];
        }

    }

}
