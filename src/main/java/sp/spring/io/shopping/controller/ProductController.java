package sp.spring.io.shopping.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sp.spring.io.shopping.entity.ProductInfo;
import sp.spring.io.shopping.service.ProductService;

@RestController
@CrossOrigin
public class ProductController {

	@Autowired
	private ProductService productService;

//	@Autowired
//	private CategoryService categoryService;

	@GetMapping("/product")
	public Page<ProductInfo> findAll(@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "page", defaultValue = "3") Integer size) {
		PageRequest request = PageRequest.of(page, size);
		return productService.findAll(request);
	}

	@GetMapping("/product/{productId}")
	public ProductInfo showOneProduct(@PathVariable("productId") String productId) {
		return productService.findOne(productId);
	}

	@PostMapping("/seller/product/new")
	public ResponseEntity<?> createProduct(@Valid @RequestBody ProductInfo product, BindingResult bindingResult) {
		ProductInfo productExist = productService.findOne(product.getProductId());
		if (productExist != null) {
			bindingResult.rejectValue("productId", "error.product", "product code exist");
		}
		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(bindingResult);
		}
		ProductInfo saved = productService.save(product);
		return ResponseEntity.ok(saved);
	}

	@PutMapping("/seller/product/{id}/edit")
	public ResponseEntity<?> updateProduct(@PathVariable("id") String productId,
			@Valid @RequestBody ProductInfo product, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return ResponseEntity.badRequest().body(bindingResult);
		}
		if (!productId.equals(product.getProductId())) {
			return ResponseEntity.badRequest().body("Id didnt match");
		}
		ProductInfo edit = productService.update(product);
		return ResponseEntity.ok(edit);
	}

	@DeleteMapping("/seller/product/{id}/delete")
	public ResponseEntity<?> delete(@PathVariable("id") String productId) {
		productService.delete(productId);
		return ResponseEntity.ok().build();
	}

}
