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

import com.airportdemo.testcategory.Fast;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class ChunkIteratorAsyncTest {
    @Category(Fast.class)
    public static class iterate {
        @Test
        public void Can_Iterate_String_List() {
            final Integer expectedResult = 2;
            final List<Integer> result = Arrays.asList(0);

            ChunkIteratorAsync.of(Arrays.asList("a", "b", "c", "d"))
                    .setSize(2)
                    .iterate(chunk -> {
                        result.set(0, result.get(0) + 1);
                    });

            assertEquals(expectedResult, result.get(0));
        }
    }
}
