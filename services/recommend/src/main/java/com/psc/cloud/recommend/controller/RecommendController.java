package com.psc.cloud.recommend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.mongodb.DuplicateKeyException;
import com.psc.cloud.api.controller.RecommendControllerInterface;
import com.psc.cloud.api.dto.Recommend;
import com.psc.cloud.api.exception.InvalidInputException;
import com.psc.cloud.api.exception.NotFoundException;
import com.psc.cloud.recommend.domain.RecommendEntity;
import com.psc.cloud.recommend.domain.RecommendRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
public class RecommendController implements RecommendControllerInterface {

	private final RecommendRepository repository;
	private final RecommendMapper mapper;

	@Override
	public Recommend createRecommend(Recommend body) {
		try {
			RecommendEntity entity = mapper.dtoToEntity(body);
			RecommendEntity newEntity = repository.save(entity);
			log.debug("#createRecommend#############");
			log.debug(newEntity.toString());
			return mapper.entityToDto(newEntity);

		} catch (DuplicateKeyException dke) {
			throw new InvalidInputException("Duplicate Recommend Id:" + body.getRecommendId());
		}
	}

	@Override
	public List<Recommend> getRecommends(int productId) {

		if (productId < 1) throw new InvalidInputException("Invalid productId:" + productId);
		List<RecommendEntity> entity = repository.findByProductId(productId);
				
		List<Recommend> recommends = mapper.entityListToApiList(entity);
		return recommends;
	}

	@Override
	public void deleteRecommends(int productId) {

		log.debug("deleteRecommend productId: {}", productId);
		repository.findByProductId(productId).forEach(e -> repository.delete(e));
	}

//	@Override
//	public List<Recommend> getRecommends(int productId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void deleteRecommends(int productId) {
//		// TODO Auto-generated method stub
//		
//	}

}
