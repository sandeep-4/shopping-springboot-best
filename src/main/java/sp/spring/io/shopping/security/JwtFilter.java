package sp.spring.io.shopping.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import sp.spring.io.shopping.entity.User;
import sp.spring.io.shopping.service.UserService;

@Component
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	JwtProvider jwtProvider;

	@Autowired
	private UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String jwt = getToken(request);
		if (jwt != null && jwtProvider.validate(jwt)) {
			try {
				String userAccount = jwtProvider.getUserAccount(jwt);
				User user = userService.findOne(userAccount);
				SimpleGrantedAuthority sga = new SimpleGrantedAuthority(user.getRole());
				ArrayList<SimpleGrantedAuthority> list = new ArrayList<>();
				list.add(sga);
				UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getEmail(),
						null, list);
				SecurityContextHolder.getContext().setAuthentication(auth);

			} catch (Exception e) {
				logger.error("exrror filter jwt");
			}
			filterChain.doFilter(request, response);
		}

	}

	public String getToken(HttpServletRequest request) {
		String authHeader = request.getHeader("Authorization");
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.replace("Bearer ", "");
		}
		return null;

	}
}
