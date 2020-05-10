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

import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public final class Timer {

    private long start = 0L;

    /**
     * Create a empty time, use setTimer to start
     */
    public Timer() {
    }

    /**
     * Create a empty time, auto start if autoSet is true
     *
     * @param autoSet Auto set the timer
     */
    public Timer(final boolean autoSet) {
        if (autoSet) {
            this.start = System.currentTimeMillis();
        }
    }

    /**
     * Create a empty time, use the param to init the startTime.
     *
     * @param strt New startTime
     */
    public Timer(final long strt) {
        this.start = strt;
    }

    /**
     * Return how long time running when timer set and destory the timer.
     *
     * @return time
     */
    public long endTimer() {
        final long time = System.currentTimeMillis() - this.start;
        this.start = 0;
        return time;
    }

    /**
     * Return how long time running after atTimeS. THIS NOT WILL DESTORY AND STOP THE TIMER
     *
     * @param atTime The inited time
     * @return time
     */
    public long getTimerAt(final long atTime) {
        return atTime - this.start;
    }

    /**
     * Create a Timer. Time Unit: ms
     */
    public void setTimer() {
        this.start = System.currentTimeMillis();
    }

    /**
     * Return how long time running when timer set. THIS NOT WILL DESTORY AND STOP THE TIMER
     *
     * @return time
     */
    public long getTimer() {
        return System.currentTimeMillis() - this.start;
    }

}