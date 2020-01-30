/*package biz.advanceitgroup.taxirideserver.test.auth;


import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MvcResult;

import biz.advanceitgroup.taxirideserver.AbstractTest;
import biz.advanceitgroup.taxirideserver.authentification.dto.LoginRequest;
import biz.advanceitgroup.taxirideserver.authentification.dto.PhoneVerificationRequestDTO;
import biz.advanceitgroup.taxirideserver.authentification.dto.RegistrationRequest;
import biz.advanceitgroup.taxirideserver.authentification.enums.AuthProvider;
import biz.advanceitgroup.taxirideserver.authentification.webs.AuthController;
import edu.emory.mathcs.backport.java.util.Collections;


public class AuthServiceControllerTest extends AbstractTest {
   @Override
   @Before
   public void setUp() {
      super.setUp();
   }
   

   @Autowired
   private AuthController authController;

  
 
   //Test Insert or POST: create a  new registration, parameter: email, phone number, password and provider.
   @Test
   public void testCreateUser() throws Exception {
	   
	   String uri = "/api/auth/register";
	   RegistrationRequest rq = new RegistrationRequest();
		      
		      rq.setEmail("merlin.saha@advance-it-group.biz");
		      rq.setPhoneNumber("+237694005217");
		      rq.setPassword("Test@12345*");
		      rq.setProvider(AuthProvider.local);
		     
		      
		String inputJson = super.mapToJson(rq);
		     
		      MvcResult mvcResult = 
		    		  mvc.perform(post(uri)
		         .contentType(MediaType.APPLICATION_JSON_VALUE)
		         .content(inputJson)).andReturn();
		      
		      int status = mvcResult.getResponse().getStatus();
		      assertEquals(200, status);
		      //String content = mvcResult.getResponse().getContentAsString();
		      //assertEquals(200, content+" "+status);
		      //assertEquals(content, "{\"data\":\"Users registered successfully. Check your email for verification\",\"success\":true}");
		      
		      rq = new RegistrationRequest();
   }
   
 
 //Test login using phone number, user account must exist in database. paameter: username and password
   @Test
   public void testLoginByPhoneNumber() throws Exception{
	   
	   String uri = "/api/auth/login";
		      
	   LoginRequest lr = new LoginRequest();
		   
		   lr.setUsername("+237695005217");
		   lr.setPassword("Test12345@");
		   
		   HttpServletRequest request = null;
		String inputJson = super.mapToJson(authController.authenticateUser(lr, request));
		   
		         MvcResult mvcResult = 
		       		  mvc.perform(post(uri)
		            .contentType(MediaType.APPLICATION_JSON_VALUE)
		            .content(inputJson)).andReturn();
		         
		         int status = mvcResult.getResponse().getStatus();
		         //assertEquals(200, status);
			     String content = mvcResult.getResponse().getContentAsString();
			      assertEquals(200, content+" "+status);
		         lr = new LoginRequest();
   }
   
   //Test phone number verification, user account must exist in database, parameter: code and phone number
   @Test
   public void testVerifiedPhoneNumber() throws Exception{
	   
	   String uri = "/api/auth/registerconfirmationbyphone";
		      
	   PhoneVerificationRequestDTO pvr = new PhoneVerificationRequestDTO();
		   
		   pvr.setCode("1");
		   pvr.setPhoneNumber("+237695005217");
		   
		   String inputJson = super.mapToJson(authController.registerConfirmationByPhone(pvr));
		   
		         MvcResult mvcResult = 
		       		  mvc.perform(post(uri)
		            .contentType(MediaType.APPLICATION_JSON_VALUE)
		            .content(inputJson)).andReturn();
		         
		         int status = mvcResult.getResponse().getStatus();
		       //assertEquals(200, status);
			      String content = mvcResult.getResponse().getContentAsString();
			      assertEquals(200, content+" "+status);
		         pvr = new PhoneVerificationRequestDTO();
   }
   
   
   
   
}
*/