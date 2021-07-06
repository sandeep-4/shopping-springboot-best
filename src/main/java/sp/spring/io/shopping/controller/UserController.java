package sp.spring.io.shopping.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sp.spring.io.shopping.entity.User;
import sp.spring.io.shopping.entity.request.LoginForm;
import sp.spring.io.shopping.entity.response.JwtResponse;
import sp.spring.io.shopping.security.JwtProvider;
import sp.spring.io.shopping.service.UserService;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginForm loginform) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginform.getUsername(), loginform.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtProvider.generate(authentication);

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			User user = userService.findOne(userDetails.getUsername());

			return ResponseEntity.ok(new JwtResponse(jwt, user.getEmail(), user.getName(), user.getRole()));

		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> save(@RequestBody User user) {
		try {
			User saveUser = userService.save(user);
			return ResponseEntity.ok(saveUser);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@PutMapping("/profile")
	public ResponseEntity<?> update(@RequestBody User user, Principal principal) {
		try {
			if (principal.getName().equals(user.getEmail())) {
				return ResponseEntity.ok(userService.update(user));
			} else {
				throw new IllegalAccessError();
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/profile/{email}")
	public ResponseEntity<?> getProfile(@PathVariable("email") String email, Principal principal) {
		if (!principal.getName().equals(email)) {
			throw new IllegalArgumentException();
		}
		return ResponseEntity.ok(userService.findOne(email));
	}

}
