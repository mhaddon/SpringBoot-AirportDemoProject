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

@Data
final public class ChunkIterator<T extends Iterable<U>, U extends Iterable> {
    static public <T extends Iterable<U>, U extends Iterable> ChunkIterator<T, U> of(final T iterable) {
        return new ChunkIterator<>(iterable);
    }

    final private T iterable;
    private int size = 100;

    private ChunkIterator(final T iterable) {
        this.iterable = iterable;
    }

    final public void iterate(final Consumer<List<U>> consumer) {
        final List<U> chunk = new ArrayList<>();

        for (final U iteration : iterable) {
            chunk.add(iteration);

            if (chunk.size() >= size) {
                consumer.accept(chunk);
                chunk.clear();
            }
        }
        consumer.accept(chunk);
        chunk.clear();
    }
}
