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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RunwayServiceImpl implements RunwayService {
    private final RunwayRepository runwayRepository;

    @Autowired
    public RunwayServiceImpl(final RunwayRepository runwayRepository) {
        this.runwayRepository = runwayRepository;
    }

    @Override
    public List<RunwayIdentificationStats> topRunwayIdentifications() {
        return runwayRepository.topRunwayIdentifications().stream()
                .map(Object[].class::cast)
                .map(result -> RunwayIdentificationStats.builder()
                        .identification(result[0].toString())
                        .count(Integer.parseInt(result[1].toString()))
                        .build())
                .collect(Collectors.toList());
    }
}
