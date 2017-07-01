package hr.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hr.pb.WX.PBWxLogin;
import com.hr.pb.WXMyActivity.PBWxPepleExecutePageable;

import hr.core.util.MediaTypeExt;
import hr.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api()
@RequestMapping(value="wx/login",consumes={MediaTypeExt.APPLICATION_PROTOBUF_VALUE,MediaTypeExt.APPLICATION_JSON_VALUE},produces={MediaTypeExt.APPLICATION_PROTOBUF_VALUE,MediaTypeExt.APPLICATION_JSON_VALUE})
public class WXLoginController {

	private static final Logger logger = LoggerFactory.getLogger(WXLoginController.class.getName());

    @Autowired
    private UserService userService;
 

    @ApiOperation(value="微信登录")
    @RequestMapping(path="login",method=RequestMethod.POST)
    public ResponseBody login(@RequestBody PBWxLogin wxLogin) {
    	
        return null;
    }
    
   
 
}
