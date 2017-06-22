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

import com.airportdemo.modules.SearchAnalysers;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.lucene.analysis.charfilter.HTMLStripCharFilterFactory;
import org.apache.lucene.analysis.charfilter.MappingCharFilterFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilterFactory;
import org.apache.lucene.analysis.phonetic.PhoneticFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.apache.lucene.analysis.synonym.SynonymFilterFactory;
import org.hibernate.search.annotations.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Optional;

@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = false)
@AnalyzerDef(name = SearchAnalysers.ENGLISH_WORD_ANALYSER,
        charFilters = {
                @CharFilterDef(factory = MappingCharFilterFactory.class, params = {
                        @Parameter(name = "mapping", value = "analyser/mapping-chars.properties")
                })
        },
        tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),
        filters = {
                @TokenFilterDef(factory = StandardFilterFactory.class),
                @TokenFilterDef(factory = LowerCaseFilterFactory.class),
                @TokenFilterDef(factory = SnowballPorterFilterFactory.class, params = {
                        @org.hibernate.search.annotations.Parameter(name = "language", value = "English")
                }),
                @TokenFilterDef(factory = ASCIIFoldingFilterFactory.class),
                @TokenFilterDef(factory = PhoneticFilterFactory.class, params = {
                        @org.hibernate.search.annotations.Parameter(name = "encoder", value = "DoubleMetaphone")
                }),
                @TokenFilterDef(factory = StopFilterFactory.class, params = {
                        @org.hibernate.search.annotations.Parameter(name = "words", value = "analyser/stoplist.properties"),
                        @org.hibernate.search.annotations.Parameter(name = "ignoreCase", value = "true")
                })
        })
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
