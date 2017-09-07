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

import com.airportdemo.models.core.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RunwayRepository extends BaseRepository<Runway> {
    @Query(
            value = "SELECT `r`.`le_ident`, COUNT(`r`.`le_ident`) as `count` FROM `airports` as `a` " +
                    "LEFT JOIN ( " +
                    "    SELECT `runways`.`airport_id`, `runways`.`le_ident` FROM `runways` " +
                    "    GROUP BY  `runways`.`le_ident`, `runways`.`airport_id` " +
                    ") as `r` ON `a`.`id` = `r`.`airport_id` " +
                    "WHERE `r`.`le_ident` IS NOT NULL " +
                    "GROUP BY `r`.`le_ident` " +
                    "ORDER BY `count` DESC, `le_ident` ASC " +
                    "LIMIT 10",
            nativeQuery = true
    )
    List<Object> topRunwayIdentifications();
}
