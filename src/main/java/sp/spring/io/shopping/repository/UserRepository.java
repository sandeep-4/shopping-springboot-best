package sp.spring.io.shopping.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import sp.spring.io.shopping.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

	User findByEmail(String email);

	Collection<User> findAllByRole(String role);

}
