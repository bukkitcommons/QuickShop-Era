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

package com.github.bukkitcommons.bukkit.api.handle.live.locational;

import com.github.bukkitcommons.core.live.LiveLocational;
import io.github.portlek.configs.jsonparser.JsonObject;
import java.util.Objects;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public final class LiveLocationalNormal implements LiveLocational {

    @NotNull
    private final Location location;

    @NotNull
    private final World world;

    public LiveLocationalNormal(@NotNull final Location lctn) {
        this.location = lctn;
        this.world = Objects.requireNonNull(lctn.getWorld());
    }

    @NotNull
    @Override
    public JsonObject asJsonValue() {
        final JsonObject object = new JsonObject();
        object.add("x", this.location.getX());
        object.add("y", this.location.getY());
        object.add("z", this.location.getZ());
        object.add("yaw", this.location.getYaw());
        object.add("pitch", this.location.getPitch());
        object.add("world-uuid", this.world.getUID().toString());
        return object;
    }

}
