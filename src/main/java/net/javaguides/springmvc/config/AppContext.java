package net.javaguides.springmvc.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
public class AppContext {
	@Autowired
	private Environment environment;
	
	@Bean
	public LocalSessionFactoryBean sessionFactory() throws URISyntaxException {
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		sessionFactory.setDataSource(dataSource());
		sessionFactory.setPackagesToScan(new String[] {
				"net.javaguides.springmvc.entity"
		});
		sessionFactory.setHibernateProperties(hibernateProperties());
		return sessionFactory;
	}
	
	@Bean
	public DataSource dataSource() throws URISyntaxException {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driverClassName"));
//		dataSource.setUrl(environment.getRequiredProperty("jdbc.url"));
//		dataSource.setUsername(environment.getRequiredProperty("jdbc.username"));
//		dataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		URI dbUri = new URI(System.getenv("DATABASE_URL"));
        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath() + ":" + dbUri.getPort() + dbUri.getPath();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
		return dataSource;
	}
	

//	    @Bean
//	    public BasicDataSource dataSource() throws URISyntaxException {
//	        URI dbUri = new URI(System.getenv("DATABASE_URL"));
//
//	        String username = dbUri.getUserInfo().split(":")[0];
//	        String password = dbUri.getUserInfo().split(":")[1];
//	        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + dbUri.getPath() + ":" + dbUri.getPort() + dbUri.getPath();
//
//	        BasicDataSource basicDataSource = new BasicDataSource();
//	        basicDataSource.setUrl(dbUrl);
//	        basicDataSource.setUsername(username);
//	        basicDataSource.setPassword(password);
//
//	        return basicDataSource;
//	    }
	
	private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("hibernate.hbm2ddl.auto"));
        return properties;
    }
	
	@Bean
    public HibernateTransactionManager getTransactionManager() throws Exception {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

}
