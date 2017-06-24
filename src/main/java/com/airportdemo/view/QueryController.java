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

import com.airportdemo.models.country.Country;
import com.airportdemo.models.country.CountryService;
import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class QueryController {

    private final CountryService countryService;

    @Autowired
    public QueryController(final CountryService countryService) {
        this.countryService = countryService;
    }

    @RequestMapping(value = "/query/", params = {"q"}, method = RequestMethod.GET)
    public String queryCountryEmpty(@RequestParam("q") final String queryText,
                                    final Model model) throws ParseException {
        return queryCountry(queryText, model);
    }

    @RequestMapping(value = "/query/{query}", method = RequestMethod.GET)
    public String queryCountry(@PathVariable("query") final String queryString, final Model model) throws ParseException {
        final Optional<Country> countryOptional = countryService.queryCountry(queryString);

        countryOptional.ifPresent(country -> {
            model.addAttribute("country", country);
            model.addAttribute("searchTerm", queryString);
        });

        return "pages/query";
    }

    @RequestMapping(value = "/query/", method = RequestMethod.GET)
    public String queryCountryEmpty(final Model model) throws ParseException {
        return queryCountry(" ", model);
    }
}
