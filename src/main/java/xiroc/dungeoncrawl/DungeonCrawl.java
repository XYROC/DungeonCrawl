package xiroc.dungeoncrawl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.config.ModConfig.Type;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import xiroc.dungeoncrawl.config.Config;
import xiroc.dungeoncrawl.config.JsonConfig;
import xiroc.dungeoncrawl.dungeon.Dungeon;
import xiroc.dungeoncrawl.dungeon.segment.DungeonSegmentModelBlock;
import xiroc.dungeoncrawl.dungeon.segment.DungeonSegmentModelRegistry;
import xiroc.dungeoncrawl.dungeon.treasure.Treasure;
import xiroc.dungeoncrawl.module.BOPCompatModule;
import xiroc.dungeoncrawl.module.ModuleManager;
import xiroc.dungeoncrawl.part.block.BlockRegistry;
import xiroc.dungeoncrawl.theme.JsonTheme;
import xiroc.dungeoncrawl.util.IBlockPlacementHandler;
import xiroc.dungeoncrawl.util.Tools;

@Mod(DungeonCrawl.MODID)
public class DungeonCrawl {

	public static final String MODID = "dungeoncrawl";
	public static final String NAME = "Dungeon Crawl";
	public static final String VERSION = "1.6.1";

	public static final Logger LOGGER = LogManager.getLogger(NAME);

	public static final Gson GSON = new GsonBuilder()
			.registerTypeAdapter(JsonTheme.JsonBaseTheme.class, new JsonTheme.JsonBaseTheme.Deserializer())
			.registerTypeAdapter(JsonTheme.JsonSubTheme.class, new JsonTheme.JsonSubTheme.Deserializer())
			.setPrettyPrinting().create();

	public static IEventBus EVENT_BUS;

	public DungeonCrawl() {
		LOGGER.info("Here we go! Launching Dungeon Crawl {}...", VERSION);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
//		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onServerStart);
		MinecraftForge.EVENT_BUS.register(this);
//		MinecraftForge.EVENT_BUS.register(new EventManager());
		MinecraftForge.EVENT_BUS.register(new Tools());
		ForgeRegistries.FEATURES
				.register(Dungeon.DUNGEON.setRegistryName(new ResourceLocation(Dungeon.NAME.toLowerCase())));
		Treasure.init();
		EVENT_BUS = Bus.MOD.bus().get();

		ModuleManager.registerModule(BOPCompatModule.class, new String[] { "biomesoplenty" });
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		LOGGER.info("Common Setup");
		ModLoadingContext.get().registerConfig(Type.COMMON, Config.CONFIG);
		Config.load(FMLPaths.CONFIGDIR.get().resolve("dungeon_crawl.toml"));

		DungeonSegmentModelBlock.load();
		IBlockPlacementHandler.load();
		BlockRegistry.load();

		DungeonCrawl.LOGGER.info("Adding features and structures");
		
		for (Biome biome : ForgeRegistries.BIOMES) {
			if (!JsonConfig.BIOME_BLACKLIST.contains(biome.getRegistryName().toString())) {
				DungeonCrawl.LOGGER.debug("Biome >> " + biome.getRegistryName());
				biome.addFeature(Decoration.UNDERGROUND_STRUCTURES, new ConfiguredFeature<NoFeatureConfig, Dungeon>(
						Dungeon.DUNGEON, NoFeatureConfig.NO_FEATURE_CONFIG));
				if (!JsonConfig.BIOME_OVERWORLD_BLACKLIST.contains(biome.getRegistryName().toString())) {
					DungeonCrawl.LOGGER.debug("Generation Biome >> " + biome.getRegistryName());
					biome.addStructure(new ConfiguredFeature<NoFeatureConfig, Dungeon>(Dungeon.DUNGEON,
							NoFeatureConfig.NO_FEATURE_CONFIG));
				}
			}
		}

		DungeonSegmentModelRegistry.load();

		ModuleManager.load();
	}

	public static String getDate() {
		return new SimpleDateFormat().format(new Date());
	}

	public static ResourceLocation locate(String path) {
		return new ResourceLocation(MODID, path);
	}

}
