package com.patrick.Runners;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.common.collect.Lists;
import com.patrick.Runners.auth.AuthDaoService;
import com.patrick.Runners.auth.Role;
import com.patrick.Runners.auth.User;
import com.patrick.Runners.runner.Runner;
import com.patrick.Runners.runner.RunnerRepository;

@SpringBootApplication
public class RunnersApplication {

	private static PasswordEncoder passwordEncoder;

	public RunnersApplication(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public static RunnerRepository runnerRepository;

	public enum roles {
		ADMIN, CONTRIBUTOR
	}

	public static void main(String[] args) {
		SpringApplication.run(RunnersApplication.class, args);

		Role admin_role = new Role(roles.ADMIN.name());
		Set<Role> admin = new HashSet<Role>();
		admin.add(admin_role);

		Role contributor_role = new Role(roles.CONTRIBUTOR.name());
		Set<Role> contributor = new HashSet<Role>();
		contributor.add(contributor_role);

		//User admin_user = new User("admin.user",passwordEncoder.encode("password"), admin, true);

		//User admin_user2 = new User("administrator", passwordEncoder.encode("password"),admin, true);

		//User contributor_user = new User("chris",passwordEncoder.encode("password"), contributor, true);
		//User contributor_user2 = new User("contributor",passwordEncoder.encode("password"), contributor, true);

		//AuthDaoService.addRole(admin_role);
		//AuthDaoService.addRole(contributor_role);

		//AuthDaoService.addNewUser(admin_user);
		//AuthDaoService.addNewUser(contributor_user);
		//AuthDaoService.addNewUser(contributor_user2);

		//AuthDaoService.addNewUser(admin_user2);

	}

	/*@Bean
	public CommandLineRunner demo(RunnerRepository runnerRepository){
		return (args) -> {
			//runnerRepository.save(new Runner("Patrick3", "hanley", "instagramName"));
		};
	}*/


	public static List<Role> createRoles(){
		List<Role> roles = Lists.newArrayList(
				new Role("CONTRIBUTOR"),
				new Role("ADMIN")
		);
		return roles;
	}


}
