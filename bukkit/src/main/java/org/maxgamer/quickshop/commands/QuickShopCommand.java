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

package org.maxgamer.quickshop.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.maxgamer.quickshop.QuickShop;
import org.maxgamer.quickshop.QuickShopAPI;

@Subcommand("quickshop|qs")
public final class QuickShopCommand extends BaseCommand {

    @NotNull
    private final QuickShopAPI api = QuickShop.getAPI();

    @Default
    public void defaultCommand(final CommandSender sender) {

    }

}
