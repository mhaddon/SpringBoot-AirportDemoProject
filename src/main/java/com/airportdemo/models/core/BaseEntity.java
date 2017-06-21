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

package com.airportdemo.models.core;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Optional;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)
//@AnalyzerDef(name = SearchAnalysers.ENGLISH_WORD_ANALYSER,
//        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
//        filters = {
//                @TokenFilterDef(factory = StandardFilterFactory.class),
//                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
//                @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
//                        @org.hibernate.search.annotations.Parameter(name = "language", value = "English")
//                }),
//                @TokenFilterDef(factory = SynonymFilterFactory.class, params = {
//                        @org.hibernate.search.annotations.Parameter(name = "ignoreCase", value = "true"),
//                        @org.hibernate.search.annotations.Parameter(name = "synonyms", value = "properties/analyser/synonyms.properties")
//                }),
//                @TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
//                @TokenFilterDef(factory = PhoneticFilterFactory.class, params = {
//                        @org.hibernate.search.annotations.Parameter(name = "encoder", value = "DoubleMetaphone")
//                }),
//                @TokenFilterDef(factory = StopFilterFactory.class, params = {
//                        @org.hibernate.search.annotations.Parameter(name = "words", value = "properties/analyser/stoplist.properties"),
//                        @org.hibernate.search.annotations.Parameter(name = "ignoreCase", value = "true")
//                })
//        })
public abstract class BaseEntity implements Serializable {
    /**
     * The ID of this entity
     */
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Getter
    private final Long id;

    /**
     * Instantiates a new Base entity with a random UUID
     */
    protected BaseEntity() {
        this(null);
    }

    /**
     * Instantiates a new Base entity.
     *
     * @param id the id
     */
    protected BaseEntity(final Long id) {
        this.id = Optional.ofNullable(id).orElse(null);
    }
}
