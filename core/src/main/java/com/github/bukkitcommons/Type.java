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

package com.github.bukkitcommons;

import org.jetbrains.annotations.NotNull;

/**
 * Type object that you can create {@link Live} objects.
 */
public interface Type<T> {

    /**
     * Deserializes to the object
     *
     * @return the object from the json object.
     */
    @NotNull T deserialize(@NotNull JsonObject object);

}
