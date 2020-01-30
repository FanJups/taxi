/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.carpooling.services.impl;

import biz.advanceitgroup.taxirideserver.carpooling.entities.PickUpBookingInformations;
import biz.advanceitgroup.taxirideserver.carpooling.entities.Travel;
import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelBooking;
import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelCondition;
import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelCurrentPickupPlace;
import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelInvitation;
import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelPath;
import biz.advanceitgroup.taxirideserver.carpooling.repository.PickUpBookingInformationsRepository;
import biz.advanceitgroup.taxirideserver.carpooling.repository.TravelBookingRepository;
import biz.advanceitgroup.taxirideserver.carpooling.repository.TravelConditionRepository;
import biz.advanceitgroup.taxirideserver.carpooling.repository.TravelCurrentPickupPlaceRepository;
import biz.advanceitgroup.taxirideserver.carpooling.repository.TravelInvitationRepository;
import biz.advanceitgroup.taxirideserver.carpooling.repository.TravelPathRepository;
import biz.advanceitgroup.taxirideserver.carpooling.repository.TravelRepository;
import biz.advanceitgroup.taxirideserver.carpooling.services.interfaces.CarpoolingService;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author daniel
 */
@Service
public class CarpoolingServiceImpl implements CarpoolingService{
    @Autowired
    TravelRepository travelRepository;
    
    @Autowired
    TravelInvitationRepository travelInvitationRepository;
    
    @Autowired
    TravelConditionRepository travelConditionRepository;
    
    @Autowired
    TravelCurrentPickupPlaceRepository travelCurrentPickupPlaceRepository;
    
    @Autowired
    TravelBookingRepository travelBookingRepository;
    
    @Autowired
    PickUpBookingInformationsRepository pickUpBookingInformationsRepository;
    
    @Autowired
    TravelPathRepository travelPathRepository;
    
    
    @Override
    public Travel saveTravel(Travel travel) {
        return travelRepository.save(travel);
    }

    @Override
    public TravelInvitation saveTravelInvitation(TravelInvitation travelInvitation) {
        return travelInvitationRepository.save(travelInvitation);
    }

    @Override
    public Optional<TravelPath> findTravelPath(String startCity, String endCity) {
        return travelPathRepository.findTravelPath(startCity, endCity);
    }

    @Override
    public TravelCondition saveTravelcondition(TravelCondition travelCondition) {
        return travelConditionRepository.save(travelCondition);
    }

    @Override
    public TravelCurrentPickupPlace saveTravelCurrentPickupPlace(TravelCurrentPickupPlace travelCurrentPickupPlace) {
        return travelCurrentPickupPlaceRepository.save(travelCurrentPickupPlace);
    }

    @Override
    public List<Travel> findDriverInfoForRoute(String emailOrPhone, long pathId) {
        return travelRepository.findDriverInfoForroute(emailOrPhone, pathId);
    }

    @Override
    public List<Travel> findDriverTravels(String emailOrPhone) {
        return travelRepository.findDriverTravels(emailOrPhone);
    }

    @Override
    public Optional<Travel> getLastMissedAppointment(String emailOrPhone, long pathId) {
        return  travelRepository.getLastCancelTravel(emailOrPhone, pathId);
    }

    @Override
    public List<TravelBooking> findAllLongDistanceTripBooking(long routeID, Date startDate, Date endDate, int bookingStatus) {
        return travelBookingRepository.findAllLongDistanceTripBooking(routeID, startDate, endDate, bookingStatus);
    }

    @Override
    public Optional<Travel> findTravelById(long travelId) {
        return travelRepository.findById(travelId);
    }

    @Override
    public Optional<TravelPath> findTravelPathById(long pathId) {
        return travelPathRepository.findById(pathId);
    }

    @Override
    public List<TravelBooking> findAllRiderBooking(long riderId) {
        return travelBookingRepository.findAllRiderBooking(riderId);
    }

    @Override
    public List<TravelInvitation> findAllLongDistanceTripInvitation(long routeID, Date startDate, Date endDate, int bookingStatus) {
        return travelInvitationRepository.findAllLongDistanceTripInvitation(routeID, startDate, endDate,bookingStatus);
    }

    @Override
    public Optional<TravelCondition> findTravelConditionByTravelId(long travelId) {
        return travelConditionRepository.findTravelConditionByTravelId(travelId);
    }

    @Override
    public List<Travel> findAllLongDistanceTrip(long routeId, Date startDate, Date endDate) {
        return travelRepository.findAllLongDistanceTrip(routeId, startDate, endDate);
    }

    @Override
    public List<TravelBooking> findBookingListForTravel(long travelId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<TravelInvitation> findTravelInvitationByRiderAndStatusId(long riderId, int status) {
        return travelInvitationRepository.findTravelInvitationByRiderAndStatusId(riderId, status);
    }

    @Override
    public TravelBooking saveTravelBooking(TravelBooking travelBooking) {
        return travelBookingRepository.save(travelBooking);
    }
    
    @Override
    public PickUpBookingInformations savePickUpBookingInformations(PickUpBookingInformations pickUpBookingInformations) {
        return pickUpBookingInformationsRepository.save(pickUpBookingInformations);
    }
    
    @Override
    public Optional<List<String>> findDepartureCities(String countryCode)
    {
    	return travelPathRepository.findDepartureCities(countryCode);
    }
    
    public Optional<List<TravelPath>>  findByDepartureCityAndCountryCode(String departureCity,String countryCode)
    {
    	return travelPathRepository.findByDepartureCityAndCountryCode(departureCity, countryCode);
    }

    @Override
    public void startTrip(long tripId) {
        travelRepository.startTrip(tripId,2);
    }
    
}
