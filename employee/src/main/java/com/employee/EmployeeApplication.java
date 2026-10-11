package com.employee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.employee",
        "config",
        "controller",
        "service",
        "enums",
        "exception",
        "client"
})
@EnableJpaRepositories(basePackages = "respository")
@EntityScan(basePackages = "model.entity")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "client")
public class EmployeeApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeApplication.class, args);
    }
}