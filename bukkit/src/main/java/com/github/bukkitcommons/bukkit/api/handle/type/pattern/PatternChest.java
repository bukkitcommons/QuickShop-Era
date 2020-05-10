/*
 * MIT License
 *
 * Copyright © 2020 Bukkit Commons Studio
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.bukkitcommons.bukkit.api.handle.type.pattern;

import com.github.bukkitcommons.bukkit.api.handle.live.pattern.LivePatternChest;
import com.github.bukkitcommons.bukkit.api.util.BukkitUtils;
import com.github.bukkitcommons.core.live.LivePattern;
import com.github.bukkitcommons.core.single.ShopStack;
import com.github.bukkitcommons.core.type.Pattern;
import io.github.portlek.configs.jsonparser.JsonObject;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Location;
import org.bukkit.block.Chest;
import org.jetbrains.annotations.NotNull;

public final class PatternChest implements Pattern {

    @NotNull
    @Override
    public String getId() {
        return "chest-shop";
    }

    @Override
    public void preCreate() {

    }

    @Override
    public void postCreate() {

    }

    @NotNull
    @Override
    public LivePattern deserialize(@NotNull final JsonObject object) {
        final Location location = BukkitUtils.getLocationFromJsonObject(object.get("location").asObject());
        final Chest chest = (Chest) location.getBlock().getState();
        // TODO fill the slots with the json object.
        final Map<Integer, ShopStack> slots = new HashMap<>();

        return new LivePatternChest(chest, slots);
    }

}
