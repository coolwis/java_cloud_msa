package com.psc.cloud.review.controller;

import java.util.List;

import com.psc.cloud.api.util.ServiceUtil;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.RestController;

import com.psc.cloud.api.controller.ReviewControllerInterface;
import com.psc.cloud.api.dto.Review;
import com.psc.cloud.api.exception.InvalidInputException;
import com.psc.cloud.review.domain.ReviewEntity;
import com.psc.cloud.review.domain.ReviewRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
public class ReviewController implements ReviewControllerInterface {

	private final ReviewRepository repository;
	private final ReviewMapper mapper;
	private final ServiceUtil serviceUtil ;

	@Override
	public Review createReview(Review body) {
		try {
			ReviewEntity entity = mapper.dtoToEntity(body);
			ReviewEntity newEntity = repository.save(entity);
			log.debug("#createReview#############");
			log.debug(newEntity.toString());
			return mapper.entityToDto(newEntity);

		} catch (DuplicateKeyException dke) {
			throw new InvalidInputException("Duplicate Review Id:" + body.getReviewId());
		}
	}

	@Override
	public List<Review> getReviews(int productId) {

		if (productId < 1) throw new InvalidInputException("Invalid productId:" + productId);
		List<ReviewEntity> entity = repository.findByProductId(productId);
				
		List<Review> reviews = mapper.entityListToDtoList(entity);
		log.debug("getReviews: {}" , productId);
		if(reviews !=null && reviews.size() > 0) {
			reviews.get(0).setServiceAddress(serviceUtil.getServiceAddress());
		}

		return reviews;
	}

	@Override
	public void deleteReviews(int productId) {

		log.debug("deleteReview productId: {}", productId);
		repository.findByProductId(productId).forEach(e -> repository.delete(e));
	}

//	@Override
//	public List<Review> getReviews(int productId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void deleteReviews(int productId) {
//		// TODO Auto-generated method stub
//		
//	}

}
