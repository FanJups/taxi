/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.carpooling.Dtos;

import biz.advanceitgroup.taxirideserver.courses.entities.RiderNotation;
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
public class DriverInfoForRouteRetour {
    
    private String driverFirstname;
    private String driverLastName;
    private String profession;
    private String profilePicture;
    private long totalTripCount;
    private long tripCountOnThisRoute;
    private long appRate;
    private List<RiderNotation> riderNotation;
    private long lastCancelledTripsCount;
    private Date lastMissedAppointments;

    public DriverInfoForRouteRetour(String driverFirstname, String driverLastName, String profession, String profilePicture, long totalTripCount, long tripCountOnThisRoute, long lastCancelledTripsCount, Date lastMissedAppointments) {
        this.driverFirstname = driverFirstname;
        this.driverLastName = driverLastName;
        this.profession = profession;
        this.profilePicture = profilePicture;
        this.totalTripCount = totalTripCount;
        this.tripCountOnThisRoute = tripCountOnThisRoute;
        this.lastCancelledTripsCount = lastCancelledTripsCount;
        this.lastMissedAppointments = lastMissedAppointments;
    }
    
    
}
