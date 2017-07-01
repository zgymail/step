package hr.core.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import hr.configurer.StepConfigurer;

public final class ToolUtil {
	private static String ossBaseUrl;
	public static String getLocalIpAddress(){
		Enumeration allNetInterfaces;
		try {
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
				System.out.println(netInterface.getName());
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					if (ip != null && ip instanceof Inet4Address) {
						return ip.getHostAddress();
					}
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		}
		return "localhost";
		
	}
	
	public static String getUrl(String icon){
		if(icon==null || "".equals(icon) || icon.startsWith("http://") || icon.startsWith("https://")){
			return icon;
		}
		if(ossBaseUrl==null){
			StepConfigurer werewolfConfigurer=(StepConfigurer) SpringContextUtil.getBean(StepConfigurer.class);
			ossBaseUrl=werewolfConfigurer.getOssBaseUrl();
		}
		
		return ossBaseUrl+"/"+icon;
	}
	
	public static String generateValidCode(){
		   String[] beforeShuffle = new String[] { "2", "3", "4", "5", "6", "7",  
				   "8", "9", "1", "0" };  
				      List list = Arrays.asList(beforeShuffle);  
				      Collections.shuffle(list);  
				      StringBuilder sb = new StringBuilder();  
				      for (int i = 0; i < list.size(); i++) {  
				          sb.append(list.get(i));  
				      }  
				      String afterShuffle = sb.toString();  
				      String result = afterShuffle.substring(3, 9);  
				     System.out .print(result) ;
				     return result;
	}
	
    private static Object lockObj = "lockerOrder";  
    /** 
     * 订单号生成计数器 
     */  
    private static long orderNumCount = 0L;  
	
    private static int maxPerMSECSize=1000;
    
	 public static String makeOrderNum(String tname) {  
		 String finOrderNum = "";  
	        try {  
	            // 最终生成的订单号  
	            
	            synchronized (lockObj) {  
	                // 取系统当前时间作为订单号变量前半部分，精确到毫秒  
	                long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));  
	                // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万  
	                if (orderNumCount >= maxPerMSECSize) {  
	                    orderNumCount = 0L;  
	                }  
	                //组装订单号  
	                String countStr=maxPerMSECSize +orderNumCount+"";  
	                finOrderNum=tname+nowLong+countStr.substring(1);  
	                orderNumCount++;  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	        return finOrderNum;
	    }  
	
	
}
