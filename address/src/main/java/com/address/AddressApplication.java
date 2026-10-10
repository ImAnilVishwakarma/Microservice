package com.address;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
                "controller",
                "service",
                "config",
                "exception",
                "com.address"
})
@EnableJpaRepositories(basePackages = "respository")
@EntityScan(basePackages = "model.entity")
@EnableDiscoveryClient
public class AddressApplication {

    public static void main(String[] args) {
        SpringApplication.run(AddressApplication.class, args);
    }
}
