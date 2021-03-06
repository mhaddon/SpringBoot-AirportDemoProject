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

import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
public class RunwayIdentificationStats {
    private String identification;
    private Integer count;

    @Builder
    private RunwayIdentificationStats(final String identification,
                                      final Integer count) {
        this.identification = Optional.ofNullable(identification).orElse("unknown");
        this.count = Optional.ofNullable(count).orElse(0);
    }
}
