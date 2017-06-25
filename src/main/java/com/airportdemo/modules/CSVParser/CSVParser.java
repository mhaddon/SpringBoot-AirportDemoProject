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

import com.airportdemo.models.core.BaseEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
abstract public class CSVParser<T extends BaseEntity> implements CSVParserStrategy {
    final public void parse(final CSVRecord record) {
        if (record != null && checkValidity(record)) {
            Optional.ofNullable(process(CSVEntity.of(record)))
                    .ifPresent(this::save);
        } else {
            logger.error("error reading {}", record);
        }
    }

    private Boolean checkValidity(final CSVRecord record) {
        return record.isConsistent();
    }

    abstract public T process(final CSVEntity record);

    final public void parseAll(final List<CSVRecord> records) {
        save(records.stream()
                .filter(this::checkValidity)
                .map(this::process)
                .collect(Collectors.toList()));
    }

    abstract protected void save(final List<T> item);

    private T process(final CSVRecord record) {
        return process(CSVEntity.of(record));
    }

    abstract protected void save(final T item);
}
