package telran.b7a.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import telran.b7a.accounting.dao.AccountingMongoRepository;
import telran.b7a.forum.dao.ForumMongoRepository;
import telran.b7a.forum.model.Post;

@Service("customSecurity")
public class CustomWebSecurity {

	ForumMongoRepository postRepo;
	AccountingMongoRepository userRepo;

	@Autowired
	public CustomWebSecurity(ForumMongoRepository postRepo, AccountingMongoRepository userRepo) {
		this.postRepo = postRepo;
		this.userRepo = userRepo;
	}

	public boolean checkPostAuthority(String postId, String userName) {
		Post post = postRepo.findById(postId).orElse(null);
		return post != null && userName.equals(post.getAuthor());
	}

	public boolean isPasswordActual(Authentication authentication) {
		CustomSecurityUser user = (CustomSecurityUser) authentication.getPrincipal();
		return user.isPasswordActual();
	}
}





