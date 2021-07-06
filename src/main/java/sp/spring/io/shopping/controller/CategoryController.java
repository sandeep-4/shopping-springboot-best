package sp.spring.io.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.var;
import sp.spring.io.shopping.entity.ProductCategory;
import sp.spring.io.shopping.entity.ProductInfo;
import sp.spring.io.shopping.entity.response.CategoryPage;
import sp.spring.io.shopping.service.CategoryService;
import sp.spring.io.shopping.service.ProductService;

@RestController
@CrossOrigin
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ProductService productService;

	@GetMapping("/category/{type}")
	public CategoryPage showOne(@PathVariable("type") Integer categoryType,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "size", defaultValue = "3") Integer size) {

		ProductCategory cate = categoryService.findByCategoryType(categoryType);
		PageRequest request = PageRequest.of(page, size);
		Page<ProductInfo> productInCategory = productService.findAllInCategory(categoryType, request);
		var tmp = new CategoryPage("", productInCategory);
		tmp.setCategory(cate.getCategoryName());
		return tmp;

	}

}
