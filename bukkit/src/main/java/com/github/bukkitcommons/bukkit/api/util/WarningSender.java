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

import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

@EqualsAndHashCode
@ToString
public final class WarningSender {

    private final long cooldown;

    private long lastSend = 0L;

    @ToString.Exclude
    private final Plugin plugin;

    /**
     * Create a warning sender
     *
     * @param plgn Main class
     * @param cldwn Time unit: ms
     */
    public WarningSender(@NotNull final Plugin plgn, final long cldwn) {
        this.plugin = plgn;
        this.cooldown = cldwn;
    }

    /**
     * Send warning a warning
     *
     * @param text The text you want send/
     * @return Success sent, if it is in a cool-down, it will return false
     */
    public boolean sendWarn(final String text) {
        if (System.currentTimeMillis() - this.lastSend > this.cooldown) {
            this.plugin.getLogger().warning(text);
            this.lastSend = System.currentTimeMillis();
            return true;
        }
        return false;
    }

}