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

package org.maxgamer.quickshop;

import co.aikar.commands.BukkitCommandManager;
import co.aikar.commands.ConditionFailedException;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.maxgamer.quickshop.commands.QuickShopCommand;

public final class QuickShop extends JavaPlugin {

    private static QuickShopAPI api;

    private static QuickShop instance;

    @NotNull
    public static QuickShop getInstance() {
        return Optional.ofNullable(QuickShop.instance).orElseThrow(() ->
            new IllegalStateException("You cannot be used QuickShop plugin before its start!")
        );
    }

    private void setInstance(@NotNull final QuickShop instance) {
        if (Optional.ofNullable(QuickShop.instance).isPresent()) {
            throw new IllegalStateException("You can't use #setInstance method twice!");
        }
        synchronized (this) {
            QuickShop.instance = instance;
        }
    }

    @NotNull
    public static QuickShopAPI getAPI() {
        return Optional.ofNullable(QuickShop.api).orElseThrow(() ->
            new IllegalStateException("You cannot be used QuickShop plugin before its start!")
        );
    }

    private void setAPI(@NotNull final QuickShopAPI loader) {
        if (Optional.ofNullable(QuickShop.api).isPresent()) {
            throw new IllegalStateException("You can't use #setAPI method twice!");
        }
        synchronized (this) {
            QuickShop.api = loader;
        }
    }

    @Override
    public void onLoad() {
        this.setInstance(this);
    }

    @Override
    public void onDisable() {
        if (QuickShop.api != null) {
            QuickShop.api.disable();
        }
    }

    @Override
    public void onEnable() {
        final BukkitCommandManager manager = new BukkitCommandManager(this);
        final QuickShopAPI loader = new QuickShopAPI(this);
        this.setAPI(loader);
        this.getServer().getScheduler().runTask(this, () ->
            this.getServer().getScheduler().runTaskAsynchronously(this, () ->
                loader.reload(true)));
        manager.getCommandConditions().addCondition(String[].class, "player", (context, exec, value) -> {
            if (value == null || value.length == 0) {
                return;
            }
            final String name = value[context.getConfigValue("arg", 0)];
            if (context.hasConfig("arg") && Bukkit.getPlayer(name) == null) {
                throw new ConditionFailedException(loader.languageFile.unknown_player
                    .build("%player_name%", () -> name));
            }
        });
        manager.registerCommand(new QuickShopCommand());
    }

}
