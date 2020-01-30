/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.places.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author daniel
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class CityPlaces {
    @Id
    @Column(name = "PLACE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeId;
    @Column(length = 3)
    @NotNull(message = "Country code can not be null")
    private String countryCode;
    @Column(length = 100)
    @NotNull(message = "city name can not be null")
    private String city;
    @Column(length = 100)
    @NotNull(message = "Place Name can not be null")
    private String name;
    @Column(length = 250)
    @NotNull(message = "address can not be null")
    private String address;
    @NotNull(message = "the longitude can not be null")
    private double longitude;
    @NotNull(message = "the latitude can not be null")
    private double latitude;
    @NotNull(message = "the date saved can not be null")
    private Date dateEnreg;

    public CityPlaces(String countryCode, String city, String name, String address, double longitude, double latitude, Date dateEnreg) {
        this.countryCode = countryCode;
        this.city = city;
        this.name = name;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.dateEnreg = dateEnreg;
    }
    
}
