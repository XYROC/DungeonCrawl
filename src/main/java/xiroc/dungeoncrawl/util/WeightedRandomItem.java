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

package xiroc.dungeoncrawl.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Random;

public class WeightedRandomItem implements IRandom<Item> {

    private final WeightedRandomItem.WeightedEntry[] entries;

    private WeightedRandomItem(Tuple<String, Integer>[] entries) {
        int weight = 0;
        for (Tuple<String, Integer> entry : entries)
            weight += entry.getB();
        this.entries = new WeightedRandomItem.WeightedEntry[entries.length];
        this.assign(entries, weight);
    }

    private void assign(Tuple<String, Integer>[] entries, int totalWeight) {
        float f = 0.0F;
        int i = 0;
        for (Tuple<String, Integer> entry : entries) {
            float weight = (float) entry.getB() / (float) totalWeight;
            this.entries[i] = new WeightedRandomItem.WeightedEntry(ForgeRegistries.ITEMS.getValue(new ResourceLocation(entry.getA())), weight + f);
            f += weight;
            i++;
        }
    }

    @Override
    public Item roll(Random rand) {
        float f = rand.nextFloat();
        for (WeightedRandomItem.WeightedEntry entry : entries)
            if (entry.getB() >= f)
                return entry.getA();
        return null;
    }

    public static WeightedRandomItem fromJson(JsonArray array) {
        WeightedRandomItem.Builder builder = new WeightedRandomItem.Builder();
        array.forEach((element) -> {
            JsonObject object = element.getAsJsonObject();
            int weight = object.has("weight") ? object.get("weight").getAsInt() : 1;
            builder.add(object.get("item").getAsString(), weight);
        });
        return builder.build();
    }

    private static class WeightedEntry extends Tuple<Item, Float> {

        public WeightedEntry(Item aIn, Float bIn) {
            super(aIn, bIn);
        }
    }

    public static class Builder {

        private final ArrayList<WeightedRandomItem.Builder.Entry> list;

        public Builder() {
            list = new ArrayList<>();
        }

        public WeightedRandomItem.Builder add(String item, int weight) {
            list.add(new WeightedRandomItem.Builder.Entry(item, weight));
            return this;
        }

        public WeightedRandomItem build() {
            return new WeightedRandomItem(list.toArray(new WeightedRandomItem.Builder.Entry[0]));
        }

        private static class Entry extends Tuple<String, Integer> {

            public Entry(String aIn, Integer bIn) {
                super(aIn, bIn);
            }
        }

    }
}