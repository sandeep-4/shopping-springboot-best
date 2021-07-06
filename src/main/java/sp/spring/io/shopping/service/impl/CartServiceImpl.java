package sp.spring.io.shopping.service.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.var;
import sp.spring.io.shopping.entity.Cart;
import sp.spring.io.shopping.entity.OrderMain;
import sp.spring.io.shopping.entity.ProductInOrder;
import sp.spring.io.shopping.entity.User;
import sp.spring.io.shopping.repository.CartRepository;
import sp.spring.io.shopping.repository.OrderRepository;
import sp.spring.io.shopping.repository.ProductInOrderRepository;
import sp.spring.io.shopping.service.CartService;
import sp.spring.io.shopping.service.ProductService;

@Service
public class CartServiceImpl implements CartService {

	@Autowired
	private CartRepository cartRepository;

	@Autowired
	private ProductService productSerive;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductInOrderRepository productInOrderRepository;

//	@Autowired
//	private UserService userService;

	@Override
	public Cart getCart(User user) {
		return user.getCart();
	}

	@Override
	@Transactional
	public void mergeLocalCart(Collection<ProductInOrder> productsInOrder, User user) {
		Cart finalCart = user.getCart();
		productsInOrder.forEach(inOrder -> {
			Set<ProductInOrder> set = finalCart.getProducts();
			Optional<ProductInOrder> old = set.stream().filter(e -> e.getProductId().equals(inOrder.getProductId()))
					.findFirst();
			ProductInOrder prod;
			if (old.isPresent()) {
				prod = old.get();
				prod.setCount(inOrder.getCount() + prod.getCount());
			} else {
				prod = inOrder;
				prod.setCart(finalCart);
				finalCart.getProducts().add(prod);
			}
		});
		cartRepository.save(finalCart);

	}

	@Override
	@Transactional
	public void delete(String itemId, User user) {
		var op = user.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProductId())).findFirst();
		op.ifPresent(productInOrder -> {
			productInOrder.setCart(null);
			productInOrderRepository.deleteById(productInOrder.getId());
		});
	}

	@Override
	public void checkout(User user) {
		OrderMain order = new OrderMain(user);
		orderRepository.save(order);

		user.getCart().getProducts().forEach(productInOrder -> {
			productInOrder.setCart(null);
			productInOrder.setOrderMain(order);
			productSerive.decreaseStock(productInOrder.getProductId(), productInOrder.getCount());
			productInOrderRepository.save(productInOrder);
		});
	}

}
