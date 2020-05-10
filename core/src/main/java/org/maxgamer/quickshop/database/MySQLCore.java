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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;
import org.jetbrains.annotations.NotNull;

public final class MySQLCore implements DatabaseCore {

    private static final ArrayList<Connection> POOL = new ArrayList<>();

    private static final int MAX_CONNECTIONS = 8;

    /**
     * The connection properties... user, pass, autoReconnect..
     */
    @NotNull
    private final Properties info;

    @NotNull
    private final String url;

    public MySQLCore(
        @NotNull final String host,
        @NotNull final String user,
        @NotNull final String pass,
        @NotNull final String database,
        @NotNull final String port,
        final boolean usessl) {
        this.info = new Properties();
        this.info.setProperty("autoReconnect", "true");
        this.info.setProperty("user", user);
        this.info.setProperty("password", pass);
        this.info.setProperty("useUnicode", "true");
        this.info.setProperty("characterEncoding", "utf8");
        this.info.setProperty("useSSL", String.valueOf(usessl));
        this.url = "jdbc:mysql://" + host + ':' + port + '/' + database;
        for (int index = 0; index < MySQLCore.MAX_CONNECTIONS; index++) {
            MySQLCore.POOL.add(null);
        }
    }

    @Override
    public void close() {
        // Nothing, because queries are executed immediately for MySQL
    }

    @Override
    public void flush() {
        // Nothing, because queries are executed immediately for MySQL
    }

    @Override
    public void queue(@NotNull final BufferStatement buffer) {
        try {
            Optional<Connection> optional = this.getConnection();
            while (!optional.isPresent()) {
                try {
                    Thread.sleep(15L);
                } catch (final InterruptedException ignored) {
                    // ignore
                }
                // Try again
                optional = this.getConnection();
            }
            final PreparedStatement statement = buffer.prepareStatement(optional.get());
            statement.execute();
            statement.close();
        } catch (final SQLException e) {
            e.printStackTrace();
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
        for (int index = 0; index < MySQLCore.MAX_CONNECTIONS; index++) {
            Connection connection = MySQLCore.POOL.get(index);
            try {
                // If we have a current connection, fetch it
                if (connection != null && !connection.isClosed()) {
                    if (connection.isValid(10)) {
                        return Optional.of(connection);
                    }
                    // Else, it is invalid, so we return another connection.
                }
                connection = DriverManager.getConnection(this.url, this.info);
                MySQLCore.POOL.set(index, connection);
                return Optional.of(connection);
            } catch (final SQLException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }

}