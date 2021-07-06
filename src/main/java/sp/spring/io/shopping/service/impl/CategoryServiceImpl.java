package sp.spring.io.shopping.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sp.spring.io.shopping.entity.ProductCategory;
import sp.spring.io.shopping.enums.ResultEnum;
import sp.spring.io.shopping.exception.MyException;
import sp.spring.io.shopping.repository.ProductCategoryRepository;
import sp.spring.io.shopping.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Override
	public List<ProductCategory> findAll() {
		List<ProductCategory> res = productCategoryRepository.findAllByOrderByCategoryType();
		return res;

	}

	@Override
	public ProductCategory findByCategoryType(Integer categoryType) {
		ProductCategory res = productCategoryRepository.findByCategoryType(categoryType);
		if (res == null)
			throw new MyException(ResultEnum.CATEGORY_NOT_FOUND);
		return res;

	}

	@Override
	public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
		List<ProductCategory> res = productCategoryRepository
				.findByCategoryTypeInOrderByCategoryTypeAsc(categoryTypeList);
		return res;

	}

	@Override
	@Transactional
	public ProductCategory save(ProductCategory productCategory) {

		return productCategoryRepository.save(productCategory);
	}

}
