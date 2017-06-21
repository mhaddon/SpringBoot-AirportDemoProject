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
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Optional;

@Data
public class CSVEntity {
    public static CSVEntity of(final CSVRecord record) {
        return new CSVEntity(record);
    }

    private final CSVRecord record;

    private CSVEntity(final CSVRecord record) {
        this.record = record;
    }

    public String get(final String key) {
        return record.get(key);
    }

    public Optional<String> getOptional(final String key) {
        return Optional.ofNullable(get(key));
    }

    public Double getDouble(final String key) {
        return getOptional(key)
                .filter(NumberUtils::isCreatable)
                .map(Double::parseDouble)
                .orElse(null);
    }

    public Integer getInt(final String key) {
        return getOptional(key)
                .filter(NumberUtils::isCreatable)
                .map(Integer::parseInt)
                .orElse(null);
    }

    public Long getLong(final String key) {
        return getOptional(key)
                .filter(NumberUtils::isCreatable)
                .map(Long::parseLong)
                .orElse(null);
    }
}
