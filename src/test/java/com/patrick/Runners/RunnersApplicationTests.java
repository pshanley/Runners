package com.patrick.Runners;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.context.support.StandardServletEnvironment;

import com.patrick.Runners.runner.RunnerRepository;

@SpringBootTest
class RunnersApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void getProfiles(){
		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(RunnersApplication.class);
		Environment environment = configurableApplicationContext.getEnvironment();
		String[] profiles = environment.getActiveProfiles();
		String activeProfile = profiles[0];
		System.out.println("This is the current profile: " + activeProfile);
		assert activeProfile.equals("dev");


	}

}
