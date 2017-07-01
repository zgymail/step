package hr.controller;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hr.pb.Author.PBUser;
import com.hr.pb.Author.PBUser1;
import com.hr.pb.Util.PBErrorMessage;
import com.hr.pb.Util.PBHttpState;
import com.hr.pb.Util.PBMessage;

import hr.configurer.StepConfigurer;
import hr.core.util.MediaTypeExt;
import hr.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "/open/user")
@RestController
@ResponseBody
@RequestMapping(value="/open/user",consumes={MediaTypeExt.APPLICATION_PROTOBUF_VALUE,MediaTypeExt.APPLICATION_JSON_UTF8_VALUE},produces={MediaTypeExt.APPLICATION_PROTOBUF_VALUE,MediaTypeExt.APPLICATION_JSON_UTF8_VALUE})

public class OpenUserController {
	 private static final Logger logger = LoggerFactory.getLogger(OpenUserController.class.getName());
    @Autowired
    private UserService userService;

   
    @Autowired
    private StepConfigurer werewolfConfigurer;

    
    
    @PostConstruct
    public void initInstance(){  
    }  
    
    @ApiOperation(value="用户注册",response=PBUser.class)
    @ApiResponses(value = {
         @ApiResponse(code =PBHttpState.ExistError_VALUE, message = "用户已存在", response=PBErrorMessage.class)  
    })
    @RequestMapping(method=RequestMethod.POST)
    
    public ResponseEntity<?> add( @RequestBody PBUser1 userAddMes) {
    	PBUser.Builder userMesBuilder=PBUser.newBuilder();
    	userMesBuilder.setIdentity("123");
    	String identity=userMesBuilder.getIdentity();
    	ResponseEntity<?> error=null;
    	if(error!=null){
    		return error;
    	}
        return ResponseEntity.ok(userMesBuilder.build());
    }
    

    
    @RequestMapping(path="/exist",method=RequestMethod.GET,consumes={MediaTypeExt.ALL_VALUE})
    @ResponseBody
    public ResponseEntity<PBMessage> exist(@RequestParam("identity") String identity) {
       // Boolean exist = userService.findByIdentity(identity);
        boolean exist=true;
        PBMessage.Builder message=PBMessage.newBuilder();
        message.setBoolValue(exist);
        return ResponseEntity.ok(message.build());
    }
    
    
  
    
   
    
    
    
}
