package allego;

import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;

@SpringBootApplication
public class WebApplication{

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

}
