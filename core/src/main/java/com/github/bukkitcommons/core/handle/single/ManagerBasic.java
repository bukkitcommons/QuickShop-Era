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

package com.github.bukkitcommons.core.handle.single;

import com.github.bukkitcommons.core.live.Manager;
import com.github.bukkitcommons.core.single.Permissible;
import io.github.portlek.configs.jsonparser.JsonArray;
import io.github.portlek.configs.jsonparser.JsonObject;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class ManagerBasic implements Manager {

    @NotNull
    private final Permissible owner;

    @NotNull
    private final List<Permissible> coOwners;

    @NotNull
    @Override
    public JsonObject asJsonValue() {
        final JsonObject jsonObject = new JsonObject();

        jsonObject.add("owner", this.owner.asJsonValue());

        final JsonArray parent = new JsonArray();

        this.coOwners.forEach(permissible -> parent.add(permissible.asJsonValue()));
        jsonObject.add("co-owners", parent);

        return jsonObject;
    }

}
