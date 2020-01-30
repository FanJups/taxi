package biz.advanceitgroup.taxirideserver.courses.services.interfaces;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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

public interface RidesServices {
	
	// Enregistrer la position courante d'un terminal mobile
	CurrentDriverLocation saveDriverLocation(CurrentDriverLocation currentDriverLocation);

	//enregistrer les log d'un user
	LogUserLocation saveUserLogLocation(LogUserLocation logUserLocation);
	
	//Enregistrer les heure de départ du taxi, de récupération du passager, de fin de course, de règlement de facture.
	LogRideEvent saveRideEventDate(LogRideEvent logRideEvent);
	
	void updateDriverLocation(long timeoutDriverConnect, String codePays, String ville, double longitude, double latitude, String position,long id ,long rideId,Boolean driverAvailable, String emailOrPhone,Date dateEnreg);
	
	//Valider la commande
	Trip postRide (Trip trip);
        
        void updateTrip(long id);
	
	//Récuperer un trip precis
	Optional<Trip> findTripById (Long id);
	
	//Save current ride request location
	CurrentRideRequestLocation saveCurrentRideRequestLocation(CurrentRideRequestLocation currentRideRequestLocation);
        
       void updateDriverLocationForAcceptTrip(long timeoutDriverConnect, double longitude, double latitude, String position,Date dateEnreg, long rideId,Boolean driverAvailable, String emailOrPhone);
        
	
	//Save message
	Messages saveMessage (Messages message);
	
	//Save Driver notatione
	DriverNotation saveDriverNotation (DriverNotation notation);
		
	//Save Rider notation
	RiderNotation saveRiderNotation (RiderNotation notation);
	
	//Find trip option by code
	Optional<TripOption> findTripOptionByCode(String option_code,String cityCode);
		
	//List all destinatate drivers language
	List<Users> findAllDriversLanguage(String language);
	
	
	//Check if driver already accept course
	Optional<CurrentRideRequestLocation> checkIfDriverExist(Long id);
        
        Optional<CurrentRideRequestLocation> checkDriverExist(long tripId);
	
		// Retreive all driver reviews
		List<DriverNotation> findAllDriverReviews(long driverid);
	
		//listing de tous les drivers disponible dans un certain rayon d'un point Gmap
		List<CurrentDriverLocation> findAllAvailableDrivers(String codePays, String codeville, double longitude, double lattitude, long rayon);

		//Cette API doit permettre au driver d'afficher les commandes en permettant de préciser celles qui sont hors de portée (distance > rayon du rider). Le driver ne peut accepter une commande hors de portée du rider.
		List<CurrentRideRequestLocation> findAllAvailableTrip(double lattitude,double longitude,String countryCode,String city, double rayon);
		
		//Accept ride by driver
		int acceptRide(String phoneNumber, long driverID, long rideID, long requestid,Date dateEnreg, double longitude, double lattitude,double durationBeforePickUp);
		
		int acceptRideUpdateDriver(String driverAddress, String driverEmail, 
				String driverPhone, double driverStartLattitude, 
				double driverStartLongitude,   int tripStatus, Long tripId,double durationBeforePickUp);
		
	//Find all notification message for Post trip
	Optional<Messages> findAllNotificationMessageForTrip(int type);
	
	//Find driver current location
	Optional<CurrentDriverLocation> findDriverLocation(String phone);
	
	List<String> findAvailableOptionsByCity(String countryCode, String cityCode);
        
        List<Trip> findAllTrip(String id, Date startDate, Date endDate);
        
        void startTrip(long id, String driverUsername,double longitude, double latitude);
        
        void endTrip(long id,String username, double longitude, double latitude,int profile);
        
        void cancelTrip(long tripId,String username, int role);
        
        double getCanceTripCost(Trip trip,int role);
        
        double getTripCost(long tripId);
        
        TripCostDto getSampleTripCost(String optionCode,long estimatedDistance,long estimatedDuration, String cityCode);
        
        void deleteTrip(long tripId);
        
        Optional<CurrentDriverLocation> findCurrentRideDriverLocation(long tripId);
        
        public int isTripEnd(long tripId);
        
        public void updateDriverLocationWithoutRideId(long timeoutDriverConnect, String codePays, String ville, double longitude, double latitude, String position, String emailOrPhone,Date date_enreg);
        
        public Optional<LogUserLocation> getLastLogUserPosition(String emailOrPhone);
}
