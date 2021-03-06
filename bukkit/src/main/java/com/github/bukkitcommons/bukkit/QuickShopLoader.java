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

package com.github.bukkitcommons.bukkit;

import com.github.bukkitcommons.bukkit.files.ConfigFile;
import com.github.bukkitcommons.bukkit.files.LanguageFile;
import org.jetbrains.annotations.NotNull;

public final class QuickShopLoader {

    @NotNull
    public final ConfigFile configFile;

    @NotNull
    public final LanguageFile languageFile;

    @NotNull
    private final QuickShop plugin;

    public QuickShopLoader(@NotNull final QuickShop plugin) {
        this.plugin = plugin;
        this.configFile = new ConfigFile();
        this.languageFile = new LanguageFile(this.configFile);
    }

    public void reload(final boolean first) {
        this.configFile.load();
        this.languageFile.load();
        if (first) {
            // Register listeners...
        }
    }

    public void disable() {

    }

}
