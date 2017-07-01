package hr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan
@ServletComponentScan
public class Application extends SpringBootServletInitializer{
	 private static final Logger logger = LoggerFactory.getLogger(Application.class.getName());
    /**
     * Start
     */
    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    
    
    
    
    
    public static void main(String[] args) {
    	ApplicationContext app = SpringApplication.run(Application.class, args);
    	
        logger.info("SpringBoot Start Success");
    }
   

}
