package hr.configurer;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import hr.core.protobuf.ProtobufHttpMessageConverter;
import hr.core.protobuf.ProtobufSpringfoxDocPlugin;
import hr.core.wx.WxAuthorizeInterceptor;
@Configuration
@ServletComponentScan("hr/servlet")
public class WebAppConfigurer extends WebMvcConfigurerAdapter{
	 private static final Logger logger = LoggerFactory.getLogger(WebAppConfigurer.class.getName());

	@Bean
	public ProtobufHttpMessageConverter protobufHttpMessageConverter() {
		 return new ProtobufHttpMessageConverter();
	}
	
	@Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		ProtobufSpringfoxDocPlugin.updateObjectMapperWithConverter(converters);
    }
	

	@Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
	  
	
	/**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
    	registry.addInterceptor(new WxAuthorizeInterceptor())
        .addPathPatterns("/wx/activity/**");
    }


    /**
     * 添加自定义的静态资源映射
      这里使用代码的方式自定义目录映射，并不影响Spring Boot的默认映射，可以同时使用。
     */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
	      .addResourceLocations("classpath:/META-INF/resources/");
	    registry.addResourceHandler("/webjars/**")
	      .addResourceLocations("classpath:/META-INF/resources/webjars/");

	    
		if(!registry.hasMappingForPattern("/static/**")){
			registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		}

		
		super.addResourceHandlers(registry);
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		super.addViewControllers(registry);
		registry.addRedirectViewController("/doc", "/swagger-ui.html");
	}
	
}
