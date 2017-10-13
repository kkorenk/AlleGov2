package allego;

import allego.models.User;
import allego.security.Role;
import allego.security.UserRole;
import allego.services.UserService;
import allego.utility.SecurityUtility;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import sun.tools.jar.CommandLine;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class WebApplication  {



	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}



}
/*
@Autowired
	private UserService userService;
implements CommandLineRunner
@Override
public void run(String... args) throws Exception {
	User user = new User();
	user.setFirstName("Kamil");
	user.setLastName("K");
	user.setUsername("korek");
	user.setPassword(SecurityUtility.passwordEncoder().encode("p"));
	user.setEmail("korekgw@wp.pl");
	Set<UserRole> userRoleSet = new HashSet<>();
	Role role = new Role();
	role.setRoleId(1);
	role.setName("ROLE_USER");
	userRoleSet.add(new UserRole(user,role));

	userService.createUser(user,userRoleSet);
}
*/