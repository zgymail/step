package hr.service;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import com.hr.pb.Author.PBUserGender;
import com.hr.pb.Author.PBUserRegisterType;
import com.hr.pb.WX.PBWxAccessToken;
import com.hr.pb.WX.PBWxMenu;
import com.hr.pb.WX.PBWxTicket;
import com.hr.pb.WX.PBWxUserAccessToken;

import hr.configurer.StepConfigurer;
import hr.core.bean.TwoTuple;
import hr.core.util.HttpsUtil;
import hr.core.util.ThreadMonitoring;
import hr.core.wx.WxUrl;
import hr.core.wx.WxUserAuthorize;
import hr.dao.UserDao;
import hr.entity.User;
import net.sf.json.JSONObject;


@Service
public class WXService {
	public static ThreadLocal<WxUserAuthorize> userAuthorThreadLocal = new ThreadLocal<WxUserAuthorize>();	
	
	private static final Logger logger = LoggerFactory.getLogger(WXService.class);
	public PBWxAccessToken accessToken = null;
	
	private PBWxTicket jsApiTicket;
	
	private ExecutorService checkTimerService;
	
	@Autowired
	private StepConfigurer stepConfigurer;
	
	@Autowired
	private UserDao userDao;
	
	@PostConstruct
	public void init(){
		checkTimerService=Executors.newSingleThreadExecutor(new ThreadFactory()
		  {
			   public Thread newThread(Runnable r)
			   {
				   return new ThreadMonitoring(r, "updateAccessToken");
			   }
		 });
		checkTimerService.execute(new Runnable(){
			@Override
			public void run() {
				 try {
					PBWxAccessToken at= updateAccessToken();
					if(at==null){
						logger.warn("updateAccessToken request is null");
						Thread.sleep(1000*3);
					}else{
						int time=at.getExpiresin()-240;
						Thread.sleep(1000*time);
						logger.info("updateAccessToken success");
					}
				 } catch (InterruptedException e) {
					 logger.error("updateAccessToken:", e);
					 try{Thread.sleep(1000*10); //发生异常休眠10秒
			         }catch (Exception e1){}
				 }
			}
		});
		checkTimerService.execute(new Runnable(){
			@Override
			public void run() {
				 try {
					 PBWxTicket at= updateJsapiTicket();
					if(at==null){
						logger.warn("updateJsapiTicket request is null");
						Thread.sleep(1000*3);
					}else{
						int time=at.getExpiresin()-240;
						Thread.sleep(1000*time);
						logger.info("updateJsapiTicket success");
					}
				 } catch (InterruptedException e) {
					 logger.error("updateJsapiTicket:", e);
					 try{Thread.sleep(1000*10); //发生异常休眠10秒
			         }catch (Exception e1){}
				 }
			}
		});
	}
	
	private PBWxAccessToken updateAccessToken(){
		 String tokenUrl = WxUrl.getAccessToken(stepConfigurer.getWxsAppId(), stepConfigurer.getWxsAppSecret());
         String response = HttpsUtil.httpsRequestToString(tokenUrl, "GET", null);
         if(response==null)return null;
         JSONObject json = JSONObject.fromObject(response);
         if(!json.containsKey("access_token")){
        	 String msg="获取access_token失败";
        	 if(json.containsKey("errmsg")){
        		 msg+=","+json.getString("errmsg");
        	 }
        	 logger.error(msg);
        	 return null;
         }
         PBWxAccessToken.Builder token =PBWxAccessToken.newBuilder();
         token.setAccessToken(json.getString("access_token"));
         token.setExpiresin(json.getInt("expires_in"));
         accessToken= token.build();
         return accessToken;
	}
	
	
	private PBWxTicket updateJsapiTicket(){
		if(this.accessToken==null){
			return null;
		}
		String ticketUrl = WxUrl.getTicket(this.accessToken.getAccessToken(), "jsapi");
        String response = HttpsUtil.httpsRequestToString(ticketUrl, "GET", null);
        if(response==null)return null;
        JSONObject json = JSONObject.fromObject(response);
        if(!json.containsKey("ticket")){
       	 String msg="获取ticket失败";
       	 if(json.containsKey("errmsg")){
       		 msg+=","+json.getString("errmsg");
       	 }
       	 logger.error(msg);
       	 return null;
        }
        PBWxTicket.Builder ticket =PBWxTicket.newBuilder();
        ticket.setTicket(json.getString("ticket"));
        ticket.setExpiresin(json.getInt("expires_in"));
        this.jsApiTicket= ticket.build();
        return this.jsApiTicket;
	}
	
	public void createMenu(PBWxMenu menu){
		if(this.accessToken==null){
			this.updateAccessToken();
		}
		if(this.accessToken==null){
			logger.error("create Menu fail,accessToken request is null");
			return;
		}
		String url = WxUrl.getMenuCreate(this.accessToken.getAccessToken());
		try {
			String jsonMenu=JsonFormat.printer().print(menu);
			jsonMenu=jsonMenu.replaceAll("subButton", "sub_button");
		    String response = HttpsUtil.httpsRequestToString(url, "POST", jsonMenu);
		    JSONObject jsonObject=JSONObject.fromObject(response);
		    if (null != jsonObject) {
	            if (0 != jsonObject.getInt("errcode")) {
	            	logger.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
	            }
	        }
		} catch (InvalidProtocolBufferException e) {
			logger.error("create Menu fail,parse protobuf error");
		}
	}
	
	
	public String getWxAuthorizeUrl(){
		return this.getWxAuthorizeUrl(null);
	}
	public String getWxAuthorizeUrl(Map<String,String> paras){
		String url=this.stepConfigurer.getWebsite()+"/wx/authorize_agree_redirect";
		if(paras!=null&&paras.size()>0){
			String parastr="";
			for(Map.Entry<String,String> entity:paras.entrySet()){
				parastr+="&"+entity.getKey()+"="+entity.getValue();
			}
			url+="?"+parastr.substring(1);
		}
		try {
			url=java.net.URLEncoder.encode(url,"UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return WxUrl.getCode(this.stepConfigurer.getWxsAppId(), url, "snsapi_userinfo");
	}
	
	public String updateUserInfo(String webAccessToken,String webRefreshToken, int expires_in, String openId){
		 String userinfoUrl = WxUrl.getUserInfo(webAccessToken, openId);
         String userinfoStr = HttpsUtil.httpsRequestToString(userinfoUrl, "GET", null);
         JSONObject userinfo = JSONObject.fromObject(userinfoStr);
         String ret=null;
         if (userinfo != null) {
               String unionid = userinfo.getString("unionid");
               String headimgurl = userinfo.getString("headimgurl");
               String nickname = userinfo.getString("nickname");
               int sex = userinfo.getInt("sex");
               PBUserGender gender=PBUserGender.None;
               if(sex==1){
            	   gender=PBUserGender.Male;
               }else if(sex==2){
            	   gender=PBUserGender.Female;
               }
               String openid = userinfo.getString("openid");
               User user=userDao.findByOpenid(openId);
               if(user==null){
            	   user=new User();
               }
          	   user.setNickname(nickname);
          	   user.setGender(gender);
          	   user.setHeadicon(headimgurl);
          	   user.setOpenid(openid);
          	   user.setIdentity(unionid);
          	   user.setPassword("");
          	   user.setRegisterType(PBUserRegisterType.Weixin);
          	   userDao.save(user);
          	   
          	   PBWxUserAccessToken.Builder token=PBWxUserAccessToken.newBuilder();
          	   token.setIdentity(unionid);
          	   token.setAccessToken(webAccessToken);
          	   token.setRefreshToken(webRefreshToken);
          	   token.setExpiresIntime(this.getExpiresIntime(expires_in));
          	   ret=this.encodeUserAccessToken(token.build());
         }else{
        	 User user=userDao.findByOpenid(openId);
        	 if(user==null){

        	 }else{
        		 PBWxUserAccessToken.Builder token=PBWxUserAccessToken.newBuilder();
            	 token.setIdentity(user.getIdentity());
        	     token.setAccessToken(webAccessToken);
        	     token.setRefreshToken(webRefreshToken);
        	     token.setExpiresIntime(this.getExpiresIntime(expires_in));
        	     ret=this.encodeUserAccessToken(token.build());
        	 }
         }
         return ret;
	}
	
	
	private String encodeUserAccessToken(PBWxUserAccessToken userAccessToken){
		 byte[] val=userAccessToken.toByteArray();
		 return Base64.getEncoder().encodeToString(val);
	}
	
	private PBWxUserAccessToken decodeUserAccessToken(String userAccessToken){
		try {
			byte[] val=Base64.getDecoder().decode(userAccessToken);
			return PBWxUserAccessToken.parseFrom(val);
		} catch (Throwable e) {
			return null;
		}
	}
	
	public long getExpiresIntime(int expires_in){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.SECOND, expires_in-120);
		return (long)calendar.getTime().getTime();
	}
	
	public WxUserAuthorize getCurrentUserAuthorize(){
		return userAuthorThreadLocal.get();
	}
	
	public String getCurrentUrl(String originUrl){
		return "";
	}
	
	public TwoTuple<PBWxUserAccessToken,String> checkAuthorize(String userAccessToken){
		if(userAccessToken==null || userAccessToken.length()==0){
			logger.info("checkAuthorize userAccessToken==null");
       	 	return null;  
        }
		PBWxUserAccessToken accessToken=this.decodeUserAccessToken(userAccessToken);
		if(accessToken==null){
			logger.info("checkAuthorize decodeUserAccessToken==null");
			return null;
		}
		long expiresIntime=accessToken.getExpiresIntime();
		long ctime=new Date().getTime();
		if(ctime>expiresIntime){
			logger.info("checkAuthorize getRefreshToken");
			String tokenUrl = WxUrl.getWebAccessToken(stepConfigurer.getWxsAppId(),accessToken.getRefreshToken());
			String tokenStr = HttpsUtil.httpsRequestToString(tokenUrl, "GET", null);
	        JSONObject tokenData = JSONObject.fromObject(tokenStr);
	        if(!tokenData.containsKey("access_token")){
	        	logger.info("checkAuthorize getRefreshToken is null");
	        	return null;
	        }
	        String webAccessToken  = tokenData.getString("access_token");
        	String webRefreshToken  = tokenData.getString("refresh_token");
        	int expires_in  = tokenData.getInt("expires_in");
        	PBWxUserAccessToken.Builder accessTokenBuilder=PBWxUserAccessToken.newBuilder();
        	accessTokenBuilder.setIdentity(accessToken.getIdentity());
        	accessTokenBuilder.setAccessToken(webAccessToken);
        	accessTokenBuilder.setRefreshToken(webRefreshToken);
        	accessTokenBuilder.setExpiresIntime(this.getExpiresIntime(expires_in));
        	accessToken=accessTokenBuilder.build();
        	logger.info("checkAuthorize success and refresh");
        	return new TwoTuple<PBWxUserAccessToken,String>(accessToken,this.encodeUserAccessToken(accessToken));
		}
		logger.info("checkAuthorize success");
		return new TwoTuple<PBWxUserAccessToken,String>(accessToken,null);
	}
	
	
	
	

}
