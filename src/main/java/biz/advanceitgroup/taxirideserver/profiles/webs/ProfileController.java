package biz.advanceitgroup.taxirideserver.profiles.webs;

import biz.advanceitgroup.taxirideserver.commons.entities.ApiResponse;
import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.authentification.services.interfaces.UserService;
import biz.advanceitgroup.taxirideserver.commons.helpers.TaxiRideCommonServices;
import biz.advanceitgroup.taxirideserver.commons.helpers.TaxiRideStorageServices;
import biz.advanceitgroup.taxirideserver.commons.services.CommonServices;
import biz.advanceitgroup.taxirideserver.commons.webs.CommonController;
import biz.advanceitgroup.taxirideserver.payements.services.interfaces.PayementService;
import biz.advanceitgroup.taxirideserver.profiles.dto.*;
import biz.advanceitgroup.taxirideserver.profiles.entities.*;
import biz.advanceitgroup.taxirideserver.profiles.services.helpers.ProfileServices;
import biz.advanceitgroup.taxirideserver.profiles.services.interfaces.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.security.access.annotation.Secured;


/**
 * Cette classe regroupe l'ensemble des API permettant la gestion de profile d'un utilisateur
 * de la plateforme TaxiRide. Pour chacune des APIs, la réponse est soit l'élement recherché ou
 * une réponse générique sous forme de l'objet ApiResponse.
 * @author Simon Ngang
 * @version 1.0, 16/09/2019
 * @see ApiResponse
 */

@Api(value = "Profile Rest API", description = "Définition des API permettant la gestion du profile d'un utilisateur.")
@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    TaxiRideCommonServices taxiRideCommonServices;

    @Autowired
    TaxiRideStorageServices taxiRideStorageServices;

    @Autowired
    OfficialDocumentService officialDocumentService;

    @Autowired
    PaymentModeService paymentModeService;

    @Autowired
    EmergencyContactService emergencyContactService;

    @Autowired
    PromotionalCodeService promotionalCodeService;

    @Autowired
    UserPaymentModeService userPaymentModeService;

    @Autowired
    UserPromotionalCodeService userPromotionalCodeService;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    VehiclePictureService vehiclePictureService;
    
    @Autowired
    CommonServices commonServices;
    
    @Autowired
    PayementService payementService;

    private static final Logger logger = Logger.getLogger(CommonController.class);


    /**
     * Cette méthode permet de rechercher un utilisateur suivant son numéro de téléphone. (Ok)
     * @param phone
     * @return l'utilisateur ayant le numéro de téléphone passé en paramètre ou une ApiResponse.
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN","ROLE_RIDER"})
    @GetMapping("/findbyphonenumber/{phone}/{language}")
    @ApiOperation(value = "Recherche d'un utilisateur suivant son numéro de téléphone.")
    public ResponseEntity<?> findUserByPhoneNumber(@PathVariable("phone") String phone,@PathVariable("language") String language){
        try{
            Optional<Users> user =  userService.findByPhoneNumber(phone);
            if(user.isPresent()){
                return new ResponseEntity<Users>(user.get(),
                        HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(722, language), false),
                        HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(845, language), false),
                    HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Cette méthode permet de rechercher un utilisateur suivant son adresse mail. (Ok)
     * @param email
     * @return l'utilisateur ayant le numéro de téléphone passé en paramètre ou une ApiResponse.
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN","ROLE_RIDER"})
    @GetMapping("/findbyemail/{email}/{language}")
    @ApiOperation(value = "Recherche d'un utilisateur suivant son adresse mail.")
    public ResponseEntity<?> findUserByEmail(@PathVariable("email") String email,@PathVariable("language") String language){

        try{
            Optional<Users> user =  userService.findByEmail(email);

            if(user.isPresent()){
                return new ResponseEntity<Users>(user.get(),
                        HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(722, language), false),
                        HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(845, language), false),
                    HttpStatus.BAD_REQUEST);
        }
    }


    /**
     *  Cette méthode permet de rechercher un utilisateur suivant son identifiant sur TaxiRide.(Ok)
     * @param id
     * @return l'utilisateur ayant l'id passé en paramètre.
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN","ROLE_RIDER"})
    @GetMapping("/findbyid/{id}/{language}")
    @ApiOperation(value = "Recherche d'un utilisateur suivant son identifiant.")
    public ResponseEntity<?> findUserById(@PathVariable("id") Long id,@PathVariable("language") String language){
        try{
            Optional<Users> user =  userService.findById(id);

            if(user.isPresent()){
                return new ResponseEntity<Users>(user.get(),
                        HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(722, language), false),
                        HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(845, language), false),
                    HttpStatus.BAD_REQUEST);
        }
    }
    
    
    /**
     *  Delete vehicule picture by username and picture number
     * @param id
     * @return .
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN"})
    @GetMapping("/deletecarpicture/{username}/{picturenumber}/{language}")
    @ApiOperation(value = "Delete car picture.")
    public ResponseEntity<?> deleteCarPictureById(@PathVariable("username") String username, @PathVariable("picturenumber") int picturenumber,@PathVariable("language") String language){
        try{
            //Optional<VehiclePicture> vp = vehiclePictureService.findOne(id);
            
            Optional<Users> us = userService.findByPhoneNumber(username) ;
            
            System.out.println("#############################################################");
        	System.out.println("Username: "+username+" picture number "+picturenumber);
        	System.out.println("#############################################################");

        	
            if(us.isPresent()){
            	vehiclePictureService.deletePicture(username, picturenumber);
                //return new ResponseEntity<VehiclePicture>(HttpStatus.OK);
                return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(616, language), true), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(722, language), false),
                        HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(845, language), false),
                    HttpStatus.BAD_REQUEST);
        }
    }
    
    
    /**
     *  Delete emergence contact
     * @param id
     * @return just send contact id.
     */
    @Secured({"ROLE_ADMIN","ROLE_RIDER"})
    @GetMapping("/deleteemergenceeontactbyid/{id}/{language}")
    @ApiOperation(value = "Delete emergence contact.")
    public ResponseEntity<?> deleteEmergenceContactById(@PathVariable("id") Long id,@PathVariable("language") String language){
        try{
            Optional<EmergencyContact> vp =  emergencyContactService.findOne(id);

            if(vp.isPresent()){
            	emergencyContactService.deleteContact(id);
                //return new ResponseEntity<VehiclePicture>(HttpStatus.OK);
                return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(617, language), true), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(728, language), false),
                        HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(845, language), false),
                    HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     *  Delete payment method
     * @param username, payment number
     * @return true
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN","ROLE_RIDER"})
    @GetMapping("/deletepaymentmethod/{username}/{paymentnumber}/{language}")
    @ApiOperation(value = "Delete payment method.")
    public ResponseEntity<?> deletepaymentmethodbyid(@PathVariable("username") String username, @PathVariable("paymentnumber") int paymentnumber,@PathVariable("language") String language){
        try{
            //Optional<PaymentMode> py =  paymentModeService.findOne(id);
            
            Optional<Users> us =  userService.findByPhoneNumber(username);
            
            if(us.isPresent()){
            	userPaymentModeService.deletePayment(us.get().getId(), paymentnumber);
                //return new ResponseEntity<UserPaymentMode>(HttpStatus.OK);
                return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(679, language), true), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(729, language), false),
                        HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(845, language), false),
                    HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     *  Delete payment method
     * @param username, paymentnumber
     * @return true.
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN","ROLE_RIDER"})
    @GetMapping("/defaultpaymentmethod/{username}/{paymentnumber}/{language}")
    @ApiOperation(value = "Set default payment method.")
    public ResponseEntity<?> defaultpaymentmethod(@PathVariable("username") String username, @PathVariable("paymentnumber") int paymentnumber,@PathVariable("language") String language){
        try{
            //Optional<PaymentMode> py =  paymentModeService.findOne(id);
        	
        	Optional<Users> us =  userService.findByPhoneNumber(username);
        	
        	
            if(us.isPresent()){
            	userPaymentModeService.defaultPayment(us.get().getId(), paymentnumber);
                //return new ResponseEntity<UserPaymentMode>(HttpStatus.OK);
                return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(620, language), true), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(729, language), false),
                        HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(845, language), false),
                    HttpStatus.BAD_REQUEST);
        }
    }
    
    /**
     *  Delete promotional code
     * @param id
     * @return just send payment id.
     */
    @Secured({"ROLE_ADMIN"})
    @GetMapping("/deletepromotionalcode/{id}/{language}")
    @ApiOperation(value = "Delete promotional code.")
    public ResponseEntity<?> deletepromotionalcode(@PathVariable("id") Long id, @PathVariable("language") String language){
        try{
            Optional<PromotionalCode> py =  promotionalCodeService.findOne(id);

            if(py.isPresent()){
            	promotionalCodeService.deletePromotional(id);
                return new ResponseEntity<PromotionalCode>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(730, language), false),
                        HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(845, language), false),
                    HttpStatus.BAD_REQUEST);
        }
    }


    /**
     * Cette méthode permet à un utilisateur de mettre à jour les informations de base de son profil. (Ok)
     * @param profileDTO
     * @return Les informations mises à jour (ProfileDTO)
     * {@link ProfileDTO}
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "Permet à un utilisateur de mettre à jour les informations de base de son profil.")
    @PostMapping("/saveProfilePersonnalInformation")
    public ResponseEntity<?> saveProfilePersonnalInformation(@Valid @RequestBody ProfileDTO profileDTO) {

        try{
            // Réchercher l'utilisateur en fonction de son numéro de téléphone
            Optional<Users> user = userService.findByPhoneNumber(profileDTO.getPhoneNumber());
            if(user.isPresent()){
                Users usertoUpdate = user.get();

                usertoUpdate.setFirstName(profileDTO.getFirstName());
                usertoUpdate.setLastName(profileDTO.getLastName());
                usertoUpdate.setBirthDate(profileDTO.getBirthDate());
                usertoUpdate.setProfession(profileDTO.getProfession());
                usertoUpdate.setLanguage(profileDTO.getLanguage());
                usertoUpdate.setCountry(profileDTO.getCountry());
                usertoUpdate.setGender(profileDTO.getGender());
                usertoUpdate.setAddress(profileDTO.getAddress());
                usertoUpdate.setCity(profileDTO.getCity());
                usertoUpdate.setDriverOperatingCityCode(profileDTO.getDriverOperatingCityCode());
                usertoUpdate.setDriverOperatingCountryCode(profileDTO.getDriverOperatingCountryCode());
                usertoUpdate.setCodeTripOption(profileDTO.getCodeTripOption());
                usertoUpdate.setStatus(profileDTO.getStatus());
                usertoUpdate.setExternalReferalCode(profileDTO.getExternalReferalCode()); // Mais il faudra vérifier que ce code existe

                usertoUpdate.setMinimalNotificationDistance(profileDTO.getMinimalNotificationDistance());
                usertoUpdate.setSubscribeToSMS(profileDTO.getSubscribeToSMS());
                usertoUpdate.setSubscribeToEmail(profileDTO.getSubscribeToEmail());
                usertoUpdate.setSubscribeToPush(profileDTO.getSubscribeToPush());
                
                usertoUpdate.setDefaultTravelOption(profileDTO.getDefaultTravelOption());
                usertoUpdate.setDefaultRequestRadius(profileDTO.getDefaultRequestRadius());
                
                
                // Mettre à jour la date de modification de l'utilisateur
                usertoUpdate.setUpdatedAt(new Date());

                // Mettre à jour l'utilisateur
                Users savedUser = userService.save(usertoUpdate);
                profileDTO.setId(savedUser.getId());
                return ResponseEntity.status(HttpStatus.OK).body(profileDTO);
            }
            return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(722, profileDTO.getLanguage()), false),
                    HttpStatus.NOT_FOUND);
        }catch (Exception e){
            return new ResponseEntity<>(new ApiResponse(commonServices.findErrorByCode(846, profileDTO.getLanguage()), false),
                    HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Cette méthode a pour but de sauvegarder la photo de profile d'un utilisateur dans un répertoire du serveur. (Ok)
     * Cette méthode met à jour l'url de la photo de profile de l'utilisateur après stockage.
     * @param phoneNumber
     * @param file
     * @return Message de chargement de la photo avec succès.
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "Upload de la photo de profil d'un utilisateur sur le serveur.")
    @PostMapping("/uploadProfilePhoto")
    public ResponseEntity<?> uploadProfilePhoto(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("file") MultipartFile file, @RequestParam("language") String language){

        try{
            // Essayer de retrouver l'utilisateur concerné à partir de son numéro de téléphone
            Optional<Users> optionalUser = userService.findByPhoneNumber(phoneNumber);

            if(optionalUser.isPresent()){

                // L'utilisateur a été retrouvé, il faut le récupérer
                Users user = optionalUser.get();

                // Fabriquer le nom de la photo à sauvegarder avec pour format (id_phoneNumber_photo)
                String fileName = "PHOTO_" + user.getId() + "_" +  user.getPhoneNumber() + "_" + taxiRideCommonServices.generateRandomUuid();

                // Sauvegarder la photo dans le répertoire de stockage
                String savedFile = taxiRideStorageServices.store(file, fileName);

                // Metre à jour le nom de la photo dans la table users
                user.setImageUrl(savedFile);
                user.setProfilePictureMimeType(FilenameUtils.getExtension(file.getOriginalFilename())); // Mettre à jour l'extension de la photo de profiles de l'utilisateur
                userService.save(user);

                return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(commonServices.findErrorByCode(621, language), true));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(722, language), false));
            }

        }catch(Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ApiResponse(commonServices.findErrorByCode(610, language), false));
        }
    }

    /**
     * Cette méthode a pour but de charger une pièce exigible à un chauffeur pour pouvoir opérer sur TaxiRide.(Ok)
     * @param phoneNumber
     * @param file
     * @param number
     * @return Message de chargement avec sussès de la pièce.
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN"})
    @ApiOperation(value = "Chargement d'une pièce administrative d'un chauffeur sur la plateforme.")
    @PostMapping("/saveProfileAttachements")
    public ResponseEntity<?> saveProfileAttachements(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("file") MultipartFile file, @RequestParam("number") String number, @RequestParam("language") String language){

        try {
            // Retrouver l'utilisateur et vérifier qu'il n'est pas encore vérifié pour effectuer toute action de chargement de document
            Optional<Users> optionalUser = userService.findByPhoneNumber(phoneNumber);

            if (optionalUser.isPresent()) {
                Users user = optionalUser.get(); // Récupération de l'utilisateur concrèt

                if (user.getIsVerified()) { // Statut déjà vérifié
                    return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(commonServices.findErrorByCode(731, language), true));
                } else { // Statut non encore vérifié

                    // Fabriquer le nom de la pièce à sauvegarder
                    Integer numero = Integer.parseInt(number);
                    String fileName = "ATTACH_" + user.getId() + "_" + number + "_" + taxiRideCommonServices.generateRandomUuid() ; // Convention de nommage du document officiel

                    /*
                     * Créer un type de document et l'enregistrer avec les paramètres de la requête au cas où l'utilisateur
                     * n'aurai pas déjà ce document dans la liste de ses documents.
                     */
                    if (userService.hasDocument(user, numero) && officialDocumentService.findByUserAndNumber(user, numero).isPresent()) { // Il possède déjà un document pareil, effectuer juste la mise à jour

                        // Retrouver l'ancienne pièce sauvegardée
                        OfficialDocument document = officialDocumentService.findByUserAndNumber(user, numero).get();

                        // Sauvegarder la nouvelle pièce dans le répertoire de stockage
                        String savedFile = taxiRideStorageServices.store(file, fileName);

                        document.setUrl(savedFile);
                        document.setMimeType(file.getContentType());

                        officialDocumentService.save(document);

                        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(commonServices.findErrorByCode(622, language), true));

                    }else{ // L'utilisateur ne possède pas encore ce document, le créer

                        // Sauvegarder le document sur le support physique
                        String savedFile = taxiRideStorageServices.store(file, fileName);

                        // Créer et stocker le document dans la base de données
                        OfficialDocument document = new OfficialDocument(user, numero, savedFile, file.getContentType());

                        officialDocumentService.save(document);
                        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(commonServices.findErrorByCode(623, language), true));
                    }
                }
            }else{
                return ResponseEntity.status(404).body(new ApiResponse("Document created uploaded successfully.", false));
            }

        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ApiResponse(commonServices.findErrorByCode(830, language), true));
        }

    }

    /**
     * Calcul du taux de completion,
     * 1- Vérifier au moins 1 numéro d'urgence 10% 
     * 2- Vérifier au moins 1 numéro de confiance 10%
     * 3- Vérifier le nombre de champs renseigné sur les informations personnelles 40%
     * 4- Vérifier l'image de profil 10%
     * 5- Vérifier le nombre de champs renseigné sur les informations du profil rider 30%
     * @param phone
     * @return le taux de remplissage du profil rider de l'utilisateur
     * {@link CommonController}
     */
    @Secured({"ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "Calcul du taux de remplissage du profil rider d'un utilisateur")
    @GetMapping("/riderprofilcompletion/{phone}/{language}")
    @ResponseBody
    public ResponseEntity<?> riderprofilcompletion(@PathVariable String phone, @PathVariable String language){
    	
    	
        int tauxDeCompletion= 0;
        
        int profilPicture = 0;
        int savetyNumber = 0;
        int trustedNumber = 0;
        int personnalInfos = 0;
        int riderInfos = 0;
        
        try{
            // Examiner la potentielle existence de l'utilisateur concerné à partir de son numéro de téléphone
            Optional<Users> user = userService.findByPhoneNumber(phone);
            
            List<EmergencyContact> mesContactUrgence = emergencyContactService.findAllByUserAndContactType(user.get(), 0);
            
            List<EmergencyContact> mesContactConfiance = emergencyContactService.findAllByUserAndContactType(user.get(), 1);
            
            		
            if(user.isPresent()){
                //Les documents ocupe 40% du profil driver
                if(!mesContactUrgence.isEmpty()) { // L'utilisateur possède au moins un numéro d'urgence

                    // Attribution du taux de pourcentge 
                	savetyNumber =10;
                   
                }
                
              //Les photos du véhicule ocupe 30% du profil driver
                if(!mesContactConfiance.isEmpty()) { // L'utilisateur possède au moins un numéro de confiance

                    // Attribution du taux de pourcentge
                	trustedNumber =10;
                }
                
                
                if(user != null){ // 
                	// Attribution du taux de pourcentge sur la photos de profil
                	if(user.get().getImageUrl()  != null) {
                		profilPicture = 10;
                	}
                }
                
                if(user != null){ // 
                	// Attribution du taux de pourcentge sur les numéro d'urgence
                	if(user.get().getLanguage()  != null) {
                		riderInfos = 6;
                	}
                }
                
                if(user != null){ // 
                	// Attribution du taux de pourcentge sur les numéro de confiance
                	if(user.get().getLanguage()  != null) {
                		riderInfos = 6;
                	}
                }
                
                if(user != null){ // On a retrouvé au moins les informations SUR LE PROFIL RIDER DE l'utilisateur
                	// Attribution du taux de pourcentge selon les champs rempli
                	if(user.get().getLanguage()  != null) {
                		riderInfos = 6;
                	}
                	
                	if(
                			user.get().getLanguage()  != null && 
                					user.get().getDefaultTravelOption()  != null) {
                		riderInfos = 12;
                	}
                	if(
                			user.get().getLanguage()  != null && 
                					user.get().getDefaultTravelOption()  != null && 
                							user.get().getProfession()  != null
                    	) {
                		riderInfos = 18;
                    	}
                	if(
                			user.get().getLanguage()  != null && 
                					user.get().getDefaultTravelOption()  != null && 
                							user.get().getProfession()  != null && 
                									user.get().getGender() != null
                        ) {
                		riderInfos = 24;
                        }
                	if(
                			user.get().getLanguage()  != null && 
                					user.get().getDefaultTravelOption()  != null && 
                							user.get().getProfession()  != null && 
                									user.get().getGender()  != null && 
                											user.get().getDefaultRequestRadius()  != null
                        ) {
                		riderInfos = 30;
                        }
                	
                }
                
                
                if(user != null){ // On a retrouvé au moins les informations personnelles sur l'utilisateur
                	// Attribution du taux de pourcentge selon les champs rempli
                	if(user.get().getFirstName()  != null) {
                		personnalInfos = 6;
                	}
                	
                	if(
                			user.get().getFirstName()  != null && 
                					user.get().getLastName()  != null) {
                		personnalInfos = 12;
                	}
                	if(
                			user.get().getFirstName()  != null && 
                					user.get().getLastName()  != null && 
                							user.get().getCountry()  != null
                    	) {
                		personnalInfos = 18;
                    	}
                	if(
                			user.get().getFirstName()  != null && 
                					user.get().getLastName()  != null && 
                							user.get().getCountry()  != null && 
                									user.get().getCity() != null
                        ) {
                		personnalInfos = 24;
                        }
                	if(
                			user.get().getFirstName()  != null && 
                					user.get().getLastName()  != null && 
                							user.get().getCountry()  != null && 
                									user.get().getCity()  != null && 
                											user.get().getEmail()  != null
                        ) {
                		personnalInfos = 30;
                        }
                	if(
                			user.get().getFirstName()  != null && 
                					user.get().getLastName()  != null && 
                							user.get().getCountry()  != null && 
                									user.get().getCountry()  != null && 
                											user.get().getEmail()  != null && 
                													user.get().getPhoneNumber()  != null
                        ) {
                		personnalInfos = 40;
                         }
                }
            }
            
            
                //
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(commonServices.findErrorByCode(847, language) +e, false));
        }
        
		tauxDeCompletion = profilPicture+ savetyNumber +trustedNumber+personnalInfos+ riderInfos;
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(""+ tauxDeCompletion, true));
    }
    
    /**
     * Calcul du taux de completion,
     * 1- Vérifier le nombre de document déjà uploader 40% 
     * 2- Vérifier le nombre d'image de vehicule 30%
     * 3- Vérifier le nombre de champs renseigné 30%
     * @param phone
     * @return le taux de remplissation du profil Driver de l'utilisateur
     * {@link CommonController}
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN"})
    @ApiOperation(value = "Calcul du taux de remplissage du profil Driver d'un utilisateur")
    @GetMapping("/driverprofilcompletion/{phone}/{language}")
    @ResponseBody
    public ResponseEntity<?> driverprofilcompletion(@PathVariable String phone, @PathVariable String language){
    	
    	int sizeOfDocument = 0;
        int sizeOfCabPicture = 0;
        int sizeOfField = 0;
        int tauxDeCompletion= 0;
        
        try{
            // Examiner la potentielle existence de l'utilisateur concerné à partir de son numéro de téléphone
            Optional<Users> user = userService.findByPhoneNumber(phone);
            
            List<OfficialDocument> mesDocuments = officialDocumentService.findAllByUser(user.get());
            
            List<VehiclePicture> vehiclePictures = vehiclePictureService.findAllByUsername(phone);
            
         // Rechercher tous les véhicules de cet utilisateur sur le système
            List<Vehicle> mesVehicules = vehicleService.findAllByUser(user.get());
            
            //information sur le vehicule
            Optional<Vehicle> monVehicule = vehicleService.findByUserId(user.get().getId());
            
            
            

            if(user.isPresent()){
                
                //Les documents ocupe 40% du profil driver
                if(!mesDocuments.isEmpty()) { // L'utilisateur possède au moins un document

                    // Attribution du taux de pourcentge selon le nombre de document
                	if(mesDocuments.size() == 0) {
                		sizeOfDocument =0;
                	}
                	if(mesDocuments.size() == 1) {
                		sizeOfDocument =6;
                	}
                	if(mesDocuments.size() == 2) {
                		sizeOfDocument =12;
                	}
                	if(mesDocuments.size() == 3) {
                		sizeOfDocument =18;
                	}
                	if(mesDocuments.size() == 4) {
                		sizeOfDocument =24;
                	}
                	if(mesDocuments.size() == 5) {
                		sizeOfDocument =30;
                	}
                	if(mesDocuments.size() == 6) {
                		sizeOfDocument =40;
                	}
                   
                }
                
              //Les photos du véhicule ocupe 30% du profil driver
                if(!vehiclePictures.isEmpty()) { // L'utilisateur possède au moins un document

                    // Attribution du taux de pourcentge selon le nombre de photos du vehicule
                	if(vehiclePictures.size() == 0) {
                		sizeOfCabPicture =0;
                	}
                	if(vehiclePictures.size() == 1) {
                		sizeOfCabPicture =5;
                	}
                	if(vehiclePictures.size() == 2) {
                		sizeOfCabPicture =10;
                	}
                	if(vehiclePictures.size() == 3) {
                		sizeOfCabPicture =15;
                	}
                	if(vehiclePictures.size() == 4) {
                		sizeOfCabPicture =20;
                	}
                	if(vehiclePictures.size() == 5) {
                		sizeOfCabPicture =25;
                	}
                	if(vehiclePictures.size() == 6) {
                		sizeOfCabPicture =30;
                	}
                   
                }
                
                if(monVehicule != null){ // On a retrouvé au moins les informations sur le profil driver
                	// Attribution du taux de pourcentge selon les champs rempli sur le profil driver
                	if(monVehicule.get().getBrand()  != null) {
                		sizeOfField = 3;
                	}
                	
                	if(
                		monVehicule.get().getBrand()  != null && 
                		monVehicule.get().getVehicleType()  != null) {
                		sizeOfField = 6;
                	}
                	if(
                    	monVehicule.get().getBrand()  != null && 
                    	monVehicule.get().getVehicleType()  != null && 
                    	monVehicule.get().getTravelOption()  != null
                    	) {
                    		sizeOfField = 9;
                    	}
                	if(
                        monVehicule.get().getBrand()  != null && 
                        monVehicule.get().getVehicleType()  != null && 
                        monVehicule.get().getTravelOption()  != null && 
                        monVehicule.get().getMatriculationNumber()  != null
                        ) {
                        	sizeOfField = 12;
                        }
                	if(
                        monVehicule.get().getBrand()  != null && 
                        monVehicule.get().getVehicleType()  != null && 
                        monVehicule.get().getTravelOption()  != null && 
                        monVehicule.get().getMatriculationNumber()  != null && 
                        monVehicule.get().getFirstUseDate()  != null
                        ) {
                           sizeOfField = 15;
                        }
                	if(
                        monVehicule.get().getBrand()  != null && 
                        monVehicule.get().getVehicleType()  != null && 
                        monVehicule.get().getTravelOption()  != null && 
                        monVehicule.get().getMatriculationNumber()  != null && 
                        monVehicule.get().getFirstUseDate()  != null && 
                        monVehicule.get().getNumberOfSeat()  != null
                        ) {
                            sizeOfField = 18;
                         }
                	if(
                        monVehicule.get().getBrand()  != null && 
                        monVehicule.get().getVehicleType()  != null && 
                            monVehicule.get().getTravelOption()  != null && 
                            monVehicule.get().getMatriculationNumber()  != null && 
                            monVehicule.get().getFirstUseDate()  != null && 
                            monVehicule.get().getNumberOfSeat()  != null && 
                            monVehicule.get().getModel()  != null
                            ) {
                                sizeOfField = 21;
                             }
                	if(
                            monVehicule.get().getBrand()  != null && 
                            monVehicule.get().getVehicleType()  != null && 
                                monVehicule.get().getTravelOption()  != null && 
                                monVehicule.get().getMatriculationNumber()  != null && 
                                monVehicule.get().getFirstUseDate()  != null && 
                                monVehicule.get().getNumberOfSeat()  != null && 
                                monVehicule.get().getNumbersWheel()  != null && 
                                monVehicule.get().getModel()  != null
                                ) {
                                    sizeOfField = 24;
                                 }
                	
                	if(
                            monVehicule.get().getBrand()  != null && 
                            monVehicule.get().getVehicleType()  != null && 
                                monVehicule.get().getTravelOption()  != null && 
                                monVehicule.get().getMatriculationNumber()  != null && 
                                monVehicule.get().getFirstUseDate()  != null && 
                                monVehicule.get().getNumberOfSeat()  != null && 
                                monVehicule.get().getNumbersWheel()  != null && 
                                monVehicule.get().getModel()  != null && 
                                monVehicule.get().getCarconstructor()  != null
                                ) {
                                    sizeOfField = 27;
                                 }
                	if(
                            monVehicule.get().getBrand()  != null && 
                            monVehicule.get().getVehicleType()  != null && 
                                monVehicule.get().getTravelOption()  != null && 
                                monVehicule.get().getMatriculationNumber()  != null && 
                                monVehicule.get().getFirstUseDate()  != null && 
                                monVehicule.get().getNumberOfSeat()  != null && 
                                monVehicule.get().getNumbersWheel()  != null && 
                                monVehicule.get().getModel()  != null && 
                                monVehicule.get().getCarconstructor()  != null && 
                                monVehicule.get().getCityScope()  != null
                                ) {
                                    sizeOfField = 30;
                                 }
                	
                }
            }
            
            
                //
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(commonServices.findErrorByCode(848, language) +e, false));
        }
		tauxDeCompletion = sizeOfField+ sizeOfCabPicture +sizeOfDocument;;
		return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(""+ tauxDeCompletion, true));
    }
    

    /**
     * Méthode qui permettant de retourner tous les documents d'un utilisateur à partir de son numéro de téléphone.
     * Une fois la liste des documents retrouvés, chacun peut être téléchargé suivant son lien avec la méthode de
     * téléchargement downloadResource du controlleur CommonController. (Ok)
     * @param phone
     * @return La liste des documents que possède un utilisateur
     * {@link CommonController}
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN"})
    @ApiOperation(value = "Recherche des documents associés à un utilisateur.")
    @GetMapping("/findAttachementsByPhone/{phone}/{language}")
    @ResponseBody
    public ResponseEntity<?> findAttachementsByPhone(@PathVariable String phone, @PathVariable String language){
        try{
            // Examiner la potentielle existence de l'utilisateur concerné à partir de son numéro de téléphone
            Optional<Users> user = userService.findByPhoneNumber(phone);

            if(user.isPresent()){
                // Retrouver tous les potentiels documents de l'utilisateurs
                List<OfficialDocument> mesDocuments = officialDocumentService.findAllByUser(user.get());
                if(!mesDocuments.isEmpty()) { // L'utilisateur possède au moins un document

                    // Filtrer pour convertir en OfficialDocumentDTO
                    List<OfficialDocumentDTO> documents = mesDocuments.stream()
                            .map(document -> ProfileServices.convertOfficialDocumentToDTO(document))
                            .collect(Collectors.toList());

                    return ResponseEntity.status(HttpStatus.OK).body(documents);
                }else { // L'utilsateur n'a pas de document
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(commonServices.findErrorByCode(732, language), true));
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(722, language), false));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(commonServices.findErrorByCode(845, language), false));
        }
    }

    
    /**
     * Nouvelle API Cette méthode permet de sauvegarder dans un répertoire du serveur une image associée à un utilisateur en utilisant son nuéro de téléphone sans country code au lieu d'un véhicule comme avant,
     * les informations associée à cette image. (Ok)
     * @param phoneNumber
     * @param pictureNumber
     * @param file
     * @return Les informations sur l'image du véhicule qui est sauvegardée.
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN"})
    @ApiOperation(value = "Upload de l'image d'un véhicule.")
    @PostMapping("/saveProfileCabImage")
    public ResponseEntity<?> saveProfileCabImage(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("pictureNumber") Integer pictureNumber,  @RequestParam("file") MultipartFile file, @RequestParam("language") String language){

        try{
        	
            // Essayer de retrouver l'utilisateur concerné
            Optional<Users> optionalUser = userService.findByPhoneNumber(phoneNumber);

            if(optionalUser.isPresent()){ // L'utilisateur existe

                
                	
                	List<VehiclePicture> vehiclePictures = vehiclePictureService.findAllByUsername(phoneNumber);


                    // Retrouver l'ensemble des images dont le numéro est celui en création
                    List<VehiclePicture> alreadyExistPictures = vehiclePictures
                            .stream()
                            .filter(picture -> picture.getPictureNumber() == Integer.valueOf(pictureNumber))
                            .collect(Collectors.toList());
                    
                    System.out.println("#################################################################");
                    
                    System.out.println("Avant ");
                    
                    System.out.println("#################################################################");
                    

                    if(alreadyExistPictures.size() == 0 && vehiclePictures.size() < 6){ // le numéro de l'image n'existe pas encore et le nombre d'images est inférieur à 6 pour ce véhicule

                        // Fabriquer le nom de l'image à sauvegarder avec pour format (VEHICULE_idUser_pictureNumber)
                        String fileName = "VEHICLE_" +  optionalUser.get().getId() + "_" + pictureNumber + "_" + taxiRideCommonServices.generateRandomUuid();

                        // Sauvegarder le fichier image dans le répertoire de stockage
                        String savedFile = taxiRideStorageServices.store(file, fileName);

                        // Sauvagarder l'image du véhicule
                        VehiclePicture newVehiclePicture = new VehiclePicture();
                        
                        //we define picture number from picture size list
                        newVehiclePicture.setPictureNumber(vehiclePictures.size()+1);
                        newVehiclePicture.setPictureURL(savedFile);
                        newVehiclePicture.setPictureMimeType(file.getContentType());
                        newVehiclePicture.setUsername(phoneNumber);
                        
                        
                        System.out.println("#################################################################");
                        
                        System.out.println("Images infos: "+newVehiclePicture.getPictureNumber() +" "+newVehiclePicture.getUsername());
                        
                        System.out.println("#################################################################");
                        
                        
                        VehiclePicture savedVehiclePicture =  vehiclePictureService.save(newVehiclePicture);
                        
                        System.out.println("#################################################################");
                        
                        System.out.println("Après");
                        
                        System.out.println("#################################################################");
                        

                        return ResponseEntity.status(HttpStatus.OK).body(ProfileServices.convertVehiculePictureToDTO(savedVehiclePicture));
                    }else{ // Le numéro de l'image existe déjà
                    	System.out.println("#################################################################");
                        
                        System.out.println("Après 0");
                        
                        System.out.println("#################################################################");
                        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ApiResponse("The picture number " + pictureNumber + " has already been provided.", false));
                    }
                }else{ // L'utilisateur n'a pas encore de véhicule dans le système
                	System.out.println("#################################################################");
                    
                    System.out.println("Après 1 ");
                    
                    System.out.println("#################################################################");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(734, language), false));
                }
            /*}else{ // L'utilisateur n'existe pas
            	System.out.println("#################################################################");
                
                System.out.println("Après 2 ");
                
                System.out.println("#################################################################");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("The user is not found.", false));
            }*/
        }catch(Exception e){
            logger.error(e);
            System.out.println("#################################################################");
            
            System.out.println("Après 3"+e);
            
            System.out.println("#################################################################");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ApiResponse(commonServices.findErrorByCode(610, language), false));
        }
    }
    
    /**
     * Cette méthode permet de sauvegarder dans un répertoire du serveur une image associée à un véhicule, puis dans la base de données
     * les informations associée à cette image. (Ok)
     * @param phoneNumber
     * @param pictureNumber
     * @param file
     * @deprecated This fucntion is change with the new one because, user no need to create vehicle, or have many vehicle
     * @return Les informations sur l'image du véhicule qui est sauvegardée.
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN"})
    @ApiOperation(value = "Upload de l'image d'un véhicule.")
    @PostMapping("/saveProfileCabImageOld")
    public ResponseEntity<?> saveProfileCabImageOld(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("pictureNumber") String pictureNumber,  @RequestParam("file") MultipartFile file, @RequestParam("language") String language){

        try{
        	
        	System.out.println("#################################################################");
            
            System.out.println("Avant 0 ");
            
            System.out.println("#################################################################");
            
            
            // Essayer de retrouver l'utilisateur concerné
            Optional<Users> optionalUser = userService.findByPhoneNumber(phoneNumber);

            if(optionalUser.isPresent()){ // L'utilisateur existe

                // Retrouver le véhicule de cet utilisateur
                List<Vehicle> mesVehicules = vehicleService.findAllByUser(optionalUser.get());
                
                System.out.println("#################################################################");
                
                System.out.println("Avant 1 ");
                
                System.out.println("#################################################################");
                

                if(mesVehicules.size() != 0){ // L'utilisateur a au moins un véhicule (et l'unique) dans le système

                    // Le véhicule de l'utilisateur est forcément le premier de la liste
                    Vehicle vehicle = mesVehicules.get(0);

                    // Retrouver l'ensemble des images du véhicule de l'utilisateur
                    List<VehiclePicture> vehiclePictures = vehiclePictureService.findAllByVehicle(vehicle);

                    // Retrouver l'ensemble des images dont le numéro est celui en création
                    List<VehiclePicture> alreadyExistPictures = vehiclePictures
                            .stream()
                            .filter(picture -> picture.getPictureNumber() == Integer.valueOf(pictureNumber))
                            .collect(Collectors.toList());
                    
                    System.out.println("#################################################################");
                    
                    System.out.println("Avant ");
                    
                    System.out.println("#################################################################");
                    

                    if(alreadyExistPictures.size() == 0 && vehiclePictures.size() < 6){ // le numéro de l'image n'existe pas encore et le nombre d'images est inférieur à 6 pour ce véhicule

                        // Fabriquer le nom de l'image à sauvegarder avec pour format (VEHICULE_idUser_pictureNumber)
                        String fileName = "VEHICLE_" +  optionalUser.get().getId() + "_" + pictureNumber + "_" + taxiRideCommonServices.generateRandomUuid();

                        // Sauvegarder le fichier image dans le répertoire de stockage
                        String savedFile = taxiRideStorageServices.store(file, fileName);

                        // Sauvagarder l'image du véhicule
                        //VehiclePicture newVehiclePicture = new VehiclePicture(vehicle, Integer.valueOf(pictureNumber), savedFile, file.getContentType(), phoneNumber);
                        VehiclePicture newVehiclePicture = new VehiclePicture();
                        //Vehicle vehicle, Integer pictureNumber, String pictureURL, String pictureMimeType, String username
                        //newVehiclePicture.setVehicle(vehicle);
                        newVehiclePicture.setPictureNumber(Integer.valueOf(pictureNumber));
                        newVehiclePicture.setPictureURL(savedFile);
                        newVehiclePicture.setPictureMimeType(file.getContentType());
                        newVehiclePicture.setUsername(phoneNumber);
                        
                        
                        System.out.println("#################################################################");
                        
                        System.out.println("Images infos: "+newVehiclePicture.getPictureNumber() +" "+newVehiclePicture.getUsername());
                        
                        System.out.println("#################################################################");
                        
                        
                        VehiclePicture savedVehiclePicture =  vehiclePictureService.save(newVehiclePicture);
                        
                        System.out.println("#################################################################");
                        
                        System.out.println("Après");
                        
                        System.out.println("#################################################################");
                        

                        return ResponseEntity.status(HttpStatus.OK).body(ProfileServices.convertVehiculePictureToDTO(savedVehiclePicture));
                    }else{ // Le numéro de l'image existe déjà
                    	System.out.println("#################################################################");
                        
                        System.out.println("Après 0");
                        
                        System.out.println("#################################################################");
                        return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ApiResponse("The picture number " + pictureNumber + " has already been provided.", false));
                    }
                }else{ // L'utilisateur n'a pas encore de véhicule dans le système
                	System.out.println("#################################################################");
                    
                    System.out.println("Après 1 ");
                    
                    System.out.println("#################################################################");
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(734, language), false));
                }
            }else{ // L'utilisateur n'existe pas
            	System.out.println("#################################################################");
                
                System.out.println("Après 2 ");
                
                System.out.println("#################################################################");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(722, language), false));
            }
        }catch(Exception e){
            logger.error(e);
            System.out.println("#################################################################");
            
            System.out.println("Après 3"+e);
            
            System.out.println("#################################################################");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ApiResponse(commonServices.findErrorByCode(610, language), false));
        }
    }


    /**
     * Cette méthode retourne toutes les images associées à un véhicule. Chaque image peut donc être téléchargée à partir de son url
     * avec la méthode générique downloadResource du controller CommonController. (Ok)
     * @param phone
     * @return La liste des images du véhicule de l'utilisateur dont le numéro de téléphone est passé en paramètre.
     * {@link CommonController}
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN"})
    @ApiOperation(value = "Retourner toutes les images d'un véhicule appartenant à un utilisateur dont le numéro de téléphone est mentionné.")
    @GetMapping("/findCabImagesByPhone/{phone}/{language}")
    public ResponseEntity<?> findCabImagesByPhone(@PathVariable String phone, @PathVariable String language){
        try{
            // Examiner la potentielle existence de l'utilisateur concerné à partir de son numéro de téléphone
            Optional<Users> optionalUser = userService.findByPhoneNumber(phone);

            if(optionalUser.isPresent()){ // L'utilisateur existe
               // Charger le véhicule de cet utilisateur
                //List<Vehicle> mesVehicules = vehicleService.findAllByUser(optionalUser.get());
                //if(mesVehicules.size() != 0){ // Au moins un véhicule a été trouvé pour l'utilisateur;
                    // Charger la liste des images du premier (et d'ailleurs l'unique) véhicule.
                    List<VehiclePicture> vehiclePictures = vehiclePictureService.findAllByUsername(phone);

                    // Filtrer pour convertir en VehiclePictureDTO
                    List<VehiclePictureDTO> pictures = vehiclePictures.stream()
                            .map(picture -> ProfileServices.convertVehiculePictureToDTO(picture))
                            .collect(Collectors.toList());

                    return ResponseEntity.status(HttpStatus.OK).body(pictures);
                /*}else{
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No cab picture found for your account.", false));
                }*/
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(722, language), false));
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(commonServices.findErrorByCode(845, language), false));
        }
    }

    /**
     * Cette méthode permet d'enregistrer les informations de l'automobile d'un chauffeur sur la plateforme TaxiRide. (Ok)
     * @param vehicleDTO
     * @return Les informations sur le véhicule enregistré (y compris son identifiant)
     * {@link VehicleDTO}
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN"})
    @ApiOperation(value = "Enregistrement des informations de l'automobile.")
    @PostMapping("/saveProfileCabInformation")
    public ResponseEntity<?> saveProfileCabInformation(@Valid @RequestBody VehicleDTO vehicleDTO){
        try{
            // Rechercher le potentiel propriétaire du véhicule
            Optional<Users> optionalUser = userService.findByPhoneNumber(vehicleDTO.getPhoneNumber());

            if(optionalUser.isPresent()){ // L'utilisateur existe sur le système

                Users user = optionalUser.get();

                // Se rassurer que cet utilisateur n'a pas encore un véhicule enregistré sur TaxiRide
                List<Vehicle> mesVehicules = vehicleService.findAllByUser(user);

                if(mesVehicules.size() == 0){ // L'utilisateur n'a pas encore de véhicule dans le système
                    // Construire un véhicule à partir des informations reçues de la requête et l'enregistrer
                    Vehicle newVehicle = new Vehicle();

                    newVehicle.setUser(user);

                    newVehicle.setVehicleType(vehicleDTO.getVehicleType());
                    newVehicle.setNumberOfSeat(vehicleDTO.getNumberOfSeat());
                    newVehicle.setModel(vehicleDTO.getModel());
                    newVehicle.setBrand(vehicleDTO.getBrand());
                    newVehicle.setMatriculationNumber(vehicleDTO.getMatriculationNumber());
                    newVehicle.setFirstUseDate(vehicleDTO.getFirstUseDate());
                    newVehicle.setColor(vehicleDTO.getColor());
                    newVehicle.setNumbersWheel(vehicleDTO.getNumbersWheel());
                    newVehicle.setDescription(vehicleDTO.getDescription());
                    newVehicle.setCarconstructor(vehicleDTO.getCarconstructor());
                    newVehicle.setTravelOption(vehicleDTO.getTravelOption());
                    newVehicle.setCityScope(vehicleDTO.getCityScope());

                    Vehicle savedVehicle = vehicleService.save(newVehicle);
                    vehicleDTO.setId(savedVehicle.getId());

                    return ResponseEntity.status(HttpStatus.OK).body(vehicleDTO);
                }else{ // L'utilisateur a déjà un véhicule enregistré dans le système
                	
                	
                    
                    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ApiResponse(commonServices.findErrorByCode(735, vehicleDTO.getLanguage()), false));
                }
            }else{
            	
            	
                
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(722, vehicleDTO.getLanguage()), false));
            }
        }catch (Exception e){
        	
        	
            
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(commonServices.findErrorByCode(848, vehicleDTO.getLanguage()), false));
        }
    }

    /**
     * Cette méthode recherche les informations sur l'automobile d'un chauffeur à partir de son numéro de téléphone. (Ok)
     * @param phone
     * @return Les informations sur le véhicule (supposé être unique dans le système) d'un chauffeur.
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN"})
    @ApiOperation(value = "Recherche des informations sur l'automobile d'un utilisateur à partir de son numéro de téléphone.")
    @GetMapping("/findCabInformationByPhone/{phone}/{language}")
    public ResponseEntity<?> findCabInformationByPhone(@PathVariable String phone, @PathVariable String language){

        try{
            // Rechercher le potentiel propriétaire du véhicule
            Optional<Users> optionalUser = userService.findByPhoneNumber(phone);

            if(optionalUser.isPresent()){ // L'utilisateur est retrouvé

                // Rechercher tous les véhicules de cet utilisateur sur le système
                List<Vehicle> mesVehicules = vehicleService.findAllByUser(optionalUser.get());

                if(mesVehicules.size() > 0){ // On a retrouvé au moins un véhicule, retourner le premier véhicule de la liste (le seul en fait)
                    return ResponseEntity.status(HttpStatus.OK).body(ProfileServices.convertVehicleToDTO(mesVehicules.get(0)));
                }else{
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(734, language), false));
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(722, language), false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(commonServices.findErrorByCode(848, language), false));
        }
    }


    /**
     * Cette méthode permet d'enregistrer un moyen de paiement au profil d'un utilisateur. (Ok)
     * @param userPaymentModeDTO
     * @return Les informations sur le mode de paiement enregistré
     * {@link UserPaymentModeDTO}
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "Enregistrement d'un moyen de paiement au profil d'un utilisateur")
    @PostMapping("/saveProfilePaymentInformation")
    public ResponseEntity<?> saveProfilePaymentInformation(@Valid @RequestBody UserPaymentModeDTO userPaymentModeDTO){

        try{
            // Retrouver le potentiel utilisateur
            Optional<Users> optionalUser = userService.findByPhoneNumber(userPaymentModeDTO.getPhoneNumber());

            // Retrouver le mode de payment concerné
            Optional<PaymentMode> optionalPaymentMode = paymentModeService.findByPaymentType(userPaymentModeDTO.getPaymentType());

            if(optionalUser.isPresent() && optionalPaymentMode.isPresent()){ // L'utilisateur existe ainsi que le mode de paiement sollicité
                Users user = optionalUser.get();
                PaymentMode paymentMode = optionalPaymentMode.get();

                // Retrouver et filtrer tous les modes de paiement de l'utilisateur pour voir s'il ya un qui possède le type du mode en création
                List<UserPaymentMode> mesModesExistants = userPaymentModeService.findAllByUser(user)
                        .stream()
                        .filter(mode -> mode.getPaymentMode().getPaymentType() == userPaymentModeDTO.getPaymentType())
                        .collect(Collectors.toList());

                if(mesModesExistants.size() == 0){ // L'utilisateur n'a pas encore ce mode de payment dans son profil

                    UserPaymentMode userPaymentMode = new UserPaymentMode(user, paymentMode, userPaymentModeDTO.getValue(), userPaymentModeDTO.getIsDefault(), userPaymentModeDTO.getPaymentType());
                    UserPaymentMode savedUserPaymentMode = userPaymentModeService.save(userPaymentMode);

                    // Mettre à jour le mode de payment par défaut de l'utilisateur
                    if(userPaymentModeDTO.getIsDefault()){ // Ce mode de payment est supposé être celui par défaut
                        user.setDefaultPaymentmode(userPaymentModeDTO.getPaymentType());
                        userService.save(user);
                    }

                    // Retourner les informations sur le mode de paiement qui vient d'être enregistré
                    userPaymentModeDTO.setId(savedUserPaymentMode.getId());

                    return ResponseEntity.status(HttpStatus.OK).body(userPaymentModeDTO);
                }else{
                    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ApiResponse(commonServices.findErrorByCode(736, userPaymentModeDTO.getLanguage()), true));
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("The user account not found or payment mode dont exist.", false));
            }

        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(commonServices.findErrorByCode(850, userPaymentModeDTO.getLanguage()), false));
        }
    }

    /**
     * Cette méthode permet de retrouver tous les modes de paiement qu'un utilisateur a enregistré dans son profil. (Ok)
     * @param phone
     * @return La liste des moyens de paiement qu'a enregistré un utilisateur dans son profil.
     */
    @Secured({"ROLE_DRIVER","ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "Recherche des moyens de paiement d'un utilisateur à partir de son numéro de téléphone.")
    @GetMapping("/findPaymentInformationByPhone/{phone}/{language}")
    public ResponseEntity<?> findPaymentInformationByPhone(@PathVariable String phone, @PathVariable String language){

        try{
            // Retrouver le potentiel utilisateur
            Optional<Users> optionalUser = userService.findByPhoneNumber(phone);

            if(optionalUser.isPresent()){ // L'utilisateur est présent sur le système, retourner ses modes de paiement
                List<UserPaymentMode> mesModesPaiement = userPaymentModeService.findAllByUser(optionalUser.get());
                if(! mesModesPaiement.isEmpty()){ // Il existe au moins un moyen de paiement pour cet utilisateur

                    // Filtrer pour convertir en UserPaiementModeDTO
                    List<UserPaymentModeDTO> mesModes = mesModesPaiement.stream()
                            .map(mode -> ProfileServices.convertUserPaymentModeToDTO(mode))
                            .collect(Collectors.toList());

                    return ResponseEntity.status(HttpStatus.OK).body(mesModesPaiement);
                }else{
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(commonServices.findErrorByCode(737, language), true));
                }
            }else{ // L'utilisateur n'existe pas dans le système
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(722, language), false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while searching the payment methods.", false));
        }
    }


    /**
     * Cette méthode permet d'enregistrer un numéro d'urgence ou de confiance d'un utilisateur de la plateforme TaxiRide. (Ok)
     * @param emergencyContactDTO
     * @return Les informations sur le contact qui vien d'être enregistré.
     * {@link EmergencyContactDTO}
     */
    @Secured({"ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "Enregistrement d'un numéro d'urgence ou de confiance d'un utilisateur., si le message retourné est OK simplement, ça veut dire que ce numéro de téléphone existe déjà pour le type de numéro choisi")
    @PostMapping("/saveProfileSafetyNumber")
    public ResponseEntity<?> saveProfileSafetyNumber(@Valid @RequestBody EmergencyContactDTO emergencyContactDTO){

        try{
            // Rechercher le potentiel propriétaire du véhicule
            Optional<Users> optionalUser = userService.findByPhoneNumber(emergencyContactDTO.getPhoneNumber());
            
            

            Integer contactType = Integer.valueOf(emergencyContactDTO.getContactType());

            if(optionalUser.isPresent() && (contactType == 0 || contactType == 1)){ // L'utilisateur existe sur le système

                Users user = optionalUser.get();

                // Se rassurer que cet utilisateur n'a pas encore atteint son quota de numéros de la catégorie sollicitée
                List<EmergencyContact> mesContacts = emergencyContactService.findAllByUserAndContactType(user, Integer.valueOf(emergencyContactDTO.getContactType()));
                
                //vérifier que cet numéro de téléphone n'est pas encore enregistré
                List<EmergencyContact> existeContact = emergencyContactService.verifierNumeroExiste(
                		emergencyContactDTO.getContactType(),
                		user.getId(), emergencyContactDTO.getPhone());
                
                if(existeContact.size() > 0)
                	//return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ApiResponse("Vous avez déjà enregistré ce numéro de téléphone!", false));
                	throw new RuntimeException("Vous avez déjà enregistré ce numéro de téléphone!");
                	//+ emergencyContactDTO.getContactType() + " "+user.getId() +" "+ emergencyContactDTO.getPhone());
                
                	
        		
                if(mesContacts.size() < 3){ // L'utilisateur n'a pas encore dépassé 3 contacts dans la catégorie sollicitée
                    // Construire un contact à partir des informations reçues et l'enregistrer
                    EmergencyContact newContact = new EmergencyContact();

                    newContact.setUser(user);
                    newContact.setName(emergencyContactDTO.getName());
                    newContact.setPhone(emergencyContactDTO.getPhone());
                    newContact.setContactType(Integer.valueOf(emergencyContactDTO.getContactType()));

                    EmergencyContact savedContact = emergencyContactService.save(newContact);
                    emergencyContactDTO.setId(savedContact.getId());

                    return ResponseEntity.status(HttpStatus.OK).body(emergencyContactDTO);
                }else{ // L'utilisateur a épuisé son quota de contact de la catégorie sélectionnée (03 contacts)
                    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ApiResponse(commonServices.findErrorByCode(739, emergencyContactDTO.getLanguage()), false));
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("The user account is not found or contact type does not exists.", false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(commonServices.findErrorByCode(848, emergencyContactDTO.getLanguage()), false));
        }
    }

    /**
     * Cette méthpde permet de retrouver tous les contacts d'urgence ou de confiance enrégistré sur le profil d'un utilisateur. (Ok)
     * @param phone
     * @return La liste des numéro d'urgence ou de confiance qu'un utilisateur a enregistré sur son profil.
     */
    @Secured({"ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "Recherche des contacts d'un utilisateur à partir de son numéro de téléphone.")
    @GetMapping("/findSafetyNumberByPhone/{phone}/{language}")
    public ResponseEntity<?> findSafetyNumberByPhone(@PathVariable String phone, @PathVariable String language){
        try{
            // Retrouver le potentiel utilisateur
            Optional<Users> optionalUser = userService.findByPhoneNumber(phone);

            if(optionalUser.isPresent()){ // L'utilisateur est présent sur le système, retourner ses contacts

                List<EmergencyContact> mesContacts = emergencyContactService.findAllByUser(optionalUser.get());
                if(mesContacts.size() != 0){ // Au moins un contact est retrouvé

                    // Filtrer pour convertir en EmergencyContactDTO
                    List<EmergencyContactDTO> contactts = mesContacts.stream()
                            .map(contact -> ProfileServices.convertEmergencyContactToDTO(contact))
                            .collect(Collectors.toList());

                    return ResponseEntity.status(HttpStatus.OK).body(contactts);
                }else{ // Aucun contact retrouvé pour cet utilisateur
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(commonServices.findErrorByCode(740, language), false));
                }
            }else{ // L'utilisateur n'existe pas dans le système
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(722, language), false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while searching the payment methods.", false));
        }
    }


    /**
     * Cette méthode permet de rechercher uniquement soit les numéro d'urgence ou les numéro de confiance qu'un utilisateur a enregistré (Ok)
     * sur son profil.
     * @param phone
     * @param type
     * @return La liste des contacts d'une catégorie donnée qu'un utilisateur a enregistré sur son profil.
     */
    @Secured({"ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "Recherche des contacts de confiance ou d'émergence d'un utilisateur à partir de son numéro de téléphone.")
    @GetMapping("/findConfidenceOrAlertNumberByPhone/{phone}/{type}/{language}")
    public ResponseEntity<?> findConfidenceOrAlertNumberByPhone(@PathVariable String phone, @PathVariable String type, @PathVariable String language){
        try{
            // Retrouver le potentiel utilisateur
            Optional<Users> optionalUser = userService.findByPhoneNumber(phone);

            if(optionalUser.isPresent()){ // L'utilisateur est présent sur le système, retourner ses contacts

                // Filtrer la liste des contacts de l'utilisateur pour retrouver uniquement les contacts de confiance
                List<EmergencyContact> mesContactsConfiance = emergencyContactService.findAllByUser(optionalUser.get())
                        .stream()
                        .filter(contact -> contact.getContactType() == Integer.valueOf(type))
                        .collect(Collectors.toList());
                if(mesContactsConfiance.size() != 0){ // Au moins un contact de confiance est retrouvé

                    // Filtrer pour convertir en EmergencyContactDTO
                    List<EmergencyContactDTO> contactts = mesContactsConfiance.stream()
                            .map(contact -> ProfileServices.convertEmergencyContactToDTO(contact))
                            .collect(Collectors.toList());

                    return ResponseEntity.status(HttpStatus.OK).body(contactts);
                }else{ // Aucun contact de confiance retrouvé pour cet utilisateur
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(commonServices.findErrorByCode(741, language), false));
                }
            }else{ // L'utilisateur n'existe pas dans le système
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(722, language), false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while searching the payment methods.", false));
        }
    }


    /**
     * Cette méthode permet de vérifier l'existence d'un code de parainage sur le système taxiRide. (Ok)
     * @param referalCodeDTO
     * @return Si oui ou non le code de parainage existe.
     * {@link ReferalCodeDTO}
     */
    @Secured({"ROLE_ADMIN"})
    @ApiOperation(value = "Valider un code de parainage.")
    @PostMapping("/validateReferalCode")
    public ResponseEntity<?> validateReferalCode(@Valid @RequestBody ReferalCodeDTO referalCodeDTO){

        try{
            // Rechercher le potentiel utilisateur
            Optional<Users> optionalUser = userService.findByPhoneNumber(referalCodeDTO.getPhoneNumber());

            // Vérifie que cet utilisateur existe et que son code de référence existe aussi
            if(optionalUser.isPresent() && userService.existsByReferalCodeAndPhone(referalCodeDTO.getReferalCode(), referalCodeDTO.getPhoneNumber())){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(625, referalCodeDTO.getLanguage()), true));
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(742, referalCodeDTO.getLanguage()), false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while searching the payment methods.", false));
        }
    }


    /**
     * Cette méthode permet à un utilisatur d'ajouter un code promotionnel à son profil. (Ok)
     * @param userPromotionalCodeDTO
     * @return Les détails du code promotionnel qui vient d'être enregistré.
     * {@link UserPromotionalCodeDTO}
     */
    @Secured({"ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "Enregistrement du code promotionnel d'un profil utilisateur.")
    @PostMapping("/saveProfilePromoCode")
    public ResponseEntity<?> saveProfilePromoCode(@Valid @RequestBody UserPromotionalCodeDTO userPromotionalCodeDTO){

        try{
            // Rechercher le potentiel propriétaire du véhicule
            Optional<Users> optionalUser = userService.findByPhoneNumber(userPromotionalCodeDTO.getPhoneNumber());

            // Rechercher le code promotionnel associé dans le système
            Optional<PromotionalCode> optionalPromotionalCode = promotionalCodeService.findByKey(userPromotionalCodeDTO.getCodeKey());

            if(optionalUser.isPresent() && optionalPromotionalCode.isPresent()){ // L'utilisateur existe sur le système

                Users user = optionalUser.get();
                PromotionalCode promotionalCode = optionalPromotionalCode.get();

                // Se rassurer que cet utilisateur n'a pas encore enregistré ce code promotionnel pour son profil
                List<UserPromotionalCode> codesPromoExistants = userPromotionalCodeService.findAllByUser(user)
                        .stream()
                        .filter(code -> code.getPromotionalCode().getCodeKey().equals(promotionalCode.getCodeKey()))
                        .collect(Collectors.toList());

                if(codesPromoExistants.size() == 0){ // L'utilisateur n'a pas encore enregistré un tel code promotionnel dans son profil

                    // Construire un code promotionnel de l'utilisateur à partir des informations reçues de la requête et l'enregistrer
                    UserPromotionalCode newUserCode = new UserPromotionalCode();

                    newUserCode.setUser(user);
                    newUserCode.setPromotionalCode(promotionalCode);
                    newUserCode.setPromotionalCodeUsed(false); // Le code promotionnel vient d'être généré, il n'est pas encore utilisé

                    UserPromotionalCode savedCode = userPromotionalCodeService.save(newUserCode);
                    userPromotionalCodeDTO.setId(savedCode.getId());
                    userPromotionalCodeDTO.setPromotionalCodeUsed(savedCode.getPromotionalCodeUsed());

                    return ResponseEntity.status(HttpStatus.OK).body(userPromotionalCodeDTO); // Renvoyer les informations sur le code enregistré
                }else{ // L'utilisateur a déjà un véhicule enregistré dans le système
                    return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new ApiResponse(commonServices.findErrorByCode(743, userPromotionalCodeDTO.getLanguage()), false));
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Promotional code or user account not found.", false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(commonServices.findErrorByCode(848, userPromotionalCodeDTO.getLanguage()), false));
        }
    }

    /**
     * Cette méthode permet de vérifier qu'un code promotionnel enregistré sur le profil de l'utilisateur est valide.
     * La validité d'un code promotionnel tient compte du fait que le code est non expiré et est non utilisé. (Ok)
     * @param userPromotionalCodeDTO
     * @return Si vrai ou non un code promotionnel est valide.
     */
    @Secured({"ROLE_ADMIN"})
    @ApiOperation(value = "Valider un code de promotionnel du profil utilisateur.")
    @PostMapping("/validatePromoCode")
    public ResponseEntity<?> validatePromoCode(@Valid @RequestBody UserPromotionalCodeDTO userPromotionalCodeDTO){

        try{
            // Retrouver l'utilisateur concerné
            Optional<Users> optionalUser = userService.findByPhoneNumber(userPromotionalCodeDTO.getPhoneNumber());

            // Retrouver le code promotionnel de l'utilisateur de clé associée
            Optional<PromotionalCode> optionalCode = promotionalCodeService.findByKey(userPromotionalCodeDTO.getCodeKey());

            // Vérifier que ce code promotionnel est enrégistré pour l'utilisateur
            if(optionalUser.isPresent() && optionalCode.isPresent()){

                // Récupérer la liste des code promotionnels valides de cette utilisateur et dont la clé est celle donnée en paramètre
                List<UserPromotionalCode> userCodes = userPromotionalCodeService.findAllByUser(optionalUser.get())
                        .stream()
                        .filter(code -> (code.getPromotionalCode().getCodeKey().equals(optionalCode.get().getCodeKey()) && code.isValid()))
                        .collect(Collectors.toList());

                if(userCodes.size() != 0){
                    return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse(commonServices.findErrorByCode(745, userPromotionalCodeDTO.getLanguage()), true));
                }else{
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(730, userPromotionalCodeDTO.getLanguage()), false));
                }
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("The promotional code or the user account not found.", false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse(commonServices.findErrorByCode(852, userPromotionalCodeDTO.getLanguage()), false));
        }
    }

    /**
     * Cette méthode permet de rechercher tous les codes promotionnels (valides ou non) qu'un utilisateur a enregistré sur son profil.
     * @param phone
     * @return La liste des codes promotionnels qu'un utilisateur a enregistré sur son profil.
     */
    @Secured({"ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "Recherche des codes promotionnels d'un utilisateur à partir de son numéro de téléphone.")
    @GetMapping("/findPromoCodeByPhone/{phone}/{language}")
    public ResponseEntity<?> findPromoCodeByPhone(@PathVariable String phone,@PathVariable String language){
        try{
            // Retrouver le potentiel utilisateur
            Optional<Users> optionalUser = userService.findByPhoneNumber(phone);

            if(optionalUser.isPresent()){ // L'utilisateur est présent sur le système, retourner ses codes promotionnels

                List<UserPromotionalCode> mesCodesPromotionnels = userPromotionalCodeService.findAllByUser(optionalUser.get());
                if(mesCodesPromotionnels.size() != 0){ // Au moins un code promotionnel est retrouvé pour cet utilisateur

                    // Filtrer pour convertir en UserPromotionalCodeDTO
                    List<UserPromotionalCodeDTO> mesCodes = mesCodesPromotionnels.stream()
                            .map(code -> ProfileServices.convertPromotionalCodeToDTO(code))
                            .collect(Collectors.toList());

                    return ResponseEntity.status(HttpStatus.OK).body(mesCodes);
                }else{ // Aucun code promotionnel retrouvé pour cet utilisateur
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(commonServices.findErrorByCode(746, language), false));
                }
            }else{ // L'utilisateur n'existe pas dans le système
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(722, language), false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while searching the payment methods.", false));
        }
    }


    /**
     * Cette méthode permet de rechercher uniquement les codes promotionnels valides (non expirés et pas encore utilisés) qu'un utilisateur
     * a enrégistré sur son profil. (Ok)
     * @param phone
     * @return La liste des codes promotionnels valides qu'un utilisateur a enregistré sur son profil.
     */
    @Secured({"ROLE_ADMIN","ROLE_RIDER"})
    @ApiOperation(value = "Recherche des codes promotionnels non encore utilisés et non expirés d'un utilisateur à partir de son numéro de téléphone.")
    @GetMapping("/findAvailablePromoCodeByPhone/{phone}/{language}")
    public ResponseEntity<?> findAvailablePromoCodeByPhone(@PathVariable String phone, @PathVariable("language") String language){
        try{
            // Retrouver le potentiel utilisateur
            Optional<Users> optionalUser = userService.findByPhoneNumber(phone);

            if(optionalUser.isPresent()){ // L'utilisateur est présent sur le système, retourner ses codes promotionnels

                List<UserPromotionalCode> mesCodesPromotionnelsValides = userPromotionalCodeService.findAllByUser(optionalUser.get())
                        .stream()
                        .filter(code -> code.isValid())
                        .collect(Collectors.toList());
                if(mesCodesPromotionnelsValides.size() != 0){ // Au moins un code promotionnel encore utilisable est retrouvé pour cet utilisateur
                    // Filtrer pour convertir en UserPromotionalCodeDTO
                    List<UserPromotionalCodeDTO> mesCodes = mesCodesPromotionnelsValides.stream()
                            .map(code -> ProfileServices.convertPromotionalCodeToDTO(code))
                            .collect(Collectors.toList());
                    return ResponseEntity.status(HttpStatus.OK).body(mesCodes);
                }else{ // Aucun code promotionnel encore utilisable n'est retrouvé pour cet utilisateur
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ApiResponse(commonServices.findErrorByCode(746, language), false));
                }
            }else{ // L'utilisateur n'existe pas dans le système
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(commonServices.findErrorByCode(722, language), false));
            }
        }catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("An error occur while searching the payment methods.", false));
        }
    }



}
