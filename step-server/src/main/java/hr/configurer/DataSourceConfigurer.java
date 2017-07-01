package hr.configurer;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
public class DataSourceConfigurer  implements EnvironmentAware {
  private Logger logger = LoggerFactory.getLogger(DataSourceConfigurer.class);
	
	private RelaxedPropertyResolver propertyResolver;

    private Environment env;

    public void setEnvironment(Environment env) {
        this.env = env;
        this.propertyResolver = new RelaxedPropertyResolver(env, "spring.datasource.");
    }

    @Bean
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(this.propertyResolver.getProperty("url"));
        datasource.setUsername(this.propertyResolver.getProperty("username"));
        datasource.setPassword(this.propertyResolver.getProperty("password"));
        String driverClassName=this.propertyResolver.getProperty("driverClassName");
        datasource.setDriverClassName(driverClassName);

        // configuration
        datasource.setInitialSize(new Integer(this.propertyResolver.getProperty("druid.initialSize")));
        datasource.setMinIdle(new Integer(this.propertyResolver.getProperty("druid.minIdle")));
        datasource.setMaxActive(new Integer(this.propertyResolver.getProperty("druid.maxActive")));
        datasource.setMaxWait(new Integer(this.propertyResolver.getProperty("druid.maxWait")));
        datasource.setTimeBetweenEvictionRunsMillis(new Integer(this.propertyResolver.getProperty("druid.timeBetweenEvictionRunsMillis")));
        datasource.setMinEvictableIdleTimeMillis(new Integer(this.propertyResolver.getProperty("druid.minEvictableIdleTimeMillis")));

        datasource.setValidationQuery(this.propertyResolver.getProperty("druid.validationQuery"));
        datasource.setTestWhileIdle(new Boolean(this.propertyResolver.getProperty("druid.testWhileIdle")));
        datasource.setTestOnBorrow(new Boolean(this.propertyResolver.getProperty("druid.testOnBorrow")));
        datasource.setTestOnReturn(new Boolean(this.propertyResolver.getProperty("druid.testOnReturn")));
        datasource.setPoolPreparedStatements(new Boolean(this.propertyResolver.getProperty("druid.poolPreparedStatements")));
        datasource.setMaxPoolPreparedStatementPerConnectionSize(new Integer(this.propertyResolver.getProperty("druid.maxPoolPreparedStatementPerConnectionSize")));
        try {
            datasource.setFilters(this.propertyResolver.getProperty("druid.filters"));
        } catch (SQLException e) {

        }
        datasource.setConnectionProperties(this.propertyResolver.getProperty("druid.connectionProperties"));
       
        return datasource;
    }



    
    
}