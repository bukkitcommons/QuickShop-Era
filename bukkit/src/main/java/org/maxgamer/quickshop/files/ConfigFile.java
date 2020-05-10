/*
 * QuickShop
 * Copyright (C) 2020  Bukkit Commons Studio and contributors
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

package org.maxgamer.quickshop.files;

import io.github.portlek.configs.BukkitManaged;
import io.github.portlek.configs.annotations.Config;
import io.github.portlek.configs.annotations.Value;

@Config(
    name = "config",
    location = "%basedir%/QuickShop-Era"
)
public final class ConfigFile extends BukkitManaged {

    @Value
    public String plugin_language = "en-US";

    @Value
    public String plugin_prefix = "&6[&eQuickShop-Era&6]";

    @Override
    public void onCreate() {

    }

    @Override
    public void onLoad() {

    }

}
