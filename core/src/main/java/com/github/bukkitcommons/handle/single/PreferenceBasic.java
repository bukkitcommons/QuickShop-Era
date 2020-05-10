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

package org.maxgamer.quickshop.api.handle.single;

import com.eclipsesource.json.JsonObject;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.maxgamer.quickshop.api.live.Manager;
import org.maxgamer.quickshop.api.single.Preference;

@RequiredArgsConstructor
public final class PreferenceBasic implements Preference {

    @NotNull
    private final Manager manager;

    private boolean display;

    private boolean enable;

    private boolean unlimited;

    private boolean dynamic;

    @NotNull
    @Override
    public JsonObject serialize() {
        final JsonObject jsonObject = new JsonObject();

        jsonObject.add("manager", this.manager.serialize());
        jsonObject.add("display", this.display);
        jsonObject.add("enable", this.enable);
        jsonObject.add("unlimited", this.unlimited);
        jsonObject.add("dynamic", this.dynamic);

        return jsonObject;
    }

}
