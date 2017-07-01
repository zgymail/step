package hr.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.hr.pb.Author.PBUser;
import com.hr.pb.Util;
import com.hr.pb.Util.PBErrorMessage;
import com.hr.pb.Util.PBHttpState;
import com.hr.pb.WX.PBWxUserAccessToken;

import hr.configurer.StepConfigurer;
import hr.core.util.HttpsUtil;
import hr.core.util.SpringContextUtil;
import hr.core.wx.WxSign;
import hr.core.wx.WxUrl;
import hr.service.WXService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

@Api()
@Controller
@RequestMapping(value="/wx")
public class WXController {
	@Autowired
	private StepConfigurer stepConfigurer;
	
	@Autowired
	private WXService wXService;
	
	private static Logger logger = LoggerFactory.getLogger(WXController.class);
	@RequestMapping(path = "signature",method = {RequestMethod.GET,RequestMethod.POST})
    public String checkSignature(@RequestParam(name = "signature" ,required = false) String signature  ,
                                 @RequestParam(name = "nonce",required = false) String  nonce ,
                                 @RequestParam(name = "timestamp",required = false) String  timestamp ,
                                 @RequestParam(name = "echostr",required = false) String  echostr){
		 logger.info("/wx/signature request signature:"+signature+" timestamp:"+timestamp+" nonce:"+nonce+" echostr:"+echostr);
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (WxSign.checkSignature(signature, timestamp, nonce)) {
        	logger.info("接入成功");
            return echostr;
        }
        logger.error("接入失败");
        return "";
    }
	
	
	@ApiOperation(value="授权",response=ModelAndView.class)
	@RequestMapping(path = "authorize",method ={RequestMethod.GET,RequestMethod.POST})
    public ModelAndView authorize(HttpServletRequest request
    		,@RequestParam(name = "routerPath" ,required = false) String routerPath
    		,@RequestParam(name = "fromPath" ,required = false) String fromPath
    		,@RequestParam(name = "pagePath" ,required = false) String pagePath
    		){
		if(pagePath==null){
			pagePath=request.getServletPath();
			pagePath=wXService.getCurrentUrl(pagePath);
		}
		
	    Map<String,String> paras=new HashMap<String,String>();
	    paras.put("pagePath", pagePath);
	    if(routerPath!=null){
	    	 paras.put("routerPath", routerPath);
	    }
	    if(fromPath!=null){
	    	 paras.put("fromPath", fromPath);
	    }
	    WXService wXService= SpringContextUtil.getBean(WXService.class);

	    String authorizeUrl=wXService.getWxAuthorizeUrl(paras);
	    logger.info("tenxun request:"+authorizeUrl);
	    RedirectView rview=new RedirectView(authorizeUrl);
	 

	    return new ModelAndView(rview);
	}
	
	@ApiOperation(value="授权返回",response=PBWxUserAccessToken.class)
    @ApiResponses(value = {
        @ApiResponse(code = PBHttpState.AuthroizeFail_VALUE, message = "授权失败",response=PBErrorMessage.class),  
    })
	@RequestMapping(path = "authorize_agree_redirect",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView authorizeAgree(@RequestParam(name = "code" ,required = false,defaultValue="") String code,
                                 @RequestParam(name = "state",required = false) String  state,
                                 @RequestParam(name = "pagePath" ,required = false) String pagePath,
                                 @RequestParam(name = "fromPath" ,required = false) String fromPath,
                                 @RequestParam(name = "routerPath" ,required = false) String routerPath
                        ){
		logger.info("authorize_agree_redirect");
		  String currentUrl=pagePath;
		  if(routerPath!=null){
			  currentUrl="/#"+routerPath;
		  }
		  try{
            String appId = stepConfigurer.getWxsAppId();
            String secret = stepConfigurer.getWxsAppSecret();
            String tokenUrl = WxUrl.getWebAccessToken(appId, secret, code);
            String webTokenStr = HttpsUtil.httpsRequestToString(tokenUrl, "GET", null);
            JSONObject webToken = JSONObject.fromObject(webTokenStr);
            if(!webToken.containsKey("access_token")){
	           	 String msg="获取web_access_token失败";
	           	 if(webToken.containsKey("errmsg")){
	           		 msg+=","+webToken.getString("errmsg");
	           	 }
           	 	 logger.error(msg);
           	     currentUrl+="?user_access_token=null&msg="+msg;
            }else{
            	logger.info("authorize_agree_redirect success");
            	String webAccessToken  = webToken.getString("access_token");
            	String webRefreshToken  = webToken.getString("refresh_token");
            	int expires_in  = webToken.getInt("expires_in");
            	String openId = webToken.getString("openid");

                String userAccessToken=wXService.updateUserInfo(webAccessToken,webRefreshToken,expires_in, openId);
                if(userAccessToken!=null){
                	 currentUrl+="?user_access_token="+userAccessToken;
                }else{
	   	           	 String msg="获取用户信息失败";
              	 	 logger.error(msg);
              	     currentUrl+="?user_access_token=null&msg="+msg;
                }
                
            }
        	
		  }catch(JSONException e){
			  logger.error("json parse error",e);
		  }
		  if(fromPath!=null){
			  currentUrl+="&fromPath="+fromPath;
		  }
		  logger.info("authorize_agree_redirect redirect："+currentUrl);
		  RedirectView rview=new RedirectView(currentUrl,true);
  	      return new ModelAndView(rview);
    }
}
