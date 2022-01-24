package com.psc.cloud.review.controller;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.psc.cloud.api.dto.Review;
import com.psc.cloud.review.domain.ReviewEntity;

@Mapper(componentModel="spring")
public interface ReviewMapper {

	@Mappings({
			@Mapping(target="serviceAddress", ignore = true)
	})
	Review entityToDto(ReviewEntity entity);
	
	@Mappings({
		@Mapping(target="id", ignore=true),
		@Mapping(target="version", ignore=true)
	})
	ReviewEntity dtoToEntity(Review dto);
	

	List<Review> entityListToDtoList(List<ReviewEntity> entity);
	
	List<ReviewEntity> dtoListToEntityList(List<Review> dto);
	
}
