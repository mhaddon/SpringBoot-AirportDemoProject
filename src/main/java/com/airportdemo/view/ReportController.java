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

package com.airportdemo.view;

import com.airportdemo.models.country.CountryService;
import com.airportdemo.models.runway.RunwayService;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReportController {

    private final CountryService countryService;

    private final RunwayService runwayService;

    @Autowired
    public ReportController(final CountryService countryService,
                            final RunwayService runwayService) {
        this.countryService = countryService;
        this.runwayService = runwayService;
    }

    @RequestMapping(value = "/report/airport", method = RequestMethod.GET)
    public String reportAirport(final Model model) throws ParseException {
        model.addAttribute("highestAirports", countryService.topCountriesInAirportCount());
        model.addAttribute("lowestAirports", countryService.lowestCountriesInAirportCount());

        return "pages/airportstats";
    }

    @RequestMapping(value = "/report/surface", method = RequestMethod.GET)
    public String reportSurface(final Model model) throws ParseException {
        model.addAttribute("statistics", countryService.getAllSurfaceStatistics());

        return "pages/surfacestats";
    }

    @RequestMapping(value = "/report/runway", method = RequestMethod.GET)
    public String reportRunways(final Model model) throws ParseException {
        model.addAttribute("statistics", runwayService.topRunwayIdentifications());

        return "pages/runwaystats";
    }
}
