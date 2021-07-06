package sp.spring.io.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sp.spring.io.shopping.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{

}
