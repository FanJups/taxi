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
public class TravelBooking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long travelId;
    private long riderId;
    private long bookingTravelPathid;
    private double departureLongitude;
    private double departureLatitude;
    private String departureAddress;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date departureDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date effectiveDepartureDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date bookingDate;
    private double arrivalLongitude;
    private double arrivalLatitude;
    private String arrivalAddress;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date arrivalDate;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date effectiveArrivalDate;
    private int bookedSeats;
    private double seatCost;
    private double cancelCost;
    private int enableReservation;
    private int travelStatus;
    private int duration;
    private long distance;
    private boolean isCostNegotiable;

    

	public TravelBooking(long travelId, long riderId, long bookingTravelPathid, Date departureDate,
			Date effectiveDepartureDate, Date bookingDate, Date arrivalDate, Date effectiveArrivalDate, int bookedSeats,
			double seatCost, double cancelCost, int enableReservation, int travelStatus, int duration, long distance,boolean isCostNegotiable) {
		
		this.travelId = travelId;
		this.riderId = riderId;
		this.bookingTravelPathid = bookingTravelPathid;
		this.departureDate = departureDate;
		this.effectiveDepartureDate = effectiveDepartureDate;
		this.bookingDate = bookingDate;
		this.arrivalDate = arrivalDate;
		this.effectiveArrivalDate = effectiveArrivalDate;
		this.bookedSeats = bookedSeats;
		this.seatCost = seatCost;
		this.cancelCost = cancelCost;
		this.enableReservation = enableReservation;
		this.travelStatus = travelStatus;
		this.duration = duration;
		this.distance = distance;
		this.isCostNegotiable= isCostNegotiable;
	}
    
    
    
    
}
