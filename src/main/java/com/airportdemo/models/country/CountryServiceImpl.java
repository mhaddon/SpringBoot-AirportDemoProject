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

import com.airportdemo.models.runway.SurfaceTypeStats;
import com.airportdemo.modules.Query.PhraseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    private final PhraseQuery phraseQuery;

    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(final PhraseQuery phraseQuery,
                              final CountryRepository countryRepository) {
        this.phraseQuery = phraseQuery;
        this.countryRepository = countryRepository;
    }

    @Override
    public Optional<Country> queryCountry(final String queryString) {
        @SuppressWarnings("unchecked") final List<Country> countries = phraseQuery.parse(Country.class, queryString, new String[]{
                "code", "code.ngram", "code.edge", "name", "name.ngram", "name.edge"
        })
                .setMaxResults(1)
                .getResultList();

        return countries.isEmpty() ? Optional.empty() : Optional.ofNullable(countries.get(0));
    }

    @Override
    public List<Country> topCountriesInAirportCount() {
        return countryRepository.topCountriesInAirportCount();
    }

    @Override
    public List<Country> lowestCountriesInAirportCount() {
        return countryRepository.lowestCountriesInAirportCount();
    }

    @Override
    public List<SurfaceTypeStats> getSurfaceStatistics(final Country country) {
        return countryRepository.typeOfRunways(country.getId()).stream()
                .map(Object[].class::cast)
                .map(result -> SurfaceTypeStats.builder()
                        .surface(result[0].toString())
                        .count(Integer.parseInt(result[1].toString()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<CountrySurfaceTypeStats> getAllSurfaceStatistics() {
        return countryRepository.findAll().stream()
                .map(country -> CountrySurfaceTypeStats.builder()
                        .country(country)
                        .surfaceTypeStats(getSurfaceStatistics(country))
                        .build())
                .collect(Collectors.toList());
    }
}
