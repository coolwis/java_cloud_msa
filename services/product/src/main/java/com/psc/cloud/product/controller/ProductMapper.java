package com.psc.cloud.product.controller;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.psc.cloud.api.dto.Product;
import com.psc.cloud.product.domain.ProductEntity;

@Mapper(componentModel="spring")
public interface ProductMapper {

	@Mappings({
			@Mapping(target="serviceAddress", ignore = true)
	})
	Product entityToDto(ProductEntity entity);
	
	@Mappings({
		@Mapping(target="id", ignore=true),
		@Mapping(target="version", ignore=true)
	})
	ProductEntity dtoToEntity(Product api);
}
