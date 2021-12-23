package telran.b7a.security.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.b7a.accounting.dao.AccountingMongoRepository;
import telran.b7a.accounting.model.User;
import telran.b7a.forum.dao.ForumMongoRepository;
import telran.b7a.forum.model.Post;

@Service("customSecurity")
public class CustomWebSecurity {

	ForumMongoRepository repository;
	AccountingMongoRepository userRepo;

	@Autowired
	public CustomWebSecurity(ForumMongoRepository repository, AccountingMongoRepository userRepo) {
		this.repository = repository;
		this.userRepo = userRepo;
	}

	public boolean checkPostAuthority(String postId, String userName) {
		Post post = repository.findById(postId).orElse(null);
		return post != null && userName.equals(post.getAuthor());
	}
	
	public boolean isPasswordActual(String userName) {
		User user = userRepo.findById(userName).orElse(null);
		return user!= null && LocalDate.now().isBefore(user.getPasswordExpDate());
	}
}
