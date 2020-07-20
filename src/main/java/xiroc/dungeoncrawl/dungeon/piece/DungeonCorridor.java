package xiroc.dungeoncrawl.dungeon.piece;

/*
 * DungeonCrawl (C) 2019 - 2020 XYROC (XIROC1337), All Rights Reserved
 */

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.template.TemplateManager;
import xiroc.dungeoncrawl.config.Config;
import xiroc.dungeoncrawl.dungeon.DungeonBuilder;
import xiroc.dungeoncrawl.dungeon.StructurePieceTypes;
import xiroc.dungeoncrawl.dungeon.model.DungeonModel;
import xiroc.dungeoncrawl.dungeon.model.DungeonModels;
import xiroc.dungeoncrawl.dungeon.treasure.Treasure;
import xiroc.dungeoncrawl.theme.Theme;

import java.util.Random;

public class DungeonCorridor extends DungeonPiece {

    public byte[] corridorFeatures;

    public int specialType;

    public DungeonCorridor(TemplateManager manager, CompoundNBT p_i51343_2_) {
        super(StructurePieceTypes.CORRIDOR, p_i51343_2_);
        specialType = p_i51343_2_.getInt("specialType");
        if (p_i51343_2_.contains("corridorFeatures")) {
            corridorFeatures = p_i51343_2_.getByteArray("corridorFeatures");
        }
    }

    @Override
    public int determineModel(DungeonBuilder builder, Random rand) {
        if (connectedSides == 2) {
            //                case 1:
            ////				return DungeonModels.CORRIDOR_FIRE;
            //                    return DungeonModels.CORRIDOR.id;
            //                case 2:
            ////				return DungeonModels.CORRIDOR_GRASS;
            //                    return DungeonModels.CORRIDOR.id;
            if (sides[0] && sides[2] || sides[1] && sides[3]) {
                //return RandomDungeonModel.CORRIDOR_STRAIGHT.roll(rand).id;
                return DungeonModels.ModelCategory.get(DungeonModels.ModelCategory.CORRIDOR, DungeonModels.ModelCategory.getCategoryForStage(stage)).roll(rand);
            }
        }
        return DungeonModels.ModelCategory.get(DungeonModels.ModelCategory.CORRIDOR_LINKER,
                DungeonModels.ModelCategory.getCategoryForStage(stage)).roll(rand);
    }

//    @Override
//    public void customSetup(Random rand) {
//        DungeonModel model = DungeonModels.MODELS.get(modelID);
//        if (model.featurePositions != null) {
//            int features = 0;
//            float random = rand.nextFloat();
//            for (int i = model.featurePositions.length; i > 0; i--) {
//                if (random < Math.pow(0.5, i)) {
//                    features = i;
//                    break;
//                }
//            }
//            if (features > 0) {
//                this.corridorFeatures = new byte[features];
//                this.featurePositions = new DirectionalBlockPos[features];
//                for (int i = 0; i < features; i++) {
//                    int feature = rand.nextInt(5);
//                    this.corridorFeatures[i] = (byte) feature;
//                    this.featurePositions[i] = DungeonCorridorFeature.setup(this, model, model.featurePositions[i].rotate(rotation, model), feature);
//                    //DungeonCrawl.LOGGER.info("Corridor Feature Pos: {} ({})", featurePositions[i], feature);
//                    DungeonCorridorFeature.setupBounds(this, model, boundingBox, new BlockPos(x, y, z), feature);
//                }
//            }
//        }
//    }

    @Override
    public boolean addComponentParts(IWorld worldIn, Random randomIn, MutableBoundingBox structureBoundingBoxIn,
                                     ChunkPos p_74875_4_) {
        DungeonModel model = DungeonModels.MODELS.get(modelID);

        boolean ew = rotation == Rotation.NONE || rotation == Rotation.CLOCKWISE_180;

        int x = ew ? this.x : this.x + (9 - model.length) / 2;
        int z = ew ? this.z + (9 - model.length) / 2 : this.z;

        if (modelID == DungeonModels.CORRIDOR_SECRET_ROOM_ENTRANCE.id) {
            switch (rotation) {
                case NONE:
                    z--;
                    break;
                case CLOCKWISE_90:
                    x++;
                    break;
                case CLOCKWISE_180:
                    z++;
                    break;
                case COUNTERCLOCKWISE_90:
                    x--;
                    break;
            }
        }

        buildRotated(model, worldIn, structureBoundingBoxIn,
                new BlockPos(x, y, z),
                Theme.get(theme), Theme.getSub(subTheme), Treasure.Type.DEFAULT, stage, rotation, false);

//        if (connectedSides < 3 && corridorFeatures != null && featurePositions != null) {
//            for (int i = 0; i < featurePositions.length; i++) {
//                DungeonCorridorFeature.FEATURES.get(corridorFeatures[i]).build(this, worldIn, featurePositions[i],
//                        structureBoundingBoxIn, Theme.get(theme), Theme.getSub(subTheme), stage);
//            }
//        }

        if (connectedSides > 2) {
            entrances(worldIn, structureBoundingBoxIn, model);
        }

        if (Config.NO_SPAWNERS.get())
            spawnMobs(worldIn, this, model.width, model.length, new int[]{1});
        return true;
    }

    @Override
    public void setupBoundingBox() {
        if (modelID == DungeonModels.CORRIDOR_SECRET_ROOM_ENTRANCE.id) {
            this.boundingBox = new MutableBoundingBox(x - 1, y, z - 1, x + 10, y, z + 10);
        } else {
            this.boundingBox = new MutableBoundingBox(x, y, z, x + 8, y + 8, z + 8);
        }

//        if (corridorFeatures != null) {
//            DungeonModel model = DungeonModels.MODELS.get(modelID);
//            for (byte corridorFeature : corridorFeatures) {
//                DungeonCorridorFeature.setupBounds(this, model, boundingBox, new BlockPos(x, y, z), corridorFeature);
//            }
//        }
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public void readAdditional(CompoundNBT tagCompound) {
        super.readAdditional(tagCompound);
        tagCompound.putInt("specialType", specialType);
        if (corridorFeatures != null) {
            tagCompound.putByteArray("corridorFeatures", corridorFeatures);
        }
    }

    public boolean isStraight() {
        return sides[0] && sides[2] || sides[1] && sides[3];
    }

}