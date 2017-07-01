package hr.servlet;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;

import hr.configurer.StepConfigurer;
import hr.core.util.SpringContextUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@WebServlet(urlPatterns="/web/aliyunToken.do", description="阿里云token")
public class AliyunTokenServlet extends HttpServlet {
	 private static final Logger logger = LoggerFactory.getLogger(AliyunTokenServlet.class.getName());
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doPost(req, resp);
	}
	protected AssumeRoleResponse assumeRole(String region,String accessKeyId, String accessKeySecret, String roleArn,
			String roleSessionName, String policy, ProtocolType protocolType, long durationSeconds) throws ClientException 
	{
		try {
			// 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
			IClientProfile profile = DefaultProfile.getProfile(region, accessKeyId, accessKeySecret);
			DefaultAcsClient client = new DefaultAcsClient(profile);

			// 创建一个 AssumeRoleRequest 并设置请求参数
			final AssumeRoleRequest request = new AssumeRoleRequest();
			request.setVersion("2015-04-01");
			request.setMethod(MethodType.POST);
			request.setProtocol(protocolType);

			request.setRoleArn(roleArn);
			request.setRoleSessionName(roleSessionName);
			request.setPolicy(policy);
			request.setDurationSeconds(durationSeconds);

			// 发起请求，并得到response
			final AssumeRoleResponse response = client.getAcsResponse(request);

			return response;
		} catch (ClientException e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		StepConfigurer werewolfConfigurer=(StepConfigurer) SpringContextUtil.getBean(StepConfigurer.class);
		String bucket=werewolfConfigurer.getOssBucket();

		String region=werewolfConfigurer.getOssRegion();
		String accessKeyId = werewolfConfigurer.getOssAccessKeyId();
		String accessKeySecret = werewolfConfigurer.getOssAccessKeySecret();
		String roleArn = werewolfConfigurer.getOssRoleArn();
		long durationSeconds =900;
		JSONObject policyObj=new JSONObject();
		JSONArray actions=new JSONArray();
		actions.add("oss:GetObject");
		actions.add("oss:PutObject");
		JSONObject statement=new JSONObject();
		statement.put("Action", actions);
		statement.put("Effect", "Allow");
		
		JSONArray resource=new JSONArray();
		resource.add("acs:oss:*:*:"+bucket+"/**");
		statement.put("Resource",resource);
		
		JSONArray statements=new JSONArray();
		statements.add(statement);
		
		policyObj.put("Statement", statements);
		policyObj.put("Version", "1");
		
		String policy = policyObj.toString();
		String roleSessionName = "OSS";
		String baseUrl=werewolfConfigurer.getOssBaseUrl();
		// 此处必须为 HTTPS
		ProtocolType protocolType = ProtocolType.HTTPS;
		Map<String, String> respMap=null;
		try {
			final AssumeRoleResponse stsResponse = assumeRole(region,accessKeyId, accessKeySecret, roleArn, roleSessionName,
					policy, protocolType, durationSeconds);
		    respMap = new LinkedHashMap<String, String>();
			respMap.put("status", "200");
            respMap.put("AccessKeyId", stsResponse.getCredentials().getAccessKeyId());
            respMap.put("AccessKeySecret", stsResponse.getCredentials().getAccessKeySecret());
            respMap.put("SecurityToken", stsResponse.getCredentials().getSecurityToken());
            respMap.put("Expiration", stsResponse.getCredentials().getExpiration());
            respMap.put("BaseUrl", baseUrl);
            respMap.put("EndPoint", werewolfConfigurer.getEndPoint());
           
		} catch (ClientException e) {
			logger.error(e.getMessage(), e);
			respMap = new LinkedHashMap<String, String>();
			respMap.put("status", e.getErrCode());
            respMap.put("AccessKeyId", "");
            respMap.put("AccessKeySecret", "");
            respMap.put("SecurityToken", "");
            respMap.put("Expiration", "");     
            respMap.put("BaseUrl", baseUrl);
            respMap.put("EndPoint",  werewolfConfigurer.getEndPoint());
		}
		
		JSONObject results=JSONObject.fromObject(respMap);
		String callbackFunName = request.getParameter("callback");
		if (callbackFunName==null || callbackFunName.equalsIgnoreCase(""))
			response.getWriter().println(results);
		else
			response.getWriter().println(callbackFunName + "( "+results+" )");
		response.setStatus(HttpServletResponse.SC_OK);
        response.flushBuffer();
	}
}
