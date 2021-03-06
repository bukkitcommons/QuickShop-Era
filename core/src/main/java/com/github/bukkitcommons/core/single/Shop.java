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

package com.github.bukkitcommons.core.single;

import com.github.bukkitcommons.core.Live;
import java.util.UUID;
import org.jetbrains.annotations.NotNull;

/**
 * The biggest unit of the whole shops.
 */
public interface Shop extends Live {

    /**
     * Gives the uuid
     *
     * @return the uuid of the shop
     */
    @NotNull UUID getUniqueId();

    /**
     * Gives the shop type
     *
     * @return the shop type of the shop.
     */
    @NotNull com.github.bukkitcommons.core.single.ShopBase getShopBase();

}
