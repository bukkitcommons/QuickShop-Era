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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import lombok.Cleanup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Database {

    private static final Object LOCK = new Object();

    @Nullable
    private static Database instance;

    @NotNull
    private final DatabaseCore core;

    /**
     * Creates a new database and validates its connection.
     *
     * <p>If the connection is invalid, this will throw a ConnectionException.
     *
     * @param cre The core for the database, either MySQL or SQLite.
     * @throws Database.ConnectionException If the connection was invalid
     */
    public Database(@NotNull final DatabaseCore cre) throws Database.ConnectionException {
        final Optional<Connection> optional = cre.getConnection();
        if (optional.isPresent()) {
            try {
                try {
                    if (!optional.get().isValid(10)) {
                        throw new Database.ConnectionException("The database does not appear to be valid!");
                    }
                } catch (final AbstractMethodError ignored) {
                    // You don't need to validate this core.
                }
            } catch (final SQLException e) {
                throw new Database.ConnectionException(e.getMessage());
            }
        }
        this.core = cre;
    }

    @NotNull
    public static Database getInstance() {
        return Optional.ofNullable(Database.instance).orElseThrow(() ->
            new IllegalStateException("You can't use Database before its set!")
        );
    }

    @NotNull
    public static void setInstance(@NotNull final Database database) {
        if (Optional.ofNullable(Database.instance).isPresent()) {
            throw new IllegalStateException("You can't use #setInstance method twice!");
        }
        synchronized (Database.LOCK) {
            Database.instance = database;
        }
    }

    /**
     * Closes the database
     */
    public void close() {
        this.core.close();
    }

    /**
     * Executes the given statement either immediately, or soon.
     *
     * @param query The query
     * @param objs The string values for each ? in the given query.
     */
    public void execute(@NotNull final String query, @NotNull final Object... objs) {
        this.core.queue(new BufferStatement(query, objs));
    }

    /**
     * Returns true if the given table has the given column
     *
     * @param table The table
     * @param column The column
     * @return True if the given table has the given column
     * @throws SQLException If the database isn't connected
     */
    public boolean hasColumn(@NotNull final String table, @NotNull final String column) throws SQLException {
        final Optional<Connection> optional = this.getConnection();
        if (!optional.isPresent()) {
            return false;
        }
        if (!this.hasTable(table)) {
            return false;
        }
        try {
            final String query = "SELECT * FROM " + table + " LIMIT 0,1";
            @Cleanup final PreparedStatement statement = optional.get().prepareStatement(query);
            final ResultSet result = statement.executeQuery();
            while (result.next()) {
                if (result.getString(column) != null) {
                    return true;
                }
            }
        } catch (final SQLException e) {
            return false;
        }
        return false; // Uh, wtf.
    }

    /**
     * Fetches the connection to this database for querying. Try to avoid doing this in the main
     * thread.
     *
     * @return Fetches the connection to this database for querying.
     */
    @NotNull
    public Optional<Connection> getConnection() {
        return this.core.getConnection();
    }

    /**
     * Returns true if the table exists
     *
     * @param table The table to check for
     * @return True if the table is found
     * @throws SQLException Throw exception when failed execute somethins on SQL
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean hasTable(@NotNull final String table) throws SQLException {
        final Optional<Connection> optional = this.getConnection();
        if (!optional.isPresent()) {
            return false;
        }
        final ResultSet result = optional.get().getMetaData().getTables(null, null, "%", null);
        while (result.next()) {
            if (table.equalsIgnoreCase(result.getString("TABLE_NAME"))) {
                result.close();
                return true;
            }
        }
        result.close();
        return false;
    }

    /**
     * Returns the database core object, that this database runs on.
     *
     * @return the database core object, that this database runs on.
     */
    @NotNull
    public DatabaseCore getCore() {
        return this.core;
    }

    /**
     * Represents a connection error, generally when the server can't connect to MySQL or something.
     */
    public static final class ConnectionException extends Exception {

        private static final long serialVersionUID = 8348749992936357317L;

        private ConnectionException(@NotNull final String msg) {
            super(msg);
        }

    }

}