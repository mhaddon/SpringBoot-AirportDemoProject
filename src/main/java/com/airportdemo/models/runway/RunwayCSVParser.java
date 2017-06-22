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

package com.airportdemo.models.runway;

import com.airportdemo.components.CSVParser.CSVParser;
import com.airportdemo.models.airport.Airport;
import com.airportdemo.models.airport.AirportRepository;
import com.airportdemo.modules.CSVEntity;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RunwayCSVParser extends CSVParser<Runway> {
    private final RunwayRepository runwayRepository;

    private final AirportRepository airportRepository;

    @Autowired
    public RunwayCSVParser(final RunwayRepository runwayRepository,
                           final AirportRepository airportRepository) {
        this.runwayRepository = runwayRepository;
        this.airportRepository = airportRepository;
    }

    @Override
    public Runway process(final CSVEntity record) {
        return Runway.builder()
                .airport(findAirport(record.getLong("airport_ref")).orElse(null))
                .airportIdentity(record.get("airport_ident"))
                .lengthFeet(record.getInt("length_ft"))
                .widthFeet(record.getInt("width_ft"))
                .surface(record.get("surface"))
                .lighted(record.get("lighted").equals("1"))
                .closed(record.get("closed").equals("1"))
                .identification(record.get("le_ident"))
                .latitude(record.getDouble("le_latitude_deg"))
                .longitude(record.getDouble("le_longitude_deg"))
                .elevationFeet(record.getDouble("le_elevation_ft"))
                .heading(record.getDouble("le_heading_degT"))
                .displacedThresholdFeet(record.getDouble("le_displaced_threshold_ft"))
                .identificationHe(record.get("he_ident"))
                .latitudeHe(record.getDouble("he_latitude_deg"))
                .longitudeHe(record.getDouble("surface"))
                .elevationFeetHe(record.getDouble("surface"))
                .headingHe(record.getDouble("surface"))
                .displacedThresholdFeetHe(record.getDouble("surface"))
                .id(record.getLong("id"))
                .build();
    }

    @Override
    protected void save(final Runway item) {
        runwayRepository.save(item);
    }

    private Optional<Airport> findAirport(final Long referenceId) {
        return airportRepository.findOneById(referenceId);
    }
}
