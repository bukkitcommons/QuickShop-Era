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

package com.github.bukkitcommons.core.type;

import com.github.bukkitcommons.core.Type;
import com.github.bukkitcommons.core.live.LivePattern;
import com.github.bukkitcommons.core.single.ShopBase;
import org.jetbrains.annotations.NotNull;

/**
 * A {@link com.github.bukkitcommons.core.single.Shop} type, that includes live stacks, architecture in {@link ShopBase}.
 */
public interface Pattern extends Type<LivePattern> {

    /**
     * Temporary method, we should delete that.
     *
     * @return the id
     */
    @NotNull String getId();

    /**
     * TODO
     */
    void preCreate();

    /**
     * TODO
     */
    void postCreate();

}
