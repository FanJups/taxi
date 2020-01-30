/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.carpooling.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import javax.persistence.Column;
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
public class Travel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long travelPathId;
    private long driverId;
    private double departureLongitude;
    private double departureLatitude;
    @Column(length = 250)
    private String departureAddress;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date departureDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date effectiveDepartureDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date publicationDate;
    private double arrivalLongitude;
    private double arrivalLatitude;
    @Column(length = 255)
    private String arrivalAddress;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date arrivalDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date effectiveArrivalDate;
    private int availableSeats;
    private int bookedSeats;
    private double seatCost;
    private int travelStatus;
    @Column(length = 100)
    private String driverFristName;
    @Column(length = 100)
    private String driverLastName;
    @Column(length = 1)
    private String genderCode;
    @Column(length = 100)
    private String driverEmail;
    @Column(length = 15)
    private String driverPhone;
    private Date driverBirthDate;
    private String driverProfession;
    private String driverAddress;
    @Column(length = 2)
    private String driverLangCode;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatedDate;
    private boolean isCostNegotiable;

    public Travel(long travelPathId, long driverId, double departureLongitude, double departureLattitude, String departureAddress, Date departureDate, Date effectiveDepartureDate, Date publicationDate, double arrivalLongitude, double arrivalLattitude, String arrivalAddress, Date arrivalDate, Date effectiveArrivalDate, int availableSeats, int bookedSeats, double seatCost, int travelStatus, String driverFristName, String driverLastName, String genderCode, String driverEmail, String driverPhone, String driverLangCode, Date createdDate, Date updatedDate,boolean isCostNegotiable) {
        this.travelPathId = travelPathId;
        this.driverId = driverId;
        this.departureLongitude = departureLongitude;
        this.departureLatitude = departureLattitude;
        this.departureAddress = departureAddress;
        this.departureDate = departureDate;
        this.effectiveDepartureDate = effectiveDepartureDate;
        this.publicationDate = publicationDate;
        this.arrivalLongitude = arrivalLongitude;
        this.arrivalLatitude = arrivalLattitude;
        this.arrivalAddress = arrivalAddress;
        this.arrivalDate = arrivalDate;
        this.effectiveArrivalDate = effectiveArrivalDate;
        this.availableSeats = availableSeats;
        this.bookedSeats = bookedSeats;
        this.seatCost = seatCost;
        this.travelStatus = travelStatus;
        this.driverFristName = driverFristName;
        this.driverLastName = driverLastName;
        this.genderCode = genderCode;
        this.driverEmail = driverEmail;
        this.driverPhone = driverPhone;
        this.driverLangCode = driverLangCode;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.isCostNegotiable=isCostNegotiable;
    }

    public Travel(long driverId, double departureLongitude, double departureLatitude, String departureAddress, Date departureDate, Date publicationDate, double arrivalLongitude, double arrivalLatitude, String arrivalAddress, Date arrivalDate, int availableSeats, int bookedSeats, double seatCost, int travelStatus, String driverFristName, String driverLastName, String genderCode, String driverEmail, String driverPhone, Date driverBirthDate, Date createdDate, Date updatedDate,boolean isCostNegotiable) {
        this.driverId = driverId;
        this.departureLongitude = departureLongitude;
        this.departureLatitude = departureLatitude;
        this.departureAddress = departureAddress;
        this.departureDate = departureDate;
        this.publicationDate = publicationDate;
        this.arrivalLongitude = arrivalLongitude;
        this.arrivalLatitude = arrivalLatitude;
        this.arrivalAddress = arrivalAddress;
        this.arrivalDate = arrivalDate;
        this.availableSeats = availableSeats;
        this.bookedSeats = bookedSeats;
        this.seatCost = seatCost;
        this.travelStatus = travelStatus;
        this.driverFristName = driverFristName;
        this.driverLastName = driverLastName;
        this.genderCode = genderCode;
        this.driverEmail = driverEmail;
        this.driverPhone = driverPhone;
        this.driverBirthDate = driverBirthDate;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.isCostNegotiable=isCostNegotiable;
    }

    public Travel(long travelPathId, long driverId, double departureLongitude, double departureLatitude, String departureAddress, Date departureDate, Date publicationDate, double arrivalLongitude, double arrivalLatitude, String arrivalAddress, Date arrivalDate, int availableSeats, int bookedSeats, double seatCost, int travelStatus, String driverFristName, String driverLastName, String genderCode, String driverEmail, String driverPhone, String driverLangCode, Date createdDate, Date updatedDate,boolean isCostNegotiable) {
        this.travelPathId = travelPathId;
        this.driverId = driverId;
        this.departureLongitude = departureLongitude;
        this.departureLatitude = departureLatitude;
        this.departureAddress = departureAddress;
        this.departureDate = departureDate;
        this.publicationDate = publicationDate;
        this.arrivalLongitude = arrivalLongitude;
        this.arrivalLatitude = arrivalLatitude;
        this.arrivalAddress = arrivalAddress;
        this.arrivalDate = arrivalDate;
        this.availableSeats = availableSeats;
        this.bookedSeats = bookedSeats;
        this.seatCost = seatCost;
        this.travelStatus = travelStatus;
        this.driverFristName = driverFristName;
        this.driverLastName = driverLastName;
        this.genderCode = genderCode;
        this.driverEmail = driverEmail;
        this.driverPhone = driverPhone;
        this.driverLangCode = driverLangCode;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.isCostNegotiable=isCostNegotiable;
    }
    
    
    
}
