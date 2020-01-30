package biz.advanceitgroup.taxirideserver.test.courses;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PathVariable;

import biz.advanceitgroup.taxirideserver.AbstractTest;
import biz.advanceitgroup.taxirideserver.authentification.entities.Users;
import biz.advanceitgroup.taxirideserver.commons.entities.ApiResponse;
import biz.advanceitgroup.taxirideserver.courses.dto.CurrentDriverLocationDTO;
import biz.advanceitgroup.taxirideserver.courses.dto.LogRideEventDTO;
import biz.advanceitgroup.taxirideserver.courses.dto.NotificationsDTO;
import biz.advanceitgroup.taxirideserver.courses.dto.RidesDTO;
import biz.advanceitgroup.taxirideserver.courses.entities.CurrentDriverLocation;
import biz.advanceitgroup.taxirideserver.courses.entities.LogRideEvent;


/**
 * Testing Rest API For all request concern Ride
 * @author Fanon Jupkwo {@link https://github.com/fanjups}
 * @version 1.0, 13/01/2020
 * @see RidesRestController
 */


public class RidesRestControllerTest extends AbstractTest{
	
	@Override
	@Before
	public void setUp() {
	      super.setUp();
	   }
	
	//rideid has been provided by the user
	
	// POST
	
	
	//@Ignore
	@Test
	
	public void saveDriverLocation1() throws Exception 
	{
		/*
		
		String uri="/saveDriverLocation";
		
		
		CurrentDriverLocationDTO currentDriverLocationDTO = new CurrentDriverLocationDTO();
		
		
		
		//CHECK CORRECT PARAMETERS
		
		currentDriverLocationDTO.setCodePays("+237");
		currentDriverLocationDTO.setVille("Yaounde");
		
		currentDriverLocationDTO.setEmailOrPhoneNumber("jupsfan@gmail.com");
		currentDriverLocationDTO.setLatitude(0.2);
		currentDriverLocationDTO.setLongitude(0.9);
        currentDriverLocationDTO.setRIDE_ID(1L);
        currentDriverLocationDTO.setDriverAvailable(true);
        
        postMockTest(currentDriverLocationDTO,uri);
        
        */
        
	      
	}
	
	//rideid has not been provided by the user
	
	// POST
	
	//@Ignore
   @Test
	
	public void saveDriverLocation2() throws Exception
	{
		/*
	   
	   String uri="/saveDriverLocation";
        CurrentDriverLocationDTO currentDriverLocationDTO = new CurrentDriverLocationDTO();
		
		//CHECK CORRECT PARAMETERS
		
		currentDriverLocationDTO.setCodePays("+237");
		currentDriverLocationDTO.setVille("Yaounde");
		
		currentDriverLocationDTO.setEmailOrPhoneNumber("jupsfan@gmail.com");
		currentDriverLocationDTO.setLatitude(0.2);
		currentDriverLocationDTO.setLongitude(0.9);
        
        currentDriverLocationDTO.setDriverAvailable(true);
        
        postMockTest(currentDriverLocationDTO,uri);
        
        */
	}
   
 // POST
	//@Ignore
   @Test
	
	
	public void postRide() throws Exception
	{
		
	   /*
	   String uri="/postRide";
		
		
		//CHECK CORRECT PARAMETERS
		
		
		  
		RidesDTO ridesDTO = new RidesDTO();
		
		ridesDTO.setPhoneNumber("670628896");
		
		
		ridesDTO.setRiderLattitude(2.1);
		ridesDTO.setRiderLongitude(2.1);
		
		
		ridesDTO.setStartPointLatitude(0.3);
		ridesDTO.setStartPointLongitude(1.5);
		
		ridesDTO.setArrivalPoint("Oyom Abang");
		ridesDTO.setDeparturePoint("Mokolo");
		
		ridesDTO.setEndPointLatitude(3.0);
		ridesDTO.setEndPointLongitude(2.5);
		
		ridesDTO.setCodeVille("Yaounde");
		
		ridesDTO.setCodePays("cm");
		
		
		postMockTest(ridesDTO,uri);
		
		*/
	
	      
	}
	
   
// POST
	
	//@Ignore
   
   @Test
	
	public void saveRideEventDate() throws Exception
	{
		
	   /*
	   
	   String uri="/saveRideEventDate";
		
		
		//CHECK CORRECT PARAMETERS
		
		LogRideEventDTO logRideEventDTO = new LogRideEventDTO();
		
		logRideEventDTO.setEventType(1);
		logRideEventDTO.setRIDE_ID(1l);
		postMockTest(logRideEventDTO,uri);
		
		*/
	      
	}
   
   //GET
   
   @Test
  
	public void findAllAvailableDrivers() throws Exception
	{
	   
	   //Create a trip first
	   long tripId ;
	   String codePays="cm";  
	   String ville="Yaounde";
	   double longitude=2.0; 
	   double latitude=1.0;
	   long rayon=5l;
	   String language="fr";

	   String uri="/findAllAvailableDrivers/{tripId}/{codePays}/{ville}/{longitude}/{latitude}/{rayon}/{language}";
		
		
		
		
	      
	}
   
   @Test
	
	public void findAllAvailableTrip() throws Exception
	{
		String uri="/findAllAvailableTrip/{emailorphone}/{longitude}/{lattitude}/{countryCode}/{city}/{rayon}";
		
		
		//CHECK CORRECT PARAMETERS
		
		
		
	      
	}
   
   @Test
	
	public void findDriverLocation() throws Exception
	{
		String uri="/findDriverLocation/{emailorphone}/{language}";
		
		
		//CHECK CORRECT PARAMETERS
		
		
		
	      
	}
   
   @Test
	
	public void findAvailableOptionsByCity() throws Exception
	{
		String uri="/findAvailableOptionsByCity/{countryCode}/{cityCode}";
		
		
		//CHECK CORRECT PARAMETERS
		
		
		
	      
	}
   
   @Test
	
	public void findAllTrip() throws Exception
	{
		String uri="/findAllTripByUser/{user_id}/{startDate}/{endDate}/{language}";
		
		
		//CHECK CORRECT PARAMETERS
		
		
		
	      
	}
   @Test
	
	public void findTripInfo() throws Exception
	{
		String uri="/findTripInfo/{trip_id}/{language}";
		
		
		//CHECK CORRECT PARAMETERS
		
		
		
	      
	}
   
   @Test
	
	public void startTrip() throws Exception
	{
		String uri="/startTrip";
		
		
		//CHECK CORRECT PARAMETERS
		
		
		
	      
	}
   
   @Test
	
	public void endTrip() throws Exception
	{
		String uri="/endTrip";
		
		
		//CHECK CORRECT PARAMETERS
		
		
		
	      
	}
   
   @Test
	
	public void cancelTrip() throws Exception
	{
		String uri="/cancelTrip";
		
		
		//CHECK CORRECT PARAMETERS
		
		
		
	      
	}
   
   @Test
	
	public void getTripCost() throws Exception
	{
		String uri="/getTripCost/{tripId}/{language}";
		
		
		//CHECK CORRECT PARAMETERS
		
		
		
	      
	}
   
   @Test
	
	public void getCancelTripCost1() throws Exception
	{
		String uri="/getCancelTripCost/{emailOrPhone}/{countryCode}/{city}/{codeOpation}/{tripId}/{language}";
	    
		
		
		//CHECK CORRECT PARAMETERS
		
		
		
	      
	}
   
   @Test
	
	public void getCancelTripCost2() throws Exception
	{
		String uri="/getSampleTripCost/{optionCode}/{estimatedDistance}/{estimatedDuration}/{cityCode}/{language}";
			    
		
		
		//CHECK CORRECT PARAMETERS
		
		
		
	      
	}
   
   @Test
	
	public void isEndTrip() throws Exception
	{
		String uri="/isEndTrip/{tripId}/{language}";
		
		
		//CHECK CORRECT PARAMETERS
		
		
		
	      
	}
   
   @Test
	
	public void postRateByDriver() throws Exception
	{
		String uri="/postRateByDriver";
		
		
		//CHECK CORRECT PARAMETERS
		
		
		
	      
	}
   @Test
	
	public void postRateByRider() throws Exception
	{
		String uri="/postRateByRider";
		
		
		//CHECK CORRECT PARAMETERS
		
		
		
	      
	}
	
	private void postMockTest(Object object, String string) throws Exception
	{
		String inputJson = super.mapToJson(object);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(string)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(HttpStatus.OK, status);
	}
	
	
	private void putMockTest(Object object, String string) throws Exception
	{
		String inputJson = super.mapToJson(object);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put(string)
	         .contentType(MediaType.APPLICATION_JSON_VALUE)
	         .content(inputJson)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(HttpStatus.OK, status);
	}
	
	private void deleteMockTest(Object object, String string) throws Exception
	{
		String inputJson = super.mapToJson(object);
	      MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(string)).andReturn();
	      
	      int status = mvcResult.getResponse().getStatus();
	      assertEquals(HttpStatus.OK, status);
	}

}
