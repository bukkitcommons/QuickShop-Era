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

package com.github.bukkitcommons.bukkit.api;

import com.github.bukkitcommons.bukkit.api.database.DatabaseManager;
import com.github.bukkitcommons.core.Addon;
import java.util.Optional;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BukkitAddon extends JavaPlugin implements Addon {

    @Nullable
    private static BukkitAddon instance;

    @Nullable
    private DatabaseManager databasemanager;

    /**
     * Gives an instance of the plugin.
     *
     * @return instance of the plugin.
     * @throws IllegalStateException if {@link #instance} is null.
     */
    @NotNull
    public static BukkitAddon getInstance() {
        return Optional.ofNullable(BukkitAddon.instance).orElseThrow(() ->
            new IllegalStateException("You cannot be used QuickShop plugin before its start!")
        );
    }

    /**
     * Set the instance of the plugin.
     *
     * @param instance the instance
     * @throws IllegalStateException if {@link #instance} is null.
     */
    protected void setInstance(@NotNull final BukkitAddon instance) {
        if (Optional.ofNullable(BukkitAddon.instance).isPresent()) {
            throw new IllegalStateException("You can't use #setInstance method twice!");
        }
        synchronized (this) {
            BukkitAddon.instance = instance;
        }
    }

    /**
     * Gives an instance of the database manager.
     *
     * @return instance of the database manager.
     * @throws IllegalStateException if {@link #databasemanager} is null.
     */
    @NotNull
    public DatabaseManager getDatabaseManager() {
        return Optional.ofNullable(this.databasemanager).orElseThrow(() ->
            new IllegalStateException("You cannot be used QuickShop plugin before its start!")
        );
    }

    /**
     * Set the instance of the database manager.
     *
     * @param manager the instance
     * @throws IllegalStateException if {@link #databasemanager} is null.
     */
    protected void setDatabaseManager(@NotNull final DatabaseManager manager) {
        if (Optional.ofNullable(BukkitAddon.instance).isPresent()) {
            throw new IllegalStateException("You can't use #setInstance method twice!");
        }
        synchronized (this) {
            this.databasemanager = manager;
        }
    }

}
