package sp.spring.io.shopping.service.impl;

import java.util.concurrent.atomic.AtomicReference;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.var;
import sp.spring.io.shopping.entity.ProductInOrder;
import sp.spring.io.shopping.entity.User;
import sp.spring.io.shopping.repository.ProductInOrderRepository;
import sp.spring.io.shopping.service.ProductInService;

@Service
public class ProductInOrderServiceImpl implements ProductInService {

	@Autowired
	private ProductInOrderRepository productInRepository;

	@Override
	@Transactional
	public void update(String itemId, Integer quantity, User user) {

		var prod = user.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProductId())).findFirst();
		prod.ifPresent(productInOrder -> {
			productInOrder.setCount(quantity);
			productInRepository.save(productInOrder);
		});

	}

	@Override
	public ProductInOrder findOne(String itemId, User user) {
		var prod = user.getCart().getProducts().stream().filter(e -> itemId.equals(e.getProductId())).findFirst();

		AtomicReference<ProductInOrder> res = new AtomicReference<>();
		prod.ifPresent(res::set);
		return res.get();

	}

}
