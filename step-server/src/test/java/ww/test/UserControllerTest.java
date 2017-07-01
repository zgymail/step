package ww.test;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import hr.core.util.MediaTypeExt;
import ww.test.common.TestBase;

public class UserControllerTest extends TestBase{

	@Test
	public void testGetMyUser() throws Exception {
	 	String uri = "/api/user/my";
        MvcResult mvcResult = mvc.perform(
        		MockMvcRequestBuilders.get(uri)
        		.with(bearerToken)
        		.contentType(MediaTypeExt.APPLICATION_JSON)
        		.accept(MediaTypeExt.APPLICATION_JSON)
        		)
        		.andExpect(status().isOk())
        		.andDo(print())  
                .andReturn();
	}

}
