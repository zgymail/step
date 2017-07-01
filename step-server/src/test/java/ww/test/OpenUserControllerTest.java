package ww.test;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.hr.pb.Author.PBUser;

import hr.core.util.MediaTypeExt;
import hr.core.util.SecurityUtil;
import ww.test.common.TestBase;

public class OpenUserControllerTest extends TestBase{
	@Test
	public void testUserAdd() throws Exception {
		PBUser.Builder input=PBUser.newBuilder();
		input.setNickName("zzzgy");
		input.setIdentity("123456");
		input.setHeadIcon("headicon");
		PBUser puser=input.build();
		byte[] pbyte=puser.toByteArray();
		PBUser value=puser.parseFrom(pbyte);
		System.out.println(pbyte.length);
		String uri = "/open/user/add";
        MvcResult mvcResult = mvc.perform(
        		MockMvcRequestBuilders.post(uri)
        		.contentType(MediaTypeExt.APPLICATION_PROTOBUF)
        		.accept(MediaTypeExt.APPLICATION_PROTOBUF)
        		.content(pbyte)
        		)
        		.andExpect(status().isOk())
        		.andDo(print())  
                .andReturn();
        
        byte[] content=mvcResult.getResponse().getContentAsByteArray();
        PBUser mess=PBUser.parseFrom(content);
        Assert.assertEquals("hello",mess.getNickName());  
	}
	/*
	@Test
	public void testUserThirdAdd() throws Exception {
		Base.UserThirdAdd.Builder input=Base.UserThirdAdd.newBuilder();
		String uuid=UUID.randomUUID().toString();
	 	input.setAccessToken("12345");
	 	input.setRefreshToken("12345");
	 	
	 	Base.User.Builder user=Base.User.newBuilder();
	 	user.setType(1);
	 	user.setIdentity(uuid);
	 	user.setCityId(110102);
	 	user.setHeadIcon("/head1");
	 	user.setSex(1);
	 	user.setStar("天蝎座");
	 	user.setNickName("豪华");
	 	input.setUser(user);
	 	
		
		String uri = "/open/user/third";
        MvcResult mvcResult = mvc.perform(
        		MockMvcRequestBuilders.post(uri)
        		.contentType(MediaTypeExt.APPLICATION_PROTOBUF)
        		.accept(MediaTypeExt.APPLICATION_PROTOBUF)
        		.content(input.build().toByteArray())
        		)
        		.andExpect(status().isOk())
        		.andDo(print())  
                .andReturn();
	}
	
	@Test
	public void testUserExist() throws Exception {
		Base.UserThirdAdd.Builder input=Base.UserThirdAdd.newBuilder();
		String uuid=UUID.randomUUID().toString();
	 	input.setAccessToken("12345");
	 	input.setRefreshToken("12345");
	 	
	 	Base.User.Builder user=Base.User.newBuilder();
	 	user.setType(1);
	 	user.setIdentity(uuid);
	 	user.setCityId(110102);
	 	user.setHeadIcon("/head1");
	 	user.setSex(1);
	 	user.setStar("天蝎座");
	 	user.setNickName("豪华");
	 	input.setUser(user);
	 	
		
		String uri = "/open/user/third";
        MvcResult mvcResult = mvc.perform(
        		MockMvcRequestBuilders.post(uri)
        		.contentType(MediaTypeExt.APPLICATION_PROTOBUF)
        		.accept(MediaTypeExt.APPLICATION_PROTOBUF)
        		.content(input.build().toByteArray())
        		)
        		.andExpect(status().isOk())
        		.andDo(print())  
                .andReturn();
        
	    uri = "/open/user/exist";
        mvcResult = mvc.perform(
        		MockMvcRequestBuilders.get(uri)
        		.contentType(MediaTypeExt.APPLICATION_PROTOBUF)
        		.accept(MediaTypeExt.APPLICATION_PROTOBUF)
        		.param("identity", uuid)
        		)
        		.andExpect(status().isOk())
        		.andDo(print())  
                .andReturn();
        
        byte[] content=mvcResult.getResponse().getContentAsByteArray();
        Base.Message mess=Base.Message.parseFrom(content);
        Assert.assertTrue("数据一致",mess.getCode()==1);  
	}
	
	*/
	/*
	@Test
	public void testUserAdd1() throws Exception {
	 	JSONObject input=JSONObject.parseObject("{}");
	 	input.put("identity", "zgy2");
	 	input.put("md5Password", "123456");
	 	input.put("validCode", "123");

		String uri = "/api/user";
        MvcResult mvcResult = mvc.perform(
        		MockMvcRequestBuilders.post(uri)
        		.with(bearerToken)
        		.contentType(MediaTypeExt.APPLICATION_JSON)
        		.accept(MediaTypeExt.APPLICATION_JSON)
        		.content(input.toJSONString())
        		)
        		.andExpect(status().isOk())
        		.andDo(print())  
                .andReturn();
	}
*/
}
