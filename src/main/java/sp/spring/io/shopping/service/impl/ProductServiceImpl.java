package sp.spring.io.shopping.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sp.spring.io.shopping.entity.ProductInfo;
import sp.spring.io.shopping.enums.ProductStatusEnum;
import sp.spring.io.shopping.enums.ResultEnum;
import sp.spring.io.shopping.exception.MyException;
import sp.spring.io.shopping.repository.ProductInfoRepository;
import sp.spring.io.shopping.service.CategoryService;
import sp.spring.io.shopping.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductInfoRepository productInfoRepository;

	@Autowired
	CategoryService categoryService;

	@Override
	public ProductInfo findOne(String productId) {
		return productInfoRepository.findByProductId(productId);
	}

	@Override
	public Page<ProductInfo> findUpAll(Pageable pageable) {
		return productInfoRepository.findAllByProductStatusOrderByProductIdAsc(ProductStatusEnum.UP.getcode(),
				pageable);
	}

	@Override
	public Page<ProductInfo> findAll(Pageable pageable) {
		return productInfoRepository.findAllByOrderByProductId(pageable);
	}

	@Override
	public Page<ProductInfo> findAllInCategory(Integer categoryType, Pageable pageable) {
		return productInfoRepository.findAllByCategoryTypeOrderByProductIdAsc(categoryType, pageable);
	}

	@Override
	@Transactional
	public void increaseStock(String productId, int amount) {
		ProductInfo productInfo = findOne(productId);
		if (productInfo == null)
			throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);
		int update = productInfo.getProductStock() + amount;
		productInfo.setProductStock(update);
		productInfoRepository.save(productInfo);
	}

	@Override
	@Transactional
	public void decreaseStock(String productId, int amount) {
		ProductInfo productInfo = findOne(productId);
		if (productInfo == null)
			throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);
		int update = productInfo.getProductStock() + amount;
		productInfo.setProductStock(update);
		productInfoRepository.save(productInfo);

	}

	@Override
	@Transactional
	public ProductInfo offSale(String productId) {
		ProductInfo productInfo = findOne(productId);
		if (productInfo == null)
			throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);
		if (productInfo.getProductStatus().equals(ProductStatusEnum.DOWN.getcode())) {
			throw new MyException(ResultEnum.PRODUCT_STATUS_ERROR);
		}
		productInfo.setProductStatus(ProductStatusEnum.DOWN.getcode());
		return productInfoRepository.save(productInfo);

	}

	@Override
	@Transactional
	public ProductInfo onSale(String productId) {
		ProductInfo productInfo = findOne(productId);
		if (productInfo == null)
			throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);
		if (productInfo.getProductStatus().equals(ProductStatusEnum.UP.getcode())) {
			throw new MyException(ResultEnum.PRODUCT_STATUS_ERROR);
		}
		productInfo.setProductStatus(ProductStatusEnum.UP.getcode());
		return productInfoRepository.save(productInfo);
	}

	@Override
	public ProductInfo update(ProductInfo productInfo) {

		return save(productInfo);
	}

	@Override
	public ProductInfo save(ProductInfo productInfo) {
		categoryService.findByCategoryType(productInfo.getCategoryType());
		if (productInfo.getProductStatus() > 1) {
			throw new MyException(ResultEnum.PRODUCT_STATUS_ERROR);
		}
		return productInfoRepository.save(productInfo);
	}

	@Override
	public void delete(String productId) {
		ProductInfo productInfo = productInfoRepository.findByProductId(productId);
		if (productInfo == null)
			throw new MyException(ResultEnum.PRODUCT_NOT_EXIST);
		productInfoRepository.delete(productInfo);
	}

}
