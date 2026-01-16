package com.lucifer.mnafix;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class SpellCache {
    private static final Map<ResourceLocation, Recipe<?>> CACHE = new HashMap<>();
    private static volatile boolean ready = false;

    public static boolean isReady() {
        return ready;
    }

    public static Recipe<?> get(ResourceLocation id) {
        return CACHE.get(id);
    }

    public static void buildAsync(Level level) {
        ready = false;

        CompletableFuture.runAsync(() -> {
            Map<ResourceLocation, Recipe<?>> temp = new HashMap<>();
            for (Recipe<?> r : level.getRecipeManager().getRecipes()) {
                temp.put(r.getId(), r);
            }
            CACHE.clear();
            CACHE.putAll(temp);
            ready = true;
        });
    }
}
