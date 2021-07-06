package sp.spring.io.shopping.service.impl;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sp.spring.io.shopping.entity.Cart;
import sp.spring.io.shopping.entity.User;
import sp.spring.io.shopping.enums.ResultEnum;
import sp.spring.io.shopping.exception.MyException;
import sp.spring.io.shopping.repository.CartRepository;
import sp.spring.io.shopping.repository.UserRepository;
import sp.spring.io.shopping.service.UserService;

@Service
@DependsOn("passwordEncoder")
public class UserServiceImpl implements UserService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CartRepository cartRepository;

	@Override
	public User findOne(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public Collection<User> findByRole(String role) {
		return userRepository.findAllByRole(role);
	}

	@Override
	@Transactional
	public User save(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		try {
			User savedUser = userRepository.save(user);
			// cart
			Cart savedCart = cartRepository.save(new Cart(savedUser));
			savedUser.setCart(savedCart);
			return userRepository.save(savedUser);
		} catch (Exception e) {

			throw new MyException(ResultEnum.VALID_ERROR);
		}
	}

	@Override
	@Transactional
	public User update(User user) {
		User oldUser = userRepository.findByEmail(user.getEmail());
		oldUser.setPassword(passwordEncoder.encode(user.getPassword()));
		oldUser.setName(user.getName());
		oldUser.setPhone(user.getPhone());
		oldUser.setAddress(user.getAddress());
		return userRepository.save(user);

	}

}
