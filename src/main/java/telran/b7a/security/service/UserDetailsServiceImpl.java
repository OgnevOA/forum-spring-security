package telran.b7a.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import telran.b7a.accounting.dao.AccountingMongoRepository;
import telran.b7a.accounting.model.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	AccountingMongoRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User userAccount = repository.findById(username).orElseThrow(() -> new UsernameNotFoundException(username));
		String[] roles = userAccount.getRoles()
									.stream()
									.map(r -> "ROLE_" + r.toUpperCase())
									.toArray(String[]::new);
		return new org.springframework.security.core.userdetails.User(username, userAccount.getPassword(), AuthorityUtils.createAuthorityList(roles));
	}

}
