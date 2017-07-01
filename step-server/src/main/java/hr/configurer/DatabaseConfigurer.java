package hr.configurer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "hr.dao")
@EntityScan(basePackages = "hr.entity")
public class DatabaseConfigurer{
	private static final Logger logger = LoggerFactory.getLogger(DatabaseConfigurer.class.getName());
    
}
