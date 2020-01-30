/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.carpooling.webs;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.authentification.services.interfaces.UserService;
import biz.advanceitgroup.taxirideserver.carpooling.Dtos.BookTravelDto;
import biz.advanceitgroup.taxirideserver.carpooling.Dtos.CityCostDistanceDto;
import biz.advanceitgroup.taxirideserver.carpooling.Dtos.CityLongitudeLatitudeDto;
import biz.advanceitgroup.taxirideserver.carpooling.Dtos.DriverInfoForRouteRetour;
import biz.advanceitgroup.taxirideserver.carpooling.Dtos.LongDistanceBookingListRetour;
import biz.advanceitgroup.taxirideserver.carpooling.Dtos.LongDistanceTravelInvitationDto;
import biz.advanceitgroup.taxirideserver.carpooling.Dtos.LongDistanceTripListDto;
import biz.advanceitgroup.taxirideserver.carpooling.Dtos.PickUpBookingInformationsDto;
import biz.advanceitgroup.taxirideserver.carpooling.Dtos.PostLongDistanceTripDto;
import biz.advanceitgroup.taxirideserver.carpooling.Dtos.PostTravelRetourDto;
import biz.advanceitgroup.taxirideserver.carpooling.Dtos.RiderInfoForRouteDto;
import biz.advanceitgroup.taxirideserver.carpooling.Dtos.StartTripDto;
import biz.advanceitgroup.taxirideserver.carpooling.Dtos.TravelPathDto;
import biz.advanceitgroup.taxirideserver.carpooling.entities.PickUpBookingInformations;
import biz.advanceitgroup.taxirideserver.carpooling.entities.Travel;
import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelBooking;
import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelCondition;
import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelCurrentPickupPlace;
import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelInvitation;
import biz.advanceitgroup.taxirideserver.carpooling.entities.TravelPath;
import biz.advanceitgroup.taxirideserver.carpooling.services.interfaces.CarpoolingService;
import biz.advanceitgroup.taxirideserver.commons.entities.ResponseNew;
import biz.advanceitgroup.taxirideserver.commons.services.CommonServices;
import biz.advanceitgroup.taxirideserver.geolocalisation.entities.City;
import biz.advanceitgroup.taxirideserver.geolocalisation.services.interfaces.GeolocalisationServices;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.time.Duration;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author daniel
 */
@Api(value = "Carpooling Rest API")
@RestController
@RequestMapping("/api/carpooling")
public class CarpoolingRestController {
    
    @Autowired
    CommonServices commonServices;
    
    @Autowired
    UserService userService;
    
    @Autowired
    CarpoolingService carpoolingService;
    
    @Autowired
    GeolocalisationServices geolocalisationServices;
    
    /**
     *
     * @param postLongDistanceTripDto
     * @return
     */
    @Secured({"ROLE_ADMIN","ROLE_DRIVER"})
    @ApiOperation(value = "Poster un voyage..")
    @PostMapping("/postLongDistanceTrip")
    public ResponseEntity<?> postLongDistanceTrip(@Valid @RequestBody PostLongDistanceTripDto postLongDistanceTripDto){
        try{
            Optional<Users> driver=userService.findByEmailOrPhone(postLongDistanceTripDto.getEmailOrPhone());
            
            if(driver.isPresent()){
                Optional<TravelPath> travelPath=carpoolingService.findTravelPath(postLongDistanceTripDto.getStartCity(), postLongDistanceTripDto.getEndCity());
                if(travelPath.isPresent()){
                    Travel travel=carpoolingService.saveTravel(new Travel(travelPath.get().getId(), driver.get().getId(), 
                    		postLongDistanceTripDto.getDepartureLongitude(), postLongDistanceTripDto.getDepartureLatitude(), 
                    		postLongDistanceTripDto.getStartCity(), postLongDistanceTripDto.getDepartureDate(), new Date(), 
                    		postLongDistanceTripDto.getArrivalLongitude(), postLongDistanceTripDto.getArrivalLatitude(), 
                    		postLongDistanceTripDto.getArrival(), new Date(), postLongDistanceTripDto.getNumberOfSeat(), 0, 
                    		travelPath.get().getDefaultTravelCost(), 0, driver.get().getFirstName(), driver.get().getLastName(), 
                    		driver.get().getGender(), driver.get().getEmail(), driver.get().getPhoneNumber(), driver.get().getLanguage(), 
                    		new Date(), new Date(),postLongDistanceTripDto.isCostNegotiable()));
                    
                    TravelCondition travelCondition=carpoolingService.saveTravelcondition(new TravelCondition(travel.getId(),
                    		postLongDistanceTripDto.getTravelCondition().toString()));
                    
                    
                    TravelCurrentPickupPlace travelCurrentPickupPlace=carpoolingService.saveTravelCurrentPickupPlace(new TravelCurrentPickupPlace(0, travelPath.get().getId(), postLongDistanceTripDto.getCountry(), 
                    		postLongDistanceTripDto.getStartCity(), postLongDistanceTripDto.getDepartureLongitude(),
                    		postLongDistanceTripDto.getDepartureLatitude(), postLongDistanceTripDto.getDeparture()));
                    
                    
                    
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(626, commonServices.findErrorByCode(626, postLongDistanceTripDto.getLanguage()), new PostTravelRetourDto(travel.getId(),travel.getId())));
                }
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(749, commonServices.findErrorByCode(749, postLongDistanceTripDto.getLanguage()), null));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(722, commonServices.findErrorByCode(722, postLongDistanceTripDto.getLanguage()), null));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(404, commonServices.findErrorByCode(404, postLongDistanceTripDto.getLanguage()), null));
        }
     }
     //json(firstName, lastName, profession, profilePicture, totalTripCount, tripCountOnThisRoute, appRate, riderRates(rate, comment), lastCancelledTripsCount, lastMissedAppointments)

    /**
     *
     * @param emailOrPhone
     * @param departureCity
     * @param arrivalCity
     * @param language
     * @return
     */
    @Secured({"ROLE_ADMIN","ROLE_DRIVER"})
    @ApiOperation(value = "Rechercher les informations d'un chauffeur sur  des trajets..")
    @GetMapping("/findDriverInfoForARoute/{emailOrPhone}/{departureCity}/{arrivalCity}/{language}")
    @ResponseBody
    public ResponseEntity<?> findDriverInfoForARoute(@Valid @PathVariable String emailOrPhone,@PathVariable String departureCity, @PathVariable String arrivalCity, @PathVariable String language){
        try{
            Optional<Users> driver=userService.findByEmailOrPhone(emailOrPhone);
            if(driver.isPresent()){
                Optional<TravelPath> travelPath=carpoolingService.findTravelPath(departureCity, arrivalCity);
                if(travelPath.isPresent()){
                    List<Travel> allTravels= carpoolingService.findDriverTravels(emailOrPhone);
                    long numberOfCancelledTrips=0;
                    Date lastMissedAppointmentDate=null;
                    Optional<Travel> lastMissedAppointment=carpoolingService.getLastMissedAppointment(emailOrPhone, travelPath.get().getId());
                    if(lastMissedAppointment.isPresent())
                        lastMissedAppointmentDate=lastMissedAppointment.get().getDepartureDate();
                    List<Travel> travels=carpoolingService.findDriverInfoForRoute(emailOrPhone, travelPath.get().getId());
                    for(Travel travel:travels){
                        if(travel.getTravelStatus()==1)
                            numberOfCancelledTrips+=1;
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(200, commonServices.findErrorByCode(200, language),new DriverInfoForRouteRetour(driver.get().getFirstName(), driver.get().getLastName(), driver.get().getProfession(), driver.get().getProfilePictureMimeType(), allTravels.size(), travels.size(), numberOfCancelledTrips, lastMissedAppointmentDate)));
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(749, commonServices.findErrorByCode(749, language), null));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(404, commonServices.findErrorByCode(404, language), null));
        }
    }
    
    @Secured({"ROLE_ADMIN","ROLE_DRIVER"})
    @ApiOperation(value = "Retourner la liste des options disponibles dans un pays..")
    @GetMapping("/findAllLongDistanceTripBooking/{StartCity}/{endCity}/{startDate}/{endDate}/{bookingStatus}/{language}")
    @ResponseBody
    public ResponseEntity<?> findAllLongDistanceTripBooking (@PathVariable String startCity,@PathVariable String endCity,@PathVariable @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startDate,@PathVariable @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endDate, @PathVariable int bookingStatus, @PathVariable String language){
        try{
            Optional<TravelPath> travelPath=carpoolingService.findTravelPath(startCity, endCity);
            if(travelPath.isPresent()){
                List<TravelBooking> travelBooking=carpoolingService.findAllLongDistanceTripBooking(travelPath.get().getId(), startDate, endDate, bookingStatus);
                List<LongDistanceBookingListRetour> longDistanceBookingList=new ArrayList<>();
                for(TravelBooking travelBooked: travelBooking){
                    List<TravelBooking> riderTravelBooked=carpoolingService.findAllRiderBooking(travelBooked.getRiderId());
                    Optional<Travel> travel=carpoolingService.findTravelById(travelBooked.getTravelId());
                    if(travel.isPresent()){
                        longDistanceBookingList.add(new LongDistanceBookingListRetour(travelBooked.getId(),travelBooked.getTravelId(),travelBooked.getRiderId(),travel.get().getDriverId(),travelBooked.getBookingDate(),travelBooked.getDepartureDate(), travelBooked.getEffectiveDepartureDate(),travelPath.get().getDepartureCity(),travelBooked.getDepartureAddress(),travelPath.get().getArrivalCity(),travelBooked.getArrivalAddress(),travelBooked.getBookedSeats(),riderTravelBooked.size()));   
                    } 
                }
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(200,commonServices.findErrorByCode(200, language), longDistanceBookingList));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(729, commonServices.findErrorByCode(729, language), null));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(404, commonServices.findErrorByCode(404, language), null));
        }
    }
   
    @Secured({"ROLE_ADMIN","ROLE_DRIVER"})
    @ApiOperation(value = "Listing des invitations sur un trajet..")
    @GetMapping("/findAllLongDistanceTripInvitation/{travelId}/{startDate}/{endDate}/{invitationStatus}/{language}")
    @ResponseBody
    public ResponseEntity<?> findAllLongDistanceTravelInvitation(@PathVariable long travelId,@PathVariable @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startDate,@PathVariable @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endDate, @PathVariable int bookingStatus, @PathVariable String language){
        try{
            List<TravelInvitation> travelInvitations=carpoolingService.findAllLongDistanceTripInvitation(travelId, startDate, endDate, bookingStatus);
            List<LongDistanceTravelInvitationDto> longDistanceInvitationsList=new ArrayList<>();
            for(TravelInvitation travelInvitation:travelInvitations){
                Optional<Travel> travel=carpoolingService.findTravelById(travelInvitation.getId());
                if(travel.isPresent()){
                    List<TravelBooking> riderTravelBooked=carpoolingService.findAllRiderBooking(travelInvitation.getRiderId());
                    Optional<TravelPath> travelPath=carpoolingService.findTravelPathById(travel.get().getTravelPathId());
                    if(travelPath.isPresent()){
                        int availableSeatCount=travel.get().getAvailableSeats()-travel.get().getBookedSeats();
                        longDistanceInvitationsList.add(new LongDistanceTravelInvitationDto(travelInvitation.getId(), travel.get().getId(), travelInvitation.getRiderId(), travel.get().getDriverId(), travelInvitation.getInvitationDate(), travelInvitation.getDepartureDate(), travelInvitation.getDepartureDate(), travelPath.get().getDepartureCity(), travelInvitation.getDepartureAddress(), travelPath.get().getArrivalCity(), travelPath.get().getArrivalCity(), availableSeatCount, riderTravelBooked.size()));
                    }
                }
            }
            return  ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(200, commonServices.findErrorByCode(200, language), longDistanceInvitationsList));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(404, commonServices.findErrorByCode(404, language), null));
        }
    }
    @Secured({"ROLE_ADMIN","ROLE_DRIVER","ROLE_RIDER"})
    @ApiOperation(value = "Listing des voyages en cours sur un trajet..")
    @GetMapping("/findAllLongDistanceTrip/{startCity}/{endCity}/{startDate}/{endDate}/{invitationStatus}/{language}")
    @ResponseBody
    public ResponseEntity<?> findAllLongDistanceTrip(@PathVariable String startCity,@PathVariable String endCity,@PathVariable @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") Date startDate,@PathVariable @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") Date endDate, @PathVariable String language){
        try{
            Optional<TravelPath> travelPath=carpoolingService.findTravelPath(startCity, endCity);
            List<LongDistanceTripListDto> longDistanceTripList=new ArrayList<>();
            if(travelPath.isPresent()){
                List<Travel> travels=carpoolingService.findAllLongDistanceTrip(travelPath.get().getId(), startDate, endDate);
                travels.forEach((travel) -> {
                    Optional<TravelCondition> travelConditions=carpoolingService.findTravelConditionByTravelId(travel.getId());
                    longDistanceTripList.add(new LongDistanceTripListDto(travel.getId(), travel.getDriverId(), travel.getPublicationDate(), travel.getDepartureDate(), travel.getDepartureAddress(), travel.getSeatCost(),travel.getAvailableSeats(), travel.getBookedSeats(), travelConditions.get().getDescription()));
                });
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(200, commonServices.findErrorByCode(200, language),longDistanceTripList));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(729, commonServices.findErrorByCode(729, language),null));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(404, commonServices.findErrorByCode(404, language), null));
        }
    } 
    
    @Secured({"ROLE_ADMIN","ROLE_DRIVER","ROLE_RIDER"})
    @ApiOperation(value = "Informations sur un passager pour un trajet..")
    @GetMapping("/ findRiderInfoForARoute/{startCity}/{endCity}/{emailOrPhone}/{language}")
    @ResponseBody
    public ResponseEntity<?>  findRiderInfoForARoute(@PathVariable String startCity,@PathVariable String endCity,@PathVariable String emailOrPhone, @PathVariable String language){
        try{
            Optional<TravelPath> travelPath=carpoolingService.findTravelPath(startCity, endCity);
            List<Travel> listRiderTravel=new ArrayList<>();
            int numberOfCancelledTrips=0;
            Date lastCancelTrip=null;
            if(travelPath.isPresent()){
                Optional<Users> rider=userService.findByEmailOrPhone(emailOrPhone);
                if(rider.isPresent()){
                    List<TravelInvitation> travelInvitationList=carpoolingService.findTravelInvitationByRiderAndStatusId(rider.get().getId(), 2);
                    for(TravelInvitation travelInvitation:travelInvitationList){
                        Optional<Travel> travel=carpoolingService.findTravelById(travelInvitation.getTravelId());
                        if(travel.isPresent()){
                            if(travel.get().getTravelPathId()==travelPath.get().getId()){
                                listRiderTravel.add(travel.get());
                            }
                        }
                    }
                    for(Travel riderTravel:listRiderTravel){
                        if(riderTravel.getTravelStatus()==4){
                            numberOfCancelledTrips++;
                        }
                        Duration duration=Duration.between((Temporal)riderTravel.getDepartureDate(), (Temporal)riderTravel.getEffectiveDepartureDate());
                        if(Math.abs(duration.toHours())>4){
                            lastCancelTrip=riderTravel.getDepartureDate();
                        }
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(200, commonServices.findErrorByCode(200, language),new RiderInfoForRouteDto(rider.get().getFirstName(), rider.get().getLastName(), rider.get().getProfession(), rider.get().getProfilePictureMimeType(), travelInvitationList.size(), listRiderTravel.size(), 0, "slkd", numberOfCancelledTrips, lastCancelTrip)));
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(404, commonServices.findErrorByCode(404, language), null));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(404, commonServices.findErrorByCode(404, language), null));
        }
    }
    
     @Secured({"ROLE_ADMIN","ROLE_DRIVER","ROLE_RIDER"})
    @ApiOperation(value = "Retrouver l'ID d'un trajet..")
    @GetMapping("/findRouteID/{startCity}/{endCity}/{emailOrPhone}/{language}")
    @ResponseBody
    public ResponseEntity<?>  findRouteID(@PathVariable String startCity,@PathVariable String endCity,@PathVariable String countryCode, @PathVariable String language){
        try{
            Optional<TravelPath> travelPath=carpoolingService.findTravelPath(startCity, endCity);
            if(travelPath.isPresent())
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(200, commonServices.findErrorByCode(200, language), new TravelPathDto(travelPath.get().getId())));
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(729, commonServices.findErrorByCode(729, language), null));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(404, commonServices.findErrorByCode(404, language), null));
        }
    }
     
     @Secured({"ROLE_ADMIN","ROLE_DRIVER","ROLE_RIDER"})
     @ApiOperation(value = "Retrouver les villes de départ d'un pays à partir du country code")
     @GetMapping("/findDepartureCities/{countryCode}/{language}")
     @ResponseBody
     public ResponseEntity<?>  findDepartureCities(@PathVariable String countryCode,@PathVariable String language){
         try{
        	 
        	 Optional<List<String>> departureCitiesList = carpoolingService.findDepartureCities(countryCode);
        	 
        	 if(departureCitiesList.isPresent())
        	 {
        		 
        		 /*
        		  * Turn String departure Cities to list of cities.
        		  * Then, turn the cities list to CityLongitudeLatitudeDtos list.
        		  * 
        		  * */
        		 
        		 List<CityLongitudeLatitudeDto> departureCities = departureCitiesList.get().parallelStream()
        				 .map(geolocalisationServices::findByCityName).
        				 map(c -> new CityLongitudeLatitudeDto(c.getCityName(),c.getLongitude(),c.getLatitude()))
        				 .collect(Collectors.toList());
        		 
        		 return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(200, commonServices.findErrorByCode(200, language), departureCities));
        		 
        	 }else {
        		 
        		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(729, commonServices.findErrorByCode(729, language), null));
        		 
        	 }
             
         }catch(Exception e){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(404, commonServices.findErrorByCode(404, language), null));
         }
     }
     
     
     
     @Secured({"ROLE_ADMIN","ROLE_DRIVER","ROLE_RIDER"})
     @ApiOperation(value = "Renvoyer une liste contenant une ville d'arrivée, et le coût système prévu pour le trajet entre la ville de départ passée en paramètre et cette ville")
     @GetMapping("/findArrivalCities/{departureCity}/{countryCode}/{language}")
     @ResponseBody
     public ResponseEntity<?>  findArrivalCities(@PathVariable String departureCity,@PathVariable String countryCode,@PathVariable String language){
         try{
        	 
        	 Optional<List<TravelPath>> arrivalCitiesList = carpoolingService.findByDepartureCityAndCountryCode(departureCity, countryCode);
        	 
        	 if(arrivalCitiesList.isPresent())
        	 {
        		 
        		 /*
        		  * Turn arrival Cities to CityCostDistanceDtos list.
        		  * 
        		  * 
        		  * */
        		 
        		 List<CityCostDistanceDto> arrivalCities = arrivalCitiesList.get().parallelStream()
        				 .map(this::turnTravelPathObjectIntoCityCostDistanceDtoOBject).collect(Collectors.toList());
        				 
        		 
        		 return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(200, commonServices.findErrorByCode(200, language), arrivalCities));
        		 
        	 }else {
        		 
        		 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(729, commonServices.findErrorByCode(729, language), null));
        		 
        	 }
             
         }catch(Exception e){
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(404, commonServices.findErrorByCode(404, language), null));
         }
     }
    
    @Secured({"ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = " Publier une réservation sur un trajet..")
    @PostMapping("/postLongDistanceTripBooking")
    public ResponseEntity<?> postLongDistanceTripBooking(@Valid @RequestBody BookTravelDto bookTravelDto){
        try{
            Optional<Users> rider=userService.findByEmailOrPhone(bookTravelDto.getEmailOrPhone());
            TravelBooking travelBooking;
            if(rider.isPresent()){
                Optional<TravelPath> travelPath=carpoolingService.findTravelPath(bookTravelDto.getStartCity(), bookTravelDto.getEndCity());
                if(travelPath.isPresent()){
                    Optional<Travel> travel=carpoolingService.findTravelById(bookTravelDto.getTravelId());
                    if(travel.isPresent()){ 
                        if(travel.get().getDriverId()!=rider.get().getId())
                            
                        
                        	travelBooking = carpoolingService.saveTravelBooking(new TravelBooking(travel.get().getId(),
                        			rider.get().getId(),
                        			travelPath.get().getId(),
                        			bookTravelDto.getDeparturePeriodStartDate(),
                        			null,
                        			new Date(),
                        			bookTravelDto.getDeparturePeriodEndDate(),
                        			null,
                        			bookTravelDto.getNumberOfPlacesRequired(),
                        			bookTravelDto.getSeatCost(),
                        			0.0d,
                        			1,
                        			travel.get().getTravelStatus(),
                        			travelPath.get().getDefaultTravelDuration(),
                        			travelPath.get().getDefaultTravelDistance(),
                        			bookTravelDto.isCostNegotiable()));
                        
                        else
                            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseNew(404, "the driver is not allowed to book his trip", null));
                    }else{
                        travelBooking=carpoolingService.saveTravelBooking(new TravelBooking(0, rider.get().getId(), 
                        		travelPath.get().getId(),  
                        		bookTravelDto.getDeparturePeriodStartDate(), null, new Date(), bookTravelDto.getDeparturePeriodEndDate(),null, 
                        		bookTravelDto.getNumberOfPlacesRequired(),bookTravelDto.getSeatCost(),0.0d, 1, 0,travelPath.get().getDefaultTravelDuration(), 
                        		travelPath.get().getDefaultTravelDistance(),bookTravelDto.isCostNegotiable()));
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(200, commonServices.findErrorByCode(200, bookTravelDto.getLanguage()), travelBooking));
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(404, commonServices.findErrorByCode(404, bookTravelDto.getLanguage()), null));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(404, commonServices.findErrorByCode(404, bookTravelDto.getLanguage()), null));
        }
    }
    
    // postPickupBookingInformations
    
    @Secured({"ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "Renseigner les informations de ramassage pour un voyage")
    @PostMapping("/postPickupBookingInformations")
    public ResponseEntity<?> postPickupBookingInformations(@Valid @RequestBody PickUpBookingInformationsDto pickUpBookingInformationsDto){
        try{
            
        	
        	Optional<Users> rider=userService.findByEmailOrPhone(pickUpBookingInformationsDto.getEmailOrPhone());
        	PickUpBookingInformations pickUpBookingInformations;
            if(rider.isPresent()){
            	
            	Optional<TravelPath> travelPath=carpoolingService.findTravelPath(pickUpBookingInformationsDto.getDepartureCity(), pickUpBookingInformationsDto.getArrivalCity());
                
                if(travelPath.isPresent()){
                    Optional<Travel> travel=carpoolingService.findTravelById(pickUpBookingInformationsDto.getTravelId());
                    if(travel.isPresent()){
                        if(travel.get().getDriverId()!=rider.get().getId())
                        	pickUpBookingInformations = carpoolingService.savePickUpBookingInformations(new PickUpBookingInformations(pickUpBookingInformationsDto.getDepartureDate(),
                        			pickUpBookingInformationsDto.getPickupLocation(),
                        			pickUpBookingInformationsDto.getDepositLocation(),
                        			pickUpBookingInformationsDto.getTravelId(),
                        			pickUpBookingInformationsDto.getEmailOrPhone(),
                        			pickUpBookingInformationsDto.getDepartureCity(),
                        			pickUpBookingInformationsDto.getArrivalCity()));
                        
                        else
                            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseNew(404, "the driver is not allowed to book his trip", null));
                    }else{
                    	
                        //travel doesn't exist
                    	
                    	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(404, commonServices.findErrorByCode(404, pickUpBookingInformationsDto.getLanguage()), null));
                    }
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(200, commonServices.findErrorByCode(200, pickUpBookingInformationsDto.getLanguage()), pickUpBookingInformations));
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(404, commonServices.findErrorByCode(404, pickUpBookingInformationsDto.getLanguage()), null));
            
            
            
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(404, commonServices.findErrorByCode(404, pickUpBookingInformationsDto.getLanguage()), null));
        }
    }

    
    @Secured({"ROLE_ADMIN","ROLE_DRIVER"})
    @ApiOperation(value = "Démarrer un voyage..")
    @PostMapping("/startLongDistanceTrip")
    public ResponseEntity<?> startLongDistanceTrip (@Valid @RequestBody StartTripDto startTripDto){
        try{
            Optional<Travel> travel=carpoolingService.findTravelById(startTripDto.getTripId());
            if(travel.isPresent()){
                if(travel.get().getDriverEmail().equalsIgnoreCase(startTripDto.getEmailOrPhone()) || travel.get().getDriverPhone().equalsIgnoreCase(startTripDto.getEmailOrPhone())){
                    carpoolingService.startTrip(startTripDto.getTripId());
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(200, commonServices.findErrorByCode(200, startTripDto.getLanguage()),null));
                }
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(404,"no trip found", null));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(404, commonServices.findErrorByCode(404, startTripDto.getLanguage()), e));
        }
        
        
    }
    
    private CityCostDistanceDto turnTravelPathObjectIntoCityCostDistanceDtoOBject(TravelPath travelPath)
    {
    	
    	City city = geolocalisationServices.findByCityName(travelPath.getArrivalCity());
    	
    	
    	return new CityCostDistanceDto(travelPath.getArrivalCity(),city.getLongitude(),
    			city.getLatitude(),travelPath.getDefaultTravelCost(),
    			travelPath.getDefaultTravelDistance());
    	
    }
}
