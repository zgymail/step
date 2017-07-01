package ww.test.common;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import hr.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles(value="dev")
@SpringBootTest(classes = Application.class)
public class TestBase {
	protected MockMvc mvc;
	
	@Autowired  
	protected WebApplicationContext webApplicationConnect;  
	
	
	protected RequestPostProcessor bearerToken;
	
	
	 @Before  
    public void setUp() throws Exception {  
    }  
}
