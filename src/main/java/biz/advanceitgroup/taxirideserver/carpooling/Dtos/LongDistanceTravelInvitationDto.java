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
@AllArgsConstructor
@NoArgsConstructor
public class LongDistanceTravelInvitationDto {
    private long invitationId;
    private long tripId;
    private long riderId;
    private long driverId;
    private Date invitationDate;
    private Date departurePeriodStartDate;
    private Date departurePeriodEndDate;
    private String startCity;
    private String departure;
    private String endCity;
    private String arrival;
    private int SeatCount;
    private long riderBookingCount;
}
