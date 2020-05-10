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

package com.github.bukkitcommons.bukkit.api.util;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Locale;
import java.util.TimerTask;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

public final class DebugLogger extends TimerTask {

    @NotNull
    private final File logfile;

    @NotNull
    private final BufferedWriter bufferedWriter;

    @NotNull
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("[HH:mm:ss] ", Locale.ENGLISH);

    @NotNull
    private LinkedList<String> caching = new LinkedList<>();

    @SneakyThrows
    public DebugLogger(@NotNull final File file) {
        super();
        this.logfile = file;
        if (!this.logfile.exists()) {
            this.logfile.mkdirs();
            this.logfile.createNewFile();
        }
        this.bufferedWriter = new BufferedWriter(
            new OutputStreamWriter(
                new FileOutputStream(
                    file,
                    false
                ),
                StandardCharsets.UTF_8
            )
        );
        new Timer().scheduleAtFixedRate(this, 0L, 10000L);
    }

    @SneakyThrows
    public void log(@NotNull final String data) {
        this.caching.add(this.simpleDateFormat.format(new Date(System.currentTimeMillis())) + data);
    }

    @Override
    public void run() {
        //noinspection unchecked
        final Iterable<String> copy = (Iterable<String>) this.caching.clone();
        this.caching = new LinkedList<>(); //Fast replace
        for (final String line : copy) {
            try {
                this.bufferedWriter.write(line);
                this.bufferedWriter.newLine();
            } catch (final IOException ignored) {
            }
        }
    }

}
