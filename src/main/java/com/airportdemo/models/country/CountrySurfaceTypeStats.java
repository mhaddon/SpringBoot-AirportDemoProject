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
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class CountrySurfaceTypeStats {
    private final Country country;
    private final List<SurfaceTypeStats> surfaceTypeStats;

    @Builder
    private CountrySurfaceTypeStats(final Country country,
                                    final List<SurfaceTypeStats> surfaceTypeStats) {
        this.country = country;
        this.surfaceTypeStats = Optional.ofNullable(surfaceTypeStats).orElse(new ArrayList<>());
    }
}
