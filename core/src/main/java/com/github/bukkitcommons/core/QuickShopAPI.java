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

package com.github.bukkitcommons.core;

import com.github.bukkitcommons.core.handle.single.RegistryBasic;
import com.github.bukkitcommons.core.single.Registry;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class QuickShopAPI {

    @Nullable
    private static QuickShopAPI instance = null;

    private final Registry registry = new RegistryBasic();

    @NotNull
    public static QuickShopAPI getInstance() {
        return Optional.ofNullable(QuickShopAPI.instance).orElseThrow(() ->
            new IllegalStateException("You can't use QuickShop plugin before its start!")
        );
    }

    public static void setInstance(@NotNull final QuickShopAPI ins) {
        Optional.ofNullable(QuickShopAPI.instance).orElseThrow(() ->
            new IllegalStateException("You can't instantiate the QuickShop twice!")
        );
        QuickShopAPI.instance = ins;
    }

    @NotNull
    public final Registry getRegistry() {
        return this.registry;
    }

}
