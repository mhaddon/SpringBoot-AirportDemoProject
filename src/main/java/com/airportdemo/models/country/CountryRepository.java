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

import com.airportdemo.models.core.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedNativeQuery;
import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends BaseRepository<Country> {
    Optional<Country> findOneByCode(final String isoCode);

    @Query(
            value = "SELECT * FROM \"countries\" as \"c\"" +
                    "LEFT JOIN (" +
                    "    SELECT IFNULL(COUNT(*), 0) as \"airport_count\", \"airports\".\"country_id\" FROM \"airports\" " +
                    "    GROUP BY \"airports\".\"country_id\"" +
                    ") as \"a\" ON \"c\".\"id\" = \"a\".\"country_id\" " +
                    "ORDER BY \"a\".\"airport_count\" DESC, \"c\".\"name\" ASC " +
                    "LIMIT 10",
            nativeQuery = true
    )
    List<Country> topCountriesInAirportCount();

    @Query(
            value = "SELECT * FROM \"countries\" as \"c\"" +
                    "LEFT JOIN (" +
                    "    SELECT IFNULL(COUNT(*), 0) as \"airport_count\", \"airports\".\"country_id\" FROM \"airports\" " +
                    "    GROUP BY \"airports\".\"country_id\"" +
                    ") as \"a\" ON \"c\".\"id\" = \"a\".\"country_id\" " +
                    "ORDER BY \"a\".\"airport_count\" ASC, \"c\".\"name\" ASC " +
                    "LIMIT 10",
            nativeQuery = true
    )
    List<Country> lowestCountriesInAirportCount();

    @Query(
            value = "SELECT \"r\".\"surface\" as \"surface\", COUNT(\"r\".\"surface\") as \"count\" FROM \"airports\" as \"a\"\n" +
                    "LEFT JOIN (\n" +
                    "    SELECT \"runways\".\"airport_id\", \"runways\".\"surface\" FROM \"runways\" \n" +
                    "    GROUP BY  \"runways\".\"surface\", \"runways\".\"airport_id\"\n" +
                    ") as \"r\" ON \"a\".\"id\" = \"r\".\"airport_id\"\n" +
                    "WHERE \"country_id\"=:country_id AND \"r\".\"surface\" IS NOT NULL\n" +
                    "GROUP BY \"surface\"\n" +
                    "ORDER BY \"count\" DESC",
            nativeQuery = true
    )
    List<Object> typeOfRunways(@Param("country_id") final Long countryId);
}
