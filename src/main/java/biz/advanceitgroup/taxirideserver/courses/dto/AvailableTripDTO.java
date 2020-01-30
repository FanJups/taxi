/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.courses.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author daniel
 */
@Data
@AllArgsConstructor
public class AvailableTripDTO {
    @NotNull
    private String name;
    @NotNull
    private double cost;
    @NotNull
    private double distance;
    @NotNull
    private String departure;
    @NotNull
    private String arrival;
    private double startPointLatitude;
    private double startPointLongitude;
    private String profileImageUrl;
    private long requestId;
    private long tripId;
    private double notation;
    private String commentaire;
    private boolean isInTheRange;

    public AvailableTripDTO(String name, double cost, double distance, String departure, String arrival, String profileImageUrl, long requestId, long tripId,boolean isInTheRange) {
        this.name = name;
        this.cost = cost;
        this.distance = distance;
        this.departure = departure;
        this.arrival = arrival;
        this.profileImageUrl = profileImageUrl;
        this.requestId = requestId;
        this.tripId = tripId;
        this.isInTheRange=isInTheRange;
    }
    public AvailableTripDTO(String name, double cost, double distance, String departure, String arrival, String profileImageUrl, long requestId, long tripId,boolean isInTheRange,double startLatitude,double startLongitude) {
        this.name = name;
        this.cost = cost;
        this.distance = distance;
        this.departure = departure;
        this.arrival = arrival;
        this.profileImageUrl = profileImageUrl;
        this.requestId = requestId;
        this.tripId = tripId;
        this.isInTheRange=isInTheRange;
        this.startPointLatitude=startLatitude;
        this.startPointLongitude=startLongitude;
    }
    
}
