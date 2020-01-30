/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.carpooling.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author daniel
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TravelInvitation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long travelId;
    private long riderId;
    private double departureLongitude;
    private double departureLatitude;
    private String departureAddress;
     @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date departureDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date invitationDate;
    private double seatcost;
    private int invitationStatus;
}
