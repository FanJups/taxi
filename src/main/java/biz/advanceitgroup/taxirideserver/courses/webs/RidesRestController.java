package biz.advanceitgroup.taxirideserver.courses.webs;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.authentification.repositories.UserRepository;
import biz.advanceitgroup.taxirideserver.authentification.services.impl.MailService;
import biz.advanceitgroup.taxirideserver.authentification.services.interfaces.UserService;
import biz.advanceitgroup.taxirideserver.commons.entities.ApiResponse;
import biz.advanceitgroup.taxirideserver.commons.entities.ResponseNew;
import biz.advanceitgroup.taxirideserver.commons.services.CommonServices;
import biz.advanceitgroup.taxirideserver.courses.dto.AcceptTripDTO;
import biz.advanceitgroup.taxirideserver.courses.dto.AvailableTripDTO;
import biz.advanceitgroup.taxirideserver.courses.dto.AvailablesDrivers;
import biz.advanceitgroup.taxirideserver.courses.dto.CancelTripDto;
import biz.advanceitgroup.taxirideserver.courses.dto.CurrentDriverLocationDTO;
import biz.advanceitgroup.taxirideserver.courses.dto.DriverAccept;
import biz.advanceitgroup.taxirideserver.courses.dto.DriverLocationOutputDto;
import biz.advanceitgroup.taxirideserver.courses.dto.DriversReadyDTO;
import biz.advanceitgroup.taxirideserver.courses.dto.LogRideEventDTO;
import biz.advanceitgroup.taxirideserver.courses.dto.NotificationsDTO;
import biz.advanceitgroup.taxirideserver.courses.dto.RateTRipDto;
import biz.advanceitgroup.taxirideserver.courses.dto.RidesDTO;
import biz.advanceitgroup.taxirideserver.courses.dto.StartTripDto;
import biz.advanceitgroup.taxirideserver.courses.dto.TripCostDto;
import biz.advanceitgroup.taxirideserver.courses.dto.TripInfo;
import biz.advanceitgroup.taxirideserver.courses.dto.TripLogsDto;
import biz.advanceitgroup.taxirideserver.courses.dto.TripOptionDto;
import biz.advanceitgroup.taxirideserver.courses.entities.CurrentDriverLocation;
import biz.advanceitgroup.taxirideserver.courses.entities.CurrentRideRequestLocation;
import biz.advanceitgroup.taxirideserver.courses.entities.DriverNotation;
import biz.advanceitgroup.taxirideserver.courses.entities.LogRideEvent;
import biz.advanceitgroup.taxirideserver.courses.entities.LogUserLocation;
import biz.advanceitgroup.taxirideserver.courses.entities.Messages;
import biz.advanceitgroup.taxirideserver.courses.entities.RiderNotation;
import biz.advanceitgroup.taxirideserver.courses.entities.Trip;
import biz.advanceitgroup.taxirideserver.courses.entities.TripOption;
import biz.advanceitgroup.taxirideserver.courses.services.interfaces.RidesServices;
import biz.advanceitgroup.taxirideserver.notifications.fcm.PushNotificationService;
import biz.advanceitgroup.taxirideserver.notifications.fcm.dto.Sms;
import biz.advanceitgroup.taxirideserver.notifications.services.PushNotificationServiceImpl;
import biz.advanceitgroup.taxirideserver.notifications.services.SmsService;
import biz.advanceitgroup.taxirideserver.payements.services.interfaces.PayementService;
import biz.advanceitgroup.taxirideserver.profiles.entities.UserPaymentMode;
import biz.advanceitgroup.taxirideserver.profiles.entities.Vehicle;
import biz.advanceitgroup.taxirideserver.profiles.services.interfaces.OfficialDocumentService;
import biz.advanceitgroup.taxirideserver.profiles.services.interfaces.UserPaymentModeService;
import biz.advanceitgroup.taxirideserver.profiles.services.interfaces.VehicleService;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Rest API For all request concern Ride
 * @author Merlin SAHA
 * @version 1.0, 12/11/2019
 * @see ApiResponse
 */

@Api(value = "Rides Rest API", description = "Ride rest API.")
@RestController
@RequestMapping("/api/rides")
public class RidesRestController {
	
	@Autowired
	private RidesServices ridesServices;
	
	 @Autowired
	 private SmsService smsservice;
	 
	 @Autowired
	 private PushNotificationService pushNotification;  
	 
	 
	 @Autowired
	 private UserService userService;
	 
	 @Autowired
	private MailService mailService;
	 
	 @Autowired
	 private UserPaymentModeService userPaymentModeService;
         
         @Autowired
         private UserRepository userRepository;
	
           @Autowired
         private OfficialDocumentService officialDocumentService;
	
         @Autowired
         private VehicleService vehicleService;
         
         @Autowired
         private CommonServices commonServices;
         
        @Autowired
        private PushNotificationServiceImpl pushNotificationServiceImpl;
        
        @Autowired
        private PayementService payementService;
        
	/**
     * Save the current position of a mobile terminal (Ok),
     * @param currentDriverLocationDTO
     * @return Current position
     * {@link CurrentDriverLocationDTO}
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN"}) 
    @ApiOperation(value = "Save the current position of a mobile terminal. this API also create new save ligne of location log")
    @PostMapping("/saveDriverLocation")
    public ResponseEntity<?> saveDriverLocation(@Valid @RequestBody CurrentDriverLocationDTO currentDriverLocationDTO){
        try{
        	
                	// Initialise Current Driver Location
        			CurrentDriverLocation currentDriverLocation = new CurrentDriverLocation();

        			currentDriverLocation.setCodePays(currentDriverLocationDTO.getCodePays());
        			currentDriverLocation.setVille(currentDriverLocationDTO.getVille());
        			currentDriverLocation.setDateEnreg(new Date());
        			currentDriverLocation.setEmailOrPhoneNumber(currentDriverLocationDTO.getEmailOrPhoneNumber());
        			currentDriverLocation.setLatitude(currentDriverLocationDTO.getLatitude());
        			currentDriverLocation.setLongitude(currentDriverLocationDTO.getLongitude());
                                //verifies if the rideid has been provided by the user or not
                                if(currentDriverLocationDTO.getRIDE_ID()!=null)
                                    currentDriverLocation.setRIDE_ID(currentDriverLocationDTO.getRIDE_ID());
                                currentDriverLocation.setDriverAvailable(currentDriverLocationDTO.getDriverAvailable());
                                //currentDriverLocation.setCodePays(currentDriverLocationDTO.getCodePays());
                                Optional<CurrentDriverLocation> driverLocation=ridesServices.findDriverLocation(currentDriverLocationDTO.getEmailOrPhoneNumber());
                                
                                /*verifies if the given driver has already saved his position
                                    if yes it just update the position and some informations like the rideId if anything has changed
                                    else it create an instance of the driver location in the CurrentDriverLocation Class
                                */
                                if(driverLocation.isPresent()){
                                    /*
                                    if the driver has already saved his position once, it checks if there is a change in the rideId
                                    and it uploads the value present at the given line in the table;
                                    */
                                   if(currentDriverLocationDTO.getRIDE_ID()!=null){
                                       
                                        ridesServices.updateDriverLocation(currentDriverLocation.getTimeoutDriverConnect(), currentDriverLocation.getCodePays(),currentDriverLocation.getVille(),currentDriverLocation.getLongitude(),currentDriverLocation.getLatitude(),currentDriverLocation.getPosition(),driverLocation.get().getId(),currentDriverLocation.getRIDE_ID(),currentDriverLocation.getDriverAvailable(),currentDriverLocation.getEmailOrPhoneNumber(),currentDriverLocation.getDateEnreg());
                                         LogUserLocation saveLog = ridesServices.saveUserLogLocation(new LogUserLocation(currentDriverLocation.getEmailOrPhoneNumber(), currentDriverLocation.getCodePays(), currentDriverLocation.getVille(), currentDriverLocation.getLongitude(), currentDriverLocation.getLatitude(), new Date(), currentDriverLocation.getRIDE_ID()));
                                   }else{
                                       ridesServices.updateDriverLocationWithoutRideId(currentDriverLocationDTO.getTimeoutDriverConnect(), currentDriverLocationDTO.getCodePays(), currentDriverLocationDTO.getVille(), currentDriverLocationDTO.getLongitude(), currentDriverLocationDTO.getLatitude(), currentDriverLocation.getPosition(), currentDriverLocationDTO.getEmailOrPhoneNumber(), new Date());
                                        LogUserLocation saveLog = ridesServices.saveUserLogLocation(new LogUserLocation(currentDriverLocation.getEmailOrPhoneNumber(), currentDriverLocation.getCodePays(), currentDriverLocation.getVille(), currentDriverLocation.getLongitude(), currentDriverLocation.getLatitude(), new Date(), driverLocation.get().getRIDE_ID()));
                                     }
                                   }else{
                                    if(currentDriverLocation.getRIDE_ID()==null)
                                        currentDriverLocation.setRIDE_ID((long)0);
                                    CurrentDriverLocation savePosition = ridesServices.saveDriverLocation(currentDriverLocation);
                                    //savePosition.setId(savePosition.getId());
                                    
                                    LogUserLocation saveLog = ridesServices.saveUserLogLocation(new LogUserLocation(savePosition.getEmailOrPhoneNumber(), savePosition.getCodePays(), savePosition.getVille(), savePosition.getLongitude(), savePosition.getLatitude(), new Date(),(long)0));
                                }
        			//Save location log
        			
        			/*LogUserLocation logUserLocation = new LogUserLocation();
                                Optional<LogUserLocation> lastLogUserLocation=ridesServices.getLastLogUserPosition(currentDriverLocationDTO.getEmailOrPhoneNumber());
        			logUserLocation.setCodePays(currentDriverLocationDTO.getCodePays());
        			logUserLocation.setCodeVille(currentDriverLocationDTO.getVille());
        			logUserLocation.setDateEnreg(new Date());
        			logUserLocation.setEmailOrPhoneNumber(currentDriverLocationDTO.getEmailOrPhoneNumber());
        			logUserLocation.setLatitude(currentDriverLocationDTO.getLatitude());
        			logUserLocation.setLongitude(currentDriverLocationDTO.getLongitude());
        			logUserLocation.setRIDE_ID(currentDriverLocationDTO.getRIDE_ID());

        			LogUserLocation saveLog = ridesServices.saveUserLogLocation(logUserLocation);
        			
        			saveLog.setId(saveLog.getId());*/

        			return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(commonServices.findErrorByCode(611,currentDriverLocationDTO.getLanguage()), true));
           
        }catch (Exception e){
        	
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(835,currentDriverLocationDTO.getLanguage()), false));
        }
    }
    
    /**
     * Save the taxi departure time, passenger recovery, end of transport, bill payment. (Ok)
     * @param logRideEventDTO
     * @return Current position
     * {@link LogRideEventDTO}
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "Save the taxi departure time, passenger recovery, end of race, bill payment..")
    @PostMapping("/saveRideEventDate")
    public ResponseEntity<?> saveRideEventDate(@Valid @RequestBody LogRideEventDTO logRideEventDTO){
        try{
        	
        			LogRideEvent logRideEvent = new LogRideEvent();
        			
        			logRideEvent.setEventDate(new Date());
        			logRideEvent.setEventType(logRideEventDTO.getEventType());
        			logRideEvent.setRideId(logRideEventDTO.getRIDE_ID());

        			LogRideEvent saveEvent = ridesServices.saveRideEventDate(logRideEvent);
        			//saveEvent.setId(saveEvent.getId());

                    return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(commonServices.findErrorByCode(612,logRideEventDTO.getLanguage()), true));
                
           
        }catch (Exception e){
        	
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(commonServices.findErrorByCode(837,logRideEventDTO.getLanguage()), false));
        }
    }
    
    /**
     * Notifications. (Ok)
     * @param notificationsDTO
     * @return Message
     * {@link NotificationsDTO}
     * @throws java.io.IOException
     * @throws freemarker.template.TemplateException
     */
    @ApiOperation(value = "Send notification..")
    @PostMapping("/sendNotification")
    public ResponseEntity<?> sendNotification(@Valid @RequestBody NotificationsDTO notificationsDTO) throws IOException, TemplateException{
        try{
        	
        	//find user
        
        	Users userOp;
        	Optional<Users> userByPhone =  userService.findByPhoneNumber(notificationsDTO.getPhoneNumber());
        	Optional<Users> userByEmail =  userService.findByEmail(notificationsDTO.getPhoneNumber());
            
        	if(userByPhone.isPresent()){
        		userOp  = userByPhone.get();
            }else
            if(userByEmail.isPresent()){
            	userOp  = userByEmail.get();
            }
            else{
                return new ResponseEntity<>(new ApiResponse("The user account does not exists on the system.", false),
                        HttpStatus.NOT_FOUND);
            }
        	
        	
	        	//send push notification
	    		/*if(notificationsDTO.getNotifType() == 0) {
	    			try {
            			Notifications notification = new Notifications("default", "Taxiride", notificationsDTO.getMessage());
            			Push push = new Push(userOp.getTokenFCM().getToken(), "high", notification);
            			//pushNotification.sendNotification(push);
            		
	        		} catch (Exception e) {
	    				return new ResponseEntity<>(new ApiResponse("Can't send push notification ", false),
	                            HttpStatus.NOT_FOUND);
	    			}
	    			
	    		}*/
	    		
	    		//email + push notification
        		if(notificationsDTO.getNotifType() == 1) {
        			//send email
        			try {
        				mailService.sendEmailNotification(notificationsDTO.getMessage(), userOp.getEmail());
        			} catch (IOException | TemplateException | MessagingException e) {
        				return new ResponseEntity<>(new ApiResponse("Can't send email notification to "+userOp.getEmail(), false),
                                HttpStatus.NOT_FOUND);
        			}
        			
        			//send push notification
        			/*try {
	            			Notifications notification = new Notifications("default", "Taxiride", notificationsDTO.getMessage());
	            			Push push = new Push(userOp.getTokenFCM().getToken(), "high", notification);
	            			pushNotification.sendNotification(push);
	            		
	        		} catch (Exception e) {
	    				return new ResponseEntity<>(new ApiResponse("Can't send push notification ", false),
	                            HttpStatus.NOT_FOUND);
	    			}*/
        		}

                //send SMS + push notification
        		if(notificationsDTO.getNotifType() == 2) {
        			
        			//sms
        			try {
	        			Sms smsRequest = new Sms();
	            		smsRequest.setPhoneNumber("+"+userOp.getContryCode()+""+userOp.getPhoneNumber());
	            		smsRequest.setMessage("Message: "+notificationsDTO.getMessage());
	            		smsservice.sendSmsCode(smsRequest);
	        		} catch (Exception e) {
	    				return new ResponseEntity<>(new ApiResponse("Can't send sms notification to +"+userOp.getContryCode()+""+userOp.getPhoneNumber(), false),
	                            HttpStatus.NOT_FOUND);
	    			}
            		
            		//push notification
            		/*try {
            			Notifications notification = new Notifications("default", "Taxiride", notificationsDTO.getMessage());
            			Push push = new Push(userOp.getTokenFCM().getToken(), "high", notification);
            			pushNotification.sendNotification(push);
            		
	        		} catch (Exception e) {
	    				return new ResponseEntity<>(new ApiResponse("Can't send push notification ", false),
	                            HttpStatus.NOT_FOUND);
	    			}*/
        		}
        		
        		//send email + SMS + Push notification
        		if(notificationsDTO.getNotifType() == 3) {
        			
        			//send sms
        			try {
	        			Sms smsRequest = new Sms();
	            		smsRequest.setPhoneNumber("+"+userOp.getContryCode()+""+userOp.getPhoneNumber());
	            		smsRequest.setMessage("Message: "+notificationsDTO.getMessage());
	            		smsservice.sendSmsCode(smsRequest);
	        		} catch (Exception e) {
	    				return new ResponseEntity<>(new ApiResponse("Can't send sms notification to +"+userOp.getContryCode()+""+userOp.getPhoneNumber(), false),
	                            HttpStatus.NOT_FOUND);
	    			}
            		
            		//send email
        			try {
        				mailService.sendEmailNotification(notificationsDTO.getMessage(), userOp.getEmail());
        			} catch (IOException | TemplateException | MessagingException e) {
        				return new ResponseEntity<>(new ApiResponse("Can't send email notification to "+userOp.getEmail(), false),
                                HttpStatus.NOT_FOUND);
        			}
        			//push notification
        			/*try {
            			Notifications notification = new Notifications("default", "Taxiride", notificationsDTO.getMessage());
            			Push push = new Push(userOp.getTokenFCM().getToken(), "high", notification);
            			pushNotification.sendNotification(push);
            		
	        		} catch (Exception e) {
	    				return new ResponseEntity<>(new ApiResponse("Can't send push notification ", false),
	                            HttpStatus.NOT_FOUND);
	    			}*/
        		}
        		
        	
        		
        		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Notification send successfully.", true));
                
           
        }catch (Exception e){
        	
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while sending notification.", false));
        }
    }
    
    /**
     * Valider la commande. (Ok)
     * requires personnal informations and payements methods preconfigured
     * @param ridesDTO
     * @return Current position
     * {@link ridesDTO}
     */
    @Secured({"ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "Valider la commande..")
    @PostMapping("/postRide")
    public ResponseEntity<?> postRide(@RequestBody RidesDTO ridesDTO){
           try{
               
                  //Find rider by phone number
        			Optional<Users> userByPhone =  userService.findByPhoneNumber(ridesDTO.getPhoneNumber());
        			
        			if(userByPhone.isPresent()){
                                    //InternalAccount internalAccount=userByPhone.get().getInternalAccount();
                                    //TripCostDto tripCostDto=ridesServices.getSampleTripCost("VIPsample",ridesDTO.getEstimatedTripLength(),ridesDTO.getEstimatedDuration(), ridesDTO.getCodePays());
                                   // if(internalAccount!=null && internalAccount.getUserAccountBalance()>tripCostDto.getTripCost()){
                                        
                                        //ridesDTO.setCodePays(userByPhone.get().getCodeTripOption());
                                        ridesDTO.setRayon(Long.parseLong(userByPhone.get().getDefaultRequestRadius()));
                                        ridesDTO.setTripCost(10d);
                                        ridesDTO.setAvailableCashAmount(1240L);
                                        ridesDTO.setCodeOption(userByPhone.get().getCodeTripOption());
                                        //ridesDTO.setCodePays(userByPhone.get().getContryCode());


                                            //Find user defaut payment mode
                                        Optional<UserPaymentMode> userPaymentMode =  userPaymentModeService.findDefaultPayment(userByPhone.get().getId());

                                        //Find trip option by ID
                                        Optional<TripOption> tripOption =  ridesServices.findTripOptionByCode(userByPhone.get().getDefaultTravelOption(),ridesDTO.getCodeVille());


                                        Trip trip = new Trip();

                                        // Rider's informations
                                        trip.setRider(userByPhone.get());
                                        trip.setRiderFirstName(userByPhone.get().getFirstName());
                                        trip.setRiderLastName(userByPhone.get().getLastName());
                                        trip.setRiderGenderCode(userByPhone.get().getGender());
                                        trip.setRiderEmail(userByPhone.get().getEmail());
                                        trip.setRiderPhone(userByPhone.get().getPhoneNumber());
                                        trip.setRiderBirthDate(userByPhone.get().getBirthDate());
                                        trip.setRiderAddress(userByPhone.get().getAddress());
                                        trip.setNotificationDistance(ridesDTO.getRayon());
                                        trip.setRiderLangCode(userByPhone.get().getLanguage());
                                        trip.setRiderLongitude(ridesDTO.getRiderLongitude());
                                        trip.setRiderLatitude(ridesDTO.getRiderLattitude());
                                        trip.setRiderLatitude(ridesDTO.getStartPointLatitude());
                                        trip.setArrivalAddress(ridesDTO.getArrivalPoint());
                                        trip.setPickupAddress(ridesDTO.getDeparturePoint());
                                       trip.setPickupLatitude(ridesDTO.getStartPointLatitude());
                                       trip.setPickupLongitude(ridesDTO.getStartPointLongitude());
                                       trip.setArrivalLatitude(ridesDTO.getEndPointLatitude());
                                       trip.setArrivalLongitude(ridesDTO.getEndPointLongitude());
                                        trip.setCityCode(ridesDTO.getCodeVille());
                                        trip.setTripCost(0);
                                        trip.setEstimatedTripDistance(ridesDTO.getEstimatedTripDistance());
                                        trip.setEstimatedTripDuration(ridesDTO.getEstimatedDuration());
                                        // Driver's informations
                                        //trip.setDriver(userByPhone.get());

                                        //Status
                                        trip.setTripStatus(0);

                                        //trip cost
                                        /*trip.setTripCost(ridesDTO.getTripCost());
                                        trip.setEstimatedTripDistance(ridesDTO.getRayon());
                                        trip.setEstimatedTripDuration(ridesDTO.getEstimatedDuration());
                                        trip.setTripCostCalculFormula(tripOption.get().getTripCostCalculFormula());
                                        trip.setTripCancelCostCalculFormula(tripOption.get().getTripCancelCostCalculFormula());
                                        trip.setTripReservationCostCalculFormula(tripOption.get().getTripReservationCostCalculFormula());*/

                                        // Informations sur le mode de paiement
                                        /*trip.setPaymentMode(userPaymentMode.get().getPaymentMode());
                                        trip.setPayementModeName(userPaymentMode.get().getPaymentMode().getNameEn());
                                        trip.setPayementModeValue(userPaymentMode.get().getValue());
                                        trip.setPaymentStatus(0);
*/

                                        //L'option de course
                                        if(tripOption.isPresent()){
                                            trip.setTripOption(tripOption.get());
                                            trip.setTripOptionCode(tripOption.get().getOptionCode());
                                            trip.setTripOptionName(tripOption.get().getOptionName());
                                            trip.setTripOptionBaseFare(tripOption.get().getBaseFare());
                                            trip.setTripOptionMinuteRate(tripOption.get().getMinuteRate());
                                            trip.setTripOptionKilometerRate(tripOption.get().getKilometerRate());
                                            trip.setTripOptionFeeFormula(tripOption.get().getTripFeeFormula());
                                            trip.setTripOptionCancelFeeFormula(tripOption.get().getRiderCancelFeeFormula());
                                            trip.setTripOptionReservationFeeFormula(tripOption.get().getReservationFeeFormula());
                                            trip.setTripOptionCommission(tripOption.get().getTripCommission());
                                            trip.setTripOptionDriverCancelFeeFormula(tripOption.get().getDriverCancelFeeFormula());
                                            trip.setTripOptionRiderCancelFeeFormula(tripOption.get().getRiderCancelFeeFormula());
                                        }

                                        // Informations sur le pays
                                        trip.setCountry(ridesDTO.getCodePays());


                                        // Informations sur la ville
                                        //trip.setCity(ridesDTO.getCodeVille());

                                      //Faire une insertion dans la table Trip
                                            Trip saveTrip = ridesServices.postRide(trip);


                                            //Faire une insertion dans la table current_riderequest_location
                                            CurrentRideRequestLocation currentriderequestlocation = new CurrentRideRequestLocation(); 
                                            currentriderequestlocation.setCodePays(ridesDTO.getCodePays());
                                            currentriderequestlocation.setVille(ridesDTO.getCodeVille());
                                            currentriderequestlocation.setDateEnreg(new Date());
                                            currentriderequestlocation.setTripId(saveTrip.getTripID());
                                            currentriderequestlocation.setLatitude(ridesDTO.getStartPointLatitude());
                                            currentriderequestlocation.setLongitude(ridesDTO.getStartPointLongitude());
                                            //currentriderequestlocation.setRideDurationBeforePickUp(ridesDTO.getEstimatedDuration());
                                            //currentriderequestlocation.setRideID(userByPhone.get().getId());
                                            currentriderequestlocation.setDriverID(0);
                                            currentriderequestlocation.setEmailorphone(ridesDTO.getPhoneNumber());
                                            currentriderequestlocation.setTripOptionID(userByPhone.get().getCodeTripOption());

                                            CurrentRideRequestLocation request=ridesServices.saveCurrentRideRequestLocation(currentriderequestlocation);
                                            //pushNotificationServiceImpl.sendPushNotificationToToken(new Notifications("hello","test notifications","hello",userByPhone.get().getTokenFcm()));

                                            //list available drive
                                            //findAllAvailableDrivers(ridesDTO.getCodePays(), ridesDTO.getCodeVille(), ridesDTO.getStartPointLongitude(), ridesDTO.getStartPointLatitude(), ridesDTO.getRayon());

                                            //list all driver and language

                                            //Récupérer dans les paramètres, le message à envoyer comme notification dans chacune des langues recensées
                                            Optional<Messages> messages =  ridesServices.findAllNotificationMessageForTrip(1);

                                            //+ Former autant de groupes de destinataires que de langues, et envoyer à chaque groupe une 
                                            // notification push de masse dans la langue de ce groupe, en un seul appel de FCM.

                                            //French
                                            /*try {

                                                    List<String> tokens = new ArrayList<>();
                                                    List<Users> users = userService.findByLanguage("fr");

                                                    users.forEach(p -> tokens.add(p.getTokenFCM().getToken()));

                                                    Notifications notification = new Notifications("default", "Taxiride new trip", messages.get().getVALUE_FR());
                                                    Push push = new Push("high", notification, tokens);
                                                    pushNotification.sendNotification(push);

                                            } catch (Exception e) {
                                                    return new ResponseEntity<>(new ApiResponse("Can't send push notification ", false),
                                                HttpStatus.NOT_FOUND);
                                            }*/

                                            //English
                                            TripCostDto tripCostDto=new TripCostDto();
                                            tripCostDto.setRequestId(request.getRideID());
                                            tripCostDto.setTripId(saveTrip.getTripID());
                                            tripCostDto.setTripCost((long)ridesServices.getTripCost(saveTrip.getTripID()));
                                            tripCostDto.setErrorRadius((int)commonServices.findParameter("error_radius"));
                                            //tripCostDto.setErrorRadius(commonServices.);
                                            //tripCostDto.setCancelTripCostByDriver((long)ridesServices.getCanceTripCost(saveTrip, 1));
                                            tripCostDto.setCancelTripCostByRider((long)ridesServices.getCanceTripCost(saveTrip, 0));
                                        return ResponseEntity.status(HttpStatus.OK).body(tripCostDto);
                                        
                                    //}
                                   // return ResponseEntity.status(HttpStatus.PRECONDITION_REQUIRED).body(new ResponseNew(854,commonServices.findErrorByCode(853,ridesDTO.getLanguage()),null));
                
                    }
                    else{
                        return new ResponseEntity<>(new ApiResponse("The user account does not exists on the system.", false),
                                HttpStatus.NOT_FOUND);
                    }
        			
           
        }catch (Exception e){
        	
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while saving ride." +e.getMessage(), false));
        }

    }
    
    /**
     * listing de tous les drivers disponible dans un certain rayon d'un point Gmap. (Ok)
     * @param codePays
     * @param ville
     * @param longitude
     * @param latitude
     * @param rayon
     * @return Available drivers
     */
    @Secured({"ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "listing de tous les drivers disponible dans un certain rayon d'un point Gmap..")
    @GetMapping("/findAllAvailableDrivers/{tripId}/{codePays}/{ville}/{longitude}/{latitude}/{rayon}/{language}")
    @ResponseBody
    public ResponseEntity<?> findAllAvailableDrivers(@PathVariable long tripId ,@PathVariable String codePays, @PathVariable String ville, @PathVariable double longitude, @PathVariable double latitude, @PathVariable long rayon, @PathVariable String language){
        try{
        	
        	//Check driver ID
        	Optional<CurrentRideRequestLocation> driverExist = ridesServices.checkDriverExist(tripId);
            
            if(driverExist.isPresent()) {
            	//Find Driver by id
    			
    			// informations about the driver who accepted the transport
    			List<DriverNotation> dnotation =  ridesServices.findAllDriverReviews(driverExist.get().getDriverID());
    			Optional<Vehicle> vehicle=vehicleService.findByUserId(driverExist.get().getDriverID());
    			Optional<Users> user =  userService.findById(driverExist.get().getDriverID());
                        Optional<CurrentDriverLocation> currentDriverLocation=ridesServices.findCurrentRideDriverLocation(driverExist.get().getTripId());
                       
                        //informations about the surrounding drivers who didn't accepted the transport
                        List<CurrentDriverLocation> drivers = ridesServices.findAllAvailableDrivers(codePays, ville, longitude, latitude, rayon);
                        List<DriversReadyDTO> driversInfo =new ArrayList<>();
                        drivers.forEach((CurrentDriverLocation driverPresent) -> {
                            Optional<Users>  users=userService.findByEmailOrPhone(driverPresent.getEmailOrPhoneNumber());
                            Optional<CurrentDriverLocation> currentDriverPresentLocation=ridesServices.findDriverLocation(driverPresent.getEmailOrPhoneNumber());
                                if (users.isPresent() && currentDriverPresentLocation.isPresent()) {

                                    driversInfo.add(new DriversReadyDTO(currentDriverLocation.get().getLatitude(),currentDriverLocation.get().getLongitude(),user.get().getCodeTripOption()));
                                }
                        });
                        
                        if(vehicle.isPresent() && user.isPresent() && currentDriverLocation.isPresent())               
                            return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(200, commonServices.findErrorByCode(200,language),new AvailablesDrivers(new DriverAccept(user.get().getFirstName(), user.get().getLastName(),user.get().getPhoneNumber(),user.get().getProfilePictureMimeType(), currentDriverLocation.get().getLongitude(), currentDriverLocation.get().getLatitude(), user.get().getIsVerified(), dnotation.listIterator(), currentDriverLocation.get().getDuration(), vehicle.get().getModel(), vehicle.get().getMatriculationNumber(), "null", currentDriverLocation.get().getDistance(), driverExist.get().getDistance(),driverExist.get().getRideID()),driversInfo)));
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(853, commonServices.findErrorByCode(853,language), new ApiResponse(commonServices.findErrorByCode(853,language), Boolean.FALSE)));
            } else {
            	//List all available driver
            	List<CurrentDriverLocation> drivers = ridesServices.findAllAvailableDrivers(codePays, ville, longitude, latitude, rayon);
                List<DriversReadyDTO> driversInfo =new ArrayList<>();
                drivers.forEach((CurrentDriverLocation driver) -> {
                    Optional<Users>  user=userService.findByEmailOrPhone(driver.getEmailOrPhoneNumber());
                    Optional<CurrentDriverLocation> currentDriverLocation=ridesServices.findDriverLocation(driver.getEmailOrPhoneNumber());
                        if (user.isPresent() && currentDriverLocation.isPresent()) {
                    
                            driversInfo.add(new DriversReadyDTO(currentDriverLocation.get().getLatitude(),currentDriverLocation.get().getLongitude(),user.get().getCodeTripOption()));
                        }
                });
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(200, commonServices.findErrorByCode(200,language), new AvailablesDrivers(null, driversInfo)));
            	}    
        }catch (Exception e){
        	
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), false));
        }
    }
    
    
    /**
     * Lister toutes les commandes disponibles environnantes du driver. (Ok)
     * @param emailorphone
     * @param lattitude
     * @param countryCode
     * @param rayon
     * @param city
     * @return Available Trip
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN"})
    @ApiOperation(value = "Lister toutes les commandes disponibles environnantes du driver")
    @GetMapping("/findAllAvailableTrip/{emailorphone}/{longitude}/{lattitude}/{countryCode}/{city}/{rayon}")
    @ResponseBody
    public ResponseEntity<?> findAllAvailableTrip(@PathVariable String emailorphone, @PathVariable double lattitude, @PathVariable double longitude, @PathVariable String countryCode, @PathVariable String city,  @PathVariable double rayon){
       try{
        	
        	//Lire l'ID de l'option de voyage paramétrée sur ce rider
        	//Optional<Users> rider =  userRepository.findByPhoneNumber(emailorphone);
        	//String trip_optionid = rider.get().getCodeTripOption();
                CurrentDriverLocationDTO currentDriverLocationDTO=new CurrentDriverLocationDTO(emailorphone,true,0,countryCode,city,longitude,lattitude);
                ridesServices.updateDriverLocationWithoutRideId(currentDriverLocationDTO.getTimeoutDriverConnect(), currentDriverLocationDTO.getCodePays(), currentDriverLocationDTO.getVille(), currentDriverLocationDTO.getLongitude(), currentDriverLocationDTO.getLatitude(), currentDriverLocationDTO.getLatitude()+" "+currentDriverLocationDTO.getLongitude(),  currentDriverLocationDTO.getEmailOrPhoneNumber(), new Date());
        	
            //List all available ride
            List<CurrentRideRequestLocation> rides = ridesServices.findAllAvailableTrip(lattitude,longitude,countryCode,city,rayon);
            List<AvailableTripDTO> infoTrip=new ArrayList<>();
            rides.forEach((ride) -> {
                
                Optional<Users>  user=userService.findByEmailOrPhone(ride.getEmailorphone());
                Optional<Trip> trip=ridesServices.findTripById(ride.getTripId());
                //List<DriverNotation> reviews=new ArrayList<>();
                //if(ridesServices.findAllDriverReviews(ride.getDriverID()).isEmpty())reviews=ridesServices.findAllDriverReviews(ride.getDriverID());
                
                if (user.isPresent() && trip.isPresent()) {
                     infoTrip.add(new AvailableTripDTO((user.get().getFirstName()+" "+user.get().getLastName()), trip.get().getTripCost(), ride.getDistance(),trip.get().getPickupAddress() ,trip.get().getArrivalAddress(),user.get().getImageUrl(),ride.getRideID(),ride.getTripId(),ride.getIsInTheRange(),trip.get().getPickupLatitude(),trip.get().getPickupLongitude()));

                }/*else{
                    infoTrip.add(new AvailableTripDTO((user.get().getFirstName()+" "+user.get().getLastName()), 124d, ride.getDistance(),trip.get().getPickupAddress() ,trip.get().getArrivalAddress(),user.get().getImageUrl(),ride.getRideID(),ride.getTripId()));
                }*/
           });
            return ResponseEntity.status(HttpStatus.OK).body(infoTrip);
           
                
           
        }catch (Exception e){
        	
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getLocalizedMessage()+" "+e.getCause(), false));
        }
    }
    
    
    /**
     * Accetper une course. (Ok)
     * @param acceptTripDTO
     * @param AcceptTripDTO
     * @return True
     * {@link AcceptTripDTO}
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN"})
    @ApiOperation(value = "Accept trip by driver")
    @PostMapping("/acceptTrip")
    public ResponseEntity<?> acceptTrip(@Valid @RequestBody AcceptTripDTO acceptTripDTO){
        try{
        	
        	//Find trip
			//Optional<Trip> trip =  ridesServices.findTripById(acceptTripDTO.getTripID());
			
			//Find Driver
			Optional<Users> driver =  userService.findByPhoneNumber(acceptTripDTO.getEmailorphone());
			
			//Vérifier que cette course n'est pas déjà acceptée
			//Check driver ID
        	Optional<CurrentRideRequestLocation> driverExist = ridesServices.checkIfDriverExist(acceptTripDTO.getTripID());

                if(driverExist.isPresent()) {

                    if(driverExist.get().getDriverID()!=0)
                    //Si course déjà accepté
                            return ResponseEntity.status(HttpStatus.OK).body("Trip already accepted by other driver");

                else{
                    
                    Optional<Users> rider=userService.findByEmailOrPhone(driverExist.get().getEmailorphone());
                    if(rider.isPresent()&& driver.isPresent()){
                        
                        if(rider.get().getEmail().equalsIgnoreCase(driver.get().getEmail()) || rider.get().getPhoneNumber().equalsIgnoreCase(driver.get().getPhoneNumber())){
                            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(new ApiResponse("a user cannot accept a trip that he posted", Boolean.FALSE));
                            
                        }else{
                                  //composition de la place de début du driver
                                String driverStartAddress = "("+acceptTripDTO.getLatitude()+","+acceptTripDTO.getLongitude()+")";

                                //Mise à jour de la table trip
                                if(driver.isPresent()){
                                ridesServices.acceptRideUpdateDriver(
                                                driverStartAddress, 
                                                driver.get().getEmail(), 
                                                driver.get().getPhoneNumber(), 
                                                acceptTripDTO.getLatitude(), 
                                                acceptTripDTO.getLongitude(), 
                                                2, 
                                                acceptTripDTO.getTripID(),acceptTripDTO.getDistanceBeforePickup());

                                //Acceptation de la course en Mettant à jour le champ driverID dans CurrentTripRequestLocation et tripID dans CurrentDrivertLocation
                                ridesServices.acceptRide(
                                                driver.get().getPhoneNumber(), 
                                                driver.get().getId(), acceptTripDTO.getTripID(), 
                                                acceptTripDTO.getRequestid(),new Date(), acceptTripDTO.getLongitude(), acceptTripDTO.getLatitude(),acceptTripDTO.getDurationBeforePickUp());}
                                //ridesServices.updateDriverLocationForAcceptTrip(0,acceptTripDTO.getLongitude(),acceptTripDTO.getLatitude(),driverStartAddress,new Date(),acceptTripDTO.getTripID(),false,acceptTripDTO.getEmailorphone());

                                 return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Trip accepted.", true));
                        }
                    } 
                   return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("the rider doesn't existe anymore", Boolean.FALSE));
                }	
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("the trip doesn't exist", Boolean.FALSE));
        }catch (Exception e){
        	
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while accepting trip."+e.getMessage(), false));
        }
    }
    
    /**
     * Récupérer la position courente d'un driver. (Ok)
     * @param emailOrPhone
     * @return  Driver location data
     */
    @Secured({"ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "Récupérer la position courente d'un driver")
    @GetMapping("/findDriverLocation/{emailorphone}/{language}")
    @ResponseBody
    public ResponseEntity<?> findDriverLocation(@PathVariable String emailorphone,@PathVariable String language){
        try{
        	
        	//Check driver 
        	Optional<CurrentDriverLocation> driver = ridesServices.findDriverLocation(emailorphone);
            
            if(driver.isPresent()) {
                
                int status=ridesServices.isTripEnd(driver.get().getRIDE_ID());
                
                if(status==3)
                    
                    return ResponseEntity.status(HttpStatus.OK).body(new DriverLocationOutputDto(driver.get().getLongitude(), driver.get().getLongitude(), true, driver.get().getCodePays(), driver.get().getVille()));
                
                return ResponseEntity.status(HttpStatus.OK).body(new DriverLocationOutputDto(driver.get().getLongitude(), driver.get().getLongitude(), false, driver.get().getCodePays(), driver.get().getVille()));
            } else {
            				
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Can't get driver location.", false));
            }
        	 
           
        }catch (Exception e){
        	
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Can't get driver location." + e, false));
        }
    }
    
    /**
     * Récupérer la liste des options par ville. (Ok)
     * @param countryCode: String, cityCode: String
     * @param cityCode
     * @return  Liste des options remplissant les conditions(paramètres)
     */
    @Secured({"ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "Récupérer la position courente d'un driver")
    @GetMapping("/findAvailableOptionsByCity/{countryCode}/{cityCode}")
    @ResponseBody
    public ResponseEntity<?> findAvailableOptionsByCity(@PathVariable String countryCode, @PathVariable String cityCode){
        try{
        	
        	List<String> option = ridesServices.findAvailableOptionsByCity(countryCode, cityCode);
                List<TripOptionDto> tripOptionDtos=new ArrayList<>();
                if(!option.isEmpty())
                    option.forEach((e) -> {
                        tripOptionDtos.add(new TripOptionDto(e));
                });
        	return ResponseEntity.status(HttpStatus.OK).body(tripOptionDtos);
           
        }catch (Exception e){
        	
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Can't get city option for this country."+e.getMessage(), false));
        }
		
    }

    /**
     *find all the informations of a trip created within a period
     * @param id
     * @param startDate
     * @param endDate
     * @return
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "find all the trips by a user within a giiven period")
    @GetMapping("/findAllTripByUser/{user_id}/{startDate}/{endDate}/{language}")
    @ResponseBody
    public ResponseEntity<?> findAllTrip(@PathVariable("user_id") String id,@PathVariable("startDate")@DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,@PathVariable("endDate")@DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,@PathVariable("language") String language){
        try {
            
            //list of all the trips held by a driver
            List<Trip> listTrips=ridesServices.findAllTrip(id,startDate,endDate);
            List<TripLogsDto> tripLogs=new ArrayList();
            if(listTrips.isEmpty())
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseNew(204, commonServices.findErrorByCode(204,language), null));
            listTrips.forEach((trip) -> {
                tripLogs.add(new TripLogsDto(trip.getPickupAddress(),trip.getArrivalAddress(),trip.getCreatedAt(),trip.getTripCost(),trip.getEstimatedTripDuration(),trip.getEstimatedTripDistance(),trip.getTripOptionCode(),trip.getPayementModeName(),trip.getDriverFirstName(), trip.getRiderFirstName(),trip.getTripID()));
            });
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(200, commonServices.findErrorByCode(200,language),tripLogs ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(404, commonServices.findErrorByCode(404,language), null));
        }
    }
    
    /**
     *find a trip information 
     * @param id
     * @return
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "find all the informations of a given trip")
    @GetMapping("/findTripInfo/{trip_id}/{language}")
    @ResponseBody
    public ResponseEntity<?> findTripInfo(@PathVariable("trip_id") Long id,@PathVariable("language") String language){
        try{
            //optional containing the trip info(may contain one or zero element)
            
            Optional<Trip> trip=ridesServices.findTripById(id);
            if(trip.isPresent())
                return ResponseEntity.status(HttpStatus.OK).body(new TripInfo(trip.get().getPickupLatitude(),trip.get().getPickupLongitude(), trip.get().getArrivalLongitude(), trip.get().getArrivalLatitude(),trip.get().getArrivalAddress(), trip.get().getPickupAddress(),trip.get().getTripCost()));
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(commonServices.findErrorByCode(204,language),true));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(404,language),false));
        }
    }
    @Secured({"ROLE_DRIVER","ROLE_ADMIN"})
    @ApiOperation(value = "start a trip by driver")
    @PostMapping("/startTrip")
    public ResponseEntity<?> startTrip(@Valid @RequestBody StartTripDto startTripDto){
        try{
            //put the field trip status to 3 and update driver location in the table currentDriverLocation
            ridesServices.startTrip(startTripDto.getTripId(), startTripDto.getEmailOrPhone(), startTripDto.getLongitude(), startTripDto.getLatitude());
            //saves tracks of a rides and put the field even_type to 2 ie:the trip has started
            ridesServices.saveRideEventDate(new LogRideEvent(new Date(),3,startTripDto.getTripId()));
            
            //remains check the variables according to the notifications which will allow us to send push notifications or not
            return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(commonServices.findErrorByCode(200,startTripDto.getLanguage()),true));
            
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(404,startTripDto.getLanguage()),false));
        }
    }
    
    /*
    
    
    
    
    
    
    
    */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN"})
    @ApiOperation(value = "End a trip by driver")
    @PostMapping("/endTrip")
    public ResponseEntity<?> endTrip(@Valid @RequestBody StartTripDto endTripDTo){
        try{
            Optional<Trip> trip= ridesServices.findTripById(endTripDTo.getTripId());
            if(trip.isPresent()){
                switch (trip.get().getTripStatus()){
                        case 3:
                            if(endTripDTo.getEmailOrPhone().equals(trip.get().getDriverEmail())||endTripDTo.getEmailOrPhone().equals(trip.get().getDriverPhone())){
                                ridesServices.endTrip(endTripDTo.getTripId(), endTripDTo.getEmailOrPhone(),endTripDTo.getLongitude() , endTripDTo.getLatitude(), 1);
                                ridesServices.saveRideEventDate(new LogRideEvent(new Date(),4,endTripDTo.getTripId())); 
                                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(commonServices.findErrorByCode(204,endTripDTo.getLanguage()),true));
                            } 
                        break;
                        default:
                             return ResponseEntity.status(HttpStatus.LOCKED).body(new ApiResponse(commonServices.findErrorByCode(404,endTripDTo.getLanguage()),false));
                }
                
            }
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(new ApiResponse(commonServices.findErrorByCode(404,endTripDTo.getLanguage()),false));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(404,endTripDTo.getLanguage()),false));
        }
    }
    @Secured({"ROLE_DRIVER","ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "Cancel a trip by a user")
    @PostMapping("/cancelTrip")
    public ResponseEntity<?> cancelTrip(@Valid @RequestBody CancelTripDto cancelTripDto){
        try{
            Optional<Trip> trip=ridesServices.findTripById(cancelTripDto.getTripId());
            if(trip.isPresent()){
                if(trip.get().getTripStatus()==1)
                    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(commonServices.findErrorByCode(403,cancelTripDto.getLanguage()));       
                if(cancelTripDto.getEmailOrPhone().equals(trip.get().getDriverEmail()) || cancelTripDto.getEmailOrPhone().equals(trip.get().getDriverPhone())){
                    ridesServices.cancelTrip(cancelTripDto.getTripId(), cancelTripDto.getEmailOrPhone(),1);
                    ridesServices.saveRideEventDate(new LogRideEvent(new Date(), 4,cancelTripDto.getTripId()));
                    return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(commonServices.findErrorByCode(200,cancelTripDto.getLanguage()),true));
                }else{
                    ridesServices.cancelTrip(cancelTripDto.getTripId(),cancelTripDto.getEmailOrPhone(), 0);
                    ridesServices.saveRideEventDate(new LogRideEvent(new Date(),4,cancelTripDto.getTripId()));
                    return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(commonServices.findErrorByCode(200,cancelTripDto.getLanguage()),true));
                }
            } 
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(404,cancelTripDto.getLanguage())+" the trip id wasn't found",false));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(404,cancelTripDto.getLanguage()),false));
        }
    }
    
    @Secured({"ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "get a trip cost")
    @GetMapping("/getTripCost/{tripId}/{language}")
    @ResponseBody
    public ResponseEntity<?> getTripCost(@PathVariable("tripId") long tripId,@PathVariable("language") String language){
        try{
            //this portion of code is supposed to compute transpot  fees througn a formula written in the tripOption table;
            TripCostDto tripCostDto=new TripCostDto();
            tripCostDto.setTripCost((long)ridesServices.getTripCost(tripId));
            return ResponseEntity.status(HttpStatus.OK).body(tripCostDto);
           
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(404,language),false));
        }
    }
    @Secured({"ROLE_DRIVER","ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "get a trip cost for cancellation")
    @GetMapping("/getCancelTripCost/{emailOrPhone}/{countryCode}/{city}/{codeOpation}/{tripId}/{language}")
    @ResponseBody
    public ResponseEntity<?> getCancelTripCost(@PathVariable("emailOrPhone") String emailOrPhone,@PathVariable("countryCode") String countryCode,@PathVariable("city") String city,@PathVariable("codeOpation") String codeOpation,@PathVariable("tripId")long tripId,@PathVariable("language") String language){
        try{
            //this portion of code is supposed to compute transpot  fees througn a formula written in the tripOption table;
            Optional<Trip> trip=ridesServices.findTripById(tripId);
            if(trip.isPresent()){     
                if(emailOrPhone.equals(trip.get().getDriverEmail()) || emailOrPhone.equals(trip.get().getDriverPhone())){
                    long cost=(long)ridesServices.getCanceTripCost(trip.get(), 1);
                    TripCostDto tripCostDto=new TripCostDto();
                    tripCostDto.setCancelTripCostByDriver(cost);
                    return ResponseEntity.status(HttpStatus.OK).body(tripCostDto);
                }else{
                    long cost=(long)ridesServices.getCanceTripCost(trip.get(), 2);
                    TripCostDto tripCostDto=new TripCostDto();
                    tripCostDto.setCancelTripCostByRider(cost);
                    return ResponseEntity.status(HttpStatus.OK).body(tripCostDto);
                }
            }else{
              return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(404,language)+"\n the trip was not found",false));
            } 
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(404,language)+"\n an erreur occured in the treatment;",false));
        }
    }
    
    /*
    *get Sample Trip Cost
    *
    */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "get a trip cost for cancellation")
    @GetMapping("/getSampleTripCost/{optionCode}/{estimatedDistance}/{estimatedDuration}/{cityCode}/{language}")
    @ResponseBody
    public ResponseEntity<?> getCancelTripCost(@PathVariable("optionCode") String optionCode,@PathVariable("estimatedDistance") long estimatedDistace,@PathVariable("estimatedDuration") long estimatedDuration,@PathVariable("cityCode") String cityCode,@PathVariable("language") String language){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(ridesServices.getSampleTripCost(optionCode, estimatedDistace, estimatedDuration,cityCode));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(404,language),false));
        }
    }
    
     @Secured({"ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "get the status of a trip at a given moment")
    @GetMapping("/isEndTrip/{tripId}/{language}")
    @ResponseBody
    public ResponseEntity<?> isEndTrip(@PathVariable("tripId") long tripId,@PathVariable("language") String language){
     try{
         
         int tripStatus=ridesServices.isTripEnd(tripId);
         
         if(tripStatus==4)
             return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("true", Boolean.TRUE));
         return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(new ApiResponse(commonServices.findErrorByCode(748, language), Boolean.FALSE));
     }catch(Exception e){
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(404, language),false));
     }
    }
    
    
    @Secured({"ROLE_DRIVER","ROLE_ADMIN"})
    @ApiOperation(value = "rate a trip by a driver")
    @PostMapping("/postRateByDriver")
    public ResponseEntity<?> postRateByDriver(@Valid @RequestBody RateTRipDto rateTRipDto){
        try{
            Optional<Trip> trip=ridesServices.findTripById(rateTRipDto.getTripId());
            if(trip.isPresent()){
                ridesServices.saveDriverNotation(new DriverNotation(rateTRipDto.getUserId(), rateTRipDto.getRate(), rateTRipDto.getComment(), new Date(), rateTRipDto.getTripId(), trip.get().getRider().getId()));
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseNew(626, commonServices.findErrorByCode(626, rateTRipDto.getLanguage()),null));
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(858, commonServices.findErrorByCode(858,rateTRipDto.getLanguage()), null));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(854, commonServices.findErrorByCode(854, rateTRipDto.getLanguage()), e));
        }
    }
    
    @Secured({"ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "rate a trip by a rider")
    @PostMapping("/postRateByRider")
    public ResponseEntity<?> postRateByRider(@Valid @RequestBody RateTRipDto rateTRipDto){
        try{
             Optional<Trip> trip=ridesServices.findTripById(rateTRipDto.getTripId());
            if(trip.isPresent()){
                ridesServices.saveRiderNotation(new RiderNotation(rateTRipDto.getUserId(), rateTRipDto.getRate(), rateTRipDto.getComment(), new Date(), rateTRipDto.getTripId(), trip.get().getDriver().getId()));
            }
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(858, commonServices.findErrorByCode(858,rateTRipDto.getLanguage()), null));
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseNew(855, commonServices.findErrorByCode(855, rateTRipDto.getLanguage()), e));
        }    
    } 
}
