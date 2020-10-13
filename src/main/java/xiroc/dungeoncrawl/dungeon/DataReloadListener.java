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

import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IFutureReloadListener;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.Unit;
import xiroc.dungeoncrawl.DungeonCrawl;
import xiroc.dungeoncrawl.dungeon.model.DungeonModels;
import xiroc.dungeoncrawl.dungeon.monster.RandomEquipment;
import xiroc.dungeoncrawl.dungeon.monster.RandomMonster;
import xiroc.dungeoncrawl.dungeon.monster.RandomPotionEffect;
import xiroc.dungeoncrawl.dungeon.monster.SpawnRates;
import xiroc.dungeoncrawl.dungeon.treasure.RandomSpecialItem;
import xiroc.dungeoncrawl.theme.Theme;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class DataReloadListener implements IFutureReloadListener {

    public void reload(IResourceManager resourceManager) {
        DungeonCrawl.LOGGER.info("Loading data...");
        DungeonModels.load(resourceManager);
        ChildPieceHandler.load();
        Theme.loadJson(resourceManager);
        SpawnRates.loadJson(resourceManager);
        RandomSpecialItem.loadJson(resourceManager);
        RandomMonster.loadJson(resourceManager);
        RandomEquipment.loadJson(resourceManager);
        RandomPotionEffect.loadJson(resourceManager);
        DungeonCrawl.LOGGER.info("Finished data loading.");
    }

    @Override
    public CompletableFuture<Void> reload(IStage stage, IResourceManager resourceManager, IProfiler preparationsProfiler, IProfiler reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
        return stage.markCompleteAwaitingOthers(Unit.INSTANCE).thenRunAsync(() -> {
            this.reload(resourceManager);
        }, gameExecutor);
    }

}
