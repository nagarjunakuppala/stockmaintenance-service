package org.uhcl.edu.stockmaintenance.stockmaintenanceservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableEurekaClient
@EnableAutoConfiguration
//@EnableJpaRepositories(basePackages = "org.uhcl.edu.stockmaintenance.stockmaintenanceservice.repository")
public class StockmaintenanceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockmaintenanceServiceApplication.class, args);
	}
}
