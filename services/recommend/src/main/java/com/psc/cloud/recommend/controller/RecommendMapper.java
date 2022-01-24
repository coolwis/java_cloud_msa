package com.psc.cloud.recommend.controller;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.psc.cloud.api.dto.Recommend;
import com.psc.cloud.recommend.domain.RecommendEntity;

@Mapper(componentModel="spring")
public interface RecommendMapper {

	@Mappings({
			@Mapping(target="serviceAddress", ignore = true)
	})
	Recommend entityToDto(RecommendEntity entity);
	
	@Mappings({
		@Mapping(target="id", ignore=true),
		@Mapping(target="version", ignore=true)
	})
	RecommendEntity dtoToEntity(Recommend dto);
	

	List<Recommend> entityListToApiList(List<RecommendEntity> entity);
	
	List<RecommendEntity> dtoListToEntityList(List<Recommend> dto);
	
}
