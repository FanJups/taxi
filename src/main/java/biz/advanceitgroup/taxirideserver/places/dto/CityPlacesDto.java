/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.places.dto;

import javax.persistence.Column;
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
public class CityPlacesDto {
   
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
    private String language;
}
