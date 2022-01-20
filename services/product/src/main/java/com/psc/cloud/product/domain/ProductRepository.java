package com.psc.cloud.product.domain;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductRepository extends PagingAndSortingRepository<ProductEntity,String> {

	Optional<ProductEntity> findByProductId(int productId);
}
