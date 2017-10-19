package com.example.nt_task.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAutoConfiguration
@ComponentScan({"com.example.nt_task"})
@EntityScan({ "com.example.nt_task" })
@EnableJpaRepositories(basePackages = {"com.example.nt_task"})
@EnableScheduling
public class DemoApplication {

    public static void main(String[] args) {
	    SpringApplication.run(DemoApplication.class, args);
	}

    @Bean(destroyMethod = "shutdown")
    public Executor taskScheduler() {
        return Executors.newScheduledThreadPool(5);
    }
}
