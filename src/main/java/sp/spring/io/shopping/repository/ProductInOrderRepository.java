package sp.spring.io.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sp.spring.io.shopping.entity.ProductInOrder;

public interface ProductInOrderRepository extends JpaRepository<ProductInOrder, Long> {

}
