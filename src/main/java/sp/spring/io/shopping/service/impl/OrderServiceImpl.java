package sp.spring.io.shopping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sp.spring.io.shopping.entity.OrderMain;
import sp.spring.io.shopping.entity.ProductInOrder;
import sp.spring.io.shopping.entity.ProductInfo;
import sp.spring.io.shopping.enums.OrderStatusEnum;
import sp.spring.io.shopping.enums.ResultEnum;
import sp.spring.io.shopping.exception.MyException;
import sp.spring.io.shopping.repository.OrderRepository;
import sp.spring.io.shopping.repository.ProductInOrderRepository;
import sp.spring.io.shopping.repository.ProductInfoRepository;
import sp.spring.io.shopping.repository.UserRepository;
import sp.spring.io.shopping.service.OrderService;
import sp.spring.io.shopping.service.ProductService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	UserRepository userRepository;
	@Autowired
	ProductInfoRepository productInfoRepository;

	@Autowired
	ProductInOrderRepository productInOrderRepository;

	@Override
	public Page<OrderMain> findAll(Pageable pageable) {
		return orderRepository.findAllByOrderByOrderStatusAscCreateTimeDesc(pageable);
	}

	@Override
	public Page<OrderMain> findByStatus(Integer status, Pageable pageable) {
		return orderRepository.findAllByOrderStatusOrderByCreateTimeDesc(status, pageable);
	}

	@Override
	public Page<OrderMain> findByBuyerEmail(String email, Pageable pageable) {
		return orderRepository.findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(email, pageable);
	}

	@Override
	public Page<OrderMain> findByBuyerPhone(String phone, Pageable pageable) {
		return orderRepository.findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(phone, pageable);
	}

	@Override
	public OrderMain findOne(Long orderId) {

		OrderMain order = orderRepository.findByOrderId(orderId);
		if (order == null) {
			throw new MyException(ResultEnum.ORDER_NOT_FOUND);
		}

		return order;
	}

	@Override
	public OrderMain finish(Long orderId) {
		OrderMain orderMain = findOne(orderId);
		if (!orderMain.getOrderStatus().equals(OrderStatusEnum.FINISHED.getcode())) {
			throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
		}
		orderMain.setOrderStatus(OrderStatusEnum.FINISHED.getcode());
		orderRepository.save(orderMain);
		return orderRepository.findByOrderId(orderId);

	}

	@Override
	public OrderMain cancel(Long orderId) {
		OrderMain orderMain = findOne(orderId);
		if (!orderMain.getOrderStatus().equals(OrderStatusEnum.NEW.getcode())) {
			throw new MyException(ResultEnum.ORDER_STATUS_ERROR);
		}
		orderMain.setOrderStatus(OrderStatusEnum.CANCELED.getcode());
		orderRepository.save(orderMain);

		// stock restore
		Iterable<ProductInOrder> products = orderMain.getProducts();
		for (ProductInOrder productInOrder : products) {
			ProductInfo productInfo = productInfoRepository.findByProductId(productInOrder.getProductId());
			if (productInfo != null) {
				productService.increaseStock(productInOrder.getProductId(), productInOrder.getCount());
			}
		}

		return orderRepository.findByOrderId(orderId);
	}

}
