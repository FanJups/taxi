/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.carpooling.services.interfaces;

import biz.advanceitgroup.taxirideserver.carpooling.entities.PickUpBookingInformations;
import biz.advanceitgroup.taxirideserver.carpooling.entities.Travel;
import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelBooking;
import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelCondition;
import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelCurrentPickupPlace;
import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelInvitation;
import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelPath;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;


/**
 *
 * @author daniel
 * 
 * @author Fanon Jupkwo
 */
@Service
public interface CarpoolingService {
    
    public Travel saveTravel(Travel travel);
    
    public TravelInvitation saveTravelInvitation(TravelInvitation travelInvitation);
    
    public TravelBooking saveTravelBooking(TravelBooking travelBooking);
    
    public PickUpBookingInformations savePickUpBookingInformations(PickUpBookingInformations pickUpBookingInformations);
    
    public Optional<TravelPath> findTravelPath(String startCity,String endCity);
    
    public TravelCondition saveTravelcondition(TravelCondition travelCondition);
    
    public TravelCurrentPickupPlace saveTravelCurrentPickupPlace(TravelCurrentPickupPlace travelCurrentPickupPlace);
    
    public List<Travel> findDriverInfoForRoute(String emailOrPhone,long pathId);
    
    public List<Travel> findDriverTravels(String emailOrPhone);
    
    public Optional<Travel> getLastMissedAppointment(String emailOrPhone,long pathId);
    
    public List<TravelBooking> findAllLongDistanceTripBooking(long routeID,Date startDate,Date endDate,int bookingStatus);
    
    public Optional<Travel> findTravelById(long travelId);
    
    public Optional<TravelPath> findTravelPathById(long pathId);
    
    public List<TravelBooking> findAllRiderBooking(long riderId);
    
    public List<TravelInvitation> findAllLongDistanceTripInvitation(long routeID,Date startDate,Date endDate,int bookingStatus);
    
    public Optional<TravelCondition> findTravelConditionByTravelId(long travelId);
    
    public List<Travel> findAllLongDistanceTrip(long routeId, Date startDate,Date endDate);
    
    public List<TravelBooking> findBookingListForTravel(long travelId);
    
    public List<TravelInvitation> findTravelInvitationByRiderAndStatusId(long riderId,int status);
    
    public Optional<List<String>> findDepartureCities(String countryCode);
    
   //find ArrivalCities, Costs and Distances
    
    public Optional<List<TravelPath>>  findByDepartureCityAndCountryCode(String departureCity,String countryCode);

    
    public void startTrip(long tripId);
  }
