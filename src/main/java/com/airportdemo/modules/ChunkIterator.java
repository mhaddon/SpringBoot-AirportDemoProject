/*
 * SpringBoot-AirportDemoProject  Copyright (C) 2017  Michael Haddon
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License version 3
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.airportdemo.modules;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * The class provides an abstraction over iterables so you can process their iterations in chunks.
 * For example if you do:
 * <p>
 * ChunkIterator.of(Iterable)
 * .setSize(1000)
 * .iterate(Consumer);
 * <p>
 * That will process an iterable in chunks of 1000, the consumer being sent the list of iterations.
 * The purpose of chunking like this is to try to reduce overhead when you do operations individually.
 * For example, opening/closing a mysql connection. This is unnecessary overhead when you are dealing with thousands
 * of elements.
 */
@Data
public class ChunkIterator<T extends Iterable<U>, U> {
    /**
     * Simple factory method
     *
     * @param iterable What are we iterating over
     * @param <T>      The type of what we are iterating over, like List
     * @param <U>      The type of the contents of what we are iterating over, like String
     * @return ChunkIterator
     */
    static public <T extends Iterable<U>, U> ChunkIterator<T, U> of(final T iterable) {
        return new ChunkIterator<>(iterable);
    }

    /**
     * The current cache of chunk elements that we are processing
     */
    final private List<U> chunk = new ArrayList<>();

    /**
     * The iterable that we are iterating over
     */
    final private T iterable;

    /**
     * The maximum size of the chunk
     */
    private int size = 100;

    /**
     * @param iterable What are we iterating over
     */
    protected ChunkIterator(final T iterable) {
        this.iterable = iterable;
    }

    /**
     * @param consumer
     */
    public void iterate(final Consumer<List<U>> consumer) {
        for (final U iteration : getIterable()) {
            getChunk().add(iteration);

            if (ifChunkIsFull()) {
                processChunk(consumer);
            }
        }

        processChunk(consumer);
    }

    protected boolean ifChunkIsFull() {
        return getChunk().size() >= getSize();
    }

    /**
     * Processes the chunk and resets the chunk cache
     *
     * @param consumer
     */
    protected void processChunk(final Consumer<List<U>> consumer) {
        consumer.accept(getChunk());
        getChunk().clear();
    }
}
