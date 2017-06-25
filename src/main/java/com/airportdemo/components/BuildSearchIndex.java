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

package com.airportdemo.components;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * On application start this block of code will build the lucene search index
 */
@Component
@Slf4j
public class BuildSearchIndex implements ApplicationListener<ApplicationReadyEvent>, Ordered {
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Build lucene search index
     *
     * @param event application ready event
     */
    @Override
    public final void onApplicationEvent(final ApplicationReadyEvent event) {
        try {
            final FullTextEntityManager fullTextEntityManager =
                    Search.getFullTextEntityManager(entityManager);
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (final InterruptedException e) {
            logger.info("[BuildSearchIndex] [onApplicationEvent] Build Search Index Failure", e);
        }
    }

    /**
     * Hibernate Search indexing must be the last thing to happen on startup
     * @return integer
     */
    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }
}