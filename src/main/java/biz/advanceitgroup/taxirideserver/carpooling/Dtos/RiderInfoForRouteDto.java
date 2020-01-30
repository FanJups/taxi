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
public class RiderInfoForRouteDto {
    private String firstName;
    private String lastName;
    private String profession;
    private String profilePictureLink;
    private long totalTripCount;
    private long totalTripCountOnThisPath;
    private int appRate;
    private String driverNotation;
    private long lastCancelledTripCount;
    private Date lastMissedAppointments;
}
