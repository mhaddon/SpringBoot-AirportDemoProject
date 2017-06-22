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
import com.airportdemo.modules.SearchAnalysers;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Controller
public class QueryController {

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping("/query/{query}")
    @ResponseBody
    @Transactional(readOnly = true)
    public synchronized List queryCountry(@PathVariable("query") final String queryString) throws ParseException {
        final FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        final SearchFactory searchFactory = fullTextEntityManager.getSearchFactory();

        final QueryBuilder titleQB = searchFactory
                .buildQueryBuilder().forEntity(Country.class).get();

        Query query = titleQB.phrase()
                .withSlop(3)
//                .onField(SearchAnalysers.NGRAM_ANALYSER)
//                .andField(SearchAnalysers.EDGE_ANALYSER)
                .onField("code")
                .andField("code.ngram")
                .andField("code.edge")
                .andField("name")
                .andField("name.ngram")
                .andField("name.edge")
                .sentence(queryString.toLowerCase())
                .createQuery();

        FullTextQuery fullTextQuery = fullTextEntityManager.createFullTextQuery(query, Country.class);

        fullTextQuery.setMaxResults(20);

        @SuppressWarnings("unchecked")
        List<Country> results = fullTextQuery.getResultList();

        return results;

        //
        //        final QueryParser parser = new MultiFieldQueryParser(
        //                new String[]{"code", "name"},
        //                searchFactory.getAnalyzer(SearchAnalysers.ENGLISH_WORD_ANALYSER)
        //        );
        //
        //        final Query query = parser.parse(queryString);
        //        final FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query, Country.class);
        ////        jpaQuery.setProjection(ProjectionConstants.SCORE, ProjectionConstants.EXPLANATION, ProjectionConstants.THIS);
        //
        //        return (List<Object[]>) jpaQuery.getResultList();
    }
}
