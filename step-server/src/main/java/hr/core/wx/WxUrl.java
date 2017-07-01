package hr.core.wx;

public class WxUrl {
	//获取code的请求地址
    public static String Get_Code = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STAT#wechat_redirect";
    //替换字符串
    public static String getCode(String appId, String redirectUri ,String scope) {
        return String.format(Get_Code,appId,redirectUri,scope);
    }
    
    //获取Web_access_tokenhttps的请求地址
    public static String Web_access_tokenhttps = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
    //替换字符串
    public static String getWebAccessToken(String appId, String secret,String code) {
        return String.format(Web_access_tokenhttps, appId, secret,code);
    }
    
    public static String Web_refresh_tokenhttps = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=%s&grant_type=refresh_token&refresh_token=%s";
    //替换字符串
    public static String getWebAccessToken(String appId, String refreshToken) {
        return String.format(Web_refresh_tokenhttps, appId, refreshToken);
    }
    
    
    
    
    //拉取用户信息的请求地址
    public static String User_Message = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
    //替换字符串
    public static String getUserInfo(String access_token, String openid) {
        return String.format(User_Message, access_token,openid);
    }
    

    public static String access_tokenhttps = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
    public static String getAccessToken(String appId, String secret) {
        return String.format(access_tokenhttps, appId,secret);
    }
    
    public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=%s";
    public static String getMenuCreate(String access_token) {
        return String.format(menu_create_url, access_token);
    }
    
    public static String ticket_create_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=%s";
    public static String getTicket(String access_token,String type) {
        return String.format(ticket_create_url, access_token,type);
    }
    
}
