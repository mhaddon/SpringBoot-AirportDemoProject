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

import com.airportdemo.models.airport.Airport;
import com.airportdemo.models.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "countries")
@Cacheable
@Indexed
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(force = true)
public class Country extends BaseEntity implements Serializable {
    @Column(name = "code")
    @Field
    private String code;

    @Column(name = "name")
    @Field
    private String name;

    @Column(name = "continent")
    @Field
    private String continent;

    @Column(name = "wikipedia_link")
    @Field
    private String wikipediaLink;

    @Column(name = "keywords")
    @Field
    private String keywords;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    @JsonBackReference(value = "airportToCountry")
    @ContainedIn
//    @Field(bridge = @FieldBridge(impl = CountryBridge.class),
//            analyzer = @Analyzer(definition = SearchAnalysers.ENGLISH_WORD_ANALYSER))
    private Set<Airport> airports = new HashSet<>(0);

    @Builder
    private Country(final Long id,
                    final String code,
                    final String name,
                    final String continent,
                    final String wikipediaLink,
                    final String keywords,
                    final Set<Airport> airports) {
        super(id);

        this.code = Optional.ofNullable(code).orElse("");
        this.name = Optional.ofNullable(name).orElse("");
        this.continent = Optional.ofNullable(continent).orElse("");
        this.wikipediaLink = Optional.ofNullable(wikipediaLink).orElse("");
        this.keywords = Optional.ofNullable(keywords).orElse("");
        this.airports = Optional.ofNullable(airports).orElse(new HashSet<>());
    }
}
