/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.carpooling.Dtos;

import java.util.Date;
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
public class BookTravelDto {
    private String emailOrPhone;
    private long travelId;
    private Date postDate;
    private Date departurePeriodStartDate;
    private Date departurePeriodEndDate;
    private String startCity;
    private String endCity;
    private int numberOfPlacesRequired;
    private double seatCost;
    private String language;
    private boolean isCostNegotiable;
    
}
