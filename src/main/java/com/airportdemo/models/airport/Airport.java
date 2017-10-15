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

package com.airportdemo.models.airport;

import com.airportdemo.models.core.BaseEntity;
import com.airportdemo.models.country.Country;
import com.airportdemo.models.runway.Runway;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "airports")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Indexed
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"runways", "country"})
@NoArgsConstructor(force = true)
public class Airport extends BaseEntity implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "country_id")
    @JsonManagedReference(value = "airportToCountry")
    @ContainedIn
    @JsonProperty("country")
    private Country country;

    @Column(name = "identity", length = 200)
    @Field
    private String identity;

    @Column(name = "type", length = 200)
    @Field
    private String type;

    @Column(name = "name", length = 200)
    @Field
    private String name;

    @Column(name = "latitude_deg", length = 200)
    @Field
    private Double latitude;

    @Column(name = "longitude_deg", length = 200)
    @Field
    private Double longitude;

    @Column(name = "elevation_ft", length = 200)
    @Field
    private Double elevation;

    @Column(name = "continent", length = 200)
    @Field
    private String continent;

    @Column(name = "iso_region", length = 200)
    @Field
    private String isoRegion;

    @Column(name = "municipality", length = 200)
    @Field
    private String municipality;

    @Column(name = "scheduled_service", length = 200)
    @Field
    private String scheduledService;

    @Column(name = "gps_code", length = 200)
    @Field
    private String gpsCode;

    @Column(name = "iata_code", length = 200)
    @Field
    private String iataCode;

    @Column(name = "local_code", length = 200)
    @Field
    private String localCode;

    @Column(name = "home_link", length = 200)
    @Field
    private String homeLink;

    @Column(name = "wikipedia_link", length = 200)
    @Field
    private String wikipediaLink;

    @Column(name = "keywords", length = 200)
    @Field
    private String keywords;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "airport")
    @JsonBackReference(value = "runwayToAirport")
    @ContainedIn
    @org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Runway> runways = new HashSet<>(0);

    @Builder
    private Airport(final Long id,
                    final Country country,
                    final String identity,
                    final String type,
                    final String name,
                    final Double latitude,
                    final Double longitude,
                    final Double elevation,
                    final String continent,
                    final String isoRegion,
                    final String municipality,
                    final String scheduledService,
                    final String gpsCode,
                    final String iataCode,
                    final String localCode,
                    final String homeLink,
                    final String wikipediaLink,
                    final String keywords,
                    final Set<Runway> runways) {
        super(id);

        this.identity = Optional.ofNullable(identity).orElse("");
        this.type = Optional.ofNullable(type).orElse("");
        this.name = Optional.ofNullable(name).orElse("");
        this.latitude = Optional.ofNullable(latitude).orElse(0.0);
        this.longitude = Optional.ofNullable(longitude).orElse(0.0);
        this.elevation = Optional.ofNullable(elevation).orElse(0.0);
        this.continent = Optional.ofNullable(continent).orElse("");
        this.isoRegion = Optional.ofNullable(isoRegion).orElse("");
        this.municipality = Optional.ofNullable(municipality).orElse("");
        this.scheduledService = Optional.ofNullable(scheduledService).orElse("");
        this.gpsCode = Optional.ofNullable(gpsCode).orElse("");
        this.iataCode = Optional.ofNullable(iataCode).orElse("");
        this.localCode = Optional.ofNullable(localCode).orElse("");
        this.homeLink = Optional.ofNullable(homeLink).orElse("");
        this.wikipediaLink = Optional.ofNullable(wikipediaLink).orElse("");
        this.keywords = Optional.ofNullable(keywords).orElse("");
        this.runways = Optional.ofNullable(runways).orElse(new HashSet<>());

        this.country = country;
    }

    public Optional<Country> getCountry() {
        return Optional.ofNullable(country);
    }
}
