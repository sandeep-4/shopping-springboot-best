package sp.spring.io.shopping.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import sp.spring.io.shopping.entity.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

	List<ProductCategory> findByCategoryTypeInOrderByCategoryTypeAsc(List<Integer> categoryTypes);

	List<ProductCategory> findAllByOrderByCategoryType();

	ProductCategory findByCategoryType(Integer categoryType);
}
