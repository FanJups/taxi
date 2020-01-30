package biz.advanceitgroup.taxirideserver.courses.services.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.courses.dto.TripCostDto;
import biz.advanceitgroup.taxirideserver.courses.entities.CurrentDriverLocation;
import biz.advanceitgroup.taxirideserver.courses.entities.CurrentRideRequestLocation;
import biz.advanceitgroup.taxirideserver.courses.entities.DriverNotation;
import biz.advanceitgroup.taxirideserver.courses.entities.LogRideEvent;
import biz.advanceitgroup.taxirideserver.courses.entities.LogUserLocation;
import biz.advanceitgroup.taxirideserver.courses.entities.Messages;
import biz.advanceitgroup.taxirideserver.courses.entities.RiderNotation;
import biz.advanceitgroup.taxirideserver.courses.entities.Trip;
import biz.advanceitgroup.taxirideserver.courses.entities.TripOption;
import biz.advanceitgroup.taxirideserver.courses.repositories.CostCompute;
import biz.advanceitgroup.taxirideserver.courses.repositories.CurrentDriverLocationRepository;
import biz.advanceitgroup.taxirideserver.courses.repositories.CurrentRideRequestLocationRepository;
import biz.advanceitgroup.taxirideserver.courses.repositories.DriverNotationRepository;
import biz.advanceitgroup.taxirideserver.courses.repositories.LogUserLocationRepository;
import biz.advanceitgroup.taxirideserver.courses.repositories.MessagesRepository;
import biz.advanceitgroup.taxirideserver.courses.repositories.RiderNotationRepository;
import biz.advanceitgroup.taxirideserver.courses.repositories.TripOptionRepository;
import biz.advanceitgroup.taxirideserver.courses.repositories.TripRepository;
import biz.advanceitgroup.taxirideserver.courses.repositories.LogRideEventRepository;
import biz.advanceitgroup.taxirideserver.courses.services.interfaces.RidesServices;
import java.util.Date;


@Service
public class RidesServicesImpl implements RidesServices{
        
	@Autowired
	CurrentDriverLocationRepository currentDriverLocationRepository;
	
	@Autowired
	LogUserLocationRepository logUserLocationRepository;
	
	@Autowired
	TripRepository tripRepository;
	
	@Autowired
	DriverNotationRepository driverNotationRepository;
	
	@Autowired
	RiderNotationRepository riderNotationRepository;
	
	@Autowired
	CurrentRideRequestLocationRepository currentRideRequestLocationRepository;
	
	@Autowired
	MessagesRepository messagesRepository;
	
	@Autowired
	TripOptionRepository tripOptionRepository;
        
        @Autowired
        LogRideEventRepository logRideEventRepository;
        
        @Autowired
        CostCompute costCompute;
        
	@Override
	public CurrentDriverLocation saveDriverLocation(CurrentDriverLocation currentDriverLocation) {
		return currentDriverLocationRepository.save(currentDriverLocation);
	}
	public void updateDriverLocation(long timeoutDriverConnect, String codePays, String ville, double longitude, double latitude, String position,long id, long ride_id,Boolean driverAvailable, String emailOrPhone,Date date_enreg){
            currentDriverLocationRepository.updateDriverLocation(timeoutDriverConnect, codePays, ville, longitude, latitude, position, id, ride_id, driverAvailable,emailOrPhone,date_enreg);
        }
        
	@Override
	public LogUserLocation saveUserLogLocation(LogUserLocation logUserLocation) {
		return logUserLocationRepository.save(logUserLocation);
	}


        
	@Override
	public LogRideEvent saveRideEventDate(LogRideEvent logRideEvent) {
		return logRideEventRepository.save(logRideEvent);
	}

	

	@Override
	public Trip postRide(Trip trip) {
		Trip transport=tripRepository.save(trip);
                int courseCost=costCompute.computeFees(transport);
                tripRepository.updateTripCost(transport.getTripID(), courseCost);
                //tripRepository.updateTrip(transport.getTripID());
                //tripRepository.updateTripCost(transport.getTripID(),transport.getTripOptionCode(),trip.getCityCode());
                return transport;
	}
       
	@Override
	public Optional<Trip> findTripById(Long id) {
		return tripRepository.findByTripId(id);
	}

	@Override
	public CurrentRideRequestLocation saveCurrentRideRequestLocation(
			CurrentRideRequestLocation currentRideRequestLocation) {
		return currentRideRequestLocationRepository.save(currentRideRequestLocation);
	}
	
	@Override
	public Messages saveMessage(Messages message) {
		return messagesRepository.save(message);
	}

	@Override
	public DriverNotation saveDriverNotation(DriverNotation notation) {
		return driverNotationRepository.save(notation);
	}

	@Override
	public RiderNotation saveRiderNotation(RiderNotation notation) {
		return riderNotationRepository.save(notation);
	}

	@Override
	public Optional<TripOption> findTripOptionByCode(String option_code, String cityCode) {
		return tripOptionRepository.findTripOptionByCode(option_code, cityCode);
	}
	
	@Override
	public List<DriverNotation> findAllDriverReviews(long driverid) {
		return driverNotationRepository.findAllDriverReviews(driverid);
	}
	
	@Override
	public Optional<Messages> findAllNotificationMessageForTrip(int type) {
		return messagesRepository.findAllNotificationMessageForTrip(type);
	}
	
	@Override
	public Optional<CurrentDriverLocation> findDriverLocation(String phone) {
		return currentDriverLocationRepository.findDriverLocation(phone);
	}
	

	@Override
	public List<CurrentDriverLocation> findAllAvailableDrivers(String codepays, String codeville, double longitude, double lattitude, long rayon) {
		//return currentDriverLocationRepository.findAllAvailableDrivers(codepays, codeville, longitude, lattitude, rayon);
		return currentDriverLocationRepository.findAllAvailableDrivers(codepays, codeville,longitude,lattitude,rayon);
	}

	@Override
	public Optional<CurrentRideRequestLocation> checkIfDriverExist(Long id) {
		return currentRideRequestLocationRepository.checkIfDriverExist(id);
	}
	@Override
	public Optional<CurrentRideRequestLocation> checkDriverExist(long tripId) {
		return currentRideRequestLocationRepository.checkDriverExist(tripId);
	}
	
	//Cette méthode n'est pas encore terminé
	@Override
	public List<Users> findAllDriversLanguage(String language) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	
	public int acceptRide(String phoneNumber, long driverID, long tripID, long requestid,Date dateEnreg, double longitude, double lattitude,double durationBeforePickUp) {
		
		//Mettre à jour CurrentDrivertLocation en renseignant également le champ tripID
                currentDriverLocationRepository.updateDriverLocationForAcceptTrip(0,longitude, lattitude,"blabla",tripID, Boolean.FALSE, phoneNumber);
                
                //Mettre à jour le champ driverID dans CurrentTripRequestLocation
		currentRideRequestLocationRepository.acceptRide(driverID, requestid,durationBeforePickUp);
                
		return 1;
		
	
	}
	

	@Override
	public List<String> findAvailableOptionsByCity(String countryCode, String cityCode) {
		return tripOptionRepository.findAvailableOptionsByCity(countryCode, cityCode);
	}

    @Override
    public int acceptRideUpdateDriver(String driverAddress, String driverEmail, String driverPhone, double driverStartLattitude, double driverStartLongitude, int tripStatus, Long tripId,double durationBeforePickUp) {
       tripRepository.acceptRideUpdateDriver(driverAddress,driverEmail, 
                                driverPhone, driverStartLattitude, 
                                driverStartLongitude,2,tripId,durationBeforePickUp);
       return 1;
    }

    /**
     *
     * @param lattitude
     * @param longitude
     * @param countryCode
     * @param city
     * @param rayon
     * @return
     */
    @Override
    public List<CurrentRideRequestLocation> findAllAvailableTrip(double lattitude,double longitude,String countryCode,String city, double rayon) {
        return currentRideRequestLocationRepository.findAllAvailableTrip(lattitude,longitude,city,rayon);
    }

    @Override
    public void updateTrip(long id) {
        tripRepository.updateTrip(id);
    }	

    @Override
    public List<Trip> findAllTrip(String id,Date startDate, Date endDate) {
       return tripRepository.findAllTrip(id,startDate, endDate);
    }

    @Override
    public void updateDriverLocationForAcceptTrip(long timeoutDriverConnect, double longitude, double latitude, String position,Date dateEnreg, long rideId, Boolean driverAvailable, String emailOrPhone) {
        currentDriverLocationRepository.updateDriverLocationForAcceptTrip(timeoutDriverConnect, longitude, latitude, position, rideId, driverAvailable, emailOrPhone );
    }

    @Override
    public void startTrip(long id, String email0rPhone, double longitude, double latitude) {
       tripRepository.startTrip(id, email0rPhone);
       currentDriverLocationRepository.updateSimpleDriverLocation(longitude, latitude, email0rPhone);
    }

    @Override
    public void endTrip(long id, String username, double longitude, double latitude,int profile) {
        //profile: 1=driver, 2=rider
                tripRepository.endTripByDriver(id, username);
                currentDriverLocationRepository.updateSimpleDriverLocation(longitude, latitude, username);
                deleteTrip(id);
                
    }

    @Override
    public double getCanceTripCost(Trip trip,int role) {
        if(role==1)
            return costCompute.computeDriverFees(trip);
        else
            return costCompute.computeRiderFees(trip);
    }

    @Override
    public double getTripCost(long tripId) {
        return tripRepository.getTripCost(tripId);
    }

    @Override
    public void cancelTrip(long tripId,String username,int role) {
        if(role==1){
            Optional<Trip> trip=findTripById(tripId);
            if(trip.isPresent()){
                long tripCancelCost=(long)getCanceTripCost(trip.get(),1);
                tripRepository.cancelTripByDriver(tripId, username,tripCancelCost);
                deleteTrip(tripId);
            }
        }
        else{
             Optional<Trip> trip=findTripById(tripId);
            if(trip.isPresent()){
                long tripCancelCost=(long)getCanceTripCost(trip.get(),2);
                tripRepository.cancelTripByRider(tripId, username,tripCancelCost);
                deleteTrip(tripId);
            }
        }
    }   

    @Override
    public TripCostDto getSampleTripCost(String optionCode, long estimatedDistance, long estimatedDuration,String cityCode) {
        
        Optional<TripOption> tripOption=tripOptionRepository.findOptionsByCityAndCode(optionCode, cityCode);
        if(tripOption.isPresent()){
            TripCostDto tripCost=new TripCostDto();
            tripCost.setTripCost(costCompute.computeFeesForSample(tripOption.get(), estimatedDistance, estimatedDuration));
            return tripCost;
        }
        else
            return null;
    }
    /*
    *helps to delete a trip when finished in the table current ride request Location;
    *
    */
    @Override
    public void deleteTrip(long tripId) {
        currentRideRequestLocationRepository.deleteByTripId(tripId);
    }

    @Override
    public Optional<CurrentDriverLocation> findCurrentRideDriverLocation(long tripId) {
        return currentDriverLocationRepository.findCurrentRideDriverLocation(tripId);
    }

    @Override
    public int isTripEnd(long tripId) {
        return tripRepository.getTripStatus(tripId);
    }

    @Override
    public void updateDriverLocationWithoutRideId(long timeoutDriverConnect, String codePays, String ville, double longitude, double latitude, String position, String emailOrPhone,Date date_enreg) {
        currentDriverLocationRepository.updateDriverLocationWithoutRideId(timeoutDriverConnect, codePays, ville, longitude, latitude, position, emailOrPhone, date_enreg);
    }

    @Override
    public Optional<LogUserLocation> getLastLogUserPosition(String emailOrPhone) {
        return logUserLocationRepository.getLastUserLocation(emailOrPhone);
    }  
}
