package hr.configurer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.classmate.TypeResolver;
import com.google.protobuf.UnknownFieldSet;

import hr.core.protobuf.ProtobufSpringfoxDocPlugin;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.google.common.collect.Lists.*;
import static com.google.common.collect.Sets.*;
import static springfox.documentation.schema.AlternateTypeRules.*;
@Configuration
@EnableSwagger2
public class SpringfoxDocConfigurer {
	
	
	@Bean public Docket weixin() {
		return this.configDocker("1_微信端", "/**","hr.controller");
	}
	
	//@Bean public Docket app() {
	//	return this.configDocker("2_App", "/**","hr.controller");
	//}
	


	private Docket configDocker(String group,String path,String packageName){
		ArrayList<ResponseMessage> responseMess = new ArrayList<ResponseMessage>();
		responseMess.add(new ResponseMessageBuilder().code(200).message("成功").build());

		List<Class<?>> ignoredParameterTypes=new ArrayList<Class<?>>();
		ignoredParameterTypes.addAll(ProtobufSpringfoxDocPlugin.getIgnoredParameterTypes());
		
		Class[] ignoredParameterTypesArr=ignoredParameterTypes.toArray(new Class<?>[ignoredParameterTypes.size()]);
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.groupName(group).apiInfo(apiInfo(group)).select()
				.apis(RequestHandlerSelectors.basePackage(packageName))
				.paths(PathSelectors.ant(path))
				
				.build()
				 .directModelSubstitute(java.sql.Timestamp.class, java.sql.Date.class)
				.ignoredParameterTypes(ignoredParameterTypesArr)
				.enableUrlTemplating(false)
				.globalResponseMessage(RequestMethod.POST, responseMess)
				.protocols(newHashSet("http", "https"))
				;
	}


	private ApiInfo apiInfo(String group) {
		ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder()
				.title("Api-"+group)
				.description("")
				.termsOfServiceUrl("http://app.happyrun.cn")
				.version("1.0");
		return apiInfoBuilder.build();
	}

}
