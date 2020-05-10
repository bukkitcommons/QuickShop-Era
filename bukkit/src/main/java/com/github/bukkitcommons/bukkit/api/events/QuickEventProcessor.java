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

package com.github.bukkitcommons.bukkit.api.events;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.AuthorNagException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class QuickEventProcessor {

    @NotNull
    private final Map<String, List<String>> blacklist;

    /**
     * Calling a event on Bukkit Event Bus.
     *
     * @param event The event will be called.
     * @return All plugins processed this event and results when processing a plugin.
     */
    @NotNull
    public List<EventDataContainer> fireEvent(@NotNull final Event event) {
        final List<EventDataContainer> result = new ArrayList<>();
        final HandlerList handlerlist = event.getHandlers();
        final RegisteredListener[] listeners = handlerlist.getRegisteredListeners();

        for (final RegisteredListener registration : listeners) {
            final Plugin plugin = registration.getPlugin();
            final String pluginname = plugin.getName();

            if (this.blacklist.containsKey(pluginname) || this.blacklist.get(pluginname).contains(registration.getListener().getClass().getName())) {
                continue;
            }

            try {
                registration.callEvent(event);
            } catch (final AuthorNagException ex) {
                plugin.setNaggable(false);
            } catch (final EventException e) {
                e.printStackTrace();
            } finally {
                if (event instanceof Cancellable) {
                    result.add(new EventDataContainer(plugin, registration, ((Cancellable) event).isCancelled()));
                } else {
                    result.add(new EventDataContainer(plugin, registration, false));
                }
            }
        }

        return result;
    }

}

