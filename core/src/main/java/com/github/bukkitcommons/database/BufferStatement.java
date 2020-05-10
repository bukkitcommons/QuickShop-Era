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
import java.sql.SQLException;
import java.util.Arrays;
import org.jetbrains.annotations.NotNull;

public final class BufferStatement {

    @NotNull
    private final String query;

    @NotNull
    private final Object[] values;

    /**
     * Represents a PreparedStatement in a state before preparing it (E.g. No file I/O Required)
     *
     * @param qry The query to execute. E.g. INSERT INTO accounts (user, passwd) VALUES (?, ?)
     * @param vls The values to replace ? with in query. These are in order.
     */
    public BufferStatement(@NotNull final String qry, @NotNull final Object... vls) {
        this.query = qry;
        this.values = vls.clone();
    }

    /**
     * @return A string representation of this statement. Returns "Query: " + query + ", values: " +
     * Arrays.toString(values).
     */
    @Override
    public String toString() {
        return "Query: " + this.query + ", values: " + Arrays.toString(this.values);
    }

    /**
     * Returns a prepared statement using the given connection. Will try to return an empty statement
     * if something went wrong. If that fails, returns null.
     *
     * <p>This method escapes everything automatically.
     *
     * @param con The connection to prepare this on using con.prepareStatement(..)
     * @return The prepared statement, ready for execution.
     * @throws SQLException Throw exception when failed to execute something in SQL
     */
    @NotNull
    PreparedStatement prepareStatement(@NotNull final Connection con) throws SQLException {
        final PreparedStatement statement = con.prepareStatement(this.query);
        for (int index = 1; index <= this.values.length; index++) {
            statement.setObject(index, this.values[index - 1]);
        }
        return statement;
    }

}
