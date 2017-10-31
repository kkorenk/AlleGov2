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
@SpringBootApplication
public class WebApplication  implements CommandLineRunner{

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setEnabled(true);
		user.setFirstName("Kamil");
		user.setLastName("K");
		user.setUsername("korek");
		user.setPassword(SecurityUtility.passwordEncoder().encode("p"));
		user.setEmail("korekgw@wp.pl");
		Set<UserRole> userRoleSet = new HashSet<>();
		Role role = new Role();
		role.setRoleId(2);
		role.setName("ROLE_ADMIN");
		userRoleSet.add(new UserRole(user, role));

		userService.createUser(user, userRoleSet);

		User user2 = new User();
		user2.setEnabled(true);
		user2.setFirstName("Kamil");
		user2.setLastName("K");
		user2.setUsername("user");
		user2.setPassword(SecurityUtility.passwordEncoder().encode("p"));
		user2.setEmail("korekgw2@wp.pl");
		Set<UserRole> userRoleSet2 = new HashSet<>();
		Role role2 = new Role();
		role2.setRoleId(1);
		role2.setName("ROLE_USER");
		userRoleSet2.add(new UserRole(user2, role2));

		userService.createUser(user2, userRoleSet2);
	}
}
*/
