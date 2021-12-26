package telran.b7a.security.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomSecurityUser extends User {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4558000688849995666L;
	boolean isPasswordActual;

	public CustomSecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities,
			boolean isPasswordActual) {
		super(username, password, authorities);
		this.isPasswordActual = isPasswordActual;
	}

}
