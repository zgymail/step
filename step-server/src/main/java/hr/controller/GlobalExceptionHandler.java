package hr.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.hr.pb.Util.*;

import hr.core.wx.WxAuthorizeInterceptor;
import hr.service.WXService;

@RestController
@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class.getName());

	
	@Autowired
	private WXService wXService;
	/*
	    @ExceptionHandler(value = WxAuthorizeInterceptor.WxAuthException.class)
	    @ResponseBody
	    public ModelAndView wxAuthExceptionHandler(HttpServletRequest req, Exception e) throws Exception {
	    	Map<String,String> paras=new HashMap<String,String>();
	    	String authorizeUrl=wXService.getWxAuthorizeUrl(paras);
	    	return new ModelAndView("redirect:"+authorizeUrl);  
	    }
	*/
		@ExceptionHandler(value = Exception.class)
	    @ResponseBody
	    public ResponseEntity<PBErrorMessage> defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
	    	logger.error("Controller Exception: host:"+ req.getRemoteHost()+",url:"+ req.getRequestURL()+",message:"+ e.getMessage());
	    	PBErrorMessage.Builder errorMessage=PBErrorMessage.newBuilder();
	        BodyBuilder body=ResponseEntity.status(PBHttpState.Error_VALUE);
	        errorMessage.setValue("服务器操作失败:url:"+req.getRequestURL()+"  error:"+e.getMessage());
	        ResponseEntity<PBErrorMessage> ret=body.body(errorMessage.build());
	        logger.error("服务器操作失败:url:"+req.getRequestURL(), e);
	        return ret;
	    }
}
