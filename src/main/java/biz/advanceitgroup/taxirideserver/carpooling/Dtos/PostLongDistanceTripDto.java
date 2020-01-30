/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.carpooling.Dtos;

import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author daniel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostLongDistanceTripDto {
    private String emailOrPhone;
    private Date departureDate;
    private String startCity;
    private String departure;
    private String endCity;
    private String arrival;
    private Date postDate;
    private String country;
    private int numberOfSeat;
    private long tripTimeOut;
    private String language;
    private double departureLongitude;
    private double departureLatitude;
    private double arrivalLongitude;
    private double arrivalLatitude;
    private List<String> travelCondition;
    private boolean isCostNegotiable;
}
