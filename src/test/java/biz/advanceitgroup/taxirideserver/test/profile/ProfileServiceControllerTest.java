/*package biz.advanceitgroup.taxirideserver.test.profile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.InputStream;

import org.apache.catalina.webresources.FileResource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import biz.advanceitgroup.taxirideserver.AbstractTest;
import biz.advanceitgroup.taxirideserver.profiles.webs.ProfileController;

public class ProfileServiceControllerTest extends AbstractTest {
   
	@Override
	@Before
	public void setUp() {
	     super.setUp();
	      
	}
   
   private InputStream is;
   
   @Mock
   private ProfileController profileController;
  
   
   @Spy
   @InjectMocks
   private ProfileController controller = new ProfileController();
  
   
   private MockMvc mockMvc;
   
   @Rule
   public TemporaryFolder folder = new TemporaryFolder();
    
   @Before
   public void setUp1() throws Exception {
   String uploadPath = folder.getRoot().getAbsolutePath();
   mockMvc = MockMvcBuilders
   .standaloneSetup(new FileResource(null, uploadPath, null, false, null))
   .build();
   }
    
   @Test
   public void should_upload_image_to_upload_path() throws Exception {
   MockMultipartFile file = new MockMultipartFile("file", "hello.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
   mockMvc.perform(fileUpload("/api/files").file(file))
   .andDo(print())
   .andExpect(status().isCreated());
   assertThat(folder.getRoot().toPath().resolve("hello.txt")).exists();
   }
   
   // Test driver upload car documents, parameter: phone number, number and file
   @Test
   public void testUploadFileAttachements() throws Exception {
       
	   MockMultipartFile mockMultipartFile = new MockMultipartFile("file", "picture.png", "multipart/form-data", is);
       
	   String phoneNumber = "695005217";
	   String number = "1";
	   String inputJson = super.mapToJson(profileController.saveProfileAttachements(phoneNumber, mockMultipartFile, number));
	   
	   
	   MvcResult mvcResult = 
	       		mvc.perform(post("/api/profile/saveProfileAttachements")
	            .contentType(MediaType.MULTIPART_FORM_DATA)
	            .content(inputJson)).andReturn();
	         
	         int status = mvcResult.getResponse().getStatus();
	         //assertEquals(200, status);
	         String content = mvcResult.getResponse().getContentAsString();
	         assertEquals(200, content+ " "+status);
	   
       Assert.assertEquals(200, mvcResult.getResponse().getStatus());
       Assert.assertNotNull(mvcResult.getResponse().getContentAsString());
       Assert.assertEquals("picture.png", mvcResult.getResponse().getContentAsString());
   }
   
   //Test driver downloads file document, first the driver must have 1 or more uploaded document, parameter: phone number
   @Test
   public void testDownloadFileAttchments() throws Exception {
       String phone = "+237695005217";
       String inputJson = super.mapToJson(profileController.findAttachementsByPhone(phone));
       
       MvcResult mvcResult = 
	       		  mvc.perform(get("/api/profile/findAttachementsByPhone/"+phone)
	            .contentType(MediaType.APPLICATION_OCTET_STREAM)
	            .content(inputJson)).andReturn();
	         
	         int status = mvcResult.getResponse().getStatus();
	         //assertEquals(200, status);
	         String content = mvcResult.getResponse().getContentAsString();
	         assertEquals(200, content+" "+status);
       
   }
   
    
   
}*/
