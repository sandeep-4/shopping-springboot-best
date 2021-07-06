package sp.spring.io.shopping.service;

import java.util.Collection;

import sp.spring.io.shopping.entity.User;

public interface UserService {

	User findOne(String email);
	
	Collection<User> findByRole(String role);
	
	User save(User user);
	
	User update(User user);
	
	
	
}
