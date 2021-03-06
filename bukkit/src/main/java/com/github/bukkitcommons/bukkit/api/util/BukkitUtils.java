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

package com.github.bukkitcommons.bukkit.api.util;

import io.github.portlek.configs.jsonparser.JsonObject;
import java.util.Objects;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public final class BukkitUtils {

    /**
     * Private ctor.
     */
    private BukkitUtils() {
    }

    @NotNull
    public static Location getLocationFromJsonObject(@NotNull final JsonObject object) {
        final double xpos = object.get("x").asDouble();
        final double ypos = object.get("y").asDouble();
        final double zpos = object.get("z").asDouble();
        final float yaw = object.get("yaw").asFloat();
        final float pitch = object.get("pitch").asFloat();
        final UUID uuid = UUID.fromString(object.get("world-uuid").asString());
        return new Location(Objects.requireNonNull(Bukkit.getWorld(uuid)), xpos, ypos, zpos, yaw, pitch);
    }

}
