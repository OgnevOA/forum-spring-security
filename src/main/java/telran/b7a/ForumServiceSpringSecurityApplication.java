package telran.b7a;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import telran.b7a.accounting.dao.AccountingMongoRepository;
import telran.b7a.accounting.model.User;

@SpringBootApplication
public class ForumServiceSpringSecurityApplication implements CommandLineRunner {

	@Autowired
	AccountingMongoRepository repository;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ForumServiceSpringSecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (!repository.existsById("admin")) {
			String password = passwordEncoder.encode("admin");
			User user = new User("admin", password, "", "");
			user.addRole("USER");
			user.addRole("MODERATOR");
			user.addRole("ADMINISTRATOR");
			repository.save(user);
		}

	}
}
