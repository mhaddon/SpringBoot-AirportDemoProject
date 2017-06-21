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

package com.airportdemo.models.airport;

import com.airportdemo.components.CSVParser.CSVParser;
import com.airportdemo.models.country.Country;
import com.airportdemo.models.country.CountryRepository;
import com.airportdemo.modules.CSVEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AirportCSVParser extends CSVParser<Airport> {
    private final AirportRepository airportRepository;

    private final CountryRepository countryRepository;

    @Autowired
    public AirportCSVParser(final AirportRepository airportRepository,
                            final CountryRepository countryRepository) {
        this.airportRepository = airportRepository;
        this.countryRepository = countryRepository;
    }

    @Override
    public Airport process(final CSVEntity record) {
        return Airport.builder()
                .identity(record.get("ident"))
                .type(record.get("type"))
                .name(record.get("name"))
                .latitude(record.getDouble("latitude_deg"))
                .longitude(record.getDouble("longitude_deg"))
                .elevation(record.getDouble("elevation_ft"))
                .continent(record.get("continent"))
                .country(findCountry(record.get("iso_country")).orElse(null))
                .isoRegion(record.get("iso_region"))
                .municipality(record.get("municipality"))
                .scheduledService(record.get("scheduled_service"))
                .gpsCode(record.get("gps_code"))
                .iataCode(record.get("iata_code"))
                .localCode(record.get("local_code"))
                .homeLink(record.get("home_link"))
                .wikipediaLink(record.get("wikipedia_link"))
                .id(record.getLong("id"))
                .keywords(record.get("keywords"))
                .build();
    }

    @Override
    protected void save(final Airport item) {
        airportRepository.saveAndFlush(item);
    }

    private Optional<Country> findCountry(final String countryIsoCode) {
        return countryRepository.findOneByCode(countryIsoCode);
    }
}
