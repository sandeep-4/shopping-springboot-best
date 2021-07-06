package sp.spring.io.shopping.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sp.spring.io.shopping.entity.OrderMain;

@Repository
public interface OrderRepository extends JpaRepository<OrderMain, Integer> {

	OrderMain findByOrderId(Long orderId);

	Page<OrderMain> findAllByOrderStatusOrderByCreateTimeDesc(Integer orderStatus, Pageable pageable);

	Page<OrderMain> findAllByBuyerEmailOrderByOrderStatusAscCreateTimeDesc(String buyerEmail, Pageable pageable);

	Page<OrderMain> findAllByOrderByOrderStatusAscCreateTimeDesc(Pageable pageable);

	Page<OrderMain> findAllByBuyerPhoneOrderByOrderStatusAscCreateTimeDesc(String buyerPhone, Pageable pageable);

}
