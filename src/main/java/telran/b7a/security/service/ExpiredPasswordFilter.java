package telran.b7a.security.service;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDate;

import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.b7a.accounting.dao.AccountingMongoRepository;
import telran.b7a.accounting.model.User;

@Service
public class ExpiredPasswordFilter extends GenericFilter {

	/**
	 * 
	 */
	private static final long serialVersionUID = 436544739689313508L;
	AccountingMongoRepository reopsitory;

	@Autowired
	public ExpiredPasswordFilter(AccountingMongoRepository reopsitory) {
		this.reopsitory = reopsitory;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
			throws ServletException, IOException {
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpServletRequest request = (HttpServletRequest) req;
		Principal principal = request.getUserPrincipal();
		if (principal != null && checkEndPoint(request.getMethod(), request.getServletPath())) {
			User userAccount = reopsitory.findById(principal.getName()).get();
			if (userAccount.getPasswordExpDate().isBefore(LocalDate.now())) {
				response.sendError(403, "Password expored");
				return;
			}
		}
		filterChain.doFilter(request, response);

	}

	private boolean checkEndPoint(String method, String path) {
		return !("Put".equalsIgnoreCase(method) && path.matches("/account/password/?"));
	}

}
