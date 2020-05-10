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

package com.github.bukkitcommons.bukkit.api.database;

import com.github.bukkitcommons.bukkit.api.BukkitAddon;
import com.github.bukkitcommons.bukkit.api.util.DebugUtils;
import com.github.bukkitcommons.bukkit.api.util.Timer;
import com.github.bukkitcommons.bukkit.api.util.WarningSender;
import com.github.bukkitcommons.core.database.Database;
import com.github.bukkitcommons.core.database.DatabaseTask;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DatabaseManager {

    private final Queue<DatabaseTask> sqlQueue = new LinkedBlockingQueue<>();

    @NotNull
    private final Database database;

    @NotNull
    private final BukkitAddon plugin;

    @NotNull
    private final WarningSender warningSender;

    @Nullable
    private BukkitTask task;

    private final boolean useQueue;

    /**
     * Queued database manager. Use queue to solve run SQL make server lagg issue.
     *
     * @param plgn plugin main class
     * @param db database
     */
    public DatabaseManager(@NotNull final BukkitAddon plgn, @NotNull final Database db) {
        this.plugin = plgn;
        this.warningSender = new WarningSender(plgn, 600000L);
        this.database = db;
        this.useQueue = plgn.getConfig().getBoolean("database.queue");
        if (!this.useQueue) {
            return;
        }
        try {
            this.task =
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        plgn.getDatabaseManager().runTask();
                    }
                }.runTaskTimerAsynchronously(plgn, 1, 200);
        } catch (final IllegalPluginAccessException e) {
            DebugUtils.debugLog("Plugin is disabled but trying create database task, move to Main Thread...");
            plgn.getDatabaseManager().runTask();
        }
    }

    /**
     * Add DatabaseTask to queue waiting flush to database,
     *
     * @param task The DatabaseTask you want add in queue.
     */
    public void add(@NotNull final DatabaseTask task) {
        if (this.useQueue) {
            this.sqlQueue.offer(task);
        } else {
            task.run();
        }
    }

    /**
     * Unload the DatabaseManager, run at onDisable()
     */
    public void unInit() {
        if (this.task != null && !this.task.isCancelled()) {
            this.task.cancel();
        }
        this.plugin.getLogger().info("Please wait for the data to flush its data...");
        this.runTask();
    }

    /**
     * Internal method, runTasks in queue.
     */
    private void runTask() {
        final Optional<Connection> optional = this.database.getConnection();
        if (!optional.isPresent()) {
            return;
        }
        while (true) {
            final Timer timer = new Timer(true);
            try {
                if (!optional.get().isValid(3000)) {
                    this.plugin
                        .getLogger()
                        .warning(
                            "Database connection may lost, we are trying reconnecting, if this message appear too many times, you should check your database file(sqlite) and internet connection(mysql).");
                    break; // Waiting next crycle and hope it success reconnected.
                }
            } catch (final SQLException sqle) {
                // TODO this.plugin.getSentryErrorReporter().ignoreThrow();
                sqle.printStackTrace();
            }
            final DatabaseTask task = this.sqlQueue.poll();
            if (task == null) {
                break;
            }
            DebugUtils.debugLog("Executing the SQL task: " + task);
            task.run();
            final long tookTime = timer.endTimer();
            if (tookTime > 5000L) {
                this.warningSender.sendWarn(
                    "Database performance warning: It took too long time ("
                        + tookTime
                        + "ms) to execute the task, it may cause the network connection with MySQL server or just MySQL server too slow, change to a better MySQL server or switch to a local SQLite database!");
            }
        }
        try {
            optional.get().commit();
        } catch (final SQLException e) {
            try {
                optional.get().rollback();
            } catch (final SQLException ignored) {
            }
        }
    }

}