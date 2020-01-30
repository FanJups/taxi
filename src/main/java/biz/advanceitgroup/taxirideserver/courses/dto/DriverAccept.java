/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.courses.dto;

import biz.advanceitgroup.taxirideserver.courses.entities.DriverNotation;
import java.util.ListIterator;
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
public class DriverAccept {
    private String firstName;
    private String lastName;
    private String emailOrPhone;
    private String photo;
    private double longitude;
    private double latitude;
    private Boolean isVerified;
    private ListIterator<DriverNotation> commentaires;
    private double waitingTime;
    private String carModel;
     private String matriculationNumber;  
    private String driversLicence;
    private double distanceBetweenThem;
    private double distance;
    private long rideId;

    public DriverAccept(String emailOrPhone, long rideId) {
        this.emailOrPhone = emailOrPhone;
        this.rideId = rideId;
    }
    
    
}
