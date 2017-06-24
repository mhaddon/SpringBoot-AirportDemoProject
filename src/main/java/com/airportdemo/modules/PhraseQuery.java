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

package com.airportdemo.modules;

import org.apache.lucene.search.Query;
import org.hibernate.search.SearchFactory;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.PhraseMatchingContext;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class PhraseQuery {

    @PersistenceContext
    private EntityManager entityManager;

    public FullTextQuery parse(final Class clazz, final String queryString, final String[] fields) {
        final Query query = phraseQuery(clazz, parseQueryString(queryString), fields);

        return getFullTextEntityManager()
                .createFullTextQuery(query, clazz);
    }

    private Query phraseQuery(final Class clazz, final String queryString, final String[] fields) {
        final QueryBuilder titleQB = getSearchFactory()
                .buildQueryBuilder().forEntity(clazz).get();

        PhraseMatchingContext matchingContext = titleQB.phrase()
                .withSlop(3)
                .onField(fields[0]);

        for (Integer i = 1; i < fields.length; i++) {
            matchingContext = matchingContext.andField(fields[i]);
        }

        return matchingContext.sentence(queryString.toLowerCase()).createQuery();
    }

    private String parseQueryString(final String queryString) {
        return queryString != null && !queryString.isEmpty() ? queryString : " ";
    }

    private FullTextEntityManager getFullTextEntityManager() {
        return Search.getFullTextEntityManager(entityManager);
    }

    private SearchFactory getSearchFactory() {
        return getFullTextEntityManager().getSearchFactory();
    }
}
