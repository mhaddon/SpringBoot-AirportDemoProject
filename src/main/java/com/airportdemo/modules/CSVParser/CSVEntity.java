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

package com.airportdemo.modules.CSVParser;

import lombok.Data;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Optional;

/**
 * This provides an abstraction over the apache commons CSVRecord
 * This version handles my use-case better by having methods for retrieving the values as different types, such as Double
 */
@Data
public class CSVEntity {
    /**
     * Factory method to create a new CSV entity
     *
     * @param record apache commons csv record
     * @return new csv entity
     */
    public static CSVEntity of(final CSVRecord record) {
        return new CSVEntity(record);
    }

    /**
     * Composition of apache commons CSVRecord
     */
    private final CSVRecord record;

    /**
     * @param record apache commons CSV record
     */
    private CSVEntity(final CSVRecord record) {
        this.record = record;
    }

    /**
     * Attempts to retrieve a keys value as a Double, if it fails it returns a null
     *
     * @param key key of the data
     * @return Double
     */
    public Double getDouble(final String key) {
        return getOptional(key)
                .filter(NumberUtils::isCreatable)
                .map(Double::parseDouble)
                .orElse(null);
    }

    /**
     * Retrieves a keys data as an optional
     *
     * @param key key of the data
     * @return Optional
     */
    public Optional<String> getOptional(final String key) {
        return Optional.ofNullable(get(key));
    }

    /**
     * Retrieves a keys data
     *
     * @param key key of the data
     * @return String
     */
    public String get(final String key) {
        return record.get(key);
    }

    /**
     * Attempts to retrieves a keys value as an Integer
     *
     * @param key key of the data
     * @return Integer
     */
    public Integer getInt(final String key) {
        return getOptional(key)
                .filter(NumberUtils::isCreatable)
                .map(Integer::parseInt)
                .orElse(null);
    }

    /**
     * Attempts to retrieve a keys value as a Long
     *
     * @param key key of the data
     * @return Long
     */
    public Long getLong(final String key) {
        return getOptional(key)
                .filter(NumberUtils::isCreatable)
                .map(Long::parseLong)
                .orElse(null);
    }
}
