package hr.core.wx;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hr.pb.Util.PBHttpState;
import com.hr.pb.WX.PBWxUserAccessToken;

import hr.core.bean.TwoTuple;
import hr.core.util.SpringContextUtil;
import hr.service.WXService;
import net.sf.json.JSONObject;

public class WxAuthorizeInterceptor extends HandlerInterceptorAdapter{
	private Logger logger = LoggerFactory.getLogger(WxAuthorizeInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	 String userAccessToken = request.getHeader("user_access_token");
    	 if(userAccessToken==null || userAccessToken.length()==0){
    		 userAccessToken = request.getParameter("user_access_token");
    	 }
    	 WXService wXService=SpringContextUtil.getBean(WXService.class);
    	 TwoTuple<PBWxUserAccessToken,String> accessTokenR=wXService.checkAuthorize(userAccessToken);

    	 if(accessTokenR==null){
        	 response.setStatus(PBHttpState.Authroize_VALUE);
        	 return false;  
         }
         if(accessTokenR.second!=null){
        	 response.setHeader("user_access_token_update", accessTokenR.second);
         }
         WXService.userAuthorThreadLocal.set(new WxUserAuthorize(accessTokenR.first));
         return super.preHandle(request, response, handler);
    }
    
    public class WxAuthException extends Exception{

		public WxAuthException() {
			super();
		}
    	
    }
}
