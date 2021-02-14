package com.patrick.Runners;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.google.common.collect.Lists;
import com.patrick.Runners.auth.Role;
import com.patrick.Runners.auth.User;

@SpringBootApplication
public class RunnersApplication extends SpringBootServletInitializer {

	private static PasswordEncoder passwordEncoder;

	public RunnersApplication(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public enum roles{
		ADMIN, CONTRIBUTOR
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(RunnersApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(RunnersApplication.class, args);

		Role admin_role = new Role(roles.ADMIN.name());
		Role contributor_role = new Role(roles.CONTRIBUTOR.name());

		Set<Role> admin = new HashSet<Role>();
		admin.add(admin_role);

		Set<Role> contributor = new HashSet<Role>();
		contributor.add(contributor_role);

		/*User admin_user = new User("admin.user",passwordEncoder.encode("password"), admin, true);
		User contributor_user = new User("chris",passwordEncoder.encode("password"), contributor, true);
		User contributor_user2 = new User("contributor",passwordEncoder.encode("password"), contributor, true);

		AuthDaoService.addRole(admin_role);
		AuthDaoService.addRole(contributor_role);

		AuthDaoService.addNewUser(admin_user);
		AuthDaoService.addNewUser(contributor_user);
		AuthDaoService.addNewUser(contributor_user2);*/





	}




	public static List<User> createUserObjects(List<Role> roles){

		Set<Role> student = new HashSet<Role>();
		student.add(roles.get(0));

		Set<Role> admin = new HashSet<Role>();
		admin.add(roles.get(1));

		Set<Role> trainee = new HashSet<Role>();
		trainee.add(roles.get(2));

		/*List<User> applicationUsers = Lists.newArrayList(
				new User("SallyStudent",
						passwordEncoder.encode("password"),
						student),
				new User("AlexAdmin",
						passwordEncoder.encode("password"),
						admin),
				new User("TomTrainee",
						passwordEncoder.encode("password"),
						student),
				//ADMINTRAINEE.getGrantedAuthorities()),
				new User("jonsnow",
						passwordEncoder.encode("password"),
						admin)
				//ADMINTRAINEE.getGrantedAuthorities())
		);*/

		return null;
	}

	public static List<Role> createRoles(){
		List<Role> roles = Lists.newArrayList(
				new Role("CONTRIBUTOR"),
				new Role("ADMIN")
		);
		return roles;
	}



}
