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


import com.airportdemo.models.airport.Airport;
import com.airportdemo.models.core.BaseEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Optional;

@Entity
@Table(name = "runways")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Indexed
@Data
@EqualsAndHashCode(callSuper = true, exclude = {"airport"})
@NoArgsConstructor(force = true)
public class Runway extends BaseEntity implements Serializable {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "airport_id")
    @JsonManagedReference(value = "runwayToAirport")
//    @IndexedEmbedded
    @JsonProperty("airport")
    private Airport airport;

    @Column(name = "length_ft")
    @Field
    private Integer lengthFeet;

    @Column(name = "width_ft")
    @Field
    private Integer widthFeet;

    @Column(name = "surface")
    @Field
    private String surface;

    @Column(name = "lighted")
    @Field
    private Boolean lighted;

    @Column(name = "closed")
    @Field
    private Boolean closed;

    @Column(name = "le_ident")
    @Field
    private String identification;

    @Column(name = "le_longitude_deg")
    @Field
    private Double longitude;

    @Column(name = "le_latitude_deg")
    @Field
    private Double latitude;

    @Column(name = "le_elevation_ft")
    @Field
    private Double elevationFeet;

    @Column(name = "le_heading_degT")
    @Field
    private Double heading;

    @Column(name = "le_displaced_threshold_ft")
    @Field
    private Double displacedThresholdFeet;

    @Column(name = "he_ident")
    @Field
    private String identificationHe;

    @Column(name = "he_latitude_deg")
    @Field
    private Double latitudeHe;

    @Column(name = "he_longitude_deg")
    @Field
    private Double longitudeHe;

    @Column(name = "he_elevation_ft")
    @Field
    private Double elevationFeetHe;

    @Column(name = "he_heading_degT")
    @Field
    private Double headingHe;

    @Column(name = "he_displaced_threshold_ft")
    @Field
    private Double displacedThresholdFeetHe;

    @Builder
    private Runway(final Long id,
                   final Airport airport,
                   final Integer lengthFeet,
                   final Integer widthFeet,
                   final String surface,
                   final Boolean lighted,
                   final Boolean closed,
                   final String identification,
                   final Double longitude,
                   final Double latitude,
                   final Double elevationFeet,
                   final Double heading,
                   final Double displacedThresholdFeet,
                   final String identificationHe,
                   final Double latitudeHe,
                   final Double longitudeHe,
                   final Double elevationFeetHe,
                   final Double headingHe,
                   final Double displacedThresholdFeetHe) {
        super(id);
        this.lengthFeet = Optional.ofNullable(lengthFeet).orElse(0);
        this.widthFeet = Optional.ofNullable(widthFeet).orElse(0);
        this.surface = Optional.ofNullable(surface).orElse("");
        this.lighted = Optional.ofNullable(lighted).orElse(false);
        this.closed = Optional.ofNullable(closed).orElse(false);
        this.identification = Optional.ofNullable(identification).orElse("");
        this.longitude = Optional.ofNullable(longitude).orElse(0.0);
        this.latitude = Optional.ofNullable(latitude).orElse(0.0);
        this.elevationFeet = Optional.ofNullable(elevationFeet).orElse(0.0);
        this.heading = Optional.ofNullable(heading).orElse(0.0);
        this.displacedThresholdFeet = Optional.ofNullable(displacedThresholdFeet).orElse(0.0);
        this.identificationHe = Optional.ofNullable(identificationHe).orElse("");
        this.latitudeHe = Optional.ofNullable(latitudeHe).orElse(0.0);
        this.longitudeHe = Optional.ofNullable(longitudeHe).orElse(0.0);
        this.elevationFeetHe = Optional.ofNullable(elevationFeetHe).orElse(0.0);
        this.headingHe = Optional.ofNullable(headingHe).orElse(0.0);
        this.displacedThresholdFeetHe = Optional.ofNullable(displacedThresholdFeetHe).orElse(0.0);

        this.airport = airport;
    }

    public Optional<Airport> getAirport() {
        return Optional.ofNullable(airport);
    }
}
