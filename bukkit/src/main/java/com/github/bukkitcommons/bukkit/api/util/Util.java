/*
 * QuickShop-Era
 * Copyright (C) 2020  BukkitCommons
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.github.bukkitcommons.bukkit.api.util;

import com.github.bukkitcommons.bukkit.QuickShop;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.jetbrains.annotations.NotNull;

public final class Util {

    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private static final List<String> debugLogs = new LinkedList<>();

    private static final boolean disableDebugLogger = false;

    private static final boolean devMode = false;

    private Util() {
    }

    /**
     * Print debug log when plugin running on dev mode.
     *
     * @param logs logs
     */
    public static void debugLog(@NotNull final String... logs) {
        if (Util.disableDebugLogger) {
            return;
        }
        Util.lock.writeLock().lock(); //TODO MOVE IT
        if (Util.debugLogs.size() >= 2000) {
            Util.debugLogs.clear();
        }
        //TODO <- MOVE TO THERE SHOULD BE FINE
        if (!Util.devMode) {
            for (final String log : logs) {
                Util.debugLogs.add("[DEBUG] " + log);
            }
            Util.lock.writeLock().unlock();
            return;
        }
        final StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        final String className = stackTraceElement.getClassName();
        final String methodName = stackTraceElement.getMethodName();
        final int codeLine = stackTraceElement.getLineNumber();
        for (final String log : logs) {
            Util.debugLogs.add("[DEBUG] [" + className + "] [" + methodName + "] (" + codeLine + ") " + log);
            QuickShop.getInstance().getLogger().info("[DEBUG] [" + className + "] [" + methodName + "] (" + codeLine + ") " + log);
        }
        Util.lock.writeLock().unlock();
    }

}
