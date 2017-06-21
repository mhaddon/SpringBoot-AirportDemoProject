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

package com.airportdemo.models.country;

import com.airportdemo.components.CSVParser.CSVParser;
import com.airportdemo.modules.CSVEntity;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CountryCSVParser extends CSVParser<Country> {
    private final CountryRepository countryRepository;

    @Autowired
    public CountryCSVParser(final CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public Country process(final CSVEntity record) {
        return Country.builder()
                .code(record.get("code"))
                .name(record.get("name"))
                .continent(record.get("continent"))
                .id(record.getLong("id"))
                .keywords(record.get("keywords"))
                .wikipediaLink(record.get("wikipedia_link"))
                .build();
    }

    @Override
    protected void save(final Country item) {
        countryRepository.saveAndFlush(item);
    }
}
