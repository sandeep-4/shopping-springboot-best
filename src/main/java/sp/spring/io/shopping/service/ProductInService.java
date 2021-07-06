package sp.spring.io.shopping.service;

import sp.spring.io.shopping.entity.ProductInOrder;
import sp.spring.io.shopping.entity.User;

public interface ProductInService {

	void update(String itemId, Integer quantity, User user);

	ProductInOrder findOne(String itemId, User user);
}
