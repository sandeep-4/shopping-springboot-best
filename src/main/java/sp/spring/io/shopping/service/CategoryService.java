package sp.spring.io.shopping.service;

import java.util.List;

import sp.spring.io.shopping.entity.ProductCategory;

public interface CategoryService {

	List<ProductCategory> findAll();

	ProductCategory findByCategoryType(Integer categoryType);

	List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

	ProductCategory save(ProductCategory productCategory);
}
