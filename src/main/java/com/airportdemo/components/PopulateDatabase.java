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

package com.airportdemo.components;


import com.airportdemo.models.airport.AirportCSVParser;
import com.airportdemo.models.country.CountryCSVParser;
import com.airportdemo.models.runway.RunwayCSVParser;
import com.airportdemo.modules.ChunkIteratorAsync;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

/**
 * On application start this block of code will build the lucene search index
 */
@Component
@Slf4j
public class PopulateDatabase implements ApplicationListener<ApplicationReadyEvent>, Ordered {
    private final Resource countryCsv;
    private final Resource airportCsv;
    private final Resource runwayCsv;

    private final CountryCSVParser countryCSVParser;
    private final AirportCSVParser airportCSVParser;
    private final RunwayCSVParser runwayCSVParser;

    @Autowired
    public PopulateDatabase(final CountryCSVParser countryCSVParser,
                            final AirportCSVParser airportCSVParser,
                            final RunwayCSVParser runwayCSVParser,
                            @Value(value = "classpath:static/countries.csv") final Resource countryCsv,
                            @Value(value = "classpath:static/airports.csv") final Resource airportCsv,
                            @Value(value = "classpath:static/runways.csv") final Resource runwayCsv) {
        this.countryCSVParser = countryCSVParser;
        this.airportCSVParser = airportCSVParser;
        this.runwayCSVParser = runwayCSVParser;
        this.countryCsv = countryCsv;
        this.airportCsv = airportCsv;
        this.runwayCsv = runwayCsv;
    }

    /**
     * Build lucene search index
     *
     * @param event application ready event
     */
    @Override
    public final void onApplicationEvent(final ApplicationReadyEvent event) {
        final Date timeStarted = Calendar.getInstance().getTime();
        logger.info("[PopulateDatabase] Populating database, this may take a minute.");
        logger.info("[PopulateDatabase] Importing Countries...");
        readFileRecords(countryCsv, countryCSVParser::parseAll);
        logger.info("[PopulateDatabase] Imported Countries");
        logger.info("[PopulateDatabase] Importing Airports...");
        readFileRecords(airportCsv, airportCSVParser::parseAll);
        logger.info("[PopulateDatabase] Imported Airports");
        logger.info("[PopulateDatabase] Importing Runways...");
        readFileRecords(runwayCsv, runwayCSVParser::parseAll);
        logger.info("[PopulateDatabase] Imported Runways");
        final Date timeEnded = Calendar.getInstance().getTime();
        logger.info("[PopulateDatabase] Populating database took {}ms", timeEnded.getTime() - timeStarted.getTime());
    }


    private void readFileRecords(final Resource fileResource, final Consumer<List<CSVRecord>> consumer) {
        try {
            final Reader reader = new InputStreamReader(fileResource.getInputStream(), "UTF-8");
            final CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader());
            ChunkIteratorAsync.of(parser)
                    .setSize(1000)
                    .iterate(consumer);
        } catch (final IOException e) {
            logger.warn("[PopulateDatabase] [readFileRecords] Failure to read csv records", e);
        }
    }

    @Override
    public final int getOrder() {
        return LOWEST_PRECEDENCE - 1;
    }
}