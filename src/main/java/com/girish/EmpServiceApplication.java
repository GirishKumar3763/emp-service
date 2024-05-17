package com.girish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.core.env.Environment;

@EnableDiscoveryClient
@SpringBootApplication
public class EmpServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmpServiceApplication.class, args);
	}
	
	@Autowired
	public void setEnv(Environment e)
	{
		System.out.println(e.getProperty("empservice.message"));
	}

}
