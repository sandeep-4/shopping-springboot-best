package sp.spring.io.shopping.service;

import java.util.Collection;

import sp.spring.io.shopping.entity.Cart;
import sp.spring.io.shopping.entity.ProductInOrder;
import sp.spring.io.shopping.entity.User;

public interface CartService {

	Cart getCart(User user);
	
	void mergeLocalCart(Collection<ProductInOrder> productsInOrder,User user);
	
	void delete(String itemId,User user);
	
	void checkout(User user);
}
