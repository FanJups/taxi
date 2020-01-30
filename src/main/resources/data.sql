-- Insertion des roles initiaux dans le système
INSERT INTO roles (role_id, role_name, role_description_en, role_description_fr, role_name_en, role_name_fr) VALUES
(1,'ROLE_RIDER','A rider on the system','Un passager du systeme','Rider','Passager'),
(2,'ROLE_DRIVER','A driver on the system','Un chauffeur sur le systeme','Driver','Chauffeur'),
(3,'ROLE_ADMIN','The administrator of the platform','L''administrateur de la plateforme','Administrator','Administrateur');

INSERT INTO promotional_code (promotional_code_id, created_at, updated_at, coast, code_key, end_date, start_date) VALUES
(1,'2019-09-20','2019-09-20',1200.0,'235568','2019-10-20','2019-09-20'),
(2,'2019-09-16','2019-09-16',400.0,'98821','2019-09-20','2019-09-16'),
(3,'2019-10-21','2019-10-21',700.0,'366548','2019-12-11','2019-10-21');

INSERT INTO payment_mode (payment_mode_id, created_at, updated_at, description_en, description_fr, name_en, name_fr, payment_type) VALUES
(1,'2019-09-20','2019-09-20','Mobile Payment','Paiement mobile','MOBILE MONEY', 'PAIEMENT MOBILE', 0),
(2,'2019-09-20','2019-09-20','Cash Payment','Paiement en cash','CASH', 'ESPECE', 1),
(3,'2019-09-20','2019-09-20','Wallet Payment','Paiement par portefeuille electronique','WALLET', 'MONNAIE ELECTRONIQUE', 2),
(4,'2019-09-20','2019-09-20','Credit Card Payment','Paiement par carte bancaire','CREDIT CARD', 'CARTE DE CREDIT', 3);
INSERT INTO `users` (`user_id`, `created_at`, `updated_at`, `country`, `active`, `address`, `birth_date`, `city`, `code_trip_option`, `contry_code`, `default_paymentmode`, `default_request_radius`, `default_travel_option`, `driver_operating_city_code`, `driver_operating_country_code`, `email`, `external_referal_code`, `first_name`, `gender`, `image_url`, `is_email_verified`, `is_phone_verified`, `is_refresh_active`, `is_verified`, `language`, `last_name`, `minimal_notification_distance`, `name`, `password`, `phone_number`, `profession`, `profile_picture_mime_type`, `provider`, `provider_id`, `referal_code`, `referal_code_used`, `status`, `subscribe_to_email`, `subscribe_to_push`, `subscribe_tosms`, `verification_code`) VALUES
(1, '2019-10-09 14:50:54', '2019-10-11 13:19:08', NULL, b'1', NULL, NULL, NULL, NULL, '237', NULL, NULL, NULL, NULL, NULL, 'devsahamerlin@gmail.com', NULL, NULL, NULL, 'PHOTO_1_+237695005217_ee637608-ca54-4987-ab5e-bfe6cc49b709.png', b'0', b'0', b'1', b'0', NULL, NULL, NULL, NULL, '$2a$10$.UOLqQ8MtcgEgroznzozTelxwjBYg3wRygk2zNenilxWSDP1ShSiq', '695005217', NULL, 'png', 'local', NULL, 'vm4m2w', b'0', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `users_roles`(`user_id`, `role_id`) VALUES (1,3);

INSERT INTO `internal_account`(`account_id`, `last_operation_date`,`account_type`,`internal_account_balance`,`operator_code`) VALUES (1,CURRENT_TIMESTAMP,1,0,"PAYPAL");
INSERT INTO `internal_account`(`account_id`, `last_operation_date`,`account_type`,`internal_account_balance`,`operator_code`) VALUES (2,CURRENT_TIMESTAMP,0,0,"MOMO");
INSERT INTO `internal_account`(`account_id`, `last_operation_date`,`account_type`,`internal_account_balance`,`operator_code`) VALUES (3,CURRENT_TIMESTAMP,3,0,"UBA");
INSERT INTO `users` (`user_id`, `created_at`, `updated_at`, `country`, `active`, `address`, `birth_date`, `city`, `code_trip_option`, `contry_code`, `default_paymentmode`, `default_request_radius`, `default_travel_option`, `driver_operating_city_code`, `driver_operating_country_code`, `email`, `external_referal_code`, `first_name`, `gender`, `image_url`, `is_email_verified`, `is_phone_verified`, `is_refresh_active`, `is_verified`, `language`, `last_name`, `minimal_notification_distance`, `name`, `password`, `phone_number`, `profession`, `profile_picture_mime_type`, `provider`, `provider_id`, `referal_code`, `referal_code_used`, `status`, `subscribe_to_email`, `subscribe_to_push`, `subscribe_tosms`, `verification_code`) VALUES
(2, '2019-10-09 14:50:54', '2019-10-11 13:19:08', NULL, b'1', NULL, NULL, NULL, NULL, '237', NULL, NULL, NULL, NULL, NULL, 'devforceadvit@gmail.com', NULL, NULL, NULL, 'PHOTO_1_+237695005217_ee637608-ca54-4987-ab5e-bfe6cc49b709.png', b'0', b'0', b'1', b'0', NULL, NULL, NULL, NULL, '$2a$10$8yr0N8zmavEsdubETppLduimpFUmUiZQaAAKAPA.riTpX9ABL8abe', '670338965', NULL, 'png', 'local', NULL, 'vm4m2w', b'0', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `users_roles`(`user_id`, `role_id`) VALUES (2,3);

/*
	Api :
		package biz.advanceitgroup.taxirideserver.courses.webs.RidesRestController;
		@PostMapping("/postRide")
		postRide(RidesDTO)

	Test
		package biz.advanceitgroup.taxirideserver.test.courses.RidesRestControllerTest;
		postRide()

	13.01.2020

	Author : Fanon Jupkwo

*/

/*START*/

/*INSERT INTO `users` (`user_id`, `created_at`, `updated_at`, `country`, `active`, `address`, `birth_date`, `city`, `code_trip_option`, `contry_code`, `default_paymentmode`, `default_request_radius`, `default_travel_option`, `driver_operating_city_code`, `driver_operating_country_code`, `email`, `external_referal_code`, `first_name`, `gender`, `image_url`, `is_email_verified`, `is_phone_verified`, `is_refresh_active`, `is_verified`, `language`, `last_name`, `minimal_notification_distance`, `name`, `password`, `phone_number`, `profession`, `profile_picture_mime_type`, `provider`, `provider_id`, `referal_code`, `referal_code_used`, `status`, `subscribe_to_email`, `subscribe_to_push`, `subscribe_tosms`, `verification_code`) VALUES
(3, '2020-01-13 15:50:00', '2020-01-13 17:00:08', NULL, b'1', NULL, NULL, NULL, NULL, '237', NULL, NULL, NULL, NULL, NULL, 'jupsfan@gmail.com', NULL, NULL, NULL, 'PHOTO_1_+237695005217_ee637608-ca54-4987-ab5e-bfe6cc49b709.png', b'1', b'1', b'1', b'0', NULL, NULL, NULL, NULL, '$2a$10$8yr0N8zmavEsdubETppLduimpFUmUiZQaAAKAPA.riTpX9ABL8abe', '670628896', NULL, 'png', 'local', NULL, 'vm4m2w', b'0', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `users_roles`(`user_id`, `role_id`) VALUES (3,3);*/

/*END*/


INSERT INTO `trip_option`(`created_at`, `base_fare`, `city_code`, `country_code`, `driver_cancel_fee_formula`, `driver_cancel_timeout`, `kilometer_rate`, `minute_rate`, `option_code`, `option_description`, `option_name`, `reservation_fee_formula`, `rider_cancel_fee_formula`, `rider_cancel_timeout`, `trip_cost_calcul_formula`, `trip_fee_formula`) 
VALUES (now(),10,"Yaounde","cm","SELECT (`trip_option_base_fare`+`trip_option_kilometer_rate`*`estimated_trip_distance`+`trip_option_minute_rate`*`estimated_trip_duration`)/20 FROM `trip` WHERE `trip_id`=?",300,1000,1000,"VIP","There for very mportant types of persons","Very important person","SELECT (`trip_option_base_fare`+`trip_option_kilometer_rate`*`estimated_trip_distance`+`trip_option_minute_rate`*`estimated_trip_duration`)/10 FROM `trip` WHERE `trip_id`=?","SELECT (`trip_option_base_fare`+`trip_option_kilometer_rate`*`estimated_trip_distance`+`trip_option_minute_rate`*`estimated_trip_duration`)/10 FROM `trip` WHERE `trip_id`=?",300,"SELECT (trip. 	trip_option_base_fare  + trip.trip_option_kilometer_rate * trip.estimated_trip_distance + trip.trip_option_minute_rate* trip.estimated_trip_duration)","SELECT `trip_option_base_fare`+`trip_option_kilometer_rate`*`estimated_trip_distance`+`trip_option_minute_rate`*`estimated_trip_duration`AS trip_cost FROM `trip` WHERE `trip_id`=?"),
(now(),10,"Yaounde","cm","0",300,1000,1000,"VIPsample","There for very mportant types of persons","Very important person","SELECT (trip.trip_option_base_fare  + trip.trip_option_kilometer_rate * trip.estimated_trip_distance + trip.trip_option_minute_rate* trip.estimated_trip_duration)/10","SELECT (trip.trip_option_base_fare  + trip.trip_option_kilometer_rate * trip.estimated_trip_distance + trip.trip_option_minute_rate* trip.estimated_trip_duration)/10",300,"SELECT (trip. 	trip_option_base_fare  + trip.trip_option_kilometer_rate * trip.estimated_trip_distance + trip.trip_option_minute_rate* trip.estimated_trip_duration)","SELECT `base_fare`+(?)*`kilometer_rate`+(?)*`minute_rate` FROM `trip_option` WHERE `option_code`=?");

INSERT INTO `error_definition`(`id`,`error_code`,`value_syst`,`value_en`,`value_fr`)VALUES 
(1,404,'Not found','Not found','Aucun resultat'),
(2,400,'Bad request','Bad request','Mauvaise requete'),
(3,401,'Unauthorized','Unauthorized','Pas authorisé'),
(4,200,'Ok','Ok','Ok'),
(5,204,'Not content','Not content','Aucun contenu'),
(6,201,'Created','Created','Creation reussi'),
(7,403,'Forbidden','Forbidden','interdit d access'),
(8,808,'can not be null','can not be null','Ne peut être null'),
(9,611,'Driver location successfully saved','Driver location successfully saved','position du driver sauvegardée avec success'),
(10,612,'Ride event successfully saved','Ride event successfully saved','course sauvegardée avec success'),
(11,613,'Notification successfully sent','Notification successfully sent','Notification envoyée avec success'),
(12,614,'Ride Successfully saved','Ride Successfully saved','course enrégistrée avec success'),
(13,615,'Trip accepted','Trip accepted','Transport accepté'),
(14,835,'An error occured while attempting to save location','An error occured while attempting to save location','Une erreur est survenu lors de la sauvegarde de la position courante'),
(15,834,'Failed to save the current position','Failed to save the current position','Echec de sauvegarde de la position'),
(16,837,'An error occured while saving ride','An error occured while saving ride','Une erreur est survenu lors de la sauvegarde du transport'),
(17,838,'Can send notifications','Can send notifications','Ne peut envoyer la notification'),
(18,839,'Cant send notification to..','Cant send notification to..','ne peut envoyer une notification à...'),
(19,840,'An error occured while attempting to send notifications','An error occured while attempting to send notifications','Une erreur est survenu lors de l envoie de la notification'),
(20,841,'An error occured while attempting to save ride','An error occured while attempting to save ride','une erreur est survenu lors de la sauvegarde du transport'),
(21,842,'Cant get driver location','Cant get driver location','impossible d obtenir la position du driver'),
(22,843,'Cant get city option for this country','Cant get city option for this country','Ne peut obtenir une option de transport urbain pour ce pays'),
(23,853,'Invalid cab informaitons','Invalid cab informaitons','Informations de vehicule invalide'),
(24,854,'not enough money in your internal account','not enough money in your internal account','pas assez d argent dans votre compte interne'),
(25,845,'An error occured while searching the user','An error occured while searching the user','Une erreur est survenu lors de la recherche de l utilisateur'),
(26,616,'Car picture successfully deleted','Car picture successfully deleted','Vehicule supprimé avec success'),
(27,845,'An error occured while searching picture','An error occured while searching picture','Une erreur est survenu lors de la recherche du vehicule'),
(28,617,'Emergency contact successfully deleted','Emergency contact successfully deleted','Emergency contact inexistant'),
(29,722,'The user account does not exists on the system','The user account does not exists on the system','le compte utilisateur est introuvable sur le système'),
(30,619,'payment mode successfully deleted','payment mode successfully deleted','Mode de payement supprimé'),
(31,729,'Payment does not exists','Payment does not exists','le mode de payement existe pas'),
(32,620,'Default payment mode set successfully ','Default payment mode set successfully ','Mode de payement par defaut enregistré avec success'),
(33,729,'Payment does not exists','Payment does not exists','Mode de payement inexistant'),
(34,730,'Promotional code does not exists','Promotional code does not exists','Code promotionel inexistant'),
(35,722,'Unable to find the user account','Unable to find the user account','impossible de trouver le compte utilisateur'),
(36,846,'An error occured while updating the profiles','An error occured while updating the profiles','erreur survenu lors de la mise à jour du profil'),
(37,621,'The profile picture uploaded successfully','The profile picture uploaded successfully','photo de profil modifiée avec success'),
(38,722,'The user is not found','The user is not found','utilisateur pas trouve'),
(39,610,'Unable to load the file','Unable to load the file','impossible de charger le fichier'),
(40,731,'Your status as a driver has already been verified','Your status as a driver has already been verified','Votre profil driver a ete verifie'),
(41,622,'Document updated successfully','Document updated successfully','document mis a jour'),
(42,623,'Document uploaded successfully','Document uploaded successfully',''),
(43,830,'Unable to upload the document','Unable to upload the document','Impossible de telecharger le document'),
(44,847,'And error occured while Calculate rider profil completion','And error occured while Calculate rider profil completion','Une erreur est survenu lors du calcul du taux de completion du profil passager'),
(45,848,'And error occured while Calculate driver profil completion','And error occured while Calculate driver profil completion','Erreur survenu lors du calcul du taux de completion du profil chauffeur'),
(46,732,'You have no document loaded on TaxiRide','You have no document loaded on TaxiRide','Vous n avez aucun document enrégistré sur TaxiRide'),
(47,845,'And error occured while searching for your documents','And error occured while searching for your documents','Erreur survenue lors de la recherche des documents'),
(48,734,'You have no cab registered on the TaxiRide','You have no cab registered on the TaxiRide','Vous n avez aucun vehicule enregistre sur TaxiRide'),
(49,735,'You already register a cab on TaxiRide','You already register a cab on TaxiRide','Vous avez deja enregistre un taxi sur TaxiRide'),
(50,736,'You already added that payment mode in your account','You already added that payment mode in your account','Vous avez deja ajoute ce mode de payement a votre compte'),
(51,850,'An error occur while saving the payment method','An error occur while saving the payment method','Erreur survenu lors de la sauvegarde du mode de payement'),
(52,737,'You have no payment mode registered for your account','You have no payment mode registered for your account','Vous n avez aucun mode de payement enregistre a ce compte'),
(53,739,'You Cannot register more than 03 contacts of the selected category','You Cannot register more than 03 contacts of the selected category','Impossible de sauvegarder plus de 03 contacts de la categorie selectionnee'),
(54,740,'You have no safety contact registered on the system','You have no safety contact registered on the system','Vous n avez aucun contact de confiance sauvegardé sur le système'),
(55,741,'You have no contact of that category registered on the system','You have no contact of that category registered on the system','vous n avez aucun contact de cette catégorie sur le système'),
(56,625,'The referal code is found successfully','The referal code is found successfully','Le code de parrainage a été trouvé'),
(57,742,'The referal code is not found on the system','The referal code is not found on the system','Le code de parrainage n a pas été trouvé dans le système'),
(58,743,'You already register this promotional code on your TaxiRide account','You already register this promotional code on your TaxiRide account','Vous avez déjà enrégistré un code promotionnel avec ce compte'),
(59,745,'The promotional code is found','The promotional code is found','code promotionnel retrouvé'),
(60,730,'The promotional code not found','The promotional code not found','Code promotionnel introuvable'),
(61,852,'An error occur while validating the promotional code','An error occur while validating the promotional code','Erreur obtenu lors de la validation du code promotionnel'),
(62,746,'You have no promotional code registered on the system','You have no promotional code registered on the system','Vous n avez aucun code promotionnel enrégistré sur le system'),
(63,748,'the trip is not yet end','the trip is not yet end','Le transport n est pas terminé'),
(64,853,'An error occured while recharging your account','An error occured while recharging your account','Une erreur est survenu lors de la recharge de votre compte'),
(65,626,'driver notation saved successfully','driver notation saved successfully','la notation du chauffeur a été sauvegardée avec success'),
(66,627,'rider notation saved successfully','rider notation saved successfully','la notation du passager a été sauvegardée avec success'),
(67,854,'An error occured while saving the driver notation','An error occured while saving the driver notation','une erreur est survenu lors de la sauvegarde de la notation du chauffeur'),
(68,855,'An error occured while saving the rider notation','An error occured while saving the rider notation','une erreur est survenu lors de la sauvegarde de la notation du passager'),
(69,856,'An error occured while withdrawing money from the account','An error occured while withdrawing money from the account','une erreur est survenu lors du debit du compte'),
(70,857,'Not having enough money in your internal account','Not having enough money in your internal account','Pas assez d argent dans votre compte interne'),
(71,858,'Trip id does not exist','Trip id does not exist','l identifiant de voyage n existe pas'),
(72,626,'The travel has been saved successfully','The travel has been saved successfully','le voyage a été sauvegardé avec success'),
(73,729,'The travel path does not exist in the application','The travel path does not exist in the application','le chemin de voyage n existe pas dans l application');

INSERT INTO `country` (`country_id`, `created_at`, `updated_at`, `country_name`, `notification_distance`, `notification_time`, `signal_scope`, `trip_cancellation_time`) VALUES (NULL, NULL, NULL, 'Cameroun', '3', '3', '4', '5');

INSERT INTO `city` (`city_id`, `created_at`, `updated_at`, `city_name`, `notification_distance`, `notification_time`, `signal_scope`, `trip_cancellation_time`,`longitude`,`latitude`) VALUES (NULL, NULL, NULL, 'Yaounde, Cameroun', 3, 4, 6, 6,11.5,3.8);


INSERT INTO `parameters`( `value_en`, `value_fr`, `cle`) VALUES ('1','1','error_radius');



/*
	Api :
		package biz.advanceitgroup.taxirideserver.courses.webs.RidesRestController;
		@GetMapping("/findAllAvailableDrivers/{tripId}/{codePays}/{ville}/{longitude}/{latitude}/{rayon}/{language}")
		findAllAvailableDrivers(@PathVariable long tripId ,@PathVariable String codePays, @PathVariable String ville, @PathVariable double longitude, @PathVariable double latitude, @PathVariable long rayon, @PathVariable String language)


	Test
		package biz.advanceitgroup.taxirideserver.test.courses.RidesRestControllerTest;
		findAllAvailableDrivers()

	14.01.2020

	Author : Fanon Jupkwo

*/

/*START*/

--INSERT INTO `trip`(`trip_id`,...) VALUES ();



/*END*/


/*
	
	Test Client Postman

	Api :
		package biz.advanceitgroup.taxirideserver.carpooling.webs.CarpoolingRestController;
		@PostMapping("/postPickupBookingInformations")
		postPickupBookingInformations(@Valid @RequestBody PickUpBookingInformationsDto pickUpBookingInformationsDto)

	Test
		

	17.01.2020

	Author : Fanon Jupkwo

*/

/*START*/

INSERT INTO `travel_path`(`id`,`country_code`,`departure_city`,`arrival_city`,`default_travel_cost`,`default_travel_duration`,`default_travel_distance`) VALUES (3,'cm','Yaounde','Douala',600.0,4,400);

--INSERT INTO `users_roles`(`user_id`, `role_id`) VALUES (3,3);

/*END*/


