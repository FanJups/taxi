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
@NoArgsConstructor
@AllArgsConstructor
public class LongDistanceTripListDto {
    private long travelId;
    private long driverId;
    private Date postDate;
    private Date departureDate;
    private String departure;
    private double seatCost;
    private int availableSeatCount;
    private int reservationCount;
    private String driverConditions;
}
