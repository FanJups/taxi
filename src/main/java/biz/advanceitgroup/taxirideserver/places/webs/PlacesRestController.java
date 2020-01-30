/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biz.advanceitgroup.taxirideserver.places.webs;

import biz.advanceitgroup.taxirideserver.commons.services.CommonServices;
import biz.advanceitgroup.taxirideserver.places.dto.CityPlacesDto;
import biz.advanceitgroup.taxirideserver.places.entities.CityPlaces;
import biz.advanceitgroup.taxirideserver.places.repositories.PlacesRepository;
import biz.advanceitgroup.taxirideserver.places.services.interfaces.PlacesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Date;
import java.util.List;
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

/**
 *
 * @author daniel
 */
@Api(value = "Places API")
@RestController
@RequestMapping("/api/places")
public class PlacesRestController {
    
    @Autowired 
    CommonServices commonServices;
    
    @Autowired
    PlacesService placesService;
    
    @Autowired
    PlacesRepository placesRepository;
    
    /**
     *
     * @param countryCode
     * @param city
     * @param lastId
     * @return
     */
    @ApiOperation(value = "Récupérer la liste de toutes les places nouvellement ajoutées à la bd")
    @GetMapping("/findNewPlaces/{countryCode}/{city}/{lastId}/{language}")
    @ResponseBody
    public ResponseEntity<?> findNewPlaces(@PathVariable String countryCode,@PathVariable String city,@PathVariable long lastId,@PathVariable("language") String language ){
        try {
            List<CityPlaces> cityPlaceses=placesRepository.findNewPlaces(countryCode, city, lastId);
            return ResponseEntity.status(HttpStatus.OK).body(commonServices.findErrorByCode(200,language)+"\n"+cityPlaceses);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonServices.findErrorByCode(404,language)+"\n"+e.getMessage());
        }
    }

    /**
     *
     * @param cityPlacesDTO
     * @return
     */
    @ApiOperation(value = "sauvegarder une nouvelle place dans la bd")
    @PostMapping("/postNewPlace")
    public ResponseEntity<?> postNewPlaces(@Valid @RequestBody CityPlacesDto cityPlacesDTO){
        try {
            CityPlaces place=new CityPlaces(cityPlacesDTO.getCountryCode(),cityPlacesDTO.getCity(),cityPlacesDTO.getName(),cityPlacesDTO.getAddress(), cityPlacesDTO.getLongitude(),cityPlacesDTO.getLatitude(),new Date());
            CityPlaces cityPlaces=placesRepository.save(place);
            return ResponseEntity.status(HttpStatus.OK).body(commonServices.findErrorByCode(200,cityPlacesDTO.getLanguage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(commonServices.findErrorByCode(404,cityPlacesDTO.getLanguage())+"\n"+e.getMessage());
        }
    }
}
