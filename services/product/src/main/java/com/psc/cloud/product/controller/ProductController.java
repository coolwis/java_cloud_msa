package com.psc.cloud.product.controller;

import org.springframework.web.bind.annotation.RestController;

import com.mongodb.DuplicateKeyException;
import com.psc.cloud.api.controller.ProductControllerInterface;
import com.psc.cloud.api.dto.Product;
import com.psc.cloud.api.exception.InvalidInputException;
import com.psc.cloud.api.exception.NotFoundException;
import com.psc.cloud.product.domain.ProductEntity;
import com.psc.cloud.product.domain.ProductRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
public class ProductController implements ProductControllerInterface {

	private final ProductRepository repository;
	private final ProductMapper mapper;

	@Override
	public Product createProduct(Product body) {
		try {
			ProductEntity entity = mapper.dtoToEntity(body);
			ProductEntity newEntity = repository.save(entity);
			log.debug("#createProduct#############");
			log.debug(newEntity.toString());
			return mapper.entityToDto(newEntity);

		} catch (DuplicateKeyException dke) {
			throw new InvalidInputException("Duplicate Product Id:" + body.getProductId());
		}
	}

	@Override
	public Product getProduct(int productId) {

		if (productId < 1)
			throw new InvalidInputException("Invalid ProductId:" + productId);
		ProductEntity entity = repository.findByProductId(productId)
				.orElseThrow(() -> new NotFoundException("no productId:" + productId));
		Product product = mapper.entityToDto(entity);
		return product;
	}

	@Override
	public void deleteProduct(int productId) {

		log.debug("deleteProduct: {}", productId);
		repository.findByProductId(productId).ifPresent(e -> repository.delete(e));
	}

}
