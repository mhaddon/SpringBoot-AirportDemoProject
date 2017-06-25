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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class ChunkIteratorTest {
    @Category(Fast.class)
    public static class iterate {
        @Test
        public void Can_Iterate_String_List() {
            final Integer expectedResult = 2;
            final List<Integer> result = Arrays.asList(0);

            ChunkIterator.of(Arrays.asList("a", "b", "c", "d"))
                    .setSize(2)
                    .iterate(chunk -> {
                        result.set(0, result.get(0) + 1);
                    });

            assertEquals(expectedResult, result.get(0));
        }

        @Test
        public void Can_Iterate_When_Size_Exceeds_Limit() {
            final List<String> expectedResult = Arrays.asList("a", "b", "c", "d");
            final List<String> result = new ArrayList<>();

            ChunkIterator.of(expectedResult)
                    .setSize(200)
                    .iterate(result::addAll);

            assertEquals(expectedResult, result);
        }
        @Test
        public void Size_Below_One_Does_Not_Cause_Errors() {
            final Integer expectedResult = 4;
            final List<Integer> result = Arrays.asList(0);

            ChunkIterator.of(Arrays.asList("a", "b", "c", "d"))
                    .setSize(-100)
                    .iterate(chunk -> {
                        result.set(0, result.get(0) + 1);
                    });

            assertEquals(expectedResult, result.get(0));
        }

        @Test
        public void Can_Iterate_In_Order() {
            final List<String> expectedResult = Arrays.asList("a", "b", "c", "d");
            final List<String> result = new ArrayList<>();

            ChunkIterator.of(expectedResult)
                    .setSize(2)
                    .iterate(result::addAll);

            assertEquals(expectedResult, result);
        }

        @Test(expected = NullPointerException.class)
        public void Fails_On_Iterating_Null() {
            ChunkIterator.of(null)
                    .setSize(1)
                    .iterate(e -> {
                    });
        }
    }
}
