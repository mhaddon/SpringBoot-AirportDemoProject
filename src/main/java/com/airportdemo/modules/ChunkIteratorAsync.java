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

import org.springframework.scheduling.annotation.Async;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * This class is an Async version of ChunkIterator.
 * See parent class for more information.
 *
 * Instead of processing all of the chunks sequentially this version will process each chunk async but it will
 * wait until all the chunks have finished being processed before it finishes processing.
 * This means that the use of the Async and non-async version are the same without any functional differences...
 * other than the fact that the async one may, and probably will, process the results out of order.
 */
public class ChunkIteratorAsync<T extends Iterable<U>, U> extends ChunkIterator<T, U> {
    /**
     * Simple factory method
     *
     * @param iterable What are we iterating over
     * @param <T>      The type of what we are iterating over, like List
     * @param <U>      The type of the contents of what we are iterating over, like String
     * @return ChunkIteratorAsync
     */
    static public <T extends Iterable<U>, U> ChunkIteratorAsync<T, U> of(final T iterable) {
        return new ChunkIteratorAsync<>(iterable);
    }

    /**
     * @param iterable What are we iterating over
     */
    private ChunkIteratorAsync(final T iterable) {
        super(iterable);
    }

    /**
     *
     * @param consumer
     */
    @Override
    final public void iterate(final Consumer<List<U>> consumer) {
        final List<CompletableFuture<Void>> promises = new ArrayList<>();
        for (final U iteration : getIterable()) {
            getChunk().add(iteration);

            if (ifChunkIsFull()) {
                promises.add(processChunk(consumer, getChunk()));
            }
        }
        promises.add(processChunk(consumer, getChunk()));

        CompletableFuture.allOf(promises.toArray(new CompletableFuture[0])).join();
    }

    /**
     *
     * @param consumer
     * @param chunk
     * @return
     */
    @Async
    private CompletableFuture<Void> processChunk(final Consumer<List<U>> consumer, final List<U> chunk) {
        final List<U> chunkBlock = new ArrayList<>(chunk);
        chunk.clear();
        return CompletableFuture.runAsync(() -> consumer.accept(chunkBlock));
    }
}
