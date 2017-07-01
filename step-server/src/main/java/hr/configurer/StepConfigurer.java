package hr.configurer;

import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
@Configuration
@ConfigurationProperties(prefix = "step")
public class StepConfigurer {
	private String wxsAppId;
	private String wxsAppSecret;

	
	private String ossBucket;
	
	private String ossRegion;
	
	private String ossAccessKeyId;
	
	private String ossAccessKeySecret;
	
	private String ossRoleArn;
	
	private String website;
	
	

	
	@PostConstruct
    public void initInstance(){  
		
    } 
	


	public String getOssBucket() {
		return ossBucket;
	}

	public void setOssBucket(String ossBucket) {
		this.ossBucket = ossBucket;
	}

	public String getOssRegion() {
		return ossRegion;
	}

	public void setOssRegion(String ossRegion) {
		this.ossRegion = ossRegion;
	}
	
	public String getOssBaseUrl(){
		return "http://"+this.ossBucket+".oss-"+this.ossRegion+".aliyuncs.com";
	}
	
	public String getEndPoint(){
		return "http://oss-"+this.ossRegion+".aliyuncs.com";
	}

	public String getOssAccessKeyId() {
		return ossAccessKeyId;
	}

	public void setOssAccessKeyId(String ossAccessKeyId) {
		this.ossAccessKeyId = ossAccessKeyId;
	}

	public String getOssAccessKeySecret() {
		return ossAccessKeySecret;
	}

	public void setOssAccessKeySecret(String ossAccessKeySecret) {
		this.ossAccessKeySecret = ossAccessKeySecret;
	}

	public String getOssRoleArn() {
		return ossRoleArn;
	}

	public void setOssRoleArn(String ossRoleArn) {
		this.ossRoleArn = ossRoleArn;
	}



	public String getWxsAppId() {
		return wxsAppId;
	}



	public void setWxsAppId(String wxsAppId) {
		this.wxsAppId = wxsAppId;
	}



	public String getWxsAppSecret() {
		return wxsAppSecret;
	}



	public void setWxsAppSecret(String wxsAppSecret) {
		this.wxsAppSecret = wxsAppSecret;
	}



	public String getWebsite() {
		return website;
	}



	public void setWebsite(String website) {
		this.website = website;
	}

	
	

	
	

}
