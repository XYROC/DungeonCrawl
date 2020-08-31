package xiroc.dungeoncrawl.util;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.util.Tuple;
import xiroc.dungeoncrawl.dungeon.decoration.IDungeonDecoration;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class WeightedRandom<T> implements IRandom<T> {

    //TODO: migrate other WeightedRandom classes to this

    public static final WeightedRandom.JsonReader<IDungeonDecoration> DECORATION = (array) -> {
        WeightedRandom.Builder<IDungeonDecoration> builder = new WeightedRandom.Builder<>();
        array.forEach((element) -> {
            JsonObject object = element.getAsJsonObject();
            int weight = object.has("weight") ? object.get("weight").getAsInt() : 1;
            IDungeonDecoration decoration = IDungeonDecoration.fromJson(object);
            if (decoration != null) {
                builder.entries.add(new WeightedEntry((float) weight, decoration));
            }
        });
        return builder.build();
    };

    private final int totalWeight;
    private final WeightedEntry[] entries;

    private WeightedRandom(WeightedEntry[] entries) {
        int weight = 0;
        for (WeightedEntry entry : entries)
            weight += entry.getA();
        this.totalWeight = weight;
        this.entries = new WeightedEntry[entries.length];
        this.assign(this.entries);
    }

    private void assign(WeightedEntry[] values) {
        float f = 0.0F;
        int i = 0;
        for (WeightedEntry entry : values) {
            float weight = entry.getA() / (float) totalWeight;
            entries[i] = new WeightedRandom.WeightedEntry(weight + f, entry.getB());
            f += weight;
            i++;
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public T roll(Random rand) {
        float f = rand.nextFloat();
        for (WeightedEntry entry : entries)
            if (entry.getA() >= f) {
                return (T) entry.getB();
            }
        return null;
    }

    private static class WeightedEntry extends Tuple<Float, Object> {

        public WeightedEntry(Float aIn, Object bIn) {
            super(aIn, bIn);
        }

    }

    public static class Builder<T> {

        public List<WeightedEntry> entries;

        public Builder() {
            entries = Lists.newArrayList();
        }

        public WeightedRandom.Builder<T> add(WeightedEntry[] entries) {
            this.entries.addAll(Arrays.asList(entries));
            return this;
        }

        public WeightedRandom<T> build() {
            return new WeightedRandom<>(entries.toArray(new WeightedEntry[0]));
        }

    }

    @FunctionalInterface
    public interface JsonReader<T> {

        WeightedRandom<T> fromJson(JsonArray array);

    }

}
