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

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.maxgamer.quickshop.api.single.Registry;
import org.maxgamer.quickshop.api.type.Pattern;
import org.maxgamer.quickshop.api.type.Stack;

public final class RegistryBasic implements Registry {

    private final Map<String, Pattern> patterns = new ConcurrentHashMap<>();

    private final Map<String, Stack> stacks = new ConcurrentHashMap<>();

    @Override
    public void registerPattern(@NotNull final String id, @NotNull final Pattern pattern) {
        this.patterns.put(id, pattern);
    }

    @NotNull
    @Override
    public Optional<Pattern> getPatternById(@NotNull final String id) {
        return Optional.ofNullable(this.patterns.get(id));
    }

    @NotNull
    @Override
    public Collection<Pattern> getPatterns() {
        return Collections.unmodifiableCollection(this.patterns.values());
    }

    @Override
    public void registerStack(@NotNull final String id, @NotNull final Stack stack) {
        this.stacks.put(id, stack);
    }

    @NotNull
    @Override
    public Optional<Stack> getStackById(@NotNull final String id) {
        return Optional.ofNullable(this.stacks.get(id));
    }

    @NotNull
    @Override
    public Collection<Stack> getStacks() {
        return Collections.unmodifiableCollection(this.stacks.values());
    }

}
