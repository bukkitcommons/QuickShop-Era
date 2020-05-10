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

import com.github.bukkitcommons.bukkit.api.handle.live.pattern.LivePatternSign;
import com.github.bukkitcommons.bukkit.api.util.BukkitUtils;
import com.github.bukkitcommons.core.handle.live.stack.LiveStackNone;
import com.github.bukkitcommons.core.handle.single.ShopStackBasic;
import com.github.bukkitcommons.core.live.LivePattern;
import com.github.bukkitcommons.core.type.Pattern;
import io.github.portlek.configs.jsonparser.JsonObject;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.jetbrains.annotations.NotNull;

public final class PatternSign implements Pattern {

    @NotNull
    @Override
    public String getId() {
        return "sign-shop";
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
        final Sign sign = (Sign) location.getBlock().getState();

        return new LivePatternSign(sign, new ShopStackBasic(new LiveStackNone(), new LiveStackNone(), new LiveStackNone()));
    }

}
