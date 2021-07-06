package sp.spring.io.shopping.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sp.spring.io.shopping.entity.Cart;
import sp.spring.io.shopping.entity.ProductInOrder;
import sp.spring.io.shopping.entity.ProductInfo;
import sp.spring.io.shopping.entity.User;
import sp.spring.io.shopping.form.ItemForm;
import sp.spring.io.shopping.service.CartService;
import sp.spring.io.shopping.service.ProductInService;
import sp.spring.io.shopping.service.ProductService;
import sp.spring.io.shopping.service.UserService;

@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {

	@Autowired
	private CartService cartService;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductInService productInOrderService;

	@PostMapping("")
	public ResponseEntity<?> mergerCart(@RequestBody Collection<ProductInOrder> productInOrder, Principal principal) {
		User user = userService.findOne(principal.getName());
		try {
			cartService.mergeLocalCart(productInOrder, user);
		} catch (Exception e) {
			ResponseEntity.badRequest().body("Merge not happening");
		}
		Cart cart = cartService.getCart(user);
		return ResponseEntity.ok(cart);
	}

	@GetMapping("")
	public ResponseEntity<?> getCart(Principal principal) {
		User user = userService.findOne(principal.getName());
		Cart cart = cartService.getCart(user);
		return ResponseEntity.ok(cart);
	}

	@PostMapping("/add")
	public boolean addToCart(@RequestBody ItemForm form, Principal principal) {
		ProductInfo productInfo = productService.findOne(form.getProductId());
		try {
			mergerCart(Collections.singleton(new ProductInOrder(productInfo, form.getQuantity())), principal);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@PutMapping("/{itemId}")
	public ProductInOrder modifyItem(@PathVariable("itemId") String itemId, @RequestBody Integer quantity,
			Principal principal) {
		User user = userService.findOne(principal.getName());
		productInOrderService.update(itemId, quantity, user);
		return productInOrderService.findOne(itemId, user);
	}

	@DeleteMapping("/{itemId}")
	public void deleteItem(@PathVariable("itemId") String itemId, Principal principal) {
		User user = userService.findOne(principal.getName());
		cartService.delete(itemId, user);
	}

	@PostMapping("/checkout")
	public ResponseEntity<?> checkoutProduct(Principal principal) {
		User user = userService.findOne(principal.getName());
		cartService.checkout(user);
		return null;
	}

}
