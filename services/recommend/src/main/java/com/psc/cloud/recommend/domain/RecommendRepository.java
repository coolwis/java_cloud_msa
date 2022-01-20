package com.psc.cloud.recommend.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RecommendRepository extends CrudRepository<RecommendEntity, String>{
	@Transactional(readOnly =true)
	List<RecommendEntity> findByProductId(int productId);

}
