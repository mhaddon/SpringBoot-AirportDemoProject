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

import com.airportdemo.modules.PhraseQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CountryServiceImpl implements CountryService {

    private final PhraseQuery phraseQuery;

    @Autowired
    public CountryServiceImpl(final PhraseQuery phraseQuery) {
        this.phraseQuery = phraseQuery;
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
}
