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

package xiroc.dungeoncrawl.dungeon.treasure.function;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.JSONUtils;
import xiroc.dungeoncrawl.dungeon.treasure.RandomSpecialItem;
import xiroc.dungeoncrawl.dungeon.treasure.Treasure;
import xiroc.dungeoncrawl.theme.Theme;

public class RandomItem extends LootFunction {

    public int lootLevel;

    public RandomItem(ILootCondition[] conditionsIn, int stage) {
        super(conditionsIn);
        this.lootLevel = stage;
    }

    @Override
    public ItemStack doApply(ItemStack stack, LootContext context) {
        return RandomSpecialItem.generate(context.getWorld(), context.getRandom(),
                Theme.BIOME_TO_THEME_MAP.getOrDefault(
                        context.getWorld().getBiome(context.get(LootParameters.POSITION)).getRegistryName().toString(),
                        0),
                lootLevel - 1);
    }

    @Override
    public LootFunctionType func_230425_b_() {
        return Treasure.RANDOM_ITEM;
    }

    public static class Serializer extends LootFunction.Serializer<RandomItem> {

        public Serializer() {
            super();
        }

        @Override
        public void func_230424_a_(JsonObject p_230424_1_, RandomItem p_230424_2_, JsonSerializationContext p_230424_3_) {
            super.func_230424_a_(p_230424_1_, p_230424_2_, p_230424_3_);
            p_230424_1_.addProperty("loot_level", p_230424_2_.lootLevel);
        }

        @Override
        public RandomItem deserialize(JsonObject object, JsonDeserializationContext deserializationContext,
                                      ILootCondition[] conditionsIn) {
            return new RandomItem(conditionsIn, object.get("loot_level").getAsInt());
        }

    }

}
