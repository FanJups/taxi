package biz.advanceitgroup.taxirideserver.courses.helpers;

import org.springframework.stereotype.Service;

import biz.advanceitgroup.taxirideserver.courses.dto.CurrentDriverLocationDTO;
import biz.advanceitgroup.taxirideserver.courses.dto.CurrentRideRequestLocationDTO;
import biz.advanceitgroup.taxirideserver.courses.dto.DriverNotationDTO;
import biz.advanceitgroup.taxirideserver.courses.dto.LogRideEventDTO;
import biz.advanceitgroup.taxirideserver.courses.dto.LogUserLocationDTO;
import biz.advanceitgroup.taxirideserver.courses.dto.RiderNotationDTO;
import biz.advanceitgroup.taxirideserver.courses.entities.CurrentDriverLocation;
import biz.advanceitgroup.taxirideserver.courses.entities.CurrentRideRequestLocation;
import biz.advanceitgroup.taxirideserver.courses.entities.DriverNotation;
import biz.advanceitgroup.taxirideserver.courses.entities.LogRideEvent;
import biz.advanceitgroup.taxirideserver.courses.entities.LogUserLocation;
import biz.advanceitgroup.taxirideserver.courses.entities.RiderNotation;

@Service
public class RidesServices {
	/**
     * Convert current driver location to DTO
     * @param currentDriverLocation
     * @return
     */
    public static CurrentDriverLocationDTO convertCurrentDriverLocationToDTO(CurrentDriverLocation currentDriverLocation){
        return new CurrentDriverLocationDTO(
        		currentDriverLocation.getEmailOrPhoneNumber(),
        		currentDriverLocation.getDriverAvailable(),
        		currentDriverLocation.getTimeoutDriverConnect(),
        		currentDriverLocation.getCodePays(),
        		currentDriverLocation.getVille(),
        		currentDriverLocation.getLatitude(),
        		currentDriverLocation.getLongitude(),
        		currentDriverLocation.getRIDE_ID());
    }
    
    /**
     * Convert log user location to DTO
     * @param logUserLocation
     * @return
     */
    public static LogUserLocationDTO convertLogUserLocationToDTO(LogUserLocation logUserLocation){
        return new LogUserLocationDTO(
        		logUserLocation.getEmailOrPhoneNumber(),
        		logUserLocation.getCodePays(),
        		logUserLocation.getCodeVille(),
        		logUserLocation.getLatitude(),
        		logUserLocation.getLongitude(),
        		logUserLocation.getRIDE_ID());
    }
    
    /**
     * Convert Ride request to DTO
     * @param CurrentRideRequestLocation
     * @return
     */
    public static CurrentRideRequestLocationDTO convertCurrentRideRequestLocationDTO(CurrentRideRequestLocation currentRideRequestLocation){
        return new CurrentRideRequestLocationDTO(
        		currentRideRequestLocation.getRideID(),
        		currentRideRequestLocation.getCodePays(),
        		currentRideRequestLocation.getEmailorphone(),
        		currentRideRequestLocation.getVille(),
        		currentRideRequestLocation.getLongitude(),
        		currentRideRequestLocation.getLatitude(),
        		currentRideRequestLocation.getDriverID(),
        		currentRideRequestLocation.getRideDurationBeforePickUp()
        		);
    }
    
    /**
     * Convert Driver notation request to DTO
     * @param DriverNotation
     * @return
     */
    public static DriverNotationDTO convertDriverNotationDTO(DriverNotation driverNotation){
        return new DriverNotationDTO(
        		driverNotation.getDriverID(),
        		driverNotation.getNote(),
        		driverNotation.getCommentaire(),
        		driverNotation.getDateEnreg(),
        		driverNotation.getRideID(),
        		driverNotation.getRiderID()
        		);
    }
    
    /**
     * Convert rider notation request to DTO
     * @param RiderNotation
     * @return
     */
    public static RiderNotationDTO convertRiderNotationDTO(RiderNotation riderNotation){
        return new RiderNotationDTO(
        		riderNotation.getRiderID(),
        		riderNotation.getNote(),
        		riderNotation.getCommentaire(),
        		riderNotation.getDateEnreg(),
        		riderNotation.getRideID(),
        		riderNotation.getDriverID()
        		);
    }
    
    /**
     * Convert log ride event to DTO
     * @param logRideEvent
     * @return
     */
    public static LogRideEventDTO convertLogRideEventToDTO(LogRideEvent logRideEvent){
        return new LogRideEventDTO(
        		logRideEvent.getEventType(),
        		logRideEvent.getRideId());
    }
}
