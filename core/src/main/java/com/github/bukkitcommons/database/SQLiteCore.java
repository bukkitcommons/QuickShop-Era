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

package org.maxgamer.quickshop.api.database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SQLiteCore implements DatabaseCore {

    private final LinkedList<BufferStatement> queue = new LinkedList<>();

    @NotNull
    private final File databasefile;

    @Nullable
    private Connection connection;

    @Nullable
    private volatile Thread watcher;

    public SQLiteCore(@NotNull final File file) {
        this.databasefile = file;
    }

    @Override
    public void close() {
        this.flush();
    }

    @Override
    public void flush() {
        while (!this.queue.isEmpty()) {
            final BufferStatement buffer;
            synchronized (this.queue) {
                buffer = this.queue.removeFirst();
            }
            synchronized (this.databasefile) {
                this.getConnection().ifPresent(cnnctn -> {
                    try (final PreparedStatement statement = buffer.prepareStatement(cnnctn)) {
                        statement.execute();
                    } catch (final SQLException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }

    @Override
    public void queue(@NotNull final BufferStatement statement) {
        synchronized (this.queue) {
            this.queue.add(statement);
        }
        final Thread wtchr = this.watcher;
        if (wtchr == null || !wtchr.isAlive()) {
            this.startWatcher();
        }
    }

    /**
     * Gets the database connection for executing queries on.
     *
     * @return The database connection
     */
    @NotNull
    @Override
    public Optional<Connection> getConnection() {
        while (true) {
            try {
                // If we have a current connection, fetch it
                if (this.connection != null && !this.connection.isClosed()) {
                    return Optional.of(this.connection);
                }
            } catch (final SQLException e) {
                e.printStackTrace();
            }
            if (this.databasefile.exists()) {
                // So we need a new connection
                try {
                    Class.forName("org.sqlite.JDBC");
                    this.connection = DriverManager.getConnection("jdbc:sqlite:" + this.databasefile);
                    return Optional.of(this.connection);
                } catch (final ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                    return Optional.empty();
                }
            } else {
                // So we need a new file too.
                try {
                    // Create the file
                    this.databasefile.createNewFile();
                    // Now we won't need a new file, just a connection.
                    // This will return that new connection.
                } catch (final IOException e) {
                    e.printStackTrace();
                    return Optional.empty();
                }
            }
        }
    }

    private void startWatcher() {
        final Thread wtchr = new Thread(() -> {
            try {
                Thread.sleep(30000L);
            } catch (final InterruptedException ignored) {
                // ignore
            }
            this.flush();
        });
        wtchr.start();
        this.watcher = wtchr;
    }

}